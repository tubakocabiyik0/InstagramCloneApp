package com.example.instagramcloneapp.ui.addPost;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.instagramcloneapp.R;
import com.example.instagramcloneapp.ui.home.HomeFragment;

import java.io.IOException;

public class addPostFragment extends Fragment {
    ImageView image;
    Button share;
    EditText comment;
    Bitmap images;
    Button takeP;
    Button chooseP;
    FragmentManager fragmentManager;
    private addPostViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this).get(addPostViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashpord, container, false);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = view.findViewById(R.id.imageView);
        comment = view.findViewById(R.id.editTextTextPersonName5);
        share = view.findViewById(R.id.button4);
        takeP = view.findViewById(R.id.button5);
        chooseP = view.findViewById(R.id.button6);

        takeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });
        chooseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2) {
            if (resultCode == getActivity().RESULT_OK && data != null) {
                Uri getData = data.getData();
                if (Build.VERSION.SDK_INT >= 28) {
                    try {
                        ImageDecoder.Source imageDecoder = ImageDecoder.createSource(getActivity().getContentResolver(), getData);
                        images = ImageDecoder.decodeBitmap(imageDecoder);
                        image.setImageBitmap(images);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        if (requestCode == 4) {
            if (resultCode == getActivity().RESULT_OK && data != null) {
                images = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(images);

            }
        }
    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, 2);
        }
    }

    public void takePicture(View view) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, 3);
        } else {
            Intent gallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(gallery, 4);
        }
    }
}