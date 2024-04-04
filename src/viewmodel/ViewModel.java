/*
    ALBERT ANG
    3/25/2024

 */
package viewmodel;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import view.App;

import java.util.concurrent.ExecutionException;

public class ViewModel {

    public boolean isValidDoctor(String un, String up) throws ExecutionException, InterruptedException {
        boolean result = false;

        ApiFuture<QuerySnapshot> docRef = App.fs.collection("accounts").get();
        for (QueryDocumentSnapshot document : docRef.get().getDocuments()) {
            String username = document.getString("acct_user");
            String password = document.getString("acct_pass");
            String type =document.getString("acct_type");

            if( (un.equals(username) && up.equals(password)) && (type.equals("D"))) {
              result = true;
            };
        }

        return result;
    }

    public void createDoctor() {

    }

}
