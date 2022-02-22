package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //private TextInputLayout signInEmailEditText, signInPasswordEditText;
    private EditText signInEmailEditText, signInPasswordEditText;
    private Button signInButton, changeLanguageButton;
    private TextView signUpTextView;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loadLocale();
        this.setTitle("Login");

       /* //Full Screen

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/

        mAuth = FirebaseAuth.getInstance();

        signInEmailEditText = findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText = findViewById(R.id.signInPasswordEditTextId);

        signInButton = findViewById(R.id.signInButtonId);
        changeLanguageButton = findViewById(R.id.changeLanguageButtonId);
        signUpTextView = findViewById(R.id.signUpTextViewId);
        progressBar = findViewById(R.id.progressBarId);

        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
        changeLanguageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.signInButtonId){
            userLogin();
        }

       if (view.getId()==R.id.signUpTextViewId){
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.changeLanguageButtonId){
            showChangeLanguageDialog();
        }
    }



    private void userLogin() {
        String email = signInEmailEditText.getText().toString().trim();
        String password = signInPasswordEditText.getText().toString().trim();

        if (email.isEmpty()){
            signInEmailEditText.setError("Enter email address");
            signInEmailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            signInPasswordEditText.setError("Enter password");
            signInPasswordEditText.requestFocus();
            return;
        }
        if (password.length()<6){
            signInPasswordEditText.setError("Minimum password length is 6");
            signInPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,MainDashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    signInEmailEditText.setText("");
                    signInPasswordEditText.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showChangeLanguageDialog() {

        final String[] listItems = {"English", "বাংলা"};

        AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    setLocale("en");
                    recreate();
                }
                else if (i==1){
                    setLocale("bn");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang","");
        setLocale(language);
    }
}