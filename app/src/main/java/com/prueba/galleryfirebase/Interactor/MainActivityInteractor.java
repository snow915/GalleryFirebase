package com.prueba.galleryfirebase.Interactor;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prueba.galleryfirebase.Interfaces.Contract;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivityInteractor implements Contract.Interactor {

    private Contract.onOperationListener onOperationListener;

    public MainActivityInteractor(Contract.onOperationListener mListener) {
        onOperationListener = mListener;
    }

    @Override
    public void performCreatePicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore, Uri filePath) {
        onOperationListener.onStart();
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        UploadTask uploadTask = (UploadTask) ref.putFile(filePath)
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

        saveInFirestore(ref, uploadTask, firebaseFirestore);

    }

    @Override
    public void performCreatePicture(FirebaseStorage firebaseStorage, FirebaseFirestore firebaseFirestore, Bitmap bitmap) {
        onOperationListener.onStart();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        byte[] b = stream.toByteArray();
        UploadTask uploadTask = (UploadTask) ref.putBytes(b)
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

        saveInFirestore(ref, uploadTask, firebaseFirestore);
    }

    @Override
    public void performReadPictures(FirebaseFirestore firebaseFirestore) {
        firebaseFirestore.collection("images")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        ArrayList<String> urls = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            urls.add(doc.getString("url"));
                        }
                        onOperationListener.onRead(urls);
                    }
                });
    }

    private void saveInFirestore(StorageReference ref, UploadTask uploadTask, FirebaseFirestore firebaseFirestore) {
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Map<String, Object> data = new HashMap<>();
                    data.put("url", downloadUri.toString());
                    data.put("timestamp", FieldValue.serverTimestamp());

                    firebaseFirestore.collection("images")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                } else {
                }

            }
        });
    }
}
