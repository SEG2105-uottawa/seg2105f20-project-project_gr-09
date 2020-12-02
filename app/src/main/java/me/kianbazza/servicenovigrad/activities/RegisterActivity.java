package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.misc.Vars;
import me.kianbazza.servicenovigrad.accounts.*;
import org.jetbrains.annotations.NotNull;

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

        } else if ( !(emailStr.trim().matches(Vars.emailAddressRegex)) ) {
            Toast.makeText(this, "Email address is not valid.", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(this, "Password can't be blank.", Toast.LENGTH_SHORT).show();

        } else {

            Role role = Role.fromString(roleStr);
            Account account = new Account(usernameStr, emailStr, passwordStr, role);

            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    if (!snapshot.child(account.getUsername()).exists()) {
                        // Account does not exist
                        // Go ahead with account registration
                        snapshot.child(account.getUsername()).getRef().setValue(account);

                        Intent intent;

                        switch (account.getRole()) {
                            case EMPLOYEE:
                                intent = new Intent(getApplicationContext(), EmployeeHomeActivity.class);

                                DatabaseReference branchesRef = FirebaseDatabase.getInstance().getReference().child("branches");
                                DatabaseReference branchRef = branchesRef.push();

                                Branch branch = new Branch(false, branchRef.getKey(), "", "", "", "");
                                branchRef.setValue(branch);

                                account.setBranch(branchRef.getKey());
                                snapshot.child(account.getUsername()).getRef().child("branchID").setValue(account.getBranchID());

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
                        runOnUiThread(() -> startActivity(intent));

                    } else {
                        // Account with this username already exists
                        Toast.makeText(RegisterActivity.this, "An account with this username already exists.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }

    }

}