package com.example.instagramcloneapp.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.instagramcloneapp.Login;
import com.example.instagramcloneapp.R;
import com.example.instagramcloneapp.ui.findUsers.AddFriend;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FirebaseAuth firebaseAuth;
    FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        Button logOut = root.findViewById(R.id.button7);
        Button findUser = root.findViewById(R.id.button8);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        findUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent= new Intent(getContext(), AddFriend.class);
            startActivity(intent);
            getActivity().finish();
            }
        });

        return root;
    }
}