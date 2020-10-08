package me.kianbazza.servicenovigrad.activities;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.Vars;

public class RegisterActivity extends AppCompatActivity {

    private Button bt;
    private EditText username, email, password, role;
    private Spinner roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bt = findViewById(R.id.btn_Register);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        roles = findViewById(R.id.spinner_Roles);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String roleStr = roles.getSelectedItem().toString();



    }
}