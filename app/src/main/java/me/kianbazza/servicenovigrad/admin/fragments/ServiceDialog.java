package me.kianbazza.servicenovigrad.admin.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.misc.Helper;
import me.kianbazza.servicenovigrad.services.Service;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceDialog extends AppCompatDialogFragment {

    // Interface
    EditText name, price, requiredInfo, requiredDocs;
    Button btnSave, btnDelete;
    String dialogTitle;

    // Stored data
    Service service;
    boolean isEditingService;

    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_modify_service, null);

        name = view.findViewById(R.id.serviceDisplaynameTextField);
        price = view.findViewById(R.id.servicePriceTextField);
        requiredInfo = view.findViewById(R.id.serviceRequiredInfoTextField);
        requiredDocs = view.findViewById(R.id.serviceDocumentsTextField);

        btnSave = view.findViewById(R.id.btn_saveService);
        btnDelete = view.findViewById(R.id.btn_deleteService);

        btnSave.setOnClickListener(l -> {

            if (isEditingService) {
                saveChangesToService();
            } else {
                createService();
            }

        });

        btnDelete.setOnClickListener(l -> deleteService());

        assert getArguments() != null;
        isEditingService = getArguments().getBoolean("isEditingService");

        if (isEditingService) {
            service = getArguments().getParcelable("Service");
            if (service!=null) {
                name.setText(service.getName());
                price.setText(Double.toString(service.getPrice()));
                requiredInfo.setText(service.generateCustomerInfoWithSeparator());
                requiredDocs.setText(service.generateDocumentNamesWithSeparator());
            }
        }

        setDialogTitle();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setPositiveButton("Okay", (dialog, which) -> {

                });

        return builder.create();

    }

    private void createService() {

        Service service;

        String nameStr, priceStr, requiredInfoStr, requiredDocsStr;

        nameStr = name.getText().toString().trim();
        priceStr = price.getText().toString().trim();
        requiredInfoStr = requiredInfo.getText().toString().trim();
        requiredDocsStr = requiredDocs.getText().toString().trim();


        // Validate fields
        if (TextUtils.isEmpty(nameStr)) {
            Toast.makeText(getActivity(), "Display name cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(priceStr)) {
            Toast.makeText(getActivity(), "Price cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(requiredInfoStr)) {
            Toast.makeText(getActivity(), "Required customer info cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(requiredDocsStr)) {
            Toast.makeText(getActivity(), "Required documents cannot be blank.", Toast.LENGTH_SHORT).show();
        } else {
            // Fields have passed initial validation
            DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference().child("services");

            double price = Double.parseDouble(priceStr);

            ArrayList<String> requiredInfo = new ArrayList<>(Arrays.asList(Helper.get().trimArray(requiredInfoStr.split(";"))));
            ArrayList<String> requiredDocs = new ArrayList<>(Arrays.asList(Helper.get().trimArray( requiredDocsStr.split(";") )));

            // Create new service entry in DB with unique ID
            DatabaseReference serviceRef = servicesRef.push();
            // Get the unique ID
            String id = serviceRef.getKey();

            service = new Service(id, nameStr, price, requiredInfo, requiredDocs);

            serviceRef.setValue(service);
            Toast.makeText(getActivity(), "Service created successfully!", Toast.LENGTH_SHORT).show();

            dismiss();


        }
    }

    private void saveChangesToService() {

        Service service = this.service;

        String nameStr, priceStr, requiredInfoStr, requiredDocsStr;

        nameStr = name.getText().toString().trim();
        priceStr = price.getText().toString().trim();
        requiredInfoStr = requiredInfo.getText().toString().trim();
        requiredDocsStr = requiredDocs.getText().toString().trim();


        // Validate fields
        if (TextUtils.isEmpty(nameStr)) {
            Toast.makeText(getActivity(), "Display name cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(priceStr)) {
            Toast.makeText(getActivity(), "Price cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(requiredInfoStr)) {
            Toast.makeText(getActivity(), "Required customer info cannot be blank.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(requiredDocsStr)) {
            Toast.makeText(getActivity(), "Required documents cannot be blank.", Toast.LENGTH_SHORT).show();
        } else {
            // Fields have passed initial validation
            double price = Double.parseDouble(priceStr);

            ArrayList<String> requiredInfo = new ArrayList<>(Arrays.asList(Helper.get().trimArray(requiredInfoStr.split(";"))));
            ArrayList<String> requiredDocs = new ArrayList<>(Arrays.asList(Helper.get().trimArray(requiredDocsStr.split(";"))));

            service.setName(nameStr);
            service.setPrice(price);
            service.setRequiredInfo(requiredInfo);
            service.setRequiredDocuments(requiredDocs);

            DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference().child("services");
            servicesRef.child(service.getServiceID()).setValue(service);

            Toast.makeText(getActivity(), "Service has been updated!", Toast.LENGTH_SHORT).show();

            dismiss();
        }


    }

    private void deleteService() {
        DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference().child("services");

        servicesRef.child(service.getServiceID()).setValue(null);

        Toast.makeText(getActivity(), "Service has been deleted.", Toast.LENGTH_SHORT).show();
        dismiss();

    }

    private void setDialogTitle() {

        if (isEditingService) {
            dialogTitle = "Editing " + service.getName() + " Service";
        } else {
            dialogTitle = "Create Service";
        }

    }

}
