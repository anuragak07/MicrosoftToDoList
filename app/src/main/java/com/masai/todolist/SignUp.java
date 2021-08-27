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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private EditText mEmail, mPass,mName,mConfirmPass;
    private TextView mTextView;
    private Button signUpBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail =findViewById(R.id.EmailSignup);
        mName =findViewById(R.id.etNameSignUp);
        mPass=findViewById(R.id.etPasswordSignUp);
        mConfirmPass=findViewById(R.id.etConfirmPassword);
        mTextView=findViewById(R.id.alreadySignIn);
        signUpBtn=findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }
    private  void createUser(){
        String email =mEmail.getText().toString();
        String pass =mPass.getText().toString();
        String confirmPass =mConfirmPass.getText().toString();
        String Name =mName.getText().toString();

        if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!pass.isEmpty() && pass.length() > 6 && confirmPass.length() > 6 && pass.equals(confirmPass)) {
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Registration Error!!", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                mPass.setError("Password should be atleast  7 characters");
                mConfirmPass.setError("Password is not matching");
            }
        }
            else if(email.isEmpty()){
                mEmail.setError("Empty Fields Are Not Allowed");

        }else {
                mEmail.setError("Please Enter Correct Email");
        }

    }
}