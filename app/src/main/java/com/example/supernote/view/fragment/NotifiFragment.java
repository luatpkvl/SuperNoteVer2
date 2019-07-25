package com.example.supernote.view.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.supernote.R;
import com.example.supernote.view.main.MainActivity;

public class NotifiFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifi,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_send_noti).setOnClickListener(this);
    }
    public void khoiTaoNotifi(View v){
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),1,intent,0);
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_person);
        builder.setContentTitle("Thông báo khẩn cấp");
        builder.setContentText("Chúng tôi nghi vấn bạn có vấn đề về độ đẹp trai");
        notificationManager.notify(1,builder.build());
    }

    @Override
    public void onClick(View v) {
        khoiTaoNotifi(getView());
    }
}
