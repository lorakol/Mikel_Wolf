package com.training.wafi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversationsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get current user ID

        ListView listViewConversations = findViewById(R.id.listViewConversations);
        ArrayList<Conversation> conversationsList = new ArrayList<>();
        ArrayAdapter<Conversation> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, conversationsList);
        listViewConversations.setAdapter(adapter);

        // Set up buttons
        Button backButton = findViewById(R.id.backButton);
        Button newMessageButton = findViewById(R.id.newMessageButton);

        backButton.setOnClickListener(v -> finish()); // Close the current activity

        newMessageButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConversationsActivity.this, SelectUserActivity.class);
            startActivity(intent);
        });


        mDatabase.child("Conversations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversationsList.clear();
                for (DataSnapshot conversationSnapshot : dataSnapshot.getChildren()) {
                    Conversation conversation = conversationSnapshot.getValue(Conversation.class);
                    if (conversation.getUser1Id().equals(userId) || conversation.getUser2Id().equals(userId)) {
                        conversationsList.add(conversation);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        listViewConversations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation selectedConversation = conversationsList.get(position);
                Intent intent = new Intent(ConversationsActivity.this, ChatActivity.class);
                intent.putExtra("conversationId", selectedConversation.getConversationId());
                startActivity(intent);
            }
        });
    }
    // ... Other methods
}
