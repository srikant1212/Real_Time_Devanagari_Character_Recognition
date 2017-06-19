package com.example.srika_000.nepali_hwr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddHere extends AppCompatActivity {
    private GestureLibrary gLib;
    private static final String TAG = "TrainedHere";
    private boolean mGestureDrawn;                      //tc
    private Gesture mCurrentGesture;                    //tc
    private String mGesturename;                        //tc


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_here);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // final TextView draw_here=(TextView) findViewById(R.id.wctext);
        //changing fonts to nepali
        //Typeface my_custom_fonts =Typeface.createFromAsset(getAssets(),"fonts/Preeti.ttf");
        //draw_here.setTypeface(my_custom_fonts);


        Log.d(TAG, "path = " + Environment.getExternalStorageDirectory().getAbsolutePath());
        //openOptionsMenu();

        //TODO : Action bar ma color
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if (bar != null) {


            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff4263ff")));
        }




        gLib = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "gesture.txt");
        gLib.load();

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGestureListener(mGestureListener);

        resetEverything(); //function call gareko last ma xa..

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    /**
     * our gesture listener
     */

    private GestureOverlayView.OnGestureListener mGestureListener = new GestureOverlayView.OnGestureListener() {
        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mGestureDrawn = true;
            Log.d(TAG, "in");
        }

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            mCurrentGesture = overlay.getGesture();
        }

        @Override

        public void onGestureEnded(GestureOverlayView gestureView, MotionEvent motion) {
            Log.d(TAG, "out");
        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
            Log.d(TAG, "cancel");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu_trained) {
        getMenuInflater().inflate(R.menu.menu_trained_data, menu_trained);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                reDrawGestureView();
                break;

            case R.id.save:
                if (mGestureDrawn) {
                    getName();
                } else {
                    showToast(getString(R.string.no_gesture));
                }
                //TODO : Save gesture as image


                //String pattern = "mm ss";
                //SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                //String time = formatter.format(new Date());
//                String path = ("/" +"data" + "/srikant" + time + ".png");
                String path = ("/" +"data" + "/srikant" + ".png");

                File file = new File(Environment.getExternalStorageDirectory() + path);

                try {
                   //DrawBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    //new FileOutputStream(file));
                    Toast.makeText(this, "File Saved ::" + path, Toast.LENGTH_SHORT)
                            .show();
                } catch (Exception e) {
                    Toast.makeText(this, "ERROR" + e.toString(), Toast.LENGTH_SHORT)
                            .show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
    private void getName() {
        AlertDialog.Builder namePopup = new AlertDialog.Builder(this);
        namePopup.setTitle(getString(R.string.enter_name));
        //namePopup.setMessage(R.string.enter_name);

        final EditText nameField = new EditText(this);

        //character ko name preeti font ma save garna ko lagi
        Typeface my_custom_fonts2 =Typeface.createFromAsset(getAssets(),"fonts/Preeti.ttf");
        nameField.setTypeface(my_custom_fonts2);



        namePopup.setView(nameField);
        namePopup.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //db.updateExistingMeasurement(measurement);
                if (!nameField.getText().toString().matches("")) //empty save garna mildaina, save garna aghi name dina parxa
                {
                    mGesturename = nameField.getText().toString();
                    saveGesture();
                } else {
                    getName();  // Popup menu ko lagi
                    showToast(getString(R.string.invalid_name));
                }
                //return;
            }
        });
        namePopup.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGesturename = "";
                return;
            }
        });

        namePopup.show();

    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT)
                .show();
    }

    //private void check for DuplicateName

    private void saveGesture() {
        // if(!mGesturename.matches("")) {
        //gLib = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "gesture.txt");
        //gLib.load();
        //TODO: gesture save garna ko lagi
        gLib.addGesture(mGesturename, mCurrentGesture);
        if (!gLib.save()) {
            Log.e(TAG, "gesture not saved!");
        } else {
            showToast(getString(R.string.gesture_saved) + getExternalFilesDir(null) + "/gesture.txt");
        }
        reDrawGestureView(); //reDrawGestureView() function lai invoke gareko ;
        // }
    }

    private void resetEverything() {
        mGestureDrawn = false;
        mCurrentGesture = null;
        mGesturename = "";
    }

    private void reDrawGestureView() {
        //setContentView(R.layout.activity_add_here );
        Intent i=new Intent(this,AddHere.class);
        startActivity(i);
        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.removeAllOnGestureListeners();
        gestures.addOnGestureListener(mGestureListener);
        resetEverything();
    }
    private void getUndoView(){
        setContentView(R.layout.activity_add_here);
        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGestureListener(mGestureListener);
    }

}
