package ru.mark1z11.runbuddies.users;

import android.provider.Telephony;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.mark1z11.runbuddies.R;

public class UserViewHolder extends RecyclerView.ViewHolder {


    CircleImageView profileImage;
    TextView username_tv;
    TextView rate;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImage = itemView.findViewById(R.id.profile_rv);
        username_tv = itemView.findViewById(R.id.person_tvt);
        rate = itemView.findViewById(R.id.rate_tvt);
    }
}
