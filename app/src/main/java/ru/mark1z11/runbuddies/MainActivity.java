package ru.mark1z11.runbuddies;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.IllformedLocaleException;
import java.util.Map;

import ru.mark1z11.runbuddies.bottomnav.couple.couplefrag;
import ru.mark1z11.runbuddies.bottomnav.events.eventsfrag;
import ru.mark1z11.runbuddies.bottomnav.profile.profilefrag;
import ru.mark1z11.runbuddies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        getSupportFragmentManager().beginTransaction().replace(binding.btmCont.getId(), new profilefrag()).commit();
        binding.btmNav.setSelectedItemId(R.id.profile);


        Map<Integer, Fragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.couple, new couplefrag());
        fragmentMap.put(R.id.events, new eventsfrag());
        fragmentMap.put(R.id.profile, new profilefrag());


        binding.btmNav.setOnItemSelectedListener(item ->{
            Fragment fragment = fragmentMap.get(item.getItemId());
            getSupportFragmentManager().beginTransaction().replace(binding.btmCont.getId(), fragment).commit();
            return true;
        });
    }
}