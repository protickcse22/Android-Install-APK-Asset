package com.softbd.installapkfromasset;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private Button installBT;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installBT = findViewById(R.id.installBT);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        installBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = "JellyJumpers.apk";
                AssetManager assetManager = getAssets();

                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(path);
                    out = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/JellyJumpers.apk");

                    byte[] buffer = new byte[1024];

                    int read;
                    while ((read = in.read(buffer)) != -1) {

                        out.write(buffer, 0, read);

                    }

                    in.close();
                    in = null;

                    out.flush();
                    out.close();
                    out = null;

                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + path)), "application/vnd.android.package-archive");
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}

