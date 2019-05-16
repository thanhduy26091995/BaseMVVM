package com.duybui.basemvvmjava.ui.users;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duybui.basemvvmjava.R;
import com.duybui.basemvvmjava.data.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Activity activity;
    private List<User> userList = new ArrayList<>();

    public UserAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.item_user, viewGroup, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int i) {
        User user = userList.get(i);
        holder.mTvEmail.setText(user.getEmail());
        holder.mTvPhone.setText(user.getPhone());
        holder.mTvGender.setText(user.getGender());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setData(List<User> data) {
        userList.clear();
        userList.addAll(data);
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvEmail, mTvPhone, mTvGender;
        private ImageView mIvAvatar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvEmail = itemView.findViewById(R.id.tv_email);
            mTvPhone = itemView.findViewById(R.id.tv_phone);
            mTvGender = itemView.findViewById(R.id.tv_gender);
        }
    }
}
