package com.example.haripriya.rss_reader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mloginemail,mloginpwd;
    private Button loginok;
    private ProgressDialog mloginprogress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mloginprogress=new ProgressDialog(this);
        mloginemail=findViewById(R.id.email);
        mloginpwd=findViewById(R.id.password);
        loginok=findViewById(R.id.okbutton);
        loginok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logemail=mloginemail.getEditText().getText().toString();
                String logpwd=mloginpwd.getEditText().getText().toString();
                if(!TextUtils.isEmpty(logemail)||!TextUtils.isEmpty(logpwd))
                {
                    mloginprogress.setTitle("Logging IN");
                    mloginprogress.setMessage("Please Wait!");
                    mloginprogress.setCanceledOnTouchOutside(false);
                    mloginprogress.show();
                    loginUser(logemail,logpwd);
                }
            }

            private void loginUser(String logemail, String logpwd) {
                mAuth.signInWithEmailAndPassword(logemail,logpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            mloginprogress.dismiss();
                            Intent i=new Intent(LoginActivity.this,CategoryActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            mloginprogress.hide();
                            Toast.makeText(LoginActivity.this,"Cannot Sign IN!...Please check the entries",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
