package com.training.wafi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final ArrayList<ChatMessage> messagesList;

    public ChatAdapter(ArrayList<ChatMessage> messagesList) {
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage chatMessage = messagesList.get(position);
        holder.messageTextView.setText(chatMessage.getMessageContent());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
