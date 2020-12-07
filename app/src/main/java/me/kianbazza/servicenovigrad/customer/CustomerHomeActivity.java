package me.kianbazza.servicenovigrad.customer;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Account;
import me.kianbazza.servicenovigrad.accounts.Branch;
import me.kianbazza.servicenovigrad.customer.adapters.BranchesRecyclerAdapter;
import me.kianbazza.servicenovigrad.general.LoginActivity;
import me.kianbazza.servicenovigrad.misc.VerticalSpaceItemDecoration;
import me.kianbazza.servicenovigrad.services.Service;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerHomeActivity extends AppCompatActivity implements BranchesRecyclerAdapter.onBranchListener, AdapterView.OnItemSelectedListener {

    // Activity variables
    private Account account;

    // Database
    private DatabaseReference dbRef;

    // Interface - Services
    private TextView username, role;
    private Button btnLogout;

    // Variables - Branches
    private ArrayList<Branch> branchesList;
    private ArrayList<Service> servicesList;
    private ArrayList<String> serviceNamesList;
    private HashMap<Branch, ArrayList<Service>> branchServicesMap;
    private BranchesRecyclerAdapter branchesRecyclerAdapter;

    // Interface - Branches
    private RecyclerView branchesRecyclerView;
    private Spinner branchSearchMethodSpinner;

        // Search by Address
        private LinearLayout searchByAddress_layout;
        private SearchView searchByAddress_searchView;

        // Search by Working Hours
        private LinearLayout searchByWorkingHours_layout;
        private Spinner searchByWorkingHours_dayOfTheWeekSpinner;
        private TextView searchByWorkingHours_timePickerTextView;

        // Search by Service
        private LinearLayout searchByService_layout;
        private Spinner searchByService_serviceSpinner;
        private ArrayAdapter<String> serviceSpinnerAdapter;

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

        // Branches
        branchesList = new ArrayList<>();

        branchesRecyclerView = findViewById(R.id.customer_branchesRecyclerView);
        branchesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        branchesRecyclerView.setHasFixedSize(true);
        branchesRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(30));

        getBranchesListFromFirebase();

        branchSearchMethodSpinner = findViewById(R.id.customer_branchSearchMethodSpinner);
        ArrayAdapter<CharSequence> branchSearchMethodSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.branchSearchMethod_array, android.R.layout.simple_spinner_item);
        branchSearchMethodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSearchMethodSpinner.setAdapter(branchSearchMethodSpinnerAdapter);
        branchSearchMethodSpinner.setOnItemSelectedListener(this);


        // Search By Address
        searchByAddress_layout = findViewById(R.id.customer_branchSearch_byAddressLayout);
        searchByAddress_searchView = findViewById(R.id.searchByAddressSearchView);

        searchByAddress_layout.setVisibility(View.INVISIBLE);
        searchByAddress_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                branchesRecyclerAdapter.getFilter().filter(newText);
                return true;
            }
        });

        // Search By Working Hours
        searchByWorkingHours_layout = findViewById(R.id.customer_branchSearch_byWorkingHoursLayout);
        searchByWorkingHours_dayOfTheWeekSpinner = findViewById(R.id.searchByWorkingHours_DayOfTheWeekSpinner);
        searchByWorkingHours_timePickerTextView = findViewById(R.id.searchByWorkingHours_TimePickerTextView);

        searchByWorkingHours_layout.setVisibility(View.INVISIBLE);

        ArrayAdapter<CharSequence> dayOfTheWeekSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.DaysOfTheWeek_array, android.R.layout.simple_spinner_item);
        dayOfTheWeekSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchByWorkingHours_dayOfTheWeekSpinner.setAdapter(dayOfTheWeekSpinnerAdapter);

        searchByWorkingHours_timePickerTextView.setText("(Click to filter by time)");
        searchByWorkingHours_timePickerTextView.setOnClickListener(l -> {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("America/Toronto"));

            TimePickerDialog timePickerDialog = new TimePickerDialog(CustomerHomeActivity.this,
                    (view1, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                        dateFormat.setCalendar(calendar);
                        searchByWorkingHours_timePickerTextView.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false);

            timePickerDialog.show();
        });

        searchByWorkingHours_timePickerTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean dayPicked = searchByWorkingHours_dayOfTheWeekSpinner.getSelectedItem().toString().length()!=0;
                boolean timePicked = !searchByWorkingHours_timePickerTextView.getText().toString().equalsIgnoreCase("(Click to filter by time)");
                if (dayPicked && timePicked) {
                    List<Branch> filteredResults = branchesRecyclerAdapter.getBranchesList().stream().filter(branch -> {

                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                        try {
                            Date branchOpeningTime = dateFormat.parse(branch.getOpeningTime());
                            Date branchClosingTime = dateFormat.parse(branch.getClosingTime());

                            Date specifiedTime = dateFormat.parse(searchByWorkingHours_timePickerTextView.getText().toString());

                            Log.d("TestMe", "Branch Open Time: " + branchOpeningTime.toString() + "    Branch Close Time: " + branchClosingTime.toString() + "    User Time: " + specifiedTime.toString());

                            return (branchOpeningTime.before(specifiedTime) || branchOpeningTime.equals(specifiedTime)) && branchClosingTime.after(specifiedTime);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        return false;

                    }).collect(Collectors.toList());

                    branchesRecyclerAdapter.setFilteredBranchesList(new ArrayList<>(filteredResults));
                    branchesRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

        // Search by Service
        servicesList = new ArrayList<>();
        serviceNamesList = new ArrayList<>();

        getServicesListFromFirebase();

        searchByService_layout = findViewById(R.id.customer_branchSearch_byServicesLayout);
        searchByService_serviceSpinner = findViewById(R.id.searchByService_serviceSpinner);

        searchByService_layout.setVisibility(View.INVISIBLE);

        searchByService_serviceSpinner.setOnItemSelectedListener(this);



    }

    private void getBranchesListFromFirebase() {

        Query query = dbRef.child("branches");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearBranchesList();

                dataSnapshot.getChildren().forEach(snapshot -> {

                    boolean doneSetup = snapshot.child("doneSetup").getValue(Boolean.class);
                    String branchID = snapshot.child("branchID").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                    String openingTime = snapshot.child("openingTime").getValue(String.class);
                    String closingTime = snapshot.child("closingTime").getValue(String.class);

                    Branch branch = new Branch(doneSetup, branchID, address, phoneNumber, openingTime, closingTime);

                    branchesList.add(branch);
                });

                branchesRecyclerAdapter = new BranchesRecyclerAdapter(getApplicationContext(), branchesList, CustomerHomeActivity.this);
                branchesRecyclerView.setAdapter(branchesRecyclerAdapter);
                branchesRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getBranchesListFromFirebaseOnce() {

        Query query = dbRef.child("branches");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearBranchesList();

                dataSnapshot.getChildren().forEach(snapshot -> {

                    boolean doneSetup = snapshot.child("doneSetup").getValue(Boolean.class);
                    String branchID = snapshot.child("branchID").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                    String openingTime = snapshot.child("openingTime").getValue(String.class);
                    String closingTime = snapshot.child("closingTime").getValue(String.class);

                    Branch branch = new Branch(doneSetup, branchID, address, phoneNumber, openingTime, closingTime);

                    branchesList.add(branch);
                });

                branchesRecyclerAdapter = new BranchesRecyclerAdapter(getApplicationContext(), branchesList, CustomerHomeActivity.this);
                branchesRecyclerView.setAdapter(branchesRecyclerAdapter);
                branchesRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void clearBranchesList() {

        if (branchesList!=null) {
            branchesList.clear();

            if (branchesRecyclerAdapter!=null) {
                branchesRecyclerAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onBranchClick(int position) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String filterMethod = parent.getItemAtPosition(position).toString();
        if (filterMethod.equalsIgnoreCase("No Filter")) {
            setItemVisibilities(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
        } else if (filterMethod.equalsIgnoreCase("By Address")) {
            setItemVisibilities(View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
        } else if (filterMethod.equalsIgnoreCase("By Working Hours")) {
            setItemVisibilities(View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
        } else if (filterMethod.equalsIgnoreCase("By Service")) {
            setItemVisibilities(View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
        } else {
            if (branchSearchMethodSpinner.getSelectedItem().toString().equalsIgnoreCase("By Service")) {
                filterBranchesByService();

            }
        }

    }

    private void setItemVisibilities(int addressVis, int workingHoursVis, int serviceVis) {
        searchByAddress_layout.setVisibility(addressVis);
        searchByWorkingHours_layout.setVisibility(workingHoursVis);
        searchByService_layout.setVisibility(serviceVis);

        getBranchesListFromFirebaseOnce();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getServicesListFromFirebase() {
        Query query = dbRef.child("services");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                servicesList.clear();

                dataSnapshot.getChildren().forEach(snapshot -> {
                    Service service = snapshot.getValue(Service.class);
                    servicesList.add(service);
                });

                updateServiceNamesList();

                serviceSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, serviceNamesList);
                serviceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchByService_serviceSpinner.setAdapter(serviceSpinnerAdapter);

                serviceSpinnerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateServiceNamesList() {
        ArrayList<String> serviceNamesList = new ArrayList<>();

        for (Service service : servicesList) {
            serviceNamesList.add(service.getName());
        }

        this.serviceNamesList = serviceNamesList;
    }

    private void filterBranchesByService() {

        String filteringService = searchByService_serviceSpinner.getSelectedItem().toString();

        Query query = dbRef.child("branches");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Branch> filteredResults = new ArrayList<>();

                dataSnapshot.getChildren().forEach(snapshot -> {

                    ArrayList<Service> branchServices = new ArrayList<>();

                    snapshot.child("services").getChildren().forEach(serviceSnapshot -> branchServices.add(serviceSnapshot.getValue(Service.class)));

                    Iterator<Service> itr = branchServices.iterator();
                    while (itr.hasNext()) {
                        Service current = itr.next();

                        if (current.getName().equalsIgnoreCase(filteringService)) {

                            boolean doneSetup = snapshot.child("doneSetup").getValue(Boolean.class);
                            String branchID = snapshot.child("branchID").getValue(String.class);
                            String address = snapshot.child("address").getValue(String.class);
                            String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                            String openingTime = snapshot.child("openingTime").getValue(String.class);
                            String closingTime = snapshot.child("closingTime").getValue(String.class);

                            Branch branch = new Branch(doneSetup, branchID, address, phoneNumber, openingTime, closingTime);

                            filteredResults.add(branch);

                            itr.forEachRemaining(nextOne -> {
                            });
                        }

                    }

                });

                branchesRecyclerAdapter.setFilteredBranchesList(new ArrayList<>(filteredResults));
                branchesRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });

    }
}