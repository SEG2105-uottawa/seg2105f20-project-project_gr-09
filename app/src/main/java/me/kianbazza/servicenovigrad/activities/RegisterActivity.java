package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.database.DatabaseManager;
import me.kianbazza.servicenovigrad.database.FirebaseCallback;
import me.kianbazza.servicenovigrad.misc.AccountHelper;
import me.kianbazza.servicenovigrad.misc.Vars;
import me.kianbazza.servicenovigrad.accounts.*;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button button;
    private EditText username, email, password, role;
    private TextView loginInstead;
    private Spinner roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = findViewById(R.id.btn_Register);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        roles = findViewById(R.id.spinner_Roles);

        loginInstead = findViewById(R.id.login_registerScreen);

        button.setOnClickListener( v -> createAccount() );
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        loginInstead.setOnClickListener(( l -> startActivity(intent) ));

    }

    private void createAccount() {

        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String roleStr = roles.getSelectedItem().toString();

        if (TextUtils.isEmpty(usernameStr)) {
            Toast.makeText(this, "Username can't be blank.", Toast.LENGTH_SHORT).show();

        } else if ( !(emailStr.trim().matches(Vars.emailPattern)) ) {
            Toast.makeText(this, "Email address is not valid.", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(this, "Password can't be blank.", Toast.LENGTH_SHORT).show();

        } else {

            AccountHelper accountHelper = new AccountHelper();
            UserRole.RoleName roleName = UserRole.RoleName.fromString(roleStr);

            Account account = new Account(usernameStr, emailStr, passwordStr, roleName);

            DatabaseManager databaseManager = new DatabaseManager();

            databaseManager.readChildrenReference("users/", new FirebaseCallback() {
                @Override
                public void getData(HashMap<String, Object> data) {

                }

                @Override
                public void getRef(HashMap<String, DataSnapshot> data) {
                    if ( !data.containsKey(account.getUsername()) ) {
                        // Account does not exist
                        // Go ahead with account registration
                        accountHelper.registerAccount(account);
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        intent.putExtra("Account", account);
                        runOnUiThread(() -> startActivity(intent));

                    } else {
                        // Account with this username already exists
                        Toast.makeText(RegisterActivity.this, "An account with this username already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

}