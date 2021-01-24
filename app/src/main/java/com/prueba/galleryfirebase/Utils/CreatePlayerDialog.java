package com.prueba.galleryfirebase.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.prueba.galleryfirebase.R;

import java.io.IOException;

public class CreatePlayerDialog extends AppCompatDialogFragment {

    private ImageView img_selected;
    private Button mSaveBtn, mGalleryBtn, mCameraBtn;
    private createPlayerDialogListener mListerner;
    private final int PICK_IMAGE_REQUEST = 1;
    private final int CAMERA_REQUEST_CODE = 2;
    public static final int CAMERA_PERM_CODE = 3;
    private Uri filePath;
    private Bitmap bitmap;
    private String source = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view);
        builder.setTitle("Add new picture");
        builder.setCancelable(true);

        img_selected = (ImageView) view.findViewById(R.id.img_view);
        mGalleryBtn = (Button) view.findViewById(R.id.btn_from_gallery);
        mSaveBtn = (Button) view.findViewById(R.id.btn_save);
        mCameraBtn = (Button) view.findViewById(R.id.btn_from_camera);

        mGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (source.equals("GALLERY")) {
                    mListerner.savePlayer(filePath);
                } else {
                    mListerner.savePlayer(bitmap);
                }
                dismiss();
            }
        });

        mCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });
        return builder.create();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void captureImage() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            captureImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        } else {
            Toast.makeText(getContext(), "Se necesita el permiso de camara", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                img_selected.setImageBitmap(bitmap);
                source = "GALLERY";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                img_selected.setImageBitmap(bitmap);
                source = "CAMERA";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListerner = (createPlayerDialogListener) context;
    }

    public interface createPlayerDialogListener {
        void savePlayer(Uri filepath);
        void savePlayer(Bitmap bitmap);
    }

}
