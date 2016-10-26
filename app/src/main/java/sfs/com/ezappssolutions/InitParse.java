package sfs.com.ezappssolutions;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by Umer Ejaz on 10/25/2016.
 */

public class InitParse extends Application {
    // parse application key
    public static final String PARSE_APPLICATION_ID = "ohCJCMwrSghdT1fF48rR8bEjGLG3TlNMHQTqNJJ6";

    // parse client key
    public static final String PARSE_CLIENT_KEY = "1UH0xoZxXj2YCjIicSpdEEHOTLmv4DBELHO3SCQt";
    @Override
    public void onCreate() {
        super.onCreate();
        initParse();
    }
    private void initParse()
    {
        Parse.enableLocalDatastore(this);
        // Initialize parse server with client application
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(PARSE_APPLICATION_ID)
                .clientKey(PARSE_CLIENT_KEY).server("https://cjpacific.com/parse")
                .build());
        ParseUser.enableAutomaticUser();
    }
}
