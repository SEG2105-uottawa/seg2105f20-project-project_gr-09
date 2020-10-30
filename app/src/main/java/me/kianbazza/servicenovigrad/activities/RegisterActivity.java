package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.misc.AccountHelper;
import me.kianbazza.servicenovigrad.misc.Vars;
import me.kianbazza.servicenovigrad.accounts.*;

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

            Account account;

            switch (Roles.fromString(roleStr)) {
                case CUSTOMER:
                    account = new CustomerAccount(usernameStr, emailStr, passwordStr);
                    break;
                case EMPLOYEE:
                    account = new EmployeeAccount(usernameStr, emailStr, passwordStr);
                    break;
                case ADMIN:
                    account = new AdminAccount(usernameStr, emailStr, passwordStr);
                default:
                    return;
            }

            AccountHelper accountHelper = new AccountHelper();

            if ( !accountHelper.isRegistered(account.getUsername()) ) {

                accountHelper.register(account);
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "An account already exists with this username!", Toast.LENGTH_SHORT).show();
            }


        }

    }

}