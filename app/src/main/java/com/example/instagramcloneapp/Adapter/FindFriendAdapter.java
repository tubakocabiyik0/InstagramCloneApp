package com.example.instagramcloneapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramcloneapp.R;

import java.util.ArrayList;

public class FindFriendAdapter extends RecyclerView.Adapter<FindFriendAdapter.Myholder> {
    ArrayList<String> userMail = new ArrayList<>();

    public FindFriendAdapter(ArrayList<String> userMail) {
        this.userMail = userMail;
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
        holder.userMail.setText(userMail.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return userMail.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView userMail;
        Button follow;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            userMail = itemView.findViewById(R.id.textView6);
            follow = itemView.findViewById(R.id.button10);
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }
}
