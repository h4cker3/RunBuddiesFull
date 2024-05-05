package ru.mark1z11.runbuddies.bottomnav.couple;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ru.mark1z11.runbuddies.R;
import ru.mark1z11.runbuddies.databinding.FragCoupleBinding;
import ru.mark1z11.runbuddies.users.User;
import ru.mark1z11.runbuddies.users.UserAdapter;

public class couplefrag extends Fragment {
    FragCoupleBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragCoupleBinding.inflate(inflater, container, false);
        loadUsers();

        return binding.getRoot();
    }


    private void loadUsers() {
        ArrayList<User> users = new ArrayList<User>();
        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        continue;
                    }

                    String username = userSnapshot.child("name").getValue().toString();
                    String profileImage = userSnapshot.child("profileImage").getValue().toString();
                    String rate = userSnapshot.child("rating").getValue().toString();


                    users.add(new User(username, profileImage, rate));
                }


                binding.coupleLst.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.coupleLst.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                binding.coupleLst.setAdapter(new UserAdapter(users));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
