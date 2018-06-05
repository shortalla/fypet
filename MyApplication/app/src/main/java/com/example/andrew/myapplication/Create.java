package com.example.andrew.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Create extends Activity implements OnClickListener {
    String petName = "";
    Context ctx = this;
    AlertDialog.Builder dialogBuilder;
    DatabaseOperations db = new DatabaseOperations(ctx);
    Bitmap[] bmp = new Bitmap[4];
    RelativeLayout faceLayout;
    private DrawingView drawView;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private float smallPen, mediumPen, largePen;
    String petFace;
    ImageView head, body, arms, legs, face, face1, face2, face3, face4;
    TextView instruction;
    byte[] petHead, petBody, petArms, petLegs = null;
    String[] currentlyDrawing = {"head", "body", "arms", "legs"};
    int count = 0;
    byte[][] petFeature = new byte[4][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        drawView = (DrawingView)findViewById(R.id.drawing);
        instruction = (TextView)findViewById(R.id.instruction);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fypetfont.ttf");
        instruction.setTypeface(font);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);

        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        //instantiate pen sizes
        smallPen = getResources().getInteger(R.integer.small_size);
        mediumPen = getResources().getInteger(R.integer.medium_size);
        largePen = getResources().getInteger(R.integer.large_size);

        drawBtn = (ImageButton)findViewById(R.id.pen_btn);
        drawBtn.setOnClickListener(this);
        drawView.setBrushSize(mediumPen);

        eraseBtn = (ImageButton)findViewById(R.id.eraser_btn);
        eraseBtn.setOnClickListener(this);

        newBtn = (ImageButton)findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        saveBtn = (ImageButton)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        AnimationDrawable animation1 = new AnimationDrawable();
        animation1.addFrame(getResources().getDrawable(R.drawable.face1frame1), 1800);
        animation1.addFrame(getResources().getDrawable(R.drawable.face1frame2), 300);
        animation1.addFrame(getResources().getDrawable(R.drawable.face1frame3), 300);
        animation1.addFrame(getResources().getDrawable(R.drawable.face1frame2), 300);
        animation1.setOneShot(false);

        ImageView imageAnim =  (ImageView) findViewById(R.id.face1);
        imageAnim.setBackgroundDrawable(animation1);

        // start the animation!
        animation1.start();

        AnimationDrawable animation2 = new AnimationDrawable();
        animation2.addFrame(getResources().getDrawable(R.drawable.face2frame1), 1800);
        animation2.addFrame(getResources().getDrawable(R.drawable.face2frame2), 300);
        animation2.addFrame(getResources().getDrawable(R.drawable.face2frame3), 300);
        animation2.addFrame(getResources().getDrawable(R.drawable.face2frame2), 300);
        animation2.setOneShot(false);

        ImageView imageAnim2 =  (ImageView) findViewById(R.id.face2);
        imageAnim2.setBackgroundDrawable(animation2);

        // start the animation!
        animation2.start();

        AnimationDrawable animation3 = new AnimationDrawable();
        animation3.addFrame(getResources().getDrawable(R.drawable.face3frame1), 1800);
        animation3.addFrame(getResources().getDrawable(R.drawable.face3frame2), 300);
        animation3.addFrame(getResources().getDrawable(R.drawable.face3frame3), 300);
        animation3.addFrame(getResources().getDrawable(R.drawable.face3frame2), 300);
        animation3.setOneShot(false);

        ImageView imageAnim3 =  (ImageView) findViewById(R.id.face3);
        imageAnim3.setBackgroundDrawable(animation3);

        // start the animation!
        animation3.start();

        AnimationDrawable animation4 = new AnimationDrawable();
        animation4.addFrame(getResources().getDrawable(R.drawable.face4frame1), 1800);
        animation4.addFrame(getResources().getDrawable(R.drawable.face4frame2), 300);
        animation4.addFrame(getResources().getDrawable(R.drawable.face4frame3), 300);
        animation4.addFrame(getResources().getDrawable(R.drawable.face4frame2), 300);
        animation4.setOneShot(false);

        ImageView imageAnim4 =  (ImageView) findViewById(R.id.face4);
        imageAnim4.setBackgroundDrawable(animation4);

        // start the animation!
        animation4.start();

        faceLayout = (RelativeLayout) findViewById(R.id.chooseface);
        face = (ImageView)findViewById(R.id.petFaceImage);
        face1 = (ImageView)findViewById(R.id.face1);
        face1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                faceLayout.setVisibility(View.GONE);
                Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.face1frame1);
                face.setImageBitmap(bmap);
                petFace = "face1";
                instruction.setText("Please draw the head");
            }
        });
        face2 = (ImageView)findViewById(R.id.face2);
        face2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                faceLayout.setVisibility(View.GONE);
                Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.face2frame1);
                face.setImageBitmap(bmap);
                petFace = "face2";
                instruction.setText("Please draw the head");
            }
        });
        face3 = (ImageView)findViewById(R.id.face3);
        face3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                faceLayout.setVisibility(View.GONE);
                Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.face3frame1);
                face.setImageBitmap(bmap);
                petFace = "face3";
                instruction.setText("Please draw the head");
            }
        });
        face4 = (ImageView)findViewById(R.id.face4);
        face4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                faceLayout.setVisibility(View.GONE);
                Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.face4frame1);
                face.setImageBitmap(bmap);
                petFace = "face4";
                instruction.setText("Please draw the head");
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(Create.this);
        builder.setTitle("Welcome to Fypet!");
        builder.setMessage("Please follow the on-screen instructions to create your own virtual pet.");
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void paintClicked(View view){
        //use chosen color
        if(view!=currPaint){
            drawView.setErase(false);
            drawView.setBrushSize(drawView.getLastBrushSize());
            //update color
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();

            drawView.setColor(color);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
    }

    @Override
    public void onClick(View view) {
        //respond to clicks
        if (view.getId() == R.id.pen_btn) {
            //draw button clicked
            final Dialog penDialog = new Dialog(this);
            penDialog.setTitle("Pen size:");
            penDialog.setContentView(R.layout.pen_selector);

            ImageButton smallBtn = (ImageButton) penDialog.findViewById(R.id.small_pen);
            smallBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(smallPen);
                    drawView.setLastBrushSize(smallPen);
                    penDialog.dismiss();
                }
            });

            ImageButton mediumBtn = (ImageButton) penDialog.findViewById(R.id.medium_pen);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(mediumPen);
                    drawView.setLastBrushSize(mediumPen);
                    penDialog.dismiss();
                }
            });

            ImageButton largeBtn = (ImageButton) penDialog.findViewById(R.id.large_pen);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(largePen);
                    drawView.setLastBrushSize(largePen);
                    penDialog.dismiss();
                }
            });

            penDialog.show();
        }
        else if(view.getId()==R.id.eraser_btn){
            //switch to erase - choose size
            final Dialog penDialog = new Dialog(this);
            penDialog.setTitle("Eraser size:");
            penDialog.setContentView(R.layout.pen_selector);

            ImageButton smallBtn = (ImageButton) penDialog.findViewById(R.id.small_pen);
            smallBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallPen);
                    penDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton) penDialog.findViewById(R.id.medium_pen);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(mediumPen);
                    penDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton) penDialog.findViewById(R.id.large_pen);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(largePen);
                    penDialog.dismiss();
                }
            });
            penDialog.show();
        }
        else if(view.getId()==R.id.new_btn){
            //new button
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start over?\n\nWarning: You will lose everything you drawn on the screen.");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        else if(view.getId()==R.id.save_btn){
            storeDrawing();
        }
    }

    public void storeDrawing() {
        drawView.setDrawingCacheEnabled(true);
        bmp[count] = (Bitmap) drawView.getDrawingCache();
        File file = new File(Environment.getExternalStorageDirectory() + "/dirr");
        if (!file.isDirectory()) {
            file.mkdir();
        }
        file = new File(Environment.getExternalStorageDirectory()
                + "/dirr", currentlyDrawing[count] + ".png");
        //String imgSaved = MediaStore.Images.Media.insertImage(
        //        getContentResolver(), drawView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");
        //if(imgSaved != null) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (bmp[count].compress(Bitmap.CompressFormat.PNG, 100, fos)) {
                Toast savedToast = Toast.makeText(getApplicationContext(),
                        "Saved!", Toast.LENGTH_SHORT);
                savedToast.show();
                drawView.destroyDrawingCache();
            } else {
                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                        "Error!", Toast.LENGTH_SHORT);
                unsavedToast.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    // fileOutputStream.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            petFeature[count] = null;
            FileInputStream fis = new FileInputStream(file);
            petFeature[count] = new byte[fis.available()];
            fis.read(petFeature[count]);
            Log.e("location saved", "Saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("location saved", "Unable to save");
        }
        if (count == 0) {
            head = (ImageView) findViewById(R.id.petHeadImage);
            Bitmap bmpHead = BitmapFactory.decodeByteArray(petFeature[count], 0, petFeature[count].length);
            head.setImageBitmap(bmpHead);
            drawView.startNew();
            instruction.setText("Please draw the torso");
        } else if (count == 1) {
            body = (ImageView) findViewById(R.id.petBodyImage);
            Bitmap bmpBody = BitmapFactory.decodeByteArray(petFeature[count], 0, petFeature[count].length);
            body.setImageBitmap(bmpBody);
            drawView.startNew();
            instruction.setText("Please draw the arms");
        } else if (count == 2) {
            arms = (ImageView) findViewById(R.id.petArmsImage);
            Bitmap bmpArms = BitmapFactory.decodeByteArray(petFeature[count], 0, petFeature[count].length);
            arms.setImageBitmap(bmpArms);
            drawView.startNew();
            instruction.setText("Please draw the legs");
        } else if (count == 3) {
            legs = (ImageView) findViewById(R.id.petLegsImage);
            Bitmap bmpLegs = BitmapFactory.decodeByteArray(petFeature[count], 0, petFeature[count].length);
            legs.setImageBitmap(bmpLegs);
            namePetDialog();
        }
        count++;
    }

    /*
     * Inserts the virtual pet into the SQLite database.
     */
    public void savePetInDB() {
        db.insertInformation(db, petName, petFeature[0], petFeature[1], petFeature[2], petFeature[3], petFace);
        finish();
    }

    public void namePetDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText PET_NAME = new EditText(this);
        dialogBuilder.setTitle("Name Pet");
        dialogBuilder.setMessage("Please choose a name for the pet you just created:");
        dialogBuilder.setView(PET_NAME);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                petName = PET_NAME.getText().toString();
                Intent mainActivity = new Intent(ctx, MainActivity.class);
                Toast.makeText(getBaseContext(), petName + " has been created", Toast.LENGTH_LONG).show();
                savePetInDB();
                startActivity(mainActivity);
            }
        });
        AlertDialog saveDialog = dialogBuilder.create();
        saveDialog.show();
    }
}