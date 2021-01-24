package com.prueba.galleryfirebase.Interfaces;
import android.graphics.Bitmap;
import android.net.Uri;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;

public interface Contract {
    interface View{
        void onCreatePictureSuccessful();
        void onCreatePictureFailure();
        void onProcessStart();
        void onProcessEnd();
        void onPictureRead(ArrayList<String> urls);
    }

    interface Presenter{
        void createNewPicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore,Uri filePath);
        void createNewPicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore,Bitmap bitmap);
        void readPictures(FirebaseFirestore firebaseFirestore);
    }

    interface Interactor{
        void performCreatePicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore, Uri filePath);
        void performCreatePicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore, Bitmap bitmap);
        void performReadPictures(FirebaseFirestore firebaseFirestore);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onStart();
        void onEnd();
        void onRead(ArrayList<String> urls);
    }
}
