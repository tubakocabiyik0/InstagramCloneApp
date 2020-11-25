package com.example.instagramcloneapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramcloneapp.Adapter.PostsAdapter;
import com.example.instagramcloneapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList commentsFromFB;
    ArrayList userMailsFromFB;
    ArrayList imagesFromFB;
    FirebaseFirestore firebaseFirestore;
    PostsAdapter recyclerAdapter;
    QueryDocumentSnapshot queryDocumentSnapshot;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        commentsFromFB = new ArrayList();
        userMailsFromFB = new ArrayList();
        imagesFromFB = new ArrayList();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getDatasFromFB();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new PostsAdapter(commentsFromFB, imagesFromFB, userMailsFromFB);
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void getDatasFromFB() {
        CollectionReference collectionReference = firebaseFirestore.collection("Post");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                        Map<String, Object> datas = documentSnapshot.getData();
                        String comment = (String) datas.get("comment");
                        String userMAil = (String) datas.get("userMail");
                        String image = (String) datas.get("url");
                        System.out.println(comment);
                        commentsFromFB.add(comment);
                        userMailsFromFB.add(userMAil);
                        imagesFromFB.add(image);

                        recyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }

}