package com.prueba.galleryfirebase.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.prueba.galleryfirebase.Interactor.MainActivityInteractor;
import com.prueba.galleryfirebase.Interfaces.Contract;

import java.util.ArrayList;

public class MainActivityPresenter implements Contract.Presenter, Contract.onOperationListener {

    private MainActivityInteractor mainActivityInteractor;
    private Contract.View view;

    public MainActivityPresenter(Contract.View mView) {
        this.view = mView;
        mainActivityInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void createNewPicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore,Uri filePath) {
        mainActivityInteractor.performCreatePicture(firebaseStorage, firebaseFirestore,filePath);
    }

    @Override
    public void createNewPicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore,Bitmap bitmap) {
        mainActivityInteractor.performCreatePicture(firebaseStorage, firebaseFirestore,bitmap);
    }

    @Override
    public void readPictures(FirebaseFirestore firebaseFirestore) {
        mainActivityInteractor.performReadPictures(firebaseFirestore);
    }

    @Override
    public void onSuccess() {
        view.onCreatePictureSuccessful();

    }

    @Override
    public void onFailure() {
        view.onCreatePictureFailure();
    }

    @Override
    public void onStart() {
        view.onProcessStart();
    }

    @Override
    public void onEnd() {
        view.onProcessEnd();

    }

    @Override
    public void onRead(ArrayList<String> urls) {
        view.onPictureRead(urls);
    }
}
