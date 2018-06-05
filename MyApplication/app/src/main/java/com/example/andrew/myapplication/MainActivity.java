package com.example.andrew.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private Button feedBtn, playBtn, walkBtn, bathBtn;
    SQLiteDatabase sq;
    DatabaseOperations db;
    InputStream stream;
    Context ctx;
    Cursor cursor, cursor2;
    ListDataAdaptor lda;
    TextView textPetName;
    ImageView imageViewHead, imageViewBody, imageViewArms, imageViewLegs, imageViewFace, bground;
    RelativeLayout pet, imageLayoutFace;
    GestureDetectorCompat gestureDetectorCompat;
    String foodName, faceNumber = "";
    String barcode;
    String[] code;
    //SimpleGestureFilter detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pet.startAnimation(jumpAnim);
        pet = (RelativeLayout) findViewById(R.id.virtualPet);

        imageViewHead = (ImageView) findViewById(R.id.imageViewHead);
        imageViewBody = (ImageView) findViewById(R.id.imageViewBody);
        imageViewArms = (ImageView) findViewById(R.id.imageViewArms);
        imageViewLegs = (ImageView) findViewById(R.id.imageViewLegs);
        imageLayoutFace = (RelativeLayout) findViewById(R.id.imageLayoutFace);
        textPetName = (TextView) findViewById(R.id.textPetName);
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
        if (textPetName.getText().toString().equals("Name")) {
            Intent creationActivity = new Intent(this, Create.class);
            startActivity(creationActivity);
            finish();
        }
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

        AnimationDrawable animation = new AnimationDrawable();
        if(!faceNumber.equals("")) {
            if (faceNumber.equals("face1")) {
                animation.addFrame(getResources().getDrawable(R.drawable.face1frame1), 1800);
                animation.addFrame(getResources().getDrawable(R.drawable.face1frame2), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face1frame3), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face1frame2), 300);
            } else if (faceNumber.equals("face2")) {
                animation.addFrame(getResources().getDrawable(R.drawable.face2frame1), 1800);
                animation.addFrame(getResources().getDrawable(R.drawable.face2frame2), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face2frame3), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face2frame2), 300);
            } else if (faceNumber.equals("face3")) {
                animation.addFrame(getResources().getDrawable(R.drawable.face3frame1), 1800);
                animation.addFrame(getResources().getDrawable(R.drawable.face3frame2), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face3frame3), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face3frame2), 300);
            } else if (faceNumber.equals("face4")) {
                animation.addFrame(getResources().getDrawable(R.drawable.face4frame1), 1800);
                animation.addFrame(getResources().getDrawable(R.drawable.face4frame2), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face4frame3), 300);
                animation.addFrame(getResources().getDrawable(R.drawable.face4frame2), 300);
            }
        }
        animation.setOneShot(false);

        ImageView imageAnim = (ImageView) findViewById(R.id.imageViewFace);
        imageAnim.setBackgroundDrawable(animation);

        // start the animation!
        animation.start();

        gestureDetectorCompat = new GestureDetectorCompat(MainActivity.this, new MyGestureListener());
        //detector = new SimpleGestureFilter(this,this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {


       //  Toast.makeText(getBaseContext(),
         // event1.toString() + "\n\n" +event2.toString(),
         // Toast.LENGTH_SHORT).show();


            if(event2.getX() < event1.getX()){
                Toast.makeText(getBaseContext(),
                        "Swipe left - startActivity()",
                        Toast.LENGTH_SHORT).show();

                //switch another activity
                Intent intent = new Intent(ctx, TimerActivity.class);
                startActivity(intent);
            }

            return true;
        }
    }


    /*@Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                Intent intent = new Intent(ctx, BathPet.class);
                startActivity(intent);
                break;

        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }*/

    public void feedPet(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Feed " + textPetName.getText().toString());
        builder.setMessage("To feed " + textPetName.getText().toString() + ", scan the barcode of a food item.\n" +
                "You may have to turn your device horizontal.");
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent feedActivity = new Intent("com.google.zxing.client.android.SCAN");
                        feedBtn = (Button) findViewById(R.id.feedButton);
                        startActivityForResult(feedActivity, 0);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void bathPet(View view) {
        Intent bathActivity = new Intent(this, BathPet.class);
        bathBtn = (Button) findViewById(R.id.bathButton);
        startActivity(bathActivity);
        finish();
    }

    public void walkPet(View view) {
        walkBtn = (Button) findViewById(R.id.walkButton);
        bground = (ImageView) findViewById(R.id.bground);

        final CharSequence[] options = {"Change location", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Walk");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Change location")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void playWithPet(View view) {
        Intent playActivity = new Intent(this, PlayWithPet.class);
        playBtn = (Button) findViewById(R.id.bathButton);
        startActivity(playActivity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 0: // Feed pet
                if (resultCode == RESULT_OK) {
                    String contents = intent.getStringExtra("SCAN_RESULT");
                    String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                    Log.e("xZing", "contents: " + contents + " format: " + format); // Handle successful scan
                    code = new String[]{contents.toString()};
                    cursor2 = null;
                    cursor2 = db.checkForFood(sq,code);
                    if (cursor2!=null && cursor2.getCount()>0) {
                        cursor2.moveToFirst();
                        foodName = cursor2.getString(0);
                        barcode = cursor2.getString(1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Food item found!");
                        builder.setIcon(getResources().getIdentifier(foodName, "drawable", getPackageName()));
                        builder.setMessage(textPetName.getText().toString() + " ate the " + foodName + "!");
                                builder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                        //Toast.makeText(getBaseContext(), textPetName.getText().toString() + " ate the " +
                          //      foodName + "!", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Oh, it looks like "+ textPetName.getText().toString() + " doesn't want to" +
                                " eat this right now!");
                        builder.setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                        //Toast.makeText(getBaseContext(), textPetName.getText().toString() + " doesn't want " +
                          //      "to eat this right now...", Toast.LENGTH_LONG).show();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Log.i("xZing", "Cancelled");
                }
                break;

            case 1: // Walk pet
            super.onActivityResult(requestCode, resultCode, intent);
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
                        Drawable d = new BitmapDrawable(getResources(), rotatedBitmap);
                        bground.setBackground(d);

                        String path = android.os.Environment
                                .getExternalStorageDirectory()
                                + File.separator
                                + "Phoenix" + File.separator + "default";
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}