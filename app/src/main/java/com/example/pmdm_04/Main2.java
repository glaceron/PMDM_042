package com.example.pmdm_04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmdm_04.databinding.ActivityMain2Binding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileWriter;


public class Main2 extends AppCompatActivity
{
    private String urlImages;
    public static final String RESPUESTA = "RESPUESTA_DESCARGA";
    private ActivityMain2Binding binding;
    private ViewFlipper viewFlipper;

    Intent intent;
    IntentFilter intentFilter;
    BroadcastReceiver broadcastReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewFlipper = findViewById(R.id.view_flipper);

        intentFilter = new IntentFilter(RESPUESTA);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastReceiver = new ReceptorOperacion();

        intent = new Intent(Main2.this, DescargaFichero.class);
        startService(intent);

    }

    public void showImages()
    {
        String[] lines = getUrlImages().split(System.getProperty("line.separator"));
        System.out.println(lines.length);
      //  Toast.makeText(this, lines.length, Toast.LENGTH_SHORT).show();

        String imageUri = lines[0];
        ImageView ivBasicImage = (ImageView) findViewById(R.id.imageView1);
        Picasso.get().load(imageUri).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(ivBasicImage);

        String imageUri2 = lines[1];
        ImageView ivBasicImage2 = (ImageView) findViewById(R.id.imageView2);
        Picasso.get().load(imageUri2).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(ivBasicImage2);

        String imageUri3 = lines[2];
        ImageView ivBasicImage3 = (ImageView) findViewById(R.id.imageView3);
        Picasso.get().load(imageUri3).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(ivBasicImage3);

        String imageUri4 = lines[3];
        ImageView ivBasicImage4 = (ImageView) findViewById(R.id.imageView4);
        Picasso.get().load(imageUri4).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(ivBasicImage4);

        String imageUri5 = lines[4];
        ImageView ivBasicImage5 = (ImageView) findViewById(R.id.imageView5);
        Picasso.get().load(imageUri5).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(ivBasicImage5);

        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }


    public void previousView(View v) {
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.showPrevious();
    }

    public void nextView(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showNext();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
        stopService(intent);
    }

    public class ReceptorOperacion extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra("response");
            setUrlImages(response);
            showImages();
        }
    }

    private String getUrlImages() {
        return urlImages;
    }

    private void setUrlImages(String urlImages) {
        this.urlImages = urlImages;
    }

    public  void writeFileOnInternalStorage(String url, String error){
        File dir = new File(getApplicationContext().getFilesDir(), "mydir");
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, "errores.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append("Enlace " + url + "; Error " + error + "; "  + System.currentTimeMillis());
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
