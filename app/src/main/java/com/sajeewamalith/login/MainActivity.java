package com.sajeewamalith.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button b1,bu,b2;
    EditText m1,p1;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Patterns patterns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser !=null){
            startActivity(new Intent(MainActivity.this,MainActivity2.class));
        }

        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button3);
        b1.setOnClickListener(this);

        bu=(Button)findViewById(R.id.button);
        bu.setOnClickListener(this);
        m1=(EditText)findViewById(R.id.editTextTextEmailAddress2);
        p1=(EditText)findViewById(R.id.editTextTextPassword2);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);

     


    }
    

    

    

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button3:
                startActivity(new Intent(this,Register.class));
                 break;
            case R.id.button:
                userLogin();
                break;
            case R.id.button2:
                startActivity(new Intent(this,Forgetpassword.class));
                break;


        }


    }
    private void userLogin(){
        String mai=m1.getText().toString().trim();
        String pass=p1.getText().toString().trim();
        if (mai.isEmpty()){
            m1.setError("mail is requried!");
            m1.requestFocus();
            return;

        }

        if(!patterns.EMAIL_ADDRESS.matcher(mai).matches()){
            m1.setError("please provid vaild mail!");
            m1.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            p1.setError("password is requried!");
            p1.requestFocus();
            return;

        }
        if (pass.length()<6){
            p1.setError("password should be 6 charactrs!");
            p1.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mai,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                     startActivity(new Intent(MainActivity.this,MainActivity2.class));
                        }else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verifed your account",Toast.LENGTH_LONG).show();

                    }

                }else {
                    Toast.makeText(MainActivity.this,"Login fail!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }

            }
        });
    }





}
