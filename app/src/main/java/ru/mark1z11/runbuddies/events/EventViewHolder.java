package ru.mark1z11.runbuddies.events;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.mark1z11.runbuddies.R;

public class EventViewHolder extends RecyclerView.ViewHolder {

    CircleImageView chat_iv;
    TextView chat_name_tv;
    public EventViewHolder(@NonNull View itemView) {
        super(itemView);


        chat_iv = itemView.findViewById(R.id.profile_rv);
        chat_name_tv =  itemView.findViewById(R.id.username_tv);

    }
}
