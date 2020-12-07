package me.kianbazza.servicenovigrad.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.ServiceRequest;

import java.util.ArrayList;

public class ServiceRequestsRecyclerAdapter extends RecyclerView.Adapter<ServiceRequestsRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServiceRequest> serviceRequestsList;

    public ServiceRequestsRecyclerAdapter(Context context, ArrayList<ServiceRequest> serviceRequestsList) {
        this.context = context;
        this.serviceRequestsList = serviceRequestsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_servicerequest_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceRequestsRecyclerAdapter.ViewHolder holder, int position) {

        ServiceRequest serviceRequest = serviceRequestsList.get(position);

        holder.serviceNameTextView.setText(serviceRequest.getService().getName());
        holder.requestIDTextView.setText("Request ID: " + serviceRequest.getRequestID());
        holder.requestStatusTextView.setText("Request Status: " + serviceRequest.getRequestStatus());

    }

    @Override
    public int getItemCount() {
        return serviceRequestsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView serviceNameTextView, requestIDTextView, requestStatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceNameTextView = itemView.findViewById(R.id.customer_serviceRequest_serviceNameTextView);
            requestIDTextView = itemView.findViewById(R.id.customer_serviceRequest_requestIDTextView);
            requestStatusTextView = itemView.findViewById(R.id.customer_serviceRequest_requestStatusTextView);

        }
    }
}
