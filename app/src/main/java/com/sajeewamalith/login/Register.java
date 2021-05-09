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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    Button b2;
    EditText name,age,mail,password;
    ProgressBar pb;
    private Patterns patterns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        b2=(Button)findViewById(R.id.button5);
        b2.setOnClickListener(this);

        name=(EditText)findViewById(R.id.editTextTextPersonName3);
        age=(EditText)findViewById(R.id.editTextTextPersonName4);
        mail=(EditText)findViewById(R.id.editTextTextEmailAddress3);
        password=(EditText)findViewById(R.id.editTextTextPassword3);
        pb=(ProgressBar)findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button5:
                registeruser();
                break;

        }


    }
    private void registeruser(){
        String nam=name.getText().toString().trim();
        String ag=age.getText().toString().trim();
        String mai=mail.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if (nam.isEmpty()){
            name.setError("Name is reqried!");
            name.requestFocus();
            return;
        }
        if (ag.isEmpty()){
            age.setError("Age is requried!");
            age.requestFocus();
            return;
        }
        if (mai.isEmpty()){
            mail.setError("mail is requried!");
            mail.requestFocus();
            return;

        }
        if(!patterns.EMAIL_ADDRESS.matcher(mai).matches()){
            mail.setError("please provid vaild mail!");
            mail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            password.setError("password is requried!");
            password.requestFocus();
            return;

        }
        if (pass.length()<6){
            password.setError("password should be 6 charactrs!");
            password.requestFocus();
            return;

        }

        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mai,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user=new User(nam,ag,mai);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(Register.this,"user have been registered sucussfully",Toast.LENGTH_LONG).show();
                                       pb.setVisibility(View.GONE);
                                   }else {
                                       Toast.makeText(Register.this,"Registered fail!",Toast.LENGTH_LONG).show();
                                       pb.setVisibility(View.GONE);

                                   }
                                }
                            });
                        }else {
                            Toast.makeText(Register.this,"Registered fail!",Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.GONE);

                        }
                    }
                });





    }
}