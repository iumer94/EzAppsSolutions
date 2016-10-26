package sfs.com.ezappssolutions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    Button btn_login,btn_entity;
    SessionManager sessionManager;


    public static final String PARSE_APPLICATION_ID = "ohCJCMwrSghdT1fF48rR8bEjGLG3TlNMHQTqNJJ6";
    public static final String PARSE_CLIENT_KEY = "1UH0xoZxXj2YCjIicSpdEEHOTLmv4DBELHO3SCQt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(PARSE_APPLICATION_ID)
                .clientKey(PARSE_CLIENT_KEY).server("https://cjpacific.com/parse")
                .enableLocalDataStore()
                .build());
        ParseUser.enableAutomaticUser();

        boolean flag = checkIfUserExist();
        if(flag == true)
        {
            finish();

            Intent intent = new Intent(this, NavigationBar.class);

            startActivity(intent);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_entity=(Button) findViewById(R.id.btn_entity);
        btn_entity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== btn_login.getId())
        {
            Intent i = new Intent (this , login.class);
            startActivity(i);
        }
        if(view.getId()== btn_entity.getId())
        {
            Intent i = new Intent (this ,NavigationBar.class);
            startActivity(i);
        }
    }
    private boolean checkIfUserExist()
    {
        Boolean flag  = true;
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.userSession), 0); // 0 - for private mode
        String name = pref.getString(getString(R.string.userName),null);
        String pass = pref.getString(getString(R.string.userPass),null);
        if(name==null||pass ==null)
        {
            flag = false;
        }
        return flag;
    }
}
