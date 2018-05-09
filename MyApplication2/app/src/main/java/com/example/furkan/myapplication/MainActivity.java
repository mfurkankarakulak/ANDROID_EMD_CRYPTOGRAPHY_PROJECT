package com.example.furkan.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity  {

    private static final int COVER_PICTURE = 1;
    private static final int SECRET_PICTURE = 2;
    private static final int STEGO_PICTURE = 3;

    ImageView iv1,iv2,iv3;

    public int[][] rgbValuesCoverRed;
    public int[][] rgbValuesCoverGreen;
    public int[][] rgbValuesCoverBlue;

    public int[][] rgbValuesSecretRed;
    public int[][] rgbValuesSecretGreen;
    public int[][] rgbValuesSecretBlue;

    public String[][] BeslikValuesSecretRed;
    public String[][] BeslikValuesSecretGreen;
    public String[][] BeslikValuesSecretBlue;

    public String[][] yedilikValuesSecretRed;
    public String[][] yedilikValuesSecretGreen;
    public String[][] yedilikValuesSecretBlue;

    public int[][]  rgbValuesStegoRed;
    public int[][]  rgbValuesStegoGreen;
    public int[][]  rgbValuesStegoBlue;

    int tempCoverRed ;
    int tempCoverGreen;
    int tempCoverBlue;

    Bitmap CoverImage;
    Bitmap SecretImage;
    Bitmap StegoImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv1 = (ImageView)findViewById(R.id.imgeview);
        iv2 = (ImageView)findViewById(R.id.imgeview1);
        iv3 = (ImageView) findViewById(R.id.imgeview2);


    }

    public void CoverClick(View v){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,COVER_PICTURE);

    }
    public void StegoClick(View v){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,STEGO_PICTURE);

    }
    public void SifreCozClick(View v) {


        if((((StegoImage.getHeight()* StegoImage.getWidth())/8) == (SecretImage.getHeight()* SecretImage.getWidth()))){
            Bitmap bitmapSecret = Bitmap.createBitmap(SecretImage.getWidth(),SecretImage.getHeight(), Bitmap.Config.ARGB_8888);

            int t, red, green, blue;
            int xR, yR, xG, yG, xB, yB, yedi = 0,Xi,Xj,Yi,Yj;
            int clr;
            String sRed, sGreen, Sblue;
            for (int j = 0; j < SecretImage.getHeight(); j++) {

                for (int i = 0; i < SecretImage.getWidth(); i++) {

                    t = SecretImage.getWidth() * j + i + yedi;

                    sRed = "";
                    sGreen = "";
                    Sblue = "";

                    for (int k = 0; k < 4; k++) {

                        Xi = t % StegoImage.getWidth();
                        Xj = t / StegoImage.getWidth();
                        Yi = (t + 1) % StegoImage.getWidth();
                        Yj = (t + 1) / StegoImage.getWidth();


                        xR = rgbValuesStegoRed[Xi][Xj];
                        yR = rgbValuesStegoRed[Yi][Yj];
                        red = (xR + 2 * yR) % 5;
                        sRed += Integer.toString(red);

                        xG = rgbValuesStegoGreen[Xi][Xj];
                        yG = rgbValuesStegoGreen[Yi][Yj];
                        green = (xG + 2 * yG) % 5;
                        sGreen += Integer.toString(green);

                        xB = rgbValuesStegoBlue[Xi][Xj];
                        yB = rgbValuesStegoBlue[Yi][Yj];
                        blue = (xB + 2 * yB) % 5;
                        Sblue += Integer.toString(blue);

                        t += 2;
                    }


                    red = bin2dec(Integer.parseInt(sRed),5) ;
                    green = bin2dec(Integer.parseInt(sGreen),5);
                    blue = bin2dec(Integer.parseInt(Sblue),5);




                    clr = Color.argb(255,red,green,blue);
                    bitmapSecret.setPixel(i,j,clr);

                    yedi += 7;
                }

            }
            iv3.setImageResource(0);
            iv3.setImageBitmap(bitmapSecret);

            String sdcardBmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/secret1bmp.bmp";
            //   Bitmap testBitmap = BitmapFactory.decodeResource(getResources());
            AndroidBmpUtil bmpUtil = new AndroidBmpUtil();
            boolean isSaveResult = bmpUtil.save(bitmapSecret, sdcardBmpPath);

        }
        if((((CoverImage.getHeight()* CoverImage.getWidth())/9) == (SecretImage.getHeight()* SecretImage.getWidth()))){

            Bitmap bitmapSecret = Bitmap.createBitmap(SecretImage.getWidth(),SecretImage.getHeight(), Bitmap.Config.ARGB_8888);

            int t, red, green, blue;
            int xR, yR, xG, yG, xB, yB,zR,zG,zB, yedi = 0,Xi,Xj,Yi,Yj,Zi,Zj;
            int clr;
            String sRed, sGreen, Sblue;
            for (int j = 0; j < SecretImage.getHeight(); j++) {

                for (int i = 0; i < SecretImage.getWidth(); i++) {

                    t = SecretImage.getWidth() * j + i + yedi;

                    sRed = "";
                    sGreen = "";
                    Sblue = "";

                    for (int k = 0; k < 3; k++) {

                        Xi = t % StegoImage.getWidth();
                        Xj = t / StegoImage.getWidth();
                        Yi = (t + 1) % StegoImage.getWidth();
                        Yj = (t + 1) / StegoImage.getWidth();
                        Zi =(t+2)%StegoImage.getWidth();
                        Zj =(t+2)/StegoImage.getWidth();


                        xR = rgbValuesStegoRed[Xi][Xj];
                        yR = rgbValuesStegoRed[Yi][Yj];
                        zR = rgbValuesStegoRed[Zi][Zj];
                        red = (xR + 2 * yR + 3*zR) % 7;
                        sRed += Integer.toString(red);

                        xG = rgbValuesStegoGreen[Xi][Xj];
                        yG = rgbValuesStegoGreen[Yi][Yj];
                        zG = rgbValuesStegoGreen[Zi][Zj];
                        green = (xG + 2 * yG + 3*zG) % 7;
                        sGreen += Integer.toString(green);

                        xB = rgbValuesStegoBlue[Xi][Xj];
                        yB = rgbValuesStegoBlue[Yi][Yj];
                        zB = rgbValuesStegoBlue[Zi][Zj];
                        blue = (xB + 2 * yB + 3*zB) % 7;
                        Sblue += Integer.toString(blue);

                        t += 3;
                    }


                    red = bin2dec(Integer.parseInt(sRed),7) ;
                    green = bin2dec(Integer.parseInt(sGreen),7);
                    blue = bin2dec(Integer.parseInt(Sblue),7);




                    clr = Color.argb(255,red,green,blue);
                    bitmapSecret.setPixel(i,j,clr);

                    yedi += 8;
                }

            }
            iv3.setImageResource(0);
            iv3.setImageBitmap(bitmapSecret);

            String sdcardBmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/secret2bmp.bmp";
            //   Bitmap testBitmap = BitmapFactory.decodeResource(getResources());
            AndroidBmpUtil bmpUtil = new AndroidBmpUtil();
            boolean isSaveResult = bmpUtil.save(bitmapSecret, sdcardBmpPath);


        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SıfreleClick(View v){

        Bitmap bitmapStego = Bitmap.createBitmap(CoverImage.getWidth(),CoverImage.getHeight(), Bitmap.Config.ARGB_8888);
        int clr;

        if(((CoverImage.getHeight()* CoverImage.getWidth())/8) < (SecretImage.getHeight()* SecretImage.getWidth())){
            Toast.makeText(getApplicationContext(),"GİZLENECEK GÖRÜNTÜ SIGMIYOR", Toast.LENGTH_LONG).show();
        }
        else if((((CoverImage.getHeight()* CoverImage.getWidth())/8) == (SecretImage.getHeight()* SecretImage.getWidth()))/*&& (((CoverImage.getHeight()* CoverImage.getWidth())/9) <(SecretImage.getHeight()* SecretImage.getWidth()))*/)  {

            int artısMiktar = 0;
            int t ,Xi,Xj,Yi,Yj,fark;

            for(int j=0; j < SecretImage.getHeight(); j++) // satir
            {
                for(int i=0; i < SecretImage.getWidth(); i++){ //sutun

                   t =  SecretImage.getWidth()*j + i +artısMiktar ;

                   for(int k = 0; k < 4; k++) {

                        Xi = t%CoverImage.getWidth();
                        Xj = t/CoverImage.getWidth();

                        Yi =(t+1)%CoverImage.getWidth();
                        Yj =(t+1)/CoverImage.getWidth();


                        tempCoverRed = (rgbValuesCoverRed[Xi][Xj] + 2 * rgbValuesCoverRed[Yi][Yj]) % 5;
                        fark = tempCoverRed - Integer.parseInt(BeslikValuesSecretRed[i][j].substring(k,k+1));
                        fark = (fark + 5) % 5;
                        if (fark == 1) rgbValuesCoverRed[Xi][Xj] -= 1;
                        if (fark == 2) rgbValuesCoverRed[Yi][Yj] -= 1;
                        if (fark == 3) rgbValuesCoverRed[Yi][Yj] += 1;
                        if (fark == 4) rgbValuesCoverRed[Xi][Xj] += 1;

                       tempCoverGreen= (rgbValuesCoverGreen[Xi][Xj] + 2 * rgbValuesCoverGreen[Yi][Yj]) % 5;
                       fark = tempCoverGreen - Integer.parseInt(BeslikValuesSecretGreen[i][j].substring(k,k+1));
                       fark = (fark + 5) % 5;

                       if (fark == 1) rgbValuesCoverGreen[Xi][Xj] -= 1;
                       if (fark == 2) rgbValuesCoverGreen[Yi][Yj] -= 1;
                       if (fark == 3) rgbValuesCoverGreen[Yi][Yj] += 1;
                       if (fark == 4) rgbValuesCoverGreen[Xi][Xj] += 1;


                       tempCoverBlue= (rgbValuesCoverBlue[Xi][Xj] + 2 * rgbValuesCoverBlue[Yi][Yj]) % 5;
                       fark = tempCoverBlue - Integer.parseInt(BeslikValuesSecretBlue[i][j].substring(k,k+1));
                       fark = (fark + 5) % 5;

                       if (fark == 1) rgbValuesCoverBlue[Xi][Xj] -= 1;
                       if (fark == 2) rgbValuesCoverBlue[Yi][Yj] -= 1;
                       if (fark == 3) rgbValuesCoverBlue[Yi][Yj] += 1;
                       if (fark == 4) rgbValuesCoverBlue[Xi][Xj] += 1;




                       clr = Color.argb(255,rgbValuesCoverRed[Xi][Xj],rgbValuesCoverGreen[Xi][Xj],rgbValuesCoverBlue[Xi][Xj]);
                        bitmapStego.setPixel(Xi,Xj,clr);

                       clr = Color.argb(255,rgbValuesCoverRed[Yi][Yj],rgbValuesCoverGreen[Yi][Yj],rgbValuesCoverBlue[Yi][Yj]);
                       bitmapStego.setPixel(Yi,Yj,clr);

                        t +=2;

                    }
                    artısMiktar += 7 ;
                }
            }

            iv3.setImageBitmap(bitmapStego);
            String sdcardBmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/stego1bmp.bmp";
         //   Bitmap testBitmap = BitmapFactory.decodeResource(getResources());
            AndroidBmpUtil bmpUtil = new AndroidBmpUtil();
            boolean isSaveResult = bmpUtil.save(bitmapStego, sdcardBmpPath);

        }

        else if((((CoverImage.getHeight()* CoverImage.getWidth())/9) == (SecretImage.getHeight()* SecretImage.getWidth()))){

            int artısMiktar = 0;
            int t ,Xi,Xj,Yi,Yj,Zi,Zj,fark;

            for(int j=0; j < SecretImage.getHeight(); j++) // satir
            {
                for(int i=0; i < SecretImage.getWidth(); i++){ //sutun

                    t =  SecretImage.getWidth()*j + i +artısMiktar ;

                    for(int k = 0; k < 3; k++) {

                        Xi = t%CoverImage.getWidth();
                        Xj = t/CoverImage.getWidth();

                        Yi =(t+1)%CoverImage.getWidth();
                        Yj =(t+1)/CoverImage.getWidth();

                        Zi =(t+2)%CoverImage.getWidth();
                        Zj =(t+2)/CoverImage.getWidth();


                        tempCoverRed = (rgbValuesCoverRed[Xi][Xj] + 2 * rgbValuesCoverRed[Yi][Yj] + 3*rgbValuesCoverRed[Zi][Zj]) % 7;
                        fark = tempCoverRed - Integer.parseInt(BeslikValuesSecretRed[i][j].substring(k,k+1));
                        fark = (fark + 7) % 7;
                        if (fark == 1) rgbValuesCoverRed[Xi][Xj] -= 1;
                        if (fark == 2) rgbValuesCoverRed[Yi][Yj] -= 1;
                        if (fark == 3) rgbValuesCoverRed[Zi][Zj] -= 1;
                        if (fark == 4) rgbValuesCoverRed[Zi][Zj] += 1;
                        if (fark == 6) rgbValuesCoverRed[Yi][Yj] += 1;
                        if (fark == 4) rgbValuesCoverRed[Xi][Xj] += 1;

                        tempCoverGreen= (rgbValuesCoverGreen[Xi][Xj] + 2 * rgbValuesCoverGreen[Yi][Yj] + 3*rgbValuesCoverGreen[Zi][Zj]) % 7;
                        fark = tempCoverGreen - Integer.parseInt(BeslikValuesSecretGreen[i][j].substring(k,k+1));
                        fark = (fark + 7) % 7;

                        if (fark == 1) rgbValuesCoverGreen[Xi][Xj] -= 1;
                        if (fark == 2) rgbValuesCoverGreen[Yi][Yj] -= 1;
                        if (fark == 3) rgbValuesCoverGreen[Zi][Zj] -= 1;
                        if (fark == 4) rgbValuesCoverGreen[Zi][Zj] += 1;
                        if (fark == 6) rgbValuesCoverGreen[Yi][Yj] += 1;
                        if (fark == 4) rgbValuesCoverGreen[Xi][Xj] += 1;

                        tempCoverBlue=(rgbValuesCoverBlue[Xi][Xj] + 2 * rgbValuesCoverBlue[Yi][Yj] + 3*rgbValuesCoverBlue[Zi][Zj]) % 7;
                        fark = tempCoverBlue - Integer.parseInt(BeslikValuesSecretBlue[i][j].substring(k,k+1));
                        fark = (fark + 7) % 7;

                        if (fark == 1) rgbValuesCoverBlue[Xi][Xj] -= 1;
                        if (fark == 2) rgbValuesCoverBlue[Yi][Yj] -= 1;
                        if (fark == 3) rgbValuesCoverBlue[Zi][Zj] -= 1;
                        if (fark == 4) rgbValuesCoverBlue[Zi][Zj] += 1;
                        if (fark == 6) rgbValuesCoverBlue[Yi][Yj] += 1;
                        if (fark == 4) rgbValuesCoverBlue[Xi][Xj] += 1;




                        clr = Color.argb(255,rgbValuesCoverRed[Xi][Xj],rgbValuesCoverGreen[Xi][Xj],rgbValuesCoverBlue[Xi][Xj]);
                        bitmapStego.setPixel(Xi,Xj,clr);

                        clr = Color.argb(255,rgbValuesCoverRed[Yi][Yj],rgbValuesCoverGreen[Yi][Yj],rgbValuesCoverBlue[Yi][Yj]);
                        bitmapStego.setPixel(Yi,Yj,clr);

                        clr = Color.argb(255,rgbValuesCoverRed[Zi][Zj],rgbValuesCoverGreen[Zi][Zj],rgbValuesCoverBlue[Zi][Zj]);
                        bitmapStego.setPixel(Zi,Zj,clr);

                        t +=3;

                    }
                    artısMiktar += 8 ;
                }
            }

            iv3.setImageBitmap(bitmapStego);
            String sdcardBmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/stego2bmp.bmp";
            AndroidBmpUtil bmpUtil = new AndroidBmpUtil();
            boolean isSaveResult = bmpUtil.save(bitmapStego, sdcardBmpPath);

        }
        else if(((CoverImage.getHeight()* CoverImage.getWidth())/9) >(SecretImage.getHeight()* SecretImage.getWidth())){
            Toast.makeText(getApplicationContext(),"GİZLENECEK GÖRÜNTÜ ÇOK BÜYÜK", Toast.LENGTH_LONG).show();
        }

    }

    public void SecretClick(View v){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,SECRET_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case COVER_PICTURE:
                if(requestCode ==  1){

                    Uri uri = data.getData();
                    String[]projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                   CoverImage = BitmapFactory.decodeFile(filePath);
                    Drawable cover =  new BitmapDrawable(CoverImage);

                  /*  BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    CoverImage = BitmapFactory.decodeFile(filePath,options);*/

                    iv1.setBackground(cover);

                    rgbValuesCoverRed = new int[CoverImage.getWidth()][CoverImage.getHeight()];
                    rgbValuesCoverGreen = new int[CoverImage.getWidth()][CoverImage.getHeight()];
                    rgbValuesCoverBlue = new int[CoverImage.getWidth()][CoverImage.getHeight()];

                    for(int i=0; i < CoverImage.getWidth(); i++)
                    {
                        for(int j=0; j < CoverImage.getHeight(); j++)
                        {
                            //This is a great opportunity to filter the ARGB values

                            rgbValuesCoverRed[i][j] = Color.red(CoverImage.getPixel(i, j));
                            rgbValuesCoverGreen[i][j] = Color.green(CoverImage.getPixel(i, j));
                            rgbValuesCoverBlue[i][j] = Color.blue(CoverImage.getPixel(i, j));

                            if( rgbValuesCoverRed[i][j] == 255 ) rgbValuesCoverRed[i][j]-=1;
                            if( rgbValuesCoverGreen[i][j] == 255 ) rgbValuesCoverGreen[i][j]-=1;
                            if( rgbValuesCoverBlue[i][j] == 255 ) rgbValuesCoverBlue[i][j]-=1;

                            if( rgbValuesCoverRed[i][j] == 0 ) rgbValuesCoverRed[i][j]+=1;
                            if( rgbValuesCoverGreen[i][j] == 0 ) rgbValuesCoverGreen[i][j]+=1;
                            if( rgbValuesCoverBlue[i][j] == 0 ) rgbValuesCoverBlue[i][j]+=1;

                            // i satır j sütun


                        }
                    }

                }
                break;
            case SECRET_PICTURE:
                if(requestCode ==  2){

                    Uri uri = data.getData();
                    String[]projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    SecretImage = BitmapFactory.decodeFile(filePath);
                    Drawable secret =  new BitmapDrawable(SecretImage);

                    iv2.setBackground(secret);

                    rgbValuesSecretRed = new int[SecretImage.getWidth()][SecretImage.getHeight()];
                    rgbValuesSecretGreen = new int[SecretImage.getWidth()][SecretImage.getHeight()];
                    rgbValuesSecretBlue = new int[SecretImage.getWidth()][SecretImage.getHeight()];

                    BeslikValuesSecretRed = new String[SecretImage.getWidth()][SecretImage.getHeight()];
                    BeslikValuesSecretGreen = new String[SecretImage.getWidth()][SecretImage.getHeight()];
                    BeslikValuesSecretBlue = new String[SecretImage.getWidth()][SecretImage.getHeight()];

                    yedilikValuesSecretRed = new String[SecretImage.getWidth()][SecretImage.getHeight()];
                    yedilikValuesSecretGreen = new String[SecretImage.getWidth()][SecretImage.getHeight()];
                    yedilikValuesSecretBlue = new String[SecretImage.getWidth()][SecretImage.getHeight()];



                    for(int i=0; i < SecretImage.getWidth(); i++)
                    {
                        for(int j=0; j < SecretImage.getHeight(); j++)
                        {
                            //This is a great opportunity to filter the ARGB values

                            rgbValuesSecretRed[i][j] = Color.red(SecretImage.getPixel(i, j));
                            rgbValuesSecretGreen[i][j] =  Color.green(SecretImage.getPixel(i, j));
                            rgbValuesSecretBlue[i][j] = Color.blue(SecretImage.getPixel(i, j));



                            BeslikValuesSecretRed[i][j] = String.valueOf(dec5bin( rgbValuesSecretRed[i][j],5));
                            BeslikValuesSecretGreen[i][j] =  String.valueOf(dec5bin(rgbValuesSecretGreen[i][j],5));
                            BeslikValuesSecretBlue[i][j] =  String.valueOf(dec5bin( rgbValuesSecretBlue[i][j],5));

                            yedilikValuesSecretRed[i][j] = String.valueOf(dec5bin( rgbValuesSecretRed[i][j] , 7));
                            yedilikValuesSecretGreen[i][j] =  String.valueOf(dec5bin(rgbValuesSecretGreen[i][j],7));
                            yedilikValuesSecretBlue[i][j] =  String.valueOf(dec5bin( rgbValuesSecretBlue[i][j],7));

                            while(BeslikValuesSecretRed[i][j].length()<4)  BeslikValuesSecretRed[i][j] = "0"+ BeslikValuesSecretRed[i][j];
                            while(BeslikValuesSecretGreen[i][j].length()<4) BeslikValuesSecretGreen[i][j] = "0"+BeslikValuesSecretGreen[i][j];
                            while(BeslikValuesSecretBlue[i][j].length()<4) BeslikValuesSecretBlue[i][j] = "0"+BeslikValuesSecretBlue[i][j];

                            while(yedilikValuesSecretRed[i][j].length()<3)  yedilikValuesSecretRed[i][j] = "0"+ yedilikValuesSecretRed[i][j];
                            while(yedilikValuesSecretGreen[i][j].length()<3) yedilikValuesSecretGreen[i][j] = "0"+yedilikValuesSecretGreen[i][j];
                            while(yedilikValuesSecretBlue[i][j].length()<3) yedilikValuesSecretBlue[i][j] = "0"+ yedilikValuesSecretBlue[i][j];


                        }
                    }
                }
                break;
            case STEGO_PICTURE:
                if(requestCode ==  3){

                    Uri uri = data.getData();
                    String[]projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    StegoImage = BitmapFactory.decodeFile(filePath);
                    Drawable stego =  new BitmapDrawable(StegoImage);

                    iv1.setImageResource(0);
                    iv2.setBackgroundResource(0);
                    iv1.setBackground(stego);

                    rgbValuesStegoRed = new int[StegoImage.getWidth()][StegoImage.getHeight()];
                    rgbValuesStegoGreen = new int[StegoImage.getWidth()][StegoImage.getHeight()];
                    rgbValuesStegoBlue = new int[StegoImage.getWidth()][StegoImage.getHeight()];

                    for(int i=0; i < StegoImage.getWidth(); i++)
                    {
                        for(int j=0; j < StegoImage.getHeight(); j++)
                        {
                            //This is a great opportunity to filter the ARGB values

                            rgbValuesStegoRed[i][j] = Color.red(StegoImage.getPixel(i, j));
                            rgbValuesStegoGreen[i][j] = Color.green(StegoImage.getPixel(i, j));
                            rgbValuesStegoBlue[i][j] = Color.blue(StegoImage.getPixel(i, j));


                        }
                    }

                }
                break;

            default:
                break;
        }
    }
    public int dec5bin(int number,int kök) {
        int FiveNumber = 0, p = 1;
        while (number != 0) {
            FiveNumber = FiveNumber + p * (number % kök);
            number = number / kök;
            p = p * 10;
        }
       return FiveNumber;
    }
   /* public int dec7bin(int number) {
        int SevenNumber = 0, p = 1;
        while (number != 0) {
            SevenNumber = SevenNumber + p * (number % 7);
            number = number / 7;
            p = p * 10;
        }
        return SevenNumber;
    }*/
    public int bin2dec(int number,int kök)
    {
        int decNumber = 0;
        int nexInt;
        int i = 0;
        while (number != 0)
        {
            nexInt = number  % 10;
            decNumber = (int) (decNumber + nexInt * Math.pow(kök,i));
            number = number / 10;
            i++;
        }

        return decNumber;
    }


}
