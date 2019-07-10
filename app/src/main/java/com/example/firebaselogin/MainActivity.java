package com.example.firebaselogin;

import android.content.Intent;
import android.provider.ContactsContract;
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

public class MainActivity extends AppCompatActivity {

      EditText email,password;
      Button login;

      FirebaseAuth mAuth;
      FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
         mAuth = FirebaseAuth.getInstance();

         mAuthListener = new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                 if(firebaseAuth.getCurrentUser()!=null){

                     startActivity(new Intent(MainActivity.this,Account.class));
                 }
             }
         };




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignIN();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private  void startSignIN(){

        String mail = email.getText().toString();
        String psw = password.getText().toString();

        if(TextUtils.isEmpty(mail)||TextUtils.isEmpty(psw)){


            Toast.makeText(this, "Fields are Empty.", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(mail, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Sign IN problem", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
