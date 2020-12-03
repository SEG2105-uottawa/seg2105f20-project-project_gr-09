package me.kianbazza.servicenovigrad.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Account;
import me.kianbazza.servicenovigrad.accounts.Role;
import me.kianbazza.servicenovigrad.admin.fragments.ServiceDialog;
import me.kianbazza.servicenovigrad.general.LoginActivity;
import me.kianbazza.servicenovigrad.admin.adapters.ServicesRecyclerAdapter;
import me.kianbazza.servicenovigrad.services.Service;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity implements ServicesRecyclerAdapter.OnServiceListener {

    // Activity variables
    private Account account;
    private Service service;

    // Database
    private DatabaseReference dbRef;

    // Interface - Services
    private TextView username, role;
    private Button btnLogout, btnAddService;
    private RecyclerView adminServicesRecyclerView;

    // Variables - Services
    private ArrayList<Service> servicesList;
    private ServicesRecyclerAdapter servicesRecyclerAdapter;

    // Interface - Accounts
    private RecyclerView adminAccountsRecyclerView;

    // Variables - Accounts
    private TextView accountStatus;
    private EditText accountName;
    private Button btnLookupAccount, btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        dbRef = FirebaseDatabase.getInstance().getReference();

        account = getIntent().getParcelableExtra("Account");

        // Services
        username = findViewById(R.id.viewUsername_homeScreen);
        role = findViewById(R.id.viewRole_homeScreen);
        btnLogout = findViewById(R.id.btn_Logout);
        btnAddService = findViewById(R.id.btn_admin_addService);
        adminServicesRecyclerView = findViewById(R.id.recyclerView_admin_listServices);

        username.setText(account.getUsername());
        role.setText(account.getRole().toString());

        btnLogout.setOnClickListener(l -> startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class)));
        btnAddService.setOnClickListener(l -> openCreateServiceDialog());

        servicesList = new ArrayList<>();
        adminServicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminServicesRecyclerView.setHasFixedSize(true);

        getServicesListFromFirebase();

        // Accounts
        accountStatus = findViewById(R.id.accountStatusTextView);
        accountName = findViewById(R.id.accountNameTextField);
        btnLookupAccount = findViewById(R.id.btn_lookupAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteAccount);

        accountStatus.setText("");

        btnLookupAccount.setOnClickListener(l -> lookupAccount());
        btnDeleteAccount.setOnClickListener(l -> deleteAccount());

    }

    private void getServicesListFromFirebase() {

        Query query = dbRef.child("services");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearServicesList();

                Service service;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    service = snapshot.getValue(Service.class);
                    servicesList.add(service);

                }

                servicesRecyclerAdapter = new ServicesRecyclerAdapter(getApplicationContext(), servicesList, AdminHomeActivity.this);
                adminServicesRecyclerView.setAdapter(servicesRecyclerAdapter);
                servicesRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void clearServicesList() {

        if (servicesList != null) {
            servicesList.clear();

            if (servicesRecyclerAdapter != null) {
                servicesRecyclerAdapter.notifyDataSetChanged();
            }
        }

    }

    private void openCreateServiceDialog() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("Service", service);
        bundle.putBoolean("isEditingService", false);

        ServiceDialog serviceDialog = new ServiceDialog();
        serviceDialog.setArguments(bundle);
        serviceDialog.show(getSupportFragmentManager(), "Service Dialog");
    }

    @Override
    public void onServiceClick(int position) {

        service = servicesList.get(position);

        Bundle bundle = new Bundle();
        bundle.putParcelable("Service", service);
        bundle.putBoolean("isEditingService", true);

        ServiceDialog serviceDialog = new ServiceDialog();
        serviceDialog.setArguments(bundle);
        serviceDialog.show(getSupportFragmentManager(), "Service Dialog");

    }







    private void lookupAccount() {
        String accountNameStr = accountName.getText().toString().trim();

        if (TextUtils.isEmpty(accountNameStr)) {
            Toast.makeText(this, "Cannot lookup blank account name.", Toast.LENGTH_SHORT).show();
        } else {

            Query query = dbRef.child("users");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Account accountResult = null;

                    for (@NonNull DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (snapshot.getKey().equalsIgnoreCase(accountNameStr)) {
                            // Account exists
                            accountResult = snapshot.getValue(Account.class);

                            break;
                        }

                    }

                    if (accountResult!=null) {
                        accountStatus.setText( "Found! Username: " + accountResult.getUsername() + "   Role: " + accountResult.getRole().name() );
                        Toast.makeText(AdminHomeActivity.this, "Account lookup successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        accountStatus.setText("Account does not exist.");
                        Toast.makeText(AdminHomeActivity.this, "Account does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void deleteAccount() {

        String accountNameStr = accountName.getText().toString().trim();

        if (TextUtils.isEmpty(accountNameStr)) {
            Toast.makeText(this, "Cannot delete blank account name.", Toast.LENGTH_SHORT).show();
        } else {
            Query query = dbRef.child("users");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Account accountResult = null;

                    for (@NonNull DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (snapshot.getKey().equalsIgnoreCase(accountNameStr)) {
                            // Account found
                            accountResult = snapshot.getValue(Account.class);
                            break;
                        }

                    }

                    if (accountResult!=null) {
                        // Search for account was successful
                        // Check that account to be deleted is NOT admin
                        if (accountResult.getRole()!=Role.ADMIN) {
                            // Account is not admin, safe to delete.
                            DatabaseReference accountRef = dbRef.getRef().child("users").child(accountResult.getUsername());
                            accountRef.setValue(null);

                            accountStatus.setText("The account \"" + accountResult.getUsername() + "\" has been deleted.");
                            Toast.makeText(AdminHomeActivity.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();

                        } else {
                            // Account is admin account
                            // This is forbidden
                            accountStatus.setText("You cannot delete an Admin account.");
                            Toast.makeText(AdminHomeActivity.this, "You cannot delete an Admin account.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // Account does not exist
                        accountStatus.setText("Account does not exist.");
                        Toast.makeText(AdminHomeActivity.this, "Account does not exist.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                }
            });
        }
    }

}