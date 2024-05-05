package ru.mark1z11.runbuddies.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ru.mark1z11.runbuddies.R;

public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private ArrayList<Event> events;
    public EventsAdapter(ArrayList<Event> events){
        this.events = events;
    }
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_rv, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.chat_name_tv.setText(events.get(position).getEvent_name());




    }
    @Override
    public int getItemCount() {
        return events.size();
    }
}
