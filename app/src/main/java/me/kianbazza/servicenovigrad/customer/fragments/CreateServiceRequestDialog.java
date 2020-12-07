package me.kianbazza.servicenovigrad.customer.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Account;
import me.kianbazza.servicenovigrad.customer.adapters.CustomerInfoRecyclerAdapter;
import me.kianbazza.servicenovigrad.customer.adapters.RequiredDocumentsRecyclerAdapter;
import me.kianbazza.servicenovigrad.services.*;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateServiceRequestDialog extends AppCompatDialogFragment {

    // Database
    private DatabaseReference dbRef;

    // Account
    private Account account;

    // Service
    private Service service;
    private ServiceRequest serviceRequest;

    // Customer Info
    private ArrayList<ServiceFormEntry<String, String>> customerInfoList;
    private RecyclerView customerInfoRecyclerView;
    private CustomerInfoRecyclerAdapter customerInfoRecyclerAdapter;

    // Required Documents
    private ArrayList<ServiceDocument> requiredDocumentsList;
    private RecyclerView requiredDocumentsRecyclerView;
    private RequiredDocumentsRecyclerAdapter requiredDocumentsRecyclerAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_servicerequest, null);

        dbRef = FirebaseDatabase.getInstance().getReference();

        account = getArguments().getParcelable("Account");
        service = getArguments().getParcelable("Service");

        // Customer Info
        customerInfoList = new ArrayList<>();
        service.getRequiredInfo().forEach(stringKey -> customerInfoList.add(new ServiceFormEntry<>(stringKey, "")));

        customerInfoRecyclerView = view.findViewById(R.id.createServiceRequest_customerInfoRecyclerView);
        customerInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerInfoRecyclerView.setHasFixedSize(true);

        customerInfoRecyclerAdapter = new CustomerInfoRecyclerAdapter(getActivity(), customerInfoList);
        customerInfoRecyclerView.setAdapter(customerInfoRecyclerAdapter);
        customerInfoRecyclerAdapter.notifyDataSetChanged();

        // Required Documents
        requiredDocumentsList = new ArrayList<>();
        service.getRequiredDocuments().forEach(docName -> requiredDocumentsList.add(new ServiceDocument(docName, "")));

        requiredDocumentsRecyclerView = view.findViewById(R.id.createServiceRequest_documentsRecyclerView);
        requiredDocumentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requiredDocumentsRecyclerView.setHasFixedSize(true);

        requiredDocumentsRecyclerAdapter = new RequiredDocumentsRecyclerAdapter(getActivity(), requiredDocumentsList);
        requiredDocumentsRecyclerView.setAdapter(requiredDocumentsRecyclerAdapter);
        requiredDocumentsRecyclerAdapter.notifyDataSetChanged();

//        TextWatcher textWatcher = new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) { }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                if (validateFields()) {
//                    AlertDialog dialog = (AlertDialog) getDialog();
//                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                } else {
//                    AlertDialog dialog = (AlertDialog) getDialog();
//                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                }
//            }
//        };

//        customerInfoRecyclerAdapter.getViewHoldersList().forEach(holder -> holder.valueTextField.addTextChangedListener(textWatcher));
//        requiredDocumentsRecyclerAdapter.getViewHoldersList().forEach(holder -> holder.valueTextField.addTextChangedListener(textWatcher));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setTitle("Creating " + service.getName() + " Service Request")
                .setNegativeButton("Cancel", (dialog, which) -> {



                })
                .setPositiveButton("Submit", null);

        return builder.create();

    }

    private boolean validateFields() {

        for (CustomerInfoRecyclerAdapter.ViewHolder holder : customerInfoRecyclerAdapter.getViewHoldersList()) {
            if (TextUtils.isEmpty(holder.valueTextField.getText().toString().trim())) {
                return false;
            }
        }

        for (RequiredDocumentsRecyclerAdapter.ViewHolder holder : requiredDocumentsRecyclerAdapter.getViewHoldersList()) {
            if (TextUtils.isEmpty(holder.valueTextField.getText().toString().trim())) {
                return false;
            }
        }

        return true;

    }

    private void submitServiceRequest() {
        
        if (validateFields()) {
            
            customerInfoRecyclerAdapter.getViewHoldersList().forEach(holder -> {
                String entryValue = holder.valueTextField.getText().toString().trim();
                customerInfoList.get(holder.getAdapterPosition()).setValue(entryValue);
            });
            
            requiredDocumentsRecyclerAdapter.getViewHoldersList().forEach(holder -> {
                String docLink = holder.valueTextField.getText().toString().trim();
                requiredDocumentsList.get(holder.getAdapterPosition()).setLinkToUpload(docLink);
            });
            
            DatabaseReference serviceRequestRef = dbRef.child("service-requests").push();
            
            serviceRequest = new ServiceRequest(serviceRequestRef.getKey(), ServiceRequestStatus.WAITING_FOR_REVIEW, account, customerInfoList, requiredDocumentsList, service);
            serviceRequestRef.setValue(serviceRequest);

            Toast.makeText(getActivity(), "Your service request was successfully submitted.", Toast.LENGTH_SHORT).show();

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            
        } else {
            Toast.makeText(getActivity(), "Oops, you left a field blank!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // Disable positive button by default
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            submitServiceRequest();
        });
    }
}
