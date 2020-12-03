package me.kianbazza.servicenovigrad.employee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.ServiceRequest;

import java.util.ArrayList;

public class ServiceRequestsRecyclerAdapter extends RecyclerView.Adapter<ServiceRequestsRecyclerAdapter.ViewHolder> {

    private static final String tag = "RecyclerView";
    private Context context;
    private ArrayList<ServiceRequest> branchServiceRequestsList;
    private OnServiceRequestListener onServiceRequestListener;

    public ServiceRequestsRecyclerAdapter(Context context, ArrayList<ServiceRequest> branchServiceRequestsList, OnServiceRequestListener onServiceRequestListener) {
        this.context = context;
        this.branchServiceRequestsList = branchServiceRequestsList;
        this.onServiceRequestListener = onServiceRequestListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_servicerequest_item, parent, false);

        return new ViewHolder(view, onServiceRequestListener);
    }

    @Override
    public void onBindViewHolder(ServiceRequestsRecyclerAdapter.ViewHolder holder, int position) {

        ServiceRequest serviceRequest = branchServiceRequestsList.get(position);

        holder.serviceRequestNameView.setText("Type: " + serviceRequest.getService().getName());
        holder.serviceRequestCustomerNameView.setText("Submitted By: " + serviceRequest.getCustomer().getUsername() + " (" + serviceRequest.getCustomer().getEmail() + ")");

    }

    @Override
    public int getItemCount() {
        return branchServiceRequestsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView serviceRequestNameView, serviceRequestCustomerNameView;
        OnServiceRequestListener onServiceRequestListener;

        public ViewHolder(View itemView, OnServiceRequestListener onServiceRequestListener) {
            super(itemView);

            serviceRequestNameView = itemView.findViewById(R.id.serviceRequestNameView);
            serviceRequestCustomerNameView = itemView.findViewById(R.id.serviceRequestCustomerNameView);
            this.onServiceRequestListener = onServiceRequestListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onServiceRequestListener.onServiceRequestClick(getAdapterPosition());

        }
    }

    public interface OnServiceRequestListener {

        void onServiceRequestClick(int position);

    }

}
