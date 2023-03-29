package com.example.googlebookslisting;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    private List<Model> models;

    public ModelAdapter(List<Model> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = models.get(position);
        holder.modelTV.setText(model.getTitle());
        try{
            Picasso.get().load(model.getThumbnailUrl()).into(holder.modelIV);
        }catch (Exception er){
            int n = 0;
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView modelTV;
        ImageView modelIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            modelTV = itemView.findViewById(R.id.modelTV);
            modelIV = itemView.findViewById(R.id.modelIV);
        }
    }
}


