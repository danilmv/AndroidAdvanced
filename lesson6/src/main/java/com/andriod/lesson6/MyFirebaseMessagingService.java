package com.andriod.lesson6;


import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        // Если надо посылать сообщения этому экземпляру приложения
        // или управлять подписками прилоения на стороне сервера,
        // сохраните этот токен в базе данных. отправьте этот токен вашему
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        MainActivity.showNotification(this, String.format("Push-message received: title:%s\nbody:%s", title, body));
    }
}
