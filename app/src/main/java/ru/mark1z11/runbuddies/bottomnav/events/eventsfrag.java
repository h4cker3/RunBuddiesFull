package ru.mark1z11.runbuddies.bottomnav.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mark1z11.runbuddies.databinding.FragEventsBinding;

public class eventsfrag extends Fragment{
    private FragEventsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragEventsBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}
