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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.instagramcloneapp.MainActivity;
import com.example.instagramcloneapp.R;
import com.example.instagramcloneapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class addPostFragment extends Fragment {
    ImageView image;
    Button share;
    EditText comment;
    Bitmap images;
    Button takeP;
    Button chooseP;
    FirebaseAuth firebaseAuth;
    private addPostViewModel dashboardViewModel;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    Uri getData;
    UUID uuid;
    String imgs;
    String email;
    String comments;
    String imageUrl;

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
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        uuid = UUID.randomUUID();
        imgs = "posts" + uuid + ".jpg";


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
                storageReference.child(imgs).putFile(getData).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference(imgs);
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUrl = uri.toString();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                email = firebaseUser.getEmail();
                                comments = comment.getText().toString();


                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("userMail", email);
                                hashMap.put("url", imageUrl);
                                hashMap.put("date", FieldValue.serverTimestamp());
                                hashMap.put("comment", comments);

                                firebaseFirestore.collection("Post").add(hashMap).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getContext(), "Post shared", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);


                                    }
                                });
                            }
                        });

                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2) {
            if (resultCode == getActivity().RESULT_OK && data != null) {
                getData = data.getData();
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
