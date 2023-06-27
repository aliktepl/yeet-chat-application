package com.example.ap2_ex3.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.view_models.MessageModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private MessageModel messageModel;
    private ChatModel chatModel;

    public MyFirebaseMessagingService() {
        chatModel = new ChatModel(getApplication());
        messageModel = new MessageModel(getApplication());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), Integer.parseInt(Objects.requireNonNull(remoteMessage.getData().get("id"))));
        }
    }

    private void sendNotification(String messageBody, String messageTitle, int id) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setSound(defaultSoundUri).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
            NotificationManager notificationManager = ContextCompat.getSystemService(this, NotificationManager.class);
            NotificationChannel channel = new NotificationChannel("1", "Messages", NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(1, notificationBuilder.build());
            messageModel.getMessagesRequest(id);
            chatModel.getChats();
        }
    }


}