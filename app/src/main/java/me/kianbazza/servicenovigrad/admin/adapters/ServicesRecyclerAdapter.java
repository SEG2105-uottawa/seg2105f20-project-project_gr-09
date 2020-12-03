package me.kianbazza.servicenovigrad.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.Service;

import java.util.ArrayList;

public class ServicesRecyclerAdapter extends RecyclerView.Adapter<ServicesRecyclerAdapter.ViewHolder> {

    private static final String tag = "RecyclerView";
    private Context context;
    private ArrayList<Service> servicesList;
    private OnServiceListener onServiceListener;

    public ServicesRecyclerAdapter(Context context, ArrayList<Service> servicesList, OnServiceListener onServiceListener) {
        this.context = context;
        this.servicesList = servicesList;
        this.onServiceListener = onServiceListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_service_item, parent, false);

        return new ViewHolder(view, onServiceListener);

    }

    @Override
    public void onBindViewHolder(ServicesRecyclerAdapter.ViewHolder holder, int position) {

        holder.serviceNameView.setText(servicesList.get(position).getName());
        holder.servicePriceView.setText(Double.toString(servicesList.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Interface
        TextView serviceNameView, servicePriceView;
        OnServiceListener onServiceListener;

        public ViewHolder(View itemView, OnServiceListener onServiceListener) {
            super(itemView);

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
