package com.example.sahan.howtoreach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class checkLoginActivity extends AppCompatActivity {

    private SharedPreferences pref;
    public static final String users = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);

        pref = getSharedPreferences(users,0);

        if (pref.getBoolean(Constants.IS_LOGGED_IN,false))
        {
            Intent intent = new Intent(checkLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(checkLoginActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
