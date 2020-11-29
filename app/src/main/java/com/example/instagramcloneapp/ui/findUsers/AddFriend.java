package com.example.instagramcloneapp.ui.findUsers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.instagramcloneapp.Adapter.FindFriendAdapter;
import com.example.instagramcloneapp.MainActivity;
import com.example.instagramcloneapp.R;
import com.example.instagramcloneapp.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AddFriend extends AppCompatActivity {
    RecyclerView recyclerView;
    FindFriendAdapter findFriendAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> arrayList;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        back = findViewById(R.id.button9);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        arrayList = new ArrayList<>();
        getUserMailFD();
        recyclerView = findViewById(R.id.usersRecycleview);
        findFriendAdapter = new FindFriendAdapter(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(findFriendAdapter);


    }

    private void getUserMailFD() {

        CollectionReference collectionReference = firebaseFirestore.collection("Post");
        collectionReference.whereNotEqualTo("userMail", firebaseUser.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value == null) {
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } else {
                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                        Map<String, Object> datas = documentSnapshot.getData();
                        String userMails = datas.get("userMail").toString();
                        arrayList.add(userMails);
                        findFriendAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
    public void back(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}