package me.kianbazza.servicenovigrad.customer;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Account;
import me.kianbazza.servicenovigrad.general.LoginActivity;
import me.kianbazza.servicenovigrad.services.Service;

public class CustomerHomeActivity extends AppCompatActivity {

    // Activity variables
    private Account account;
    private Service service;

    // Database
    private DatabaseReference dbRef;

    // Interface - Services
    private TextView username, role;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        dbRef = FirebaseDatabase.getInstance().getReference();

        account = getIntent().getParcelableExtra("Account");

        // Services
        username = findViewById(R.id.customer_viewUsernameTextView);
        role = findViewById(R.id.customer_viewRoleTextView);
        btnLogout = findViewById(R.id.btn_customer_logout);

        username.setText(account.getUsername());
        role.setText(account.getRole().toString());

        btnLogout.setOnClickListener(l -> startActivity(new Intent(CustomerHomeActivity.this, LoginActivity.class)));

    }
}