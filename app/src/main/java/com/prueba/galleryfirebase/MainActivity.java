package com.prueba.galleryfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.prueba.galleryfirebase.Interfaces.Contract;
import com.prueba.galleryfirebase.Presenter.MainActivityPresenter;
import com.prueba.galleryfirebase.Utils.CreatePlayerDialog;

public class MainActivity extends AppCompatActivity implements CreatePlayerDialog.createPlayerDialogListener, Contract.View {

    private FirebaseStorage storage;
    private MainActivityPresenter mainActivityPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityPresenter = new MainActivityPresenter(this);
        FloatingActionButton fab_from_gallery = findViewById(R.id.fab_from_gallery);

        fab_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    private void openDialog() {
        CreatePlayerDialog createPlayerDialog = new CreatePlayerDialog();
        createPlayerDialog.show(getSupportFragmentManager(), "create dialog");
    }

    @Override
    public void savePlayer(Uri filepath) {
        mainActivityPresenter.createNewPicture(storage, filepath);
    }

    @Override
    public void savePlayer(Bitmap bitmap) {
        mainActivityPresenter.createNewPicture(storage, bitmap);
    }

    @Override
    public void onCreatePictureSuccessful() {
        Toast.makeText(MainActivity.this, "IMAGEN SUBIDA CON ÉXITO!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreatePictureFailure() {
        Toast.makeText(MainActivity.this, "OCURRIÓ UN ERROR!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessStart() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
    }

    @Override
    public void onProcessEnd() {
        progressDialog.dismiss();

    }
}