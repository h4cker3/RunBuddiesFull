package ru.mark1z11.runbuddies.bottomnav.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import ru.mark1z11.runbuddies.ChangeInfActivity;
import ru.mark1z11.runbuddies.LoginActivity;
import ru.mark1z11.runbuddies.MainActivity;
import ru.mark1z11.runbuddies.Rastsolo;
import ru.mark1z11.runbuddies.RegisterActivity;
import ru.mark1z11.runbuddies.databinding.FragProfileBinding;

public class profilefrag extends Fragment {
    private FragProfileBinding binding;
    private Uri filePath;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragProfileBinding.inflate(inflater, container, false);

        binding.buttonStepsInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Rastsolo.class));
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        binding.buttonChangeInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangeInfActivity.class));
            }
        });

        loadUserInfo();

        binding.profileimgCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        return binding.getRoot();
    }


    ActivityResultLauncher<Intent> pickImageActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK && result.getData()!=null && result.getData().getData()!=null){
                filePath = result.getData().getData();

                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), filePath);
                    binding.profileimgCircle.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }

                uploadImage();
            }
        }
    });


    private void loadUserInfo(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String username = snapshot.child("name").getValue().toString();
                        String age = snapshot.child("age").getValue().toString();
                        String gender = snapshot.child("gender").getValue().toString();
                        String weight = snapshot.child("weight").getValue().toString();
                        String height = snapshot.child("height").getValue().toString();
                        String profileImage = snapshot.child("profileImage").getValue().toString();
                        String rate = snapshot.child("rating").getValue().toString();
                        binding.rateTe.setText(rate);
                        binding.usernameTv.setText(username);
                        binding.ageInf.setText("Age: "+age);
                        binding.genderInf.setText("Gender: "+gender);
                        binding.weightInf.setText("Weight: "+weight);
                        binding.heightInf.setText("Height: "+height);

                        if(!profileImage.isEmpty()){
                            Glide.with(getContext()).load(profileImage).into(binding.profileimgCircle);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        pickImageActivityResultLauncher.launch(intent);
    }


    private void uploadImage(){
        if (filePath!=null){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseStorage.getInstance().getReference().child("images/"+uid).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();


                    FirebaseStorage.getInstance().getReference().child("images/"+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profileImage").setValue(uri.toString());

                        }
                    });

                }
            });
        }
    }
}
