package com.example.andrew.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Random;

public class BathPet extends AppCompatActivity {
    SQLiteDatabase sq;
    DatabaseOperations db;
    InputStream stream;
    Context ctx;
    Cursor cursor;
    TextView textPetName;
    ImageView imageViewHead, imageViewBody, imageViewArms, imageViewLegs, ImageViewFace;
    RelativeLayout pet, imageLayoutFace;
    Animation animation;
    String faceNumber;
    int count = 0;
    int num = 2000, num2, num3, num4 = 0, num5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bath_pet);

        pet = (RelativeLayout) findViewById(R.id.virtualPet);

        imageViewHead = (ImageView) findViewById(R.id.imageViewHead);
        imageViewBody = (ImageView) findViewById(R.id.imageViewBody);
        imageViewArms = (ImageView) findViewById(R.id.imageViewArms);
        imageViewLegs = (ImageView) findViewById(R.id.imageViewLegs);
        textPetName = (TextView) findViewById(R.id.textPetName);
        imageLayoutFace = (RelativeLayout) findViewById(R.id.imageLayoutFace);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fypetfont.ttf");
        textPetName.setTypeface(font);
        db = new DatabaseOperations(getApplicationContext());
        sq = db.getReadableDatabase();
        cursor = db.retrieveInformation(sq);
        if (cursor.moveToNext()) {
            String petName = cursor.getString(0);
            byte[] petHead = cursor.getBlob(1);
            byte[] petBody = cursor.getBlob(2);
            byte[] petArms = cursor.getBlob(3);
            byte[] petLegs = cursor.getBlob(4);
            String petFace = cursor.getString(5);
            Bitmap bmpHead = BitmapFactory.decodeByteArray(petHead, 0, petHead.length);
            imageViewHead.setImageBitmap(bmpHead);
            Bitmap bmpBody = BitmapFactory.decodeByteArray(petBody, 0, petBody.length);
            imageViewBody.setImageBitmap(bmpBody);
            Bitmap bmpArms = BitmapFactory.decodeByteArray(petArms, 0, petArms.length);
            imageViewArms.setImageBitmap(bmpArms);
            Bitmap bmpLegs = BitmapFactory.decodeByteArray(petLegs, 0, petLegs.length);
            imageViewLegs.setImageBitmap(bmpLegs);
            textPetName.setText(petName);
            faceNumber = petFace;
        }

        int iColor = Color.parseColor("#222222");

        int red   = (iColor & 0xFF0000) / 0xFFFF;
        int green = (iColor & 0xFF00) / 0xFF;
        int blue  = iColor & 0xFF;

        float[] matrix = { 0, 0, 0, 0, red,
                0, 0, 0, 0, green,
                0, 0, 0, 0, blue,
                0, 0, 0, 1, 0 };

        ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
        imageViewHead.setColorFilter(colorFilter);
        imageViewBody.setColorFilter(colorFilter);
        imageViewArms.setColorFilter(colorFilter);
        imageViewLegs.setColorFilter(colorFilter);

        Animation headAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        headAnim.setDuration(1000);
        headAnim.setRepeatCount(-1);
        headAnim.setRepeatMode(Animation.REVERSE);
        headAnim.setInterpolator(new LinearInterpolator());
        imageViewHead.startAnimation(headAnim);
        imageLayoutFace.startAnimation(headAnim);

        Animation armsAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        armsAnim.setDuration(1000);
        armsAnim.setRepeatCount(-1);
        armsAnim.setRepeatMode(Animation.REVERSE);
        armsAnim.setInterpolator(new LinearInterpolator());
        imageViewArms.startAnimation(armsAnim);

        Animation bodyAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        bodyAnim.setDuration(1000);
        bodyAnim.setRepeatCount(-1);
        bodyAnim.setRepeatMode(Animation.REVERSE);
        bodyAnim.setInterpolator(new LinearInterpolator());
        imageViewBody.startAnimation(bodyAnim);

        Animation legsAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        legsAnim.setDuration(2000);
        legsAnim.setRepeatCount(-1);
        legsAnim.setRepeatMode(Animation.REVERSE);
        legsAnim.setInterpolator(new LinearInterpolator());
        imageViewLegs.startAnimation(legsAnim);

        AnimationDrawable faceanimation = new AnimationDrawable();
        if (faceNumber.equals("face1")) {
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face1frame1), 1800);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face1frame2), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face1frame3), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face1frame2), 300);
        } else if (faceNumber.equals("face2")) {
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face2frame1), 1800);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face2frame2), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face2frame3), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face2frame2), 300);
        } else if (faceNumber.equals("face3")) {
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face3frame1), 1800);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face3frame2), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face3frame3), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face3frame2), 300);
        } else if (faceNumber.equals("face4")) {
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face4frame1), 1800);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face4frame2), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face4frame3), 300);
            faceanimation.addFrame(getResources().getDrawable(R.drawable.face4frame2), 300);
        }
        faceanimation.setOneShot(false);

        ImageView imageAnim = (ImageView) findViewById(R.id.imageViewFace);
        imageAnim.setBackgroundDrawable(faceanimation);

        // start the animation!
        faceanimation.start();

        final Random random = new Random();
        animation = movePet(random);
        loopAnimation();

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BathPet.this);
        builder.setMessage("Tap on " + textPetName.getText().toString() + " to wash it.\nYou may have to tap multiple times.");
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public void loopAnimation() {
        pet.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Random random = null;
                random = new Random();
                animation = null;
                num2 = 0;
                num3 = 0;
                animation = movePet(random);
                loopAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public Animation movePet(Random random) {
        num2 = 1000 - random.nextInt(num);
        num3 = 1000 - random.nextInt(num);

        animation = new TranslateAnimation(num4, num2, num5, num3);
        num4 = num2;
        num5 = num3;
        animation.setDuration(500);
        animation.setFillAfter(false);
        pet.startAnimation(animation);

        return animation;
    }

    public void washArms(View view) {
        if ( imageViewArms.getColorFilter() != null) {
            count++;
            checkHygiene();
            imageViewArms.setColorFilter(null);
            imageViewBody.bringToFront();
        }
    }
    public void washHead(View view) {
        if ( imageViewHead.getColorFilter() != null) {
            count++;
            checkHygiene();
            imageViewHead.setColorFilter(null);
            imageViewArms.bringToFront();
            imageLayoutFace.bringToFront();
        }
    }
    public void washBody(View view) {
        if ( imageViewBody.getColorFilter() != null) {
            count++;
            checkHygiene();
            imageViewBody.setColorFilter(null);
            imageViewLegs.bringToFront();
            imageLayoutFace.bringToFront();
        }
    }
    public void washLegs(View view) {
        if ( imageViewLegs.getColorFilter() != null) {
            count++;
            checkHygiene();
            imageViewLegs.setColorFilter(null);
            imageViewBody.bringToFront();
            imageViewHead.bringToFront();
            imageLayoutFace.bringToFront();
        }
    }
    public void checkHygiene() {
        if (count==4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BathPet.this);
            builder.setMessage("Success! " + textPetName.getText().toString() + " was washed!");
            builder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent mainActivity = new Intent(BathPet.this, MainActivity.class);
                            startActivity(mainActivity);
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}