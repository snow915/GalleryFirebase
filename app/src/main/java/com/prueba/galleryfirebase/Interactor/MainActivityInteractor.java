package com.prueba.galleryfirebase.Interactor;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prueba.galleryfirebase.Interfaces.Contract;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivityInteractor implements Contract.Interactor {

    private Contract.onOperationListener onOperationListener;

    public MainActivityInteractor(Contract.onOperationListener mListener) {
        onOperationListener = mListener;
    }

    @Override
    public void performCreatePicture(FirebaseStorage firebaseStorage, Uri filePath) {
        onOperationListener.onStart();
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference= firebaseStorage.getReference();
        StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        onOperationListener.onEnd();
                        onOperationListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onOperationListener.onEnd();
                        onOperationListener.onFailure();
                    }
                });
    }

    @Override
    public void performCreatePicture(FirebaseStorage firebaseStorage, Bitmap bitmap) {
        onOperationListener.onStart();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference= firebaseStorage.getReference();
        StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
        byte[] b = stream.toByteArray();
        ref.putBytes(b)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        onOperationListener.onEnd();
                        onOperationListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onOperationListener.onEnd();
                        onOperationListener.onFailure();
                    }
                });
    }

    @Override
    public void performReadPictures(FirebaseStorage firebaseStorage) {

    }
}
