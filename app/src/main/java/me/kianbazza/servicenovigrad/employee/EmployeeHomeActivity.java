package me.kianbazza.servicenovigrad.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Account;
import me.kianbazza.servicenovigrad.accounts.Branch;
import me.kianbazza.servicenovigrad.employee.fragments.BranchSetupDialog;
import me.kianbazza.servicenovigrad.employee.fragments.ReviewServiceRequestDialog;
import me.kianbazza.servicenovigrad.general.LoginActivity;
import me.kianbazza.servicenovigrad.employee.adapters.ServiceRequestsRecyclerAdapter;
import me.kianbazza.servicenovigrad.employee.adapters.BranchServicesRecyclerAdapter;
import me.kianbazza.servicenovigrad.misc.FragmentToActivity;
import me.kianbazza.servicenovigrad.misc.Helper;
import me.kianbazza.servicenovigrad.services.Service;
import me.kianbazza.servicenovigrad.services.ServiceDocument;
import me.kianbazza.servicenovigrad.services.ServiceFormEntry;
import me.kianbazza.servicenovigrad.services.ServiceRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EmployeeHomeActivity extends AppCompatActivity implements FragmentToActivity, BranchServicesRecyclerAdapter.OnServiceListener, ServiceRequestsRecyclerAdapter.OnServiceRequestListener {

    // Activity variables
    private Account account;
    private Branch branch;

    // Database
    private DatabaseReference dbRef;

    // Interface - Main
    private TextView username, role;
    private Button btnLogout;

    // Interface - View Branch Info
    private TextView branchAddress, branchPhoneNumber, branchWorkingHours;

    // Interface - Services
    private RecyclerView employeeServicesRecyclerView;

    // Variables - Services
    private ArrayList<Service> servicesList, branchServicesList;
    private BranchServicesRecyclerAdapter branchServicesRecyclerAdapter;

    // Interface - Service Requests
    private RecyclerView employeeServiceRequestsRecyclerView;

    // Variables - Service Requests
    private ArrayList<ServiceRequest> branchServiceRequestsList;
    private ServiceRequestsRecyclerAdapter serviceRequestsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        dbRef = FirebaseDatabase.getInstance().getReference();

        account = getIntent().getParcelableExtra("Account");
        branch = getIntent().getParcelableExtra("Branch");

        // Account Info & Navigation
        username = findViewById(R.id.employee_viewUsernameTextView);
        role = findViewById(R.id.employee_viewRoleTextView);
        btnLogout = findViewById(R.id.btn_employee_logout);

        username.setText(account.getUsername());
        role.setText(account.getRole().toString());

        btnLogout.setOnClickListener(l -> startActivity(new Intent(EmployeeHomeActivity.this, LoginActivity.class)));

        // Branch Info
        branchAddress = findViewById(R.id.employee_viewBranchAddress);
        branchPhoneNumber = findViewById(R.id.employee_viewBranchPhoneNumber);
        branchWorkingHours = findViewById(R.id.employee_viewBranchWorkingHours);

        branchAddress.setText(branch.getAddress());
        branchPhoneNumber.setText(branch.getPhoneNumber());
        branchWorkingHours.setText("Opens: " + branch.getOpeningTime() + "    Closes: " + branch.getClosingTime());

        if (!branch.isDoneSetup()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("Branch", branch);

            BranchSetupDialog branchSetupDialog = new BranchSetupDialog();
            branchSetupDialog.setArguments(bundle);
            branchSetupDialog.show(getSupportFragmentManager(), "Setup Branch Information");
        }

        // Branch Services
        servicesList = new ArrayList<>();
        branchServicesList = new ArrayList<>();

        employeeServicesRecyclerView = findViewById(R.id.employee_recyclerView_listBranchServices);
        employeeServicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeServicesRecyclerView.setHasFixedSize(true);

        getServicesListFromFirebase();
        getBranchServicesListFromFirebase();

        // Service Requests
        branchServiceRequestsList = new ArrayList<>();

        employeeServiceRequestsRecyclerView = findViewById(R.id.employee_recyclerView_listServiceRequests);
        employeeServiceRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeServiceRequestsRecyclerView.setHasFixedSize(true);

        getBranchServiceRequestsListFromFirebase();

    }

    /**** BRANCH SERVICES METHODS ****/

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

                branchServicesRecyclerAdapter = new BranchServicesRecyclerAdapter(getApplicationContext(), servicesList, branchServicesList, EmployeeHomeActivity.this);
                employeeServicesRecyclerView.setAdapter(branchServicesRecyclerAdapter);
                branchServicesRecyclerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getBranchServicesListFromFirebase() {

        Query query = dbRef.child("branches").child(branch.getBranchID()).child("services");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearBranchServicesList();

                Service service;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    service = snapshot.getValue(Service.class);
                    branchServicesList.add(service);

                }

                branchServicesRecyclerAdapter = new BranchServicesRecyclerAdapter(getApplicationContext(), servicesList, branchServicesList, EmployeeHomeActivity.this);
                employeeServicesRecyclerView.setAdapter(branchServicesRecyclerAdapter);
                branchServicesRecyclerAdapter.notifyDataSetChanged();

                // createDummyServiceRequest();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void clearServicesList() {

        if (servicesList != null) {
            servicesList.clear();

            if (branchServicesRecyclerAdapter != null) {
                branchServicesRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    private void clearBranchServicesList() {

        if (branchServicesList != null) {
            branchServicesList.clear();

            if (branchServicesRecyclerAdapter != null) {
                branchServicesRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    private void removeBranchService(Service service) {

        dbRef.child("branches").child(branch.getBranchID()).child("services").child(service.getServiceID()).setValue(null);

        Toast.makeText(getApplicationContext(), "Service has been removed from branch.", Toast.LENGTH_SHORT).show();

    }

    private void addBranchService(Service service) {

        dbRef.child("branches").child(branch.getBranchID()).child("services").child(service.getServiceID()).setValue(service);

        Toast.makeText(getApplicationContext(), "Service has been added to branch.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onServiceClick(int position) {

        Service service = servicesList.get(position);

        if (Helper.get().contains(branchServicesList, service)) {
            // This service is already a branch service.
            // Remove it from the branch's services.
            removeBranchService(service);

        } else {
            // This service is not a branch service
            // Go ahead and add it to the branch's services.
            addBranchService(service);
        }

        // Refresh the service requests list so that...
        // (a) if a branch service was REMOVED, stop showing this branch the corresponding service requests.
        // (b) if a branch service was ADDED, show any corresponding service requests to the branch.
        getBranchServiceRequestsListFromFirebase();

    }

    /**** SERVICE REQUESTS METHODS ****/

    private void getBranchServiceRequestsListFromFirebase() {

        Query query = dbRef.child("service-requests");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                clearBranchServiceRequestsList();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServiceRequest serviceRequest = snapshot.getValue(ServiceRequest.class);

                    if (Helper.get().contains(branchServicesList, serviceRequest.getService())) {
                        branchServiceRequestsList.add(serviceRequest);
                    }


                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

        serviceRequestsRecyclerAdapter = new ServiceRequestsRecyclerAdapter(getApplicationContext(), branchServiceRequestsList, EmployeeHomeActivity.this);
        employeeServiceRequestsRecyclerView.setAdapter(serviceRequestsRecyclerAdapter);
        serviceRequestsRecyclerAdapter.notifyDataSetChanged();



    }

    private void clearBranchServiceRequestsList() {

        if (branchServiceRequestsList != null) {
            branchServiceRequestsList.clear();

            if (serviceRequestsRecyclerAdapter != null) {
                serviceRequestsRecyclerAdapter.notifyDataSetChanged();
            }
        }

    }

    private void openServiceRequestDialog(ServiceRequest serviceRequest) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("Service Request", serviceRequest);

        ReviewServiceRequestDialog serviceRequestDialog = new ReviewServiceRequestDialog();
        serviceRequestDialog.setArguments(bundle);
        serviceRequestDialog.show(getSupportFragmentManager(), "Review Service Request Dialog");

    }

    @Override
    public void onServiceRequestClick(int position) {
        ServiceRequest serviceRequest = branchServiceRequestsList.get(position);
        openServiceRequestDialog(serviceRequest);
    }

    private void createDummyServiceRequest() {

        if (!branchServicesList.isEmpty()) {
            Service aService = branchServicesList.get(0);

            ArrayList<ServiceFormEntry<String, String>> aForm = new ArrayList<>();

            aService.getRequiredInfo().forEach(formPrompt -> {
                ServiceFormEntry<String, String> entry = new ServiceFormEntry<>();
                entry.setKey(formPrompt);

                aForm.add(entry);
            });

            aForm.get(0).setValue("Ronaldo");
            aForm.get(1).setValue("Messi");
            aForm.get(2).setValue("30 Oct 1990");
            aForm.get(3).setValue("350 Sunnyside Ave");
            aForm.get(4).setValue("G2");

            ArrayList<ServiceDocument> aDocs = new ArrayList<>();

            aService.getRequiredDocuments().forEach(docName -> {
                ServiceDocument document = new ServiceDocument(docName, "");
            });

            aDocs.add(new ServiceDocument("Proof of Residence", "https://linktoimage.com/image/is/here.png"));

            DatabaseReference serviceReqRef = dbRef.getRef().child("service-requests").push();
            ServiceRequest serviceRequest = new ServiceRequest(serviceReqRef.getKey(), account, aForm, aDocs, aService);

            serviceReqRef.setValue(serviceRequest);

        }
    }

    /**** Receive Object from Fragment ****/

    @Override
    public void communicate(Object obj) {

        if (obj instanceof Branch) {
            branch = (Branch) obj;
            update();
        }

    }

    /**** Update Branch Information ****/

    private void update() {
        branchAddress.setText(branch.getAddress());
        branchPhoneNumber.setText(branch.getPhoneNumber());
        branchWorkingHours.setText("Opens: " + branch.getOpeningTime() + "    Closes: " + branch.getClosingTime());
    }

}