package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.*;
import me.kianbazza.servicenovigrad.database.DatabaseManager;
import me.kianbazza.servicenovigrad.misc.AccountHelper;

public class LoginActivity extends AppCompatActivity {

    private Button button;
    private EditText username, password;
    private TextView registerInstead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.btn_SignIn);
        username = findViewById(R.id.username_loginScreen);
        password = findViewById(R.id.password_loginScreen);

        registerInstead = findViewById(R.id.register_loginScreen);
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        button.setOnClickListener( v -> login() );
        registerInstead.setOnClickListener( l -> startActivity(intent) );

    }

    private void login() {

        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();

        if (TextUtils.isEmpty(usernameStr)) {
            Toast.makeText(this, "Username can't be blank.", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(this, "Password can't be blank.", Toast.LENGTH_SHORT).show();

        } else {
            // Login fields have been initially validated

            AccountHelper accountHelper = new AccountHelper();

            if (accountHelper.isRegistered(usernameStr)) {
                // Account with this username exists
                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.readChildrenData("users/" + usernameStr, data -> {

                    String correctPassword = data.get("password");

                    if (passwordStr.equals(correctPassword)) {
                        // Password matches!

                        String emailStr = data.get("email");
                        Role role = AccountHelper.roleFromString( data.get("role") );

                        Account account;

                        switch (role) {
                            case CUSTOMER:
                                account = new CustomerAccount(usernameStr, emailStr, passwordStr);
                                break;
                            case EMPLOYEE:
                                account = new EmployeeAccount(usernameStr, emailStr, passwordStr);
                                break;
                            case ADMIN:
                                account = new AdminAccount(usernameStr, emailStr, passwordStr);
                                break;
                            default:
                                return;
                        }
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("Account", account);
                        runOnUiThread(() -> {
                            startActivity(intent);
                        });

                    } else {
                        // Password is incorrect
                        Toast.makeText(this, "Incorrect password!", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Account with this username is not already registered.
                Toast.makeText(this, "Username has not been registered yet.", Toast.LENGTH_SHORT).show();

            }

        }

    }



}