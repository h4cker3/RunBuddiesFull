package ru.mark1z11.runbuddies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ru.mark1z11.runbuddies.databinding.ActivityLoginBinding;
import ru.mark1z11.runbuddies.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {


    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.emailTe.getText().toString().isEmpty() || binding.pswrdTe.getText().toString().isEmpty()
                || binding.usernameTe.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailTe.getText().toString(),binding.pswrdTe.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        HashMap<String, String> userInfo = new HashMap<>();
                                        userInfo.put("email", binding.emailTe.getText().toString());
                                        userInfo.put("name", binding.usernameTe.getText().toString());
                                        userInfo.put("profileImage", "");
                                        userInfo.put("gender", "");
                                        userInfo.put("age", "");
                                        userInfo.put("weight", "");
                                        userInfo.put("height", "");
                                        userInfo.put("steps", "");
                                        userInfo.put("rating", "100");
                                        userInfo.put("speed", "1");
                                        userInfo.put("distance", "500");
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userInfo);
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    }
                                }
                            });
                }
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}