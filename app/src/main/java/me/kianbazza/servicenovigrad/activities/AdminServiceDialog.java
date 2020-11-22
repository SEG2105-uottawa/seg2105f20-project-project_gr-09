package me.kianbazza.servicenovigrad.activities;

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
import me.kianbazza.servicenovigrad.services.ServiceDocument;
import me.kianbazza.servicenovigrad.services.ServiceFormEntry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminServiceDialog extends AppCompatDialogFragment {

    EditText name, price, requiredInfo, requiredDocs;
    Button btnSave, btnDelete;

    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_modify_service, null);

        builder.setView(view)
                .setTitle("Service Modify")
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setPositiveButton("Okay", (dialog, which) -> {

                });

        name = view.findViewById(R.id.serviceDisplaynameTextField);
        price = view.findViewById(R.id.servicePriceTextField);
        requiredInfo = view.findViewById(R.id.serviceRequiredInfoTextField);
        requiredDocs = view.findViewById(R.id.serviceDocumentsTextField);

        btnSave = view.findViewById(R.id.btn_saveService);
        btnDelete = view.findViewById(R.id.btn_deleteService);

        btnSave.setOnClickListener(l -> createService());

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

            ArrayList<ServiceFormEntry> form = new ArrayList<>();
            String[] infoEntryNames = Helper.trimArray( requiredInfoStr.split(";") );

            for (String infoEntryName : infoEntryNames) {
                form.add(new ServiceFormEntry(infoEntryName, null));
            }

            ArrayList<ServiceDocument> requiredDocs = new ArrayList<>();
            String[] docNames = Helper.trimArray( requiredDocsStr.split(";") );

            for (String docName : docNames) {
//                docNames[i] = Helper.threeDigitInt(i) + " " + docNames[i];
                requiredDocs.add(new ServiceDocument(docName, null));
            }

            // Create new service entry in DB with unique ID
            DatabaseReference serviceRef = servicesRef.push();
            // Get the unique ID
            String id = serviceRef.getKey();

            service = new Service(id, nameStr, price, form, requiredDocs);

            serviceRef.setValue(service);
            Toast.makeText(getActivity(), "Service created successfully!", Toast.LENGTH_SHORT).show();


        }
    }

}
