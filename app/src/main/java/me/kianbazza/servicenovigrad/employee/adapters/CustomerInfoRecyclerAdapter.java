package me.kianbazza.servicenovigrad.employee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.ServiceFormEntry;

import java.util.ArrayList;

public class CustomerInfoRecyclerAdapter extends RecyclerView.Adapter<CustomerInfoRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ServiceFormEntry<String, String>> customerInfoList;

    // Main Constructor
    public CustomerInfoRecyclerAdapter(Context context, ArrayList<ServiceFormEntry<String, String>> customerInfoList) {
        this.context = context;
        this.customerInfoList = customerInfoList;
    }

    /**** Superclass Methods ****/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_customerinfo_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomerInfoRecyclerAdapter.ViewHolder holder, int position) {

        ServiceFormEntry<String, String> entry = customerInfoList.get(position);

        holder.infoKeyView.setText(entry.getKey());
        holder.infoValueView.setText(entry.getValue());

    }

    @Override
    public int getItemCount() {
        return customerInfoList.size();
    }

    /**** Custom ViewHolder Class ****/

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView infoKeyView, infoValueView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            infoKeyView = itemView.findViewById(R.id.reviewServiceRequest_customerInfo_keyTextView);
            infoValueView = itemView.findViewById(R.id.reviewServiceRequest_customerInfo_valueTextView);

        }
    }
}
