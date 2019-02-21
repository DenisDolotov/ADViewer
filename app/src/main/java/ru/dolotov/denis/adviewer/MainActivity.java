package ru.dolotov.denis.adviewer;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private File file;
    private String[] FilePathStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set window to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Preset test images


        setContentView(R.layout.activity_main);

        // ImageView for displaying ADs
        final ImageView mImageView = findViewById(R.id.ADImageID);

        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "No SD card found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            // Locate the image folder in your SD card
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "ADImages");
            // Create a new folder if no folder named ADImages exist
            // file.mkdirs();
        }

        if (file.isDirectory()) {
            // Create a String array for FilePathStrings
            FilePathStrings = new String[file.listFiles().length];

            for (int i = 0; i < file.listFiles().length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = file.listFiles()[i].getAbsolutePath();
            }
        }
//        Toast.makeText(this, file.getAbsolutePath(), Toast.LENGTH_LONG)
//                .show();

        //Create the Thread for changing images
        if (FilePathStrings.length > 1) {

            new Thread(new Runnable() {
                public void run() {
                    int i = 0;
                    while (true) {
                        final int j = i;
                        try {
                            //Delay 2 sec
                            Thread.sleep(2000);
                        } catch (InterruptedException ignored) {

                        }
                        //Simple realization for ThreadSafe image changing in the ImageView
                        mImageView.post(new Runnable() {
                            public void run() {
                                Bitmap bmp = BitmapFactory.decodeFile(FilePathStrings[j]);
                                mImageView.setImageBitmap(bmp);
                            }
                        });

                        if (i < FilePathStrings.length - 1) i++;
                        else i = 0;
                    }
                }
            }).start();
        } else {
            Bitmap bmp = BitmapFactory.decodeFile(FilePathStrings[0]);
            mImageView.setImageBitmap(bmp);
        }


    }

}
