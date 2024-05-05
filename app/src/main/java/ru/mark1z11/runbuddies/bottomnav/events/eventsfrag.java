package ru.mark1z11.runbuddies.bottomnav.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ru.mark1z11.runbuddies.databinding.FragEventsBinding;
import ru.mark1z11.runbuddies.events.Event;
import ru.mark1z11.runbuddies.events.EventsAdapter;

public class eventsfrag extends Fragment {
    private FragEventsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragEventsBinding.inflate(inflater, container, false);
        loadEvents();

        return binding.getRoot();
    }

    private void loadEvents() {
        ArrayList<Event> events = new ArrayList<Event>();
        binding.eventRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.eventRv.setAdapter(new EventsAdapter(events));
    }
}
