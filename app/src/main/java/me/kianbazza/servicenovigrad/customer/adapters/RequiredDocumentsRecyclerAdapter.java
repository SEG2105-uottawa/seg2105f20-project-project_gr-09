package me.kianbazza.servicenovigrad.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.ServiceDocument;

import java.util.ArrayList;

public class RequiredDocumentsRecyclerAdapter extends RecyclerView.Adapter<RequiredDocumentsRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ServiceDocument> requiredDocumentsList;
    private ArrayList<ViewHolder> viewHoldersList;

    public RequiredDocumentsRecyclerAdapter(Context context, ArrayList<ServiceDocument> requiredDocumentsList) {
        this.context = context;
        this.requiredDocumentsList = requiredDocumentsList;

        viewHoldersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RequiredDocumentsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_requireddocuments_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequiredDocumentsRecyclerAdapter.ViewHolder holder, int position) {
        ServiceDocument document = requiredDocumentsList.get(position);

        holder.keyView.setText(document.getName());
        holder.valueTextField.setText("");
        holder.valueTextField.setHint("Paste image link here");

        viewHoldersList.add(position, holder);

    }

    @Override
    public int getItemCount() {
        return requiredDocumentsList.size();
    }

    public ArrayList<ViewHolder> getViewHoldersList() {
        return viewHoldersList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView keyView;
        public EditText valueTextField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            keyView = itemView.findViewById(R.id.createServiceRequest_providedDocs_keyTextView);
            valueTextField = itemView.findViewById(R.id.createServiceRequest_providedDocs_valueTextField);

        }
    }

}
