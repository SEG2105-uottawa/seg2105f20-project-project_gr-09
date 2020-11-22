package me.kianbazza.servicenovigrad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.services.Service;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String tag = "RecyclerView";
    private Context context;
    private ArrayList<Service> servicesList;

    public RecyclerAdapter(Context context, ArrayList<Service> servicesList) {
        this.context = context;
        this.servicesList = servicesList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_service_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        holder.serviceNameView.setText(servicesList.get(position).getName());
        holder.servicePriceView.setText(Double.toString(servicesList.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        // Interface
        TextView serviceNameView, servicePriceView;

        public ViewHolder(View itemView) {
            super(itemView);

            serviceNameView = itemView.findViewById(R.id.serviceNameView);
            servicePriceView = itemView.findViewById(R.id.servicePriceView);
        }
    }
}
