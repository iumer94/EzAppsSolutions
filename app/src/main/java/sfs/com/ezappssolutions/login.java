package sfs.com.ezappssolutions;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity implements View.OnClickListener {

    TextView forgot;
    Button login,ok;
    ImageView iv_back;
    EditText et_email, et_password;
    String st_email, st_password;
    transparentdialog td;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();
        forgot = (TextView) findViewById(R.id.tv_forgot_pass);
        forgot.setOnClickListener(this);
        login=(Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        et_email=(EditText)findViewById(R.id.et_email);
        et_password =(EditText)findViewById(R.id.et_password);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_internet_dialog);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ok=(Button) dialog.findViewById(R.id.btn_right);
        ok.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        st_email=et_email.getText().toString();
        st_password=et_password.getText().toString();
        if(view.getId() == forgot.getId()){
            Intent i = new Intent(getApplication(), reset.class);
            startActivity(i);
        }
        else if(view.getId() == login.getId()){
            if(!Patterns.EMAIL_ADDRESS.matcher(st_email).matches())
            {
                et_email.setError("Enter Valid Email address");
            }
            loginUser();
        }
        else if(NetUtil.isNetworkAvailable(this) == false )
        {
            dialog.show();
        }
        else if(view.getId() == ok.getId())
        {

            dialog.dismiss();

        }
        else if(view.getId()==iv_back.getId())
        {
            finish();
        }
    }
    private void loginUser(){
        td = new transparentdialog(this, R.drawable.loading_blue);
        td.show();
        ParseUser.logInInBackground(st_email,st_password, new LogInCallback() {

            public void done(ParseUser User, ParseException e) {
                if (User != null) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("userName", st_email);
                    editor.putString("password", st_password);
                    editor.commit();
                    finish();
                    td.dismiss();
                    Intent i = new Intent(getApplication(), NavigationBar.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(login.this, "Username & Password not matched", Toast.LENGTH_SHORT).show();
                    td.dismiss();
                }

            }
        });
    }
}
