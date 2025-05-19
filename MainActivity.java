package com.example.cpaudio_sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView text01;
    Button btn01;
    Button btn02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text01 = (TextView) findViewById(R.id.textView01);
        btn01 = (Button) findViewById(R.id.button01);
        btn02 = (Button) findViewById(R.id.button02);

        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO},MODE_PRIVATE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button01){
            ContentResolver cr = getContentResolver();

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            Cursor c = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.TITLE + "ASCE");

            int alubmIdx = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            int titleIdx = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int artistIdx = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

            StringBuffer buff = new StringBuffer();
            buff.append("Music List \n\n");
            if(c.moveToFirst()){
                do {
                    buff.append(c.getString(alubmIdx));
                    buff.append("\n");
                    buff.append(c.getString(titleIdx));
                    buff.append("\n");
                    buff.append(c.getString(artistIdx));
                    buff.append("\n");
                    buff.append("----------------------\n");
                } while (c.moveToNext());
            }

            text01.setText(buff);
        };

        if(view.getId() == R.id.button02){
            if(view.getId() == R.id.button01) {
                ContentResolver cr = getContentResolver();

                Cursor c = cr.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.TITLE + "ASCE");

                int alubmIdx = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                int titleIdx = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                int artistIdx = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

                StringBuffer buff = new StringBuffer();
                buff.append("Music List \n\n");
                if (c.moveToFirst()) {
                    do {
                        buff.append(c.getString(alubmIdx));
                        buff.append("\n");
                        buff.append(c.getString(titleIdx));
                        buff.append("\n");
                        buff.append(c.getString(artistIdx));
                        buff.append("\n");
                        buff.append("----------------------\n");
                    } while (c.moveToNext());
                }

                text01.setText(buff);
            }
        }
    }
}
