package com.training.wafi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<ChatMessage> messagesList = new ArrayList<>();
    private ChatAdapter adapter;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        RecyclerView chatRecyclerView = findViewById(R.id.chatRecyclerView);
        adapter = new ChatAdapter(messagesList);
        chatRecyclerView.setAdapter(adapter);

        String conversationId = getIntent().getStringExtra("conversationId");
        mDatabase.child("Messages").orderByChild("conversationId").equalTo(conversationId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messagesList.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage message = messageSnapshot.getValue(ChatMessage.class);
                    messagesList.add(message);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        EditText messageEditText = findViewById(R.id.messageEditText);
        Button sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(view -> {
            String messageContent = messageEditText.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                ChatMessage newMessage = new ChatMessage(currentUser, messageContent);
                mDatabase.child("Messages").push().setValue(newMessage);
                messageEditText.setText("");
            }
        });
    }
    // ... Other methods
}
