package com.example.app_camera;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 100;

    private ImageView imgPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnCamera = findViewById(R.id.btnCamera);
        imgPhoto = findViewById(R.id.imgPhoto);

        btnCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                startActivityForResult(intent, CAMERA_REQUEST);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this,
                        "No camera app available",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {

            if (resultCode == RESULT_OK
                    && data != null
                    && data.getExtras() != null) {

                Bitmap photo =
                        (Bitmap) data.getExtras().get("data");

                if (photo != null) {
                    imgPhoto.setImageBitmap(photo);
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,
                        "Photo capture cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}