package me.kianbazza.servicenovigrad.employee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.ServiceDocument;

import java.util.ArrayList;

public class ProvidedDocumentsRecyclerAdapter extends RecyclerView.Adapter<ProvidedDocumentsRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ServiceDocument> providedDocumentsList;
    private OnProvidedDocListener onProvidedDocListener;

    // Main Constructor
    public ProvidedDocumentsRecyclerAdapter(Context context, ArrayList<ServiceDocument> providedDocumentsList, OnProvidedDocListener onProvidedDocListener) {
        this.context = context;
        this.providedDocumentsList = providedDocumentsList;
        this.onProvidedDocListener = onProvidedDocListener;
    }

    /**** Superclass Methods ****/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_provideddocuments_item, parent, false);

        return new ViewHolder(view, onProvidedDocListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ProvidedDocumentsRecyclerAdapter.ViewHolder holder, int position) {

        ServiceDocument serviceDocument = providedDocumentsList.get(position);

        holder.docKeyView.setText(serviceDocument.getName());
        holder.docValueView.setText(serviceDocument.getLinkToUpload());

    }

    @Override
    public int getItemCount() {
        return providedDocumentsList.size();
    }

    /**** Custom ViewHolder Class ****/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView docKeyView, docValueView;
        OnProvidedDocListener onProvidedDocListener;

        public ViewHolder(View itemView, OnProvidedDocListener onProvidedDocListener) {
            super(itemView);

            docKeyView = itemView.findViewById(R.id.reviewServiceRequest_providedDocs_keyTextView);
            docValueView = itemView.findViewById(R.id.reviewServiceRequest_providedDocs_valueTextView);

            this.onProvidedDocListener = onProvidedDocListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            onProvidedDocListener.onProvidedDocClick(getAdapterPosition());

        }
    }

    /**** Custom OnClick Listener ****/

    public interface OnProvidedDocListener {

        void onProvidedDocClick(int position);

    }

}
