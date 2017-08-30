package com.example.sahan.howtoreach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextView lEmail;
    private TextView lPass;
    private Button lLoginBTN;
    private Button lForgotPassBTN;
    private Button lLinktoSignupBTN;


    private ProgressDialog progress;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        progress = new ProgressDialog(this);

        lEmail = (TextView)findViewById(R.id.signinEmail);
        lPass = (TextView)findViewById(R.id.signinPass);
        lLoginBTN = (Button)findViewById(R.id.signinBTN);
        lLinktoSignupBTN = (Button)findViewById(R.id.linktosignupBTN);
        lForgotPassBTN = (Button)findViewById(R.id.forgotPassBTN);

        //login process
        lLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = lEmail.getText().toString().trim();
                String userPass = lPass.getText().toString().trim();

                if (!TextUtils.isEmpty(userEmail) || !TextUtils.isEmpty(userPass))
                {
                    if (validateEmail(userEmail) == true)
                    {
                        progress.setMessage("Signing In, Please Wait!");
                        progress.show();

                        auth.signInWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {


                                    progress.dismiss();

                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    progress.dismiss();
                                    Toast.makeText(LoginActivity.this,"Email or password you entered is incorrect!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Email Invalid, Please enter a valid Email!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"You cannot have one or more empty fields!",Toast.LENGTH_SHORT).show();
                }


            }


        });



        //link to signup if the user is not a member
        lLinktoSignupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //process if the user has forgotten his/her password
        lForgotPassBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = lEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(userEmail))
                {
                    if (validateEmail(userEmail)== true)
                    {
                        progress.setMessage("Verifying Email address, Please wait!");
                        progress.show();

                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    progress.dismiss();
                                    Toast.makeText(LoginActivity.this,"An email has been sent to you with password reset details. Please check your Emails.",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    progress.dismiss();
                                    Toast.makeText(LoginActivity.this,"Cannot find an account with provided email!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Email Invalid, Please enter a valid Email!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please enter the email that you signed up with!",Toast.LENGTH_SHORT).show();
                }

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
