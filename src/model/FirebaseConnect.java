package model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;

public class FirebaseConnect {
    public Firestore connectFirebase() throws IOException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("/Firebase_Key.json")))
                .build();

        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }
}
