package com.masai.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText mEmail, mPass;
    private TextView mTextView;
    private Button signInBtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mEmail = findViewById(R.id.etEmail);
        mTextView=findViewById(R.id.tvNoAccount);
        mPass=findViewById(R.id.etPassword);
        signInBtn=findViewById(R.id.btnSignIn);

        mAuth =FirebaseAuth.getInstance();

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }
    private  void loginUser(){
        String email =mEmail.getText().toString();
        String pass =mPass.getText().toString();


        if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!pass.isEmpty() && pass.length() > 6) {
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignIn.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignIn.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this, "User Not found!! please Sign Up", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {

                mPass.setError("Password is incorrect");
            }
        }
        else if(email.isEmpty()){
            mEmail.setError("Empty Fields Are Not Allowed");

        }else {
            mEmail.setError("Please Enter Correct Email");
        }
    }
}