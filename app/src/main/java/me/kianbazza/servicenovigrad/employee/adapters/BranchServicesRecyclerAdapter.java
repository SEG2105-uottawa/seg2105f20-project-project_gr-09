package me.kianbazza.servicenovigrad.employee.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.misc.Helper;
import me.kianbazza.servicenovigrad.services.Service;

import java.util.ArrayList;

public class BranchServicesRecyclerAdapter extends RecyclerView.Adapter<BranchServicesRecyclerAdapter.ViewHolder> {

    private static final String tag = "RecyclerView";
    private Context context;
    private ArrayList<Service> servicesList, branchServicesList;
    private BranchServicesRecyclerAdapter.OnServiceListener onServiceListener;

    public BranchServicesRecyclerAdapter(Context context, ArrayList<Service> servicesList, ArrayList<Service> branchServicesList, OnServiceListener onServiceListener) {
        this.context = context;
        this.servicesList = servicesList;
        this.branchServicesList = branchServicesList;
        this.onServiceListener = onServiceListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_service_item, parent, false);

        return new ViewHolder(view, onServiceListener);
    }

    @Override
    public void onBindViewHolder(BranchServicesRecyclerAdapter.ViewHolder holder, int position) {

        holder.serviceNameView.setText(servicesList.get(position).getName());
        holder.servicePriceView.setText(Double.toString(servicesList.get(position).getPrice()));

        if (Helper.get().contains(branchServicesList, servicesList.get(position))) {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#b3ffb3"));

        }
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout relativeLayout;
        TextView serviceNameView, servicePriceView;
        OnServiceListener onServiceListener;

        public ViewHolder(View itemView, OnServiceListener onServiceListener) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.adminServiceItem_RelativeLayout);
            serviceNameView = itemView.findViewById(R.id.serviceNameView);
            servicePriceView = itemView.findViewById(R.id.servicePriceView);
            this.onServiceListener = onServiceListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onServiceListener.onServiceClick(getAdapterPosition());

        }
    }

    public interface OnServiceListener {

        void onServiceClick(int position);

    }
}
