package com.santiagonoailles.matsprofesionales;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.santiagonoailles.matsprofesionales.entity.Profesional;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.LoginProfesional;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;
import com.santiagonoailles.matsprofesionales.helper.ProfessionsHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, LoginProfesional {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button alreadyLoginButton;

    private String email;
    private String password;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //TODO REMOVE!!!!---------------------
       BaseDeDatos.guardar();

        emailEditText = (EditText) findViewById(R.id.signup_email);
        passwordEditText = (EditText) findViewById(R.id.signup_password);

        alreadyLoginButton = (Button) findViewById(R.id.signup_alreadylogin_button);
        loginButton = (Button) findViewById(R.id.signup_login_button);
        loginButton.setOnClickListener(this);
        alreadyLoginButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (MatsSettings.getProfessionalId(SignupActivity.this).equals("")) {
                        startActivity(new Intent(SignupActivity.this, InformationActivity.class));
                        SignupActivity.this.finish();
                    } else {
                        startActivity(new Intent(SignupActivity.this, NuevaMainActivity.class));
                        SignupActivity.this.finish();
                    }
                }
            }
            // ...
        };

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.waitmessage));
        progressDialog.show();

        if (view.getId() == R.id.signup_login_button) {
            String errorMessage = this.validateData();
            if (errorMessage.equals("")) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            MatsSettings.saveEmail(email, SignupActivity.this);
                            startActivity(new Intent(SignupActivity.this, InformationActivity.class));
                            SignupActivity.this.finish();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setMessage(task.getException().getMessage().toString())
                                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();
                        }
                    }


                });

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.error_message) + "\n" + errorMessage)
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        } else {
            String errorMessage = this.validateData();
            if (errorMessage.equals("")) {

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            MatsSettings.saveEmail(email, SignupActivity.this);

                            FirebaseHelper.findProfessionalByEmail(email, SignupActivity.this);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setMessage(task.getException().getMessage().toString())
                                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();
                        }
                    }
                });
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.error_message) + "\n" + errorMessage)
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        }

    }

    private String validateData() {
        StringBuffer errorBuffer = new StringBuffer("");

        email = emailEditText.getText().toString();
        if (email.trim().equals("")) {
            errorBuffer.append(getString(R.string.email) + "\n");
        }
        password = passwordEditText.getText().toString();
        if (password.trim().equals("")) {
            errorBuffer.append(getString(R.string.password) + "\n");
        } else if (password.length() < 6) {
            errorBuffer.append(getString(R.string.shortPassword));
        }

        return errorBuffer.toString();
    }

    @Override
    public void onProfesionalLogin(Profesional profesional) {
        MatsSettings.setProfessionalId(profesional.getId(), this);

        startActivity(new Intent(SignupActivity.this, NuevaMainActivity.class));
        SignupActivity.this.finish();
    }
}
