package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.*;
import me.kianbazza.servicenovigrad.database.DatabaseManager;
import me.kianbazza.servicenovigrad.database.FirebaseCallback;
import me.kianbazza.servicenovigrad.misc.AccountHelper;

import java.util.HashMap;

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
            
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.readChildrenReference("users/", new FirebaseCallback() {
                @Override
                public void getData(HashMap<String, Object> data) {
                    
                }

                @Override
                public void getRef(HashMap<String, DataSnapshot> data) {
                    
                    if (data.containsKey(usernameStr)) {
                        // Account exists
                        DataSnapshot userData = data.get(usernameStr);
                        String correctPassword = userData.child("password").getValue(String.class);

                        if (passwordStr.equals(correctPassword)) {
                            // Password matches!

                            String emailStr = userData.child("email").getValue(String.class);
                            String roleStr = userData.child("role").getValue(String.class);
                            UserRole.RoleName roleName = UserRole.RoleName.fromString(roleStr);

                            AccountHelper accountHelper = new AccountHelper();

                            Account account = new Account(usernameStr, emailStr, passwordStr, roleName);

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("Account", account);
                            runOnUiThread(() -> {
                                startActivity(intent);
                            });

                        } else {
                            // Password is incorrect
                            Toast.makeText(getApplicationContext(), "Incorrect password!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Account does not exist
                        Toast.makeText(LoginActivity.this, "Account does not exist. Register instead.", Toast.LENGTH_SHORT).show();
                    }

                    

                }
            });

        }

    }



}