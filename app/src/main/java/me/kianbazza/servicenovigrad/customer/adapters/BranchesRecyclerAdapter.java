package me.kianbazza.servicenovigrad.customer.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import me.kianbazza.servicenovigrad.R;
import me.kianbazza.servicenovigrad.accounts.Branch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class BranchesRecyclerAdapter extends RecyclerView.Adapter<BranchesRecyclerAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Branch> branchesList;
    private ArrayList<Branch> filteredBranchesList;
    private BranchesRecyclerAdapter.onBranchListener onBranchListener;

    public BranchesRecyclerAdapter(Context context, ArrayList<Branch> branchesList, BranchesRecyclerAdapter.onBranchListener onBranchListener) {
        this.context = context;
        this.filteredBranchesList = branchesList;
        this.branchesList = branchesList;
        this.onBranchListener = onBranchListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_branch_item, parent, false);

        return new ViewHolder(view, onBranchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchesRecyclerAdapter.ViewHolder holder, int position) {

        Branch branch = filteredBranchesList.get(position);

        holder.branchAddressView.setText(branch.getAddress());
        holder.branchPhoneNumberView.setText(branch.getPhoneNumber());
        holder.branchWorkingHoursView.setText(branch.generateWorkingHours());

    }

    @Override
    public int getItemCount() {
        return filteredBranchesList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Branch> filteredBranchesList = null;
                if (constraint.length()==0) {
                    filteredBranchesList = branchesList;
                } else {
                    filteredBranchesList = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredBranchesList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredBranchesList = (ArrayList<Branch>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private ArrayList<Branch> getFilteredResults(String constraint) {
        ArrayList<Branch> results = new ArrayList<>();

        for (Branch b : branchesList) {
            if (b.getAddress().toLowerCase().contains(constraint)) {
                results.add(b);
            }
        }

        return results;

    }

    public ArrayList<Branch> getBranchesList() {
        return branchesList;
    }

    public void setFilteredBranchesList(ArrayList<Branch> filteredBranchesList) {
        this.filteredBranchesList = filteredBranchesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView branchAddressView, branchPhoneNumberView, branchWorkingHoursView;
        onBranchListener onBranchListener;

        public ViewHolder(@NonNull View itemView, BranchesRecyclerAdapter.onBranchListener onBranchListener) {
            super(itemView);

            branchAddressView = itemView.findViewById(R.id.customer_branchAddressTextView);
            branchPhoneNumberView = itemView.findViewById(R.id.customer_branchPhoneNumberTextView);
            branchWorkingHoursView = itemView.findViewById(R.id.customer_branchWorkingHoursTextView);

            this.onBranchListener = onBranchListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onBranchListener.onBranchClick(getAdapterPosition());
        }
    }

    public interface onBranchListener {

        void onBranchClick(int position);
    }
}
