package ru.mark1z11.runbuddies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import ru.mark1z11.runbuddies.bottomnav.profile.profilefrag;
import ru.mark1z11.runbuddies.databinding.ActivityChangeInfBinding;
import ru.mark1z11.runbuddies.databinding.ActivityLoginBinding;


public class ChangeInfActivity extends AppCompatActivity {


    private ActivityChangeInfBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeInfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        binding.buttonGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeInfActivity.this, MainActivity.class));
            }});
        loadUserInfo1();

        SeekBar seekBar = findViewById(R.id.speedbar);
        TextView textView = findViewById(R.id.speedtext);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress + " km"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seekBar1 = findViewById(R.id.distancebar);
        TextView textVieww = findViewById(R.id.distancetext);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textVieww.setText(String.valueOf(progress + " m"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.applyeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.namepeTe.getText().toString().isEmpty() || binding.ageTe.getText().toString().isEmpty()
                        || binding.genderTe.getText().toString().isEmpty() || binding.weightTe.getText().toString().isEmpty()
                        || binding.heightTe.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } if(binding.ageTe.getText().toString().matches("^\\d+$") == false || binding.weightTe.getText().toString().matches("^\\d+$") == false
                || binding.heightTe.getText().toString().matches("^\\d+$") == false){
                    Toast.makeText(getApplicationContext(), "The numbers you need", Toast.LENGTH_SHORT).show();
                }else{
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("name").setValue(binding.namepeTe.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("age").setValue(binding.ageTe.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("gender").setValue(binding.genderTe.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("height").setValue(binding.heightTe.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("speed").setValue(seekBar.getProgress());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("distance").setValue(seekBar1.getProgress());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("weight").setValue(binding.weightTe.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChangeInfActivity.this, MainActivity.class));
                        }
                    });


                }
            }
        });
    }

    private void loadUserInfo1(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String username = snapshot.child("name").getValue().toString();
                        String age = snapshot.child("age").getValue().toString();
                        String gender = snapshot.child("gender").getValue().toString();
                        String weight = snapshot.child("weight").getValue().toString();
                        String height = snapshot.child("height").getValue().toString();
                        String speed = snapshot.child("speed").getValue().toString();
                        String distance = snapshot.child("distance").getValue().toString();
                        binding.namepeTe.setText(username);
                        binding.ageTe.setText(age);
                        binding.genderTe.setText(gender);
                        binding.weightTe.setText(weight);
                        binding.heightTe.setText(height);
                        binding.distancetext.setText(distance);
                        binding.speedtext.setText(speed);
                        binding.speedbar.setProgress(Integer.parseInt(speed));
                        binding.distancebar.setProgress(Integer.parseInt(distance));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
}
}