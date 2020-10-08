package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.Vars;
import me.kianbazza.servicenovigrad.accounts.*;

public class RegisterActivity extends AppCompatActivity {

    private Button button;
    private EditText username, email, password, role;
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

        button.setOnClickListener(new View.OnClickListener() {
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

            if (validate(account)) {

                register(account);
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);

            }


        }

    }

    private boolean validate(Account account) {

        return true;



    }

    private void register(Account account) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRoleRef = db.getReference("users/" + account.getName() + "/role");
        DatabaseReference userEmailRef = db.getReference("users/" + account.getName() + "/email");
        DatabaseReference userPassRef = db.getReference("users/" + account.getName() + "/password");

        userRoleRef.setValue(account.getRole());
        userEmailRef.setValue(account.getEmail());
        userPassRef.setValue(account.getPassword());

    }

}