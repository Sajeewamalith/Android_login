package com.sajeewamalith.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {
    Button b1;
    EditText t1;
    ProgressBar progressBar;
    private Patterns patterns;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        b1=(Button)findViewById(R.id.button4);
        t1=(EditText)findViewById(R.id.editTextTextEmailAddress);
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);

        auth=FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restpassword();
            }
        });


    }
    private void restpassword(){
        String email=t1.getText().toString().trim();
        if (email.isEmpty()){
            t1.setError("mail is requried!");
            t1.requestFocus();
            return;

        }

        if(!patterns.EMAIL_ADDRESS.matcher(email).matches()){
            t1.setError("please provid vaild mail!");
            t1.requestFocus();
            return;
        }
       progressBar.setVisibility(View.VISIBLE);
       auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()){
                   Toast.makeText(Forgetpassword.this,"Check Your email",Toast.LENGTH_LONG).show();
                   progressBar.setVisibility(View.GONE);
               }else {
                   Toast.makeText(Forgetpassword.this,"Something worng,Try again",Toast.LENGTH_LONG).show();
                   progressBar.setVisibility(View.GONE);

               }

           }
       });




    }
}