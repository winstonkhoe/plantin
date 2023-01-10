package com.winston.plantin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.model.User;
import com.winston.plantin.utility.Session;

public class RegisterActivity extends AppCompatActivity {

    private PlantinDB db;
    private EditText txtName, txtPhone, txtEmail, txtPassword, txtConfirmPassword;
    private TextView txtSignIn;
    private Button btnSignUp;
    private boolean validationToast = false;
    private String name, phone, email, password, confirmPassword;

    public void initComponents() {
        db = new PlantinDB(this);
        txtName = findViewById(R.id.txt_name);
        txtPhone = findViewById(R.id.txt_phone);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        txtConfirmPassword = findViewById(R.id.txt_confirm_password);
        txtSignIn = findViewById(R.id.txt_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
        setButton();
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean validateInput() {
        name = txtName.getText().toString();
        phone = txtPhone.getText().toString();
        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();
        confirmPassword = txtConfirmPassword.getText().toString();

        if (name.length() < 3) {
            validationToast = true;
            Toast.makeText(this, "Name must be minimum 3 character!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            validationToast = true;
            Toast.makeText(this, "Email is invalid!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (db.getUserByEmail(email)) {
            validationToast = true;
            Toast.makeText(this, "Email already taken!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() < 8 || phone.length() > 20) {
            validationToast = true;
            Toast.makeText(this, "Phone number must be 8-20 character!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8) {
            validationToast = true;
            Toast.makeText(this, "Password must be minimum 8 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!confirmPassword.equals(password)) {
            validationToast = true;
            Toast.makeText(this, "Confirm password did not match!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void sendMessage(String phoneNumber) {
        if (!verifyPermission()) return;
        SmsManager manager = SmsManager.getDefault();
        String text = "Welcome to Plantin, semoga cepet dapet tokoh tanaman yang jodoh yach!!";
        manager.sendTextMessage(phoneNumber, null, text, null, null);
    }

    @SuppressLint("WrongConstant")
    private boolean verifyPermission() {
        String[] permissions = {Manifest.permission.SEND_SMS};
        int result = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = checkSelfPermission(Manifest.permission.SEND_SMS);
        }

        if (result != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 1);
            }
            return false;
        }
        return true;
    }

    public void setButton() {
        btnSignUp.setOnClickListener(v -> {
            validationToast = false;
            Boolean valid = validateInput();
            if (valid) {
                User user = new User(null, name, email, password, phone);
                db.insertUser(user);
                sendMessage(phone);
                Intent intent = new Intent(this, MainActivity.class);
                Session.getInstance().setUser(db.getUserByEmailAndPassword(email, password));
                startActivity(intent);
            } else {
                if (!validationToast) {
                    Toast.makeText(this, "Input must be filled and in right format !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtSignIn.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}