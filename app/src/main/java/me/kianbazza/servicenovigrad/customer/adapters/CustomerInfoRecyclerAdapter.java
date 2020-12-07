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
import me.kianbazza.servicenovigrad.services.ServiceFormEntry;

import java.util.ArrayList;

public class CustomerInfoRecyclerAdapter extends RecyclerView.Adapter<CustomerInfoRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ServiceFormEntry<String, String>> customerInfoList;
    private ArrayList<ViewHolder> viewHoldersList;


    public CustomerInfoRecyclerAdapter(Context context, ArrayList<ServiceFormEntry<String, String>> customerInfoList) {
        this.context = context;
        this.customerInfoList = customerInfoList;

        viewHoldersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_requiredinfo_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerInfoRecyclerAdapter.ViewHolder holder, int position) {
        ServiceFormEntry<String, String> entry = customerInfoList.get(position);

        holder.keyView.setText(entry.getKey());
        holder.valueTextField.setText("");

        viewHoldersList.add(position, holder);

    }

    @Override
    public int getItemCount() {
        return customerInfoList.size();
    }

    public ArrayList<ViewHolder> getViewHoldersList() {
        return viewHoldersList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView keyView;
        public EditText valueTextField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            keyView = itemView.findViewById(R.id.createServiceRequest_customerInfo_keyTextView);
            valueTextField = itemView.findViewById(R.id.createServiceRequest_customerInfo_valueTextField);

        }
    }
}
