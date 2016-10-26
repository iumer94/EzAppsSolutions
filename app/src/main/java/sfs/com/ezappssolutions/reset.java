package sfs.com.ezappssolutions;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.parse.RequestPasswordResetCallback;

public class reset extends AppCompatActivity implements View.OnClickListener {
    TextView tv_call;
    EditText et_email;
    String st_email;
    Button send_mail;
    String phone = "1-818-555-2022";
    transparentdialog td;
    Dialog dialog;
    Button ok;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        et_email=(EditText)findViewById(R.id.et_email);
        et_email.setOnClickListener(this);
        tv_call = (TextView) findViewById(R.id.tv_call);
        tv_call.setOnClickListener(this);
        send_mail=(Button)findViewById(R.id.send_mail);
        send_mail.setOnClickListener(this);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_internet_dialog);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ok=(Button) dialog.findViewById(R.id.btn_right);
        ok.setOnClickListener(this);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        st_email=et_email.getText().toString();
        if (view.getId() == tv_call.getId()) {
            Intent i = new Intent(Intent.ACTION_DIAL);
            String p = "tel:1-818-555-2022";
            i.setData(Uri.parse(p));
            startActivity(i);

        }
        else if(view.getId()==send_mail.getId()){
            if(st_email.equals("")){
                Toast.makeText(this, "Please enter the valid Email", Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(st_email).matches())
            {
                et_email.setError("Enter Valid Email address");
            }else if(NetUtil.isNetworkAvailable(this) == false )
            {
                dialog.show();
            }
            else {
                sendMail(view);
            }

        }
        else if(view.getId() == ok.getId())
        {

            dialog.dismiss();

        }
        else if(view.getId()==iv_back.getId()){
            finish();
        }

    }
    public void sendMail(View view) {
//

        td = new transparentdialog(this, R.drawable.loading_blue);
        td.show();
        ParseUser.logInInBackground(st_email, String.valueOf(new LogInCallback() {

            public void done(ParseUser User, ParseException e) {
                if (User != null) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("email", st_email);
                    editor.commit();
                    finish();
                }
                else {
                    Toast.makeText(reset.this, "Your Email is not registerd", Toast.LENGTH_SHORT).show();
                }

            }
        }));
        ParseUser.requestPasswordResetInBackground(st_email, new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {

                    Toast.makeText(reset.this, "Mail Successfully sent.", Toast.LENGTH_SHORT).show();
                    td.dismiss();
                    // An email was successfully sent with reset instructions.
                }
                else {
                    Toast.makeText(reset.this, "Your Email is Not registerd", Toast.LENGTH_SHORT).show();
                    td.dismiss();
                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });

    }
}
