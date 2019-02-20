package ru.dolotov.denis.adviewer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private int[] imagesIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set window to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Preset test images
        imagesIds = new int[2];
        imagesIds[0] = R.drawable.onepiece;
        imagesIds[1] = R.drawable.mountain;

        setContentView(R.layout.activity_main);

        // ImageView for displaying ADs
        final ImageView mImageView = findViewById(R.id.ADImageID);
        mImageView.setImageResource(imagesIds[0]);

        //Create a Thread for changing images
        new Thread(new Runnable() {
            public void run() {
                //Do it 100 times
                for (int i = 0; i < 100; i++) {
                    final int j = i;
                    try {
                        //Delay 2 sec
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {

                    }
                    //Simple realization for ThreadSafe image changing in the ImageView
                    mImageView.post(new Runnable() {
                        public void run() {
                            mImageView.setImageResource(imagesIds[j % 2]);
                        }
                    });
                }
            }
        }).start();


    }

}
