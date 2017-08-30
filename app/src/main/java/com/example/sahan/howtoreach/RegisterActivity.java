package com.example.sahan.howtoreach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText sFulname;
    private EditText sEmail;
    private EditText sPassword;
    private EditText sConfPassword;
    private Button sRegisterBTN;
    private Button sLinktologin;

    private ProgressDialog progress;

    private FirebaseAuth auth;
    private DatabaseReference howtoreach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        howtoreach = FirebaseDatabase.getInstance().getReference().child("users");

        progress = new ProgressDialog(this);

        sFulname = (EditText)findViewById(R.id.signupFulname);
        sEmail = (EditText)findViewById(R.id.signupEmail);
        sPassword = (EditText) findViewById(R.id.signupPass);
        sConfPassword = (EditText)findViewById(R.id.signupConfPass);
        sRegisterBTN = (Button) findViewById(R.id.signupBTN);
        sLinktologin = (Button) findViewById(R.id.linktoSigninBTN);

        //process when signup button is clicked
        sRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = sFulname.getText().toString().trim();
                String userEmail = sEmail.getText().toString().trim();
                String userPass = sPassword.getText().toString().trim();
                String userConfPass = sConfPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(userEmail) || !TextUtils.isEmpty(userPass) || !TextUtils.isEmpty(userConfPass) || !TextUtils.isEmpty(userName))
                {
                    if (validateEmail(userEmail)== true)
                    {
                        if (userConfPass.equals(userPass))
                        {
                            progress.setMessage("Signing Up, Please Wait!");
                            progress.show();

                            auth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        progress.dismiss();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Passwords you entered do not match, Please try again!",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"Email Invalid, Please enter a valid Email!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"You cannot have one or more empty fields!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Link to login if the user is already registered
        sLinktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });






    }

    public boolean validateEmail(String email) {

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
