package me.kianbazza.servicenovigrad.general;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.*;
import me.kianbazza.servicenovigrad.admin.AdminHomeActivity;
import me.kianbazza.servicenovigrad.customer.CustomerHomeActivity;
import me.kianbazza.servicenovigrad.employee.EmployeeHomeActivity;

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

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child("users").child(usernameStr).exists()) {
                        // Account exists
                        Account account = dataSnapshot.child("users").child(usernameStr).getValue(Account.class);

                        if (account.getPassword().equals(passwordStr)) {
                            // Password matches!
                            Intent intent;

                            switch (account.getRole()) {
                                case EMPLOYEE:
                                    intent = new Intent(getApplicationContext(), EmployeeHomeActivity.class);
                                    Branch branch = dataSnapshot.child("branches").child(account.getBranchID()).getValue(Branch.class);
                                    intent.putExtra("Branch", branch);
                                    break;
                                case ADMIN:
                                    intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                                    break;
                                default:
                                    intent = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                                    break;
                            }

                            intent.putExtra("Account", account);
                            runOnUiThread(() -> {
                                startActivity(intent);
                            });
                        } else {
                            // Password is incorrect
                            Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Account does not exist
                        Toast.makeText(LoginActivity.this, "Account does not exist. Register instead.", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }



}