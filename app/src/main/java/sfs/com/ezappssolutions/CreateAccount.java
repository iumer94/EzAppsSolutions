package sfs.com.ezappssolutions;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import sfs.com.ezappssolutions.NetUtil;


import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateAccount extends AppCompatActivity  implements View.OnClickListener{
    TextView mainTitle;
    ImageView iv_back;
    Button next,ok;
    String titlle;
    EditText et_name,et_number,et_email,et_c_email,et_password,et_c_password;
    String st_name,st_number,st_email,st_c_email,st_password,st_c_password;
    transparentdialog td;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        mainTitle=(TextView)findViewById(R.id.tv_title);
        Intent intent= getIntent();
        titlle = getIntent().getStringExtra("tittle");
        mainTitle.setText(titlle);

        next=(Button)findViewById(R.id.next);
        next.setOnClickListener(this);



        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        et_name=(EditText)findViewById(R.id.et_name);
        et_number=(EditText)findViewById(R.id.et_number);

        et_email=(EditText)findViewById(R.id.et_email);
        et_c_email=(EditText)findViewById(R.id.et_c_email);

        et_password=(EditText)findViewById(R.id.et_password);
        et_c_password=(EditText)findViewById(R.id.et_c_password);

        //for phone number verification
        et_number.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            private boolean backspacingFlag = false;
            private boolean editedFlag = false;
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorComplement = s.length() - et_number.getSelectionStart();
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String phone = string.replaceAll("[^\\d]", "");
                if (!editedFlag) {


                    if (phone.length() >= 6 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        et_number.setText(ans);
                        et_number.setSelection(et_number.getText().length() - cursorComplement);
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        et_number.setText(ans);
                        et_number.setSelection(et_number.getText().length() - cursorComplement);
                    }
                } else {

                    editedFlag = false;
                }
            }
        });
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
        st_name = et_name.getText().toString();
        st_number=et_number.getText().toString();
        st_email=et_email.getText().toString();
        st_c_email=et_c_email.getText().toString();
        st_password=et_password.getText().toString();
        st_c_password=et_c_password.getText().toString();
        if(view.getId()== next.getId())
        {
            if(st_name.equals("") || st_number.equals("") || st_email.equals("") || st_c_email.equals("") || st_password.equals("") || st_c_password.equals(""))
            {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                et_name.setError("Please Enter a valid name");
                et_number.setError("Please Enter a Phone number");
                et_name.setError("Please Enter a password");
                et_name.setError("Please Enter Confirm password");
                et_email.setError("Enter Valid Email address");
                et_c_email.setError("Enter Valid confirm Email address");
                et_password.setError("Enter Valid password address");
                et_c_password.setError("Enter correct confirm password address");
            }
            else if(st_name.isEmpty() || st_name.length()>32){
                et_name.setError("Please Enter a valid name");
            }
            else if(st_number.isEmpty()){
                et_number.setError("Please Enter a Phone number");
            }
            else if(st_password.isEmpty()){
                et_name.setError("Please Enter a password");
            }
            else if(st_c_password.isEmpty()){
                et_name.setError("Please Enter Confirm password");
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(st_email).matches())
            {
                et_email.setError("Enter Valid Email address");
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(st_c_email).matches())
            {
                et_c_email.setError("Enter Valid confirm Email address");
            }
            else if (!st_email.equals(st_c_email)) {
                Toast.makeText(this, "Email and confirm email not matched.", Toast.LENGTH_SHORT).show();
            }
            else if (!st_password.equals(st_c_password)) {
                Toast.makeText(this, "Password and confirm password not matched", Toast.LENGTH_SHORT).show();
            }
            else if(NetUtil.isNetworkAvailable(this) == false )
            {
                dialog.show();
            }
            else {
                savedDataOnParse();
            }


        }

        else if(view.getId() == ok.getId())
        {

            dialog.dismiss();

        }
        else if(view.getId()== iv_back.getId())
        {
            finish();
        }
    }
    public void savedDataOnParse(){

        td = new transparentdialog(this, R.drawable.loading_blue);
        td.show();
        ParseUser user2= new ParseUser();
        user2.setUsername(st_c_email);
        user2.put("customerName",st_name);
        user2.put("phone",st_number);
        user2.setEmail(st_email);
        user2.setPassword(st_password);
        user2.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Toast.makeText(CreateAccount.this, "Done successfully", Toast.LENGTH_SHORT).show();
                    td.dismiss();

                    if(titlle.equals("Limited Liability Company")){
                        Intent i = new Intent (CreateAccount.this ,LLCInformation.class);
                        i.putExtra("tittle", titlle);
                        startActivity(i);

                    }
                    /*else if(titlle.equals("Trust")){
                        Intent i = new Intent (CreateAccount.this ,Trust_information.class);
                        i.putExtra("tittle", titlle);
                        startActivity(i);

                    }
                    else if(titlle.equals("Non Profit")){
                        Intent i = new Intent (CreateAccount.this ,npo_bussinessinfo.class);
                        i.putExtra("tittle", titlle);
                        startActivity(i);

                    }
                    else {
                        Intent i = new Intent(CreateAccount.this, BussinessInformation.class);
                        i.putExtra("tittle", titlle);
                        startActivity(i);
                    }*/
                }
                else{
                    td.dismiss();
                    switch (e.getCode()){
                        case ParseException.USERNAME_TAKEN:{

                            Toast.makeText(CreateAccount.this, "UserName Already exist", Toast.LENGTH_SHORT).show();
                        }
                        case ParseException.EMAIL_TAKEN:
                        {
                            Toast.makeText(CreateAccount.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                        }
                        default:{
                        }
                    }

                }
            }
        });
    }
}
