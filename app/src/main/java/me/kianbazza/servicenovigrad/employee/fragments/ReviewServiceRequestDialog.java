package me.kianbazza.servicenovigrad.employee.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.employee.adapters.CustomerInfoRecyclerAdapter;
import me.kianbazza.servicenovigrad.employee.adapters.ProvidedDocumentsRecyclerAdapter;
import me.kianbazza.servicenovigrad.services.Service;
import me.kianbazza.servicenovigrad.services.ServiceDocument;
import me.kianbazza.servicenovigrad.services.ServiceFormEntry;
import me.kianbazza.servicenovigrad.services.ServiceRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReviewServiceRequestDialog extends AppCompatDialogFragment implements ProvidedDocumentsRecyclerAdapter.OnProvidedDocListener {

    // Database
    DatabaseReference dbRef;

    // Service Request
    private ServiceRequest serviceRequest;

    // Customer Info
    private ArrayList<ServiceFormEntry<String, String>> customerInfoList;
    private RecyclerView customerInfoRecyclerView;
    private CustomerInfoRecyclerAdapter customerInfoRecyclerAdapter;


    // Provided Documents
    private ArrayList<ServiceDocument> providedDocumentsList;
    private RecyclerView providedDocumentsRecyclerView;
    private ProvidedDocumentsRecyclerAdapter providedDocumentsRecyclerAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_review_servicerequest, null);

        dbRef = FirebaseDatabase.getInstance().getReference();
        serviceRequest = getArguments().getParcelable("Service Request");


        // Customer Info
        customerInfoList = serviceRequest.getCustomerInfo();

        customerInfoRecyclerView = view.findViewById(R.id.reviewServiceRequest_customerInfoRecyclerView);
        customerInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerInfoRecyclerView.setHasFixedSize(true);

        customerInfoRecyclerAdapter = new CustomerInfoRecyclerAdapter(getActivity(), customerInfoList);
        customerInfoRecyclerView.setAdapter(customerInfoRecyclerAdapter);

        customerInfoRecyclerAdapter.notifyDataSetChanged();


        // Provided Documents
        providedDocumentsList = serviceRequest.getRequiredDocuments();

        providedDocumentsRecyclerView = view.findViewById(R.id.reviewServiceRequest_documentsRecyclerView);
        providedDocumentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        providedDocumentsRecyclerView.setHasFixedSize(true);

        providedDocumentsRecyclerAdapter = new ProvidedDocumentsRecyclerAdapter(getActivity(), providedDocumentsList, ReviewServiceRequestDialog.this);
        providedDocumentsRecyclerView.setAdapter(providedDocumentsRecyclerAdapter);

        providedDocumentsRecyclerAdapter.notifyDataSetChanged();


        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setTitle("Reviewing Service Request")
                .setNegativeButton("Deny", (dialog, which) -> {

                    denyServiceRequest();

                })
                .setPositiveButton("Approve", ((dialog, which) -> {

                    approveServiceRequest();

                }))
                .setNeutralButton("Cancel", ((dialog, which) -> {


                }));

        return builder.create();

    }

    private void approveServiceRequest() {

    }

    private void denyServiceRequest() {

    }

    private void openViewServiceDocumentDialog(ServiceDocument serviceDocument) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("Service Document", serviceDocument);



    }

    @Override
    public void onProvidedDocClick(int position) {

        ServiceDocument serviceDocument = providedDocumentsList.get(position);
        openViewServiceDocumentDialog(serviceDocument);

    }
}
