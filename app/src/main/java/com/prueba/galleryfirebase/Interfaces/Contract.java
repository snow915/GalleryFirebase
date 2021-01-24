package com.prueba.galleryfirebase.Interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public interface Contract {
    interface View{
        void onCreatePictureSuccessful();
        void onCreatePictureFailure();
        void onProcessStart();
        void onProcessEnd();
        //void onPictureRead(ArrayList<Picture> players);
    }

    interface Presenter{
        void createNewPicture(FirebaseStorage firebaseStorage, Uri filePath);
        void createNewPicture(FirebaseStorage firebaseStorage, Bitmap bitmap);
        void readPictures(FirebaseStorage firebaseStorage);
    }

    interface Interactor{
        void performCreatePicture(FirebaseStorage firebaseStorage, Uri filePath);
        void performCreatePicture(FirebaseStorage firebaseStorage, Bitmap bitmap);
        void performReadPictures(FirebaseStorage firebaseStorage);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onStart();
        void onEnd();
        //void onRead(ArrayList<Picture> players);
    }
}
