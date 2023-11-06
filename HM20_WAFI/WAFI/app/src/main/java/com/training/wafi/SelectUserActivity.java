package com.training.wafi;

import android.os.Bundle;
import android.util.Log;
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
import com.training.wafi.UserAppAccess.User;
import java.util.ArrayList;

public class SelectUserActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail(); // Get current user email
        String currentUserTransformedEmail = currentUserEmail.replace(".", ",");

        ListView listViewUsers = findViewById(R.id.listViewUsers);
        ArrayList<User> usersList = new ArrayList<>();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersList);
        listViewUsers.setAdapter(adapter);

        // Retrieve the organization of the current user
        mDatabase.child("users").child(currentUserTransformedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("SelectUserActivity", "DataSnapshot: " + dataSnapshot.toString());
                User currentUser = dataSnapshot.getValue(User.class);
                if (currentUser != null) {
                    String currentOrganization = currentUser.getOrganization();

                    // Fetch users from the same organization and populate the list
                    mDatabase.child("users").orderByChild("organization").equalTo(currentOrganization)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    usersList.clear();
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        User user = userSnapshot.getValue(User.class);
                                        if (!user.getEmail().replace(".", ",").equals(currentUserTransformedEmail)) { // Exclude the current user
                                            usersList.add(user);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Handle error
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = usersList.get(position);
                // Implement logic to start a new conversation with the selected user
            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the activity and go back
            }
        });
    }
}
