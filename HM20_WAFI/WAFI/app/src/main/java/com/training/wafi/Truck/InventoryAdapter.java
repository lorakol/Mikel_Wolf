package com.training.wafi.Truck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.training.wafi.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private List<Item> items = new ArrayList<>();
    private Context context;

    public InventoryAdapter(List<Item> items) {
        this.items = items;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        String formattedString = context.getString(R.string.formatted_item_name, item.getName(), item.getQuantity());
        holder.itemNameTextView.setText(formattedString);

        // Set other fields...

        // Example of highlighting items with low stock:
        // if (item.getStock() < 10) {
        //     holder.itemNameTextView.setTextColor(context.getResources().getColor(R.color.red));
        // }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        // Other views...

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            // Initialize other views...
        }
    }


    public void setItems(List<Item> items) {
        this.items = items;
    }
}

