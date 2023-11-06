package com.training.digginggame.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.training.digginggame.GameModel;
import com.training.digginggame.R;
import com.training.digginggame.object.Item;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {


    private RecyclerView inventoryRecyclerView;
    private TextView totalGoldTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventoryRecyclerView = findViewById(R.id.inventoryRecyclerView);
        totalGoldTextView = findViewById(R.id.totalGoldTextView);

        // You might get the GameModel from a singleton, a passed intent, database, etc.
        // For this example, we are instantiating it directly which might not be ideal for your actual implementation.
        //gameModel = new GameModel();

        List<Item> foundItems = GameActivity.gameModel.getFoundItems();
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventoryRecyclerView.setAdapter(new InventoryAdapter(foundItems));

        // Update the total gold
        totalGoldTextView.setText(String.format("Total Gold: %d", GameActivity.gameModel.getTotalGold()));
    }

    private class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

        private List<Item> items;

        public InventoryAdapter(List<Item> items) {
            this.items = items;
        }

        @Override
        public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            return new InventoryViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(InventoryViewHolder holder, int position) {
            Item item = items.get(position);
            holder.textView.setText(item.toString());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class InventoryViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public InventoryViewHolder(TextView itemView) {
                super(itemView);
                this.textView = itemView;
            }
        }
    }
}
