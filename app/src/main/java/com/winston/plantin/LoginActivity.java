package com.winston.plantin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.model.User;
import com.winston.plantin.utility.Session;

public class LoginActivity extends Activity {
    private EditText txtEmail, txtPassword;
    private Button btnSignIn;
    private TextView txtSignUp;
    private PlantinDB db;

    private void initComponents(){
        db = new PlantinDB(this);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        txtSignUp = findViewById(R.id.txt_sign_up);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        setButton();
    }

    public void setButton(){
        txtSignUp.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });

        btnSignIn.setOnClickListener(v -> auth());
    }

    private void auth(){
        String email = txtEmail.getText().toString(), password = txtPassword.getText().toString();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Username and password must be filled !", Toast.LENGTH_SHORT).show();
        }else {
            User user = db.getUserByEmailAndPassword(email, password);
            if (user == null) {
                Toast.makeText(this, "Email doesn't exists or wrong password !", Toast.LENGTH_SHORT).show();
            } else {
                Session.getInstance().setUser(user);
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }
}
