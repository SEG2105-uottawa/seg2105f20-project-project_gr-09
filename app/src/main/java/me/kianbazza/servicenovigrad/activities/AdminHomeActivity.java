package me.kianbazza.servicenovigrad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Account;
import me.kianbazza.servicenovigrad.adapters.RecyclerAdapter;
import me.kianbazza.servicenovigrad.services.Service;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    // Account information
    private Account account;

    // Database
    private DatabaseReference dbRef;

    // Interface
    private TextView username, role;
    private Button btnLogout, btnAddService;
    private RecyclerView recyclerView;

    // Variables
    private ArrayList<Service> servicesList;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        dbRef = FirebaseDatabase.getInstance().getReference();

        account = getIntent().getParcelableExtra("Account");

        username = findViewById(R.id.viewUsername_homeScreen);
        role = findViewById(R.id.viewRole_homeScreen);
        btnLogout = findViewById(R.id.btn_Logout);
        btnAddService = findViewById(R.id.btn_admin_addService);
        recyclerView = findViewById(R.id.recyclerView_admin_listServices);

        username.setText(account.getUsername());
        role.setText(account.getRole().getRoleName().toString());

        btnLogout.setOnClickListener(l -> startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class)));
        btnAddService.setOnClickListener(l -> openServiceDialog());

        servicesList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getServicesListFromFirebase();

    }

    private void getServicesListFromFirebase() {

        Query query = dbRef.child("services");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearServicesList();

                Service service;
//                String id, name;
//                double price;
//                ArrayList<ServiceFormEntry> form;
//                ArrayList<ServiceDocument> documents;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    service = snapshot.getValue(Service.class);
                    servicesList.add(service);

//                    id = snapshot.getKey();
//                    name = snapshot.child("name").getValue(String.class);
//                    price = snapshot.child("price").getValue(Double.class);
//                    form = new ServiceForm(snapshot.child("form"));
//
//                    documents = new ArrayList<>();
//
//                    for (DataSnapshot doc : snapshot.child("docs").getChildren()) {
//                        ServiceDocument serviceDoc = new ServiceDocument(doc.getKey().substring(4), doc.getValue(String.class));
//                        documents.add(serviceDoc);
//                    }
//
//                    service = new Service(name, displayName, price, form, documents);
//                    servicesList.add(service);

                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), servicesList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void clearServicesList() {

        if (servicesList != null) {
            servicesList.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }

    }

    private void openServiceDialog() {
        AdminServiceDialog serviceDialog = new AdminServiceDialog();
        serviceDialog.show(getSupportFragmentManager(), "Service Dialog");
    }

    /*

    private void lookupService() {

        String nameStr = name.getText().toString().trim();

        databaseManager.readChildrenReference("services/", new FirebaseCallback() {
            @Override
            public void getData(HashMap<String, Object> data) {

            }

            @Override
            public void getRef(HashMap<String, DataSnapshot> data) {
                if (data.containsKey(nameStr)) {
                    DataSnapshot serviceRef = data.get(nameStr);

                    String displaynameResult, customerInfoResult, documentsResult;
                    Long priceResult;

                    displaynameResult = serviceRef.child("display-name").getValue(String.class);
                    priceResult = serviceRef.child("price").getValue(Long.class);

                    Iterator<DataSnapshot> itr = serviceRef.child("form").getChildren().iterator();
                    StringBuilder sb = new StringBuilder();

                    while (itr.hasNext()) {
                        DataSnapshot current = itr.next();
                        sb.append(current.getKey().substring(4));

                        if (itr.hasNext()) {
                            sb.append("; ");
                        }
                    }

                    customerInfoResult = sb.toString().trim();

                    itr = serviceRef.child("docs").getChildren().iterator();
                    sb.setLength(0);

                    while (itr.hasNext()) {
                        DataSnapshot current = itr.next();
                        sb.append(current.getKey().substring(4));

                        if (itr.hasNext()) {
                            sb.append("; ");
                        }
                    }

                    documentsResult = sb.toString().trim();

                    displayName.setText(displaynameResult);
                    price.setText(priceResult.toString());
                    requiredInfo.setText(customerInfoResult);
                    requiredDocs.setText(documentsResult);

                    Toast.makeText(HomeActivity.this, "Lookup successful!", Toast.LENGTH_SHORT).show();


                } else {
                    // Service does not exist
                    Toast.makeText(HomeActivity.this, "Service doesn't exist. Hit \"Create\" to make it a service now.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        

    }

    private void deleteService() {

        String nameStr = name.getText().toString().trim();

        databaseManager.readChildrenReference("services/", new FirebaseCallback() {
            @Override
            public void getData(HashMap<String, Object> data) {

            }

            @Override
            public void getRef(HashMap<String, DataSnapshot> data) {
                if (data.get(nameStr)!=null) {
                    DatabaseReference serviceRef = data.get(nameStr).getRef();
                    serviceRef.setValue(null);
                    Toast.makeText(getApplicationContext(), "Deleted service.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveService() {

        deleteService();

        String nameStr, displayNameStr, priceStr, requiredInfoStr, requiredDocsStr;

        nameStr = name.getText().toString().trim();
        displayNameStr = displayName.getText().toString().trim();
        priceStr = price.getText().toString().trim();
        requiredInfoStr = requiredInfo.getText().toString().trim();
        requiredDocsStr = requiredDocs.getText().toString().trim();


        // Validate fields
        if (TextUtils.isEmpty(nameStr)) {
            Toast.makeText(this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
        } else if (nameStr.contains(" ")) {
            Toast.makeText(this, "Name cannot contain spaces. You may use spaces in the Full Name of the service instead.", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(displayNameStr)) {
            Toast.makeText(this, "Display name cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Price cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(requiredInfoStr)) {
            Toast.makeText(this, "Required customer info cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(requiredDocsStr)) {
            Toast.makeText(this, "Required documents cannot be blank.", Toast.LENGTH_SHORT).show();
        } else {
            // Fields have passed initial validation

            ServiceHelper serviceHelper = new ServiceHelper();

            databaseManager.readChildrenData("services/", new FirebaseCallback() {
                @Override
                public void getData(HashMap<String, Object> data) {

                    String[] infoNames = requiredInfoStr.split(";");
                    infoNames = Helper.trimArray(infoNames);

                    for (int i=0; i < infoNames.length; i++) {
                        infoNames[i] = threeDigitInt(i) + " " + infoNames[i];
                    }

                    ServiceForm form = new ServiceForm(infoNames);

                    String[] docNames = requiredDocsStr.split(";");
                    docNames = Helper.trimArray(docNames);

                    for (int i=0; i < docNames.length; i++) {
                        docNames[i] = threeDigitInt(i) + " " + docNames[i];
                    }

                    ServiceDocument[] docs = new ServiceDocument[docNames.length];
                    for (int i=0; i < docs.length; i++) {
                        ServiceDocument doc = new ServiceDocument(docNames[i], null);
                        docs[i] = doc;
                    }
                    double priceNum = Double.parseDouble(priceStr);

                    Service service = new Service(nameStr, displayNameStr, priceNum, form, docs);
                    serviceHelper.createService(service);

                    Toast.makeText(getApplicationContext(), "Updated service information.", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void getRef(HashMap<String, DataSnapshot> data) {

                }
            });


        }

    }

    private void clearService() {
        for (EditText field : inputFields) {
            field.getText().clear();
        }

        Toast.makeText(this, "Cleared text fields", Toast.LENGTH_SHORT).show();
    }

    private String threeDigitInt(int num) {
        if (num < 10 && num >= 0) {
            return "00" + num;
        } else if (num < 100) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    private void lookupAccount() {
        String accountNameStr = accountName.getText().toString().trim();

        if (TextUtils.isEmpty(accountNameStr)) {
            Toast.makeText(this, "Cannot lookup blank account name.", Toast.LENGTH_SHORT).show();
        } else {
            databaseManager.readChildrenReference("users/", new FirebaseCallback() {
                @Override
                public void getData(HashMap<String, Object> data) {

                }

                @Override
                public void getRef(HashMap<String, DataSnapshot> data) {
                    if (data.containsKey(accountNameStr)) {
                        // Account exists
                        DataSnapshot accountInfo = data.get(accountNameStr);
                        accountStatus.setText( "Found! Username: " + accountNameStr + "   Role: " + accountInfo.child("role").getValue(String.class) );
                        Toast.makeText(HomeActivity.this, "Account lookup successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Account does not exist
                        accountStatus.setText("Account does not exist.");
                        Toast.makeText(HomeActivity.this, "Account does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void deleteAccount() {

        String accountNameStr = accountName.getText().toString().trim();

        if (TextUtils.isEmpty(accountNameStr)) {
            Toast.makeText(this, "Cannot delete blank account name.", Toast.LENGTH_SHORT).show();
        } else {
            databaseManager.readChildrenReference("users/", new FirebaseCallback() {
                @Override
                public void getData(HashMap<String, Object> data) {

                }

                @Override
                public void getRef(HashMap<String, DataSnapshot> data) {
                    if (data.containsKey(accountNameStr)) {
                        // Account exists
                        DataSnapshot accountInfo = data.get(accountNameStr);
                        String accountRoleNameStr = accountInfo.child("role").getValue(String.class);

                        if (UserRole.RoleName.fromString(accountRoleNameStr)==UserRole.RoleName.ADMIN) {
                            // Cannot delete admin account
                            accountStatus.setText("You cannot delete an Admin account.");
                            Toast.makeText(HomeActivity.this, "You cannot delete an Admin account.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Account is not admin, thus safe to delete.
                            accountInfo.getRef().setValue(null);
                            accountStatus.setText("The account \"" + accountNameStr + "\" has been deleted.");
                            Toast.makeText(HomeActivity.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // Account does not exist
                        accountStatus.setText("Account does not exist.");
                        Toast.makeText(HomeActivity.this, "Account does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    */

}