package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Account;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Account account = getIntent().getParcelableExtra("Account");
        TextView username, role;
        Button btnLogout;

        username = findViewById(R.id.viewUsername_homeScreen);
        role = findViewById(R.id.viewRole_homeScreen);
        btnLogout = findViewById(R.id.btn_Logout);

        username.setText(account.getUsername());
        role.setText(account.getRole().name());

        btnLogout.setOnClickListener(l -> startActivity(new Intent(HomeActivity.this, LoginActivity.class)));
    }
}