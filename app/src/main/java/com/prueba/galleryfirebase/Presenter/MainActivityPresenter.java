package com.prueba.galleryfirebase.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.prueba.galleryfirebase.Interactor.MainActivityInteractor;
import com.prueba.galleryfirebase.Interfaces.Contract;

public class MainActivityPresenter implements Contract.Presenter, Contract.onOperationListener {

    private MainActivityInteractor mainActivityInteractor;
    private Contract.View view;

    public MainActivityPresenter(Contract.View mView) {
        this.view = mView;
        mainActivityInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void createNewPicture(FirebaseStorage firebaseStorage, Uri filePath) {
        mainActivityInteractor.performCreatePicture(firebaseStorage, filePath);
    }

    @Override
    public void createNewPicture(FirebaseStorage firebaseStorage, Bitmap bitmap) {
        mainActivityInteractor.performCreatePicture(firebaseStorage, bitmap);
    }

    @Override
    public void readPictures(FirebaseStorage firebaseStorage) {

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
}
