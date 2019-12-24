package com.example.manifestread;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager pm = MainActivity.this.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(
                PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES
        );
        for(int i=0;i<apps.size();i++){
            System.out.println(apps.get(i).publicSourceDir);
        }
        try {
            ZipFile apk = new ZipFile("/data/app/com.example.hciproject-LYfHg-H69CWdeBt01jqlvg==/base.apk");
            ZipEntry manifest = apk.getEntry("AndroidManifest.xml");
            if (manifest != null){
                Log.d("ManifestGetter", "Manifest size = " + manifest);
                System.out.println("Manifst size=" + manifest.getName() + "Size" + manifest.getSize());




                //ZipEntry entries = apk.getEntry("AndroidManifest.xml");


                InputStream streamLast = apk.getInputStream(manifest);

                System.out.println(convertStreamToString(streamLast));
//                InputStreamReader isReader = new InputStreamReader(streamLast);
//                //Creating a character array
//                char charArray[] = new char[(int) 10000];
//                //Reading the contents of the reader
//                isReader.read(charArray);
//                //Converting character array to a String
//                String contents = new String(charArray);
//                System.out.println(contents);


//                System.out.println(isToString(streamLast));
//                JarInputStream jarStream = new JarInputStream(streamLast);
//                Manifest mf = jarStream.getManifest();
//                mf.read(streamLast);
//                System.out.println(streamLast);







                BufferedReader r = new BufferedReader(new InputStreamReader(streamLast));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }



                    //System.out.println(total.toString());
                streamLast.close();



                // do stuff with the file here e.g. get contents

                //stream.close();
            }
            apk.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


//        InputStream stream = new InputStream() {
//            @Override
//            public int read() throws IOException {
//                return 0;
//            }
//        };
//        JarInputStream jarStream = null;
//        try {
//            jarStream = new JarInputStream(stream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Manifest mf = jarStream.getManifest();
//        try {
//            mf.read(stream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        setContentView(R.layout.activity_main);
//        System.out.println(stream);
//        System.out.println(mf);



    }
    public String isToString(InputStream is) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, StandardCharsets.UTF_8);
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
