package com.example.instagramcloneapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramcloneapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FindFriendAdapter extends RecyclerView.Adapter<FindFriendAdapter.Myholder> {

    ArrayList<String> arrayList = new ArrayList<>();

    public FindFriendAdapter(ArrayList<String> arrayList) {

        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.findfriend_recycle, parent, false);

        return new Myholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        holder.userMails.setText(arrayList.get(position).toString());

        }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView userMails;
        Button follow;


        public String followingMail;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            userMails = itemView.findViewById(R.id.textView6);
            follow = itemView.findViewById(R.id.button10);
            FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    follow.setText("Following");
                    HashMap<String,Object> hashMap= new HashMap<>();
                    hashMap.put("following",userMails.getText());
                    hashMap.put("follower",firebaseUser.getEmail());
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("Followings").add(hashMap).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                       System.out.println(e.getLocalizedMessage());
                        }
                    });
                }
            });

        }
    }


}

