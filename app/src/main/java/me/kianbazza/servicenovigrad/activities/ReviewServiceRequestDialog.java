package me.kianbazza.servicenovigrad.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.ServiceRequest;

public class ReviewServiceRequestDialog extends AppCompatDialogFragment {

    // Service Request
    private ServiceRequest serviceRequest;

    // Interface
    private RecyclerView customerInfoRecyclerView, providedDocumentsRecyclerView;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        serviceRequest = getArguments().getParcelable("Service Request");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_review_servicerequest, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setTitle("Review Service Request")
                .setNegativeButton("Deny", (dialog, which) -> {

                })
                .setPositiveButton("Approve", ((dialog, which) -> {

                }))
                .setNeutralButton("Cancel", ((dialog, which) -> {

                }));

        return builder.create();

    }
}
