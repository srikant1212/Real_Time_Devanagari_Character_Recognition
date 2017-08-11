package com.example.srika_000.nepali_hwr;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.icu.util.Output;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.util.ArrayList;

import static android.R.attr.contextClickable;
import static android.R.attr.label;
import static android.R.attr.showText;
import static android.R.attr.text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private GestureLibrary gLib,gLibrary;
    private static final String TAGa = "MainActivity";
    private TextView result,suggested;
    private String R1,R2;
    private ImageButton append,clearall,space,copy,backspace;
    private String space1=" ";
    private ClipData cd;
    private String name="Srikant";
    private File trained_data;







    Boolean fabopen = false;
    FloatingActionButton fab, fab1, fab2;
    Animation fab_open, fab_close, clock, anticlock;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1_delete);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2_help);
        result = (TextView) findViewById(R.id.resulttv);
        suggested =(TextView) findViewById(R.id.sugessted);
        //append = (ImageButton) findViewById(R.id.bAppend);
        clearall=(ImageButton) findViewById(R.id.bClearAll);
        space=(ImageButton) findViewById(R.id.bSpace);
        copy=(ImageButton) findViewById(R.id.bCopy);
        backspace=(ImageButton) findViewById(R.id.bBackSpace);


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clock);
        anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anticlock);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fabopen) {

                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab.startAnimation(anticlock);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    fabopen = false;


                } else {

                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    fab.startAnimation(clock);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    fabopen = true;

                }
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2_help);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentFab = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(intentFab);

            }
        });


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab1_delete);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(MainActivity.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirmation Exit")
                        .setMessage("Are You Sure Want To Exit??")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();


            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fonts change gareko
        //final TextView tv=(TextView)findViewById(R.id.tv);
        //Typeface my_custom_fonts1 =Typeface.createFromAsset(getAssets(),"fonts/Preeti.ttf");
        //tv.setTypeface(my_custom_fonts1);


        final GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gesturelayout);
        final ImageButton ib = (ImageButton) findViewById(R.id.bDelete);
        //to clear gesture layout after clicking delete icon
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestures.cancelClearAnimation();
                gestures.clear(true);

            }
        });


//        TODO:sabai clear garauonako lagi "bClearAll"
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestures.cancelClearAnimation();
                gestures.clear(true);
                result.setText(null);
                suggested.setText(null);


            }
        });


//<!--.............-->
        openOptionsMenu();
        gLib = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "gesture.txt");
        gLib.load();

     //   gLib = GestureLibraries.fromRawResource(this, R.raw.trained_data_2);
        //gLib = GestureLibraries.fromRawResource(this, R.raw.nep2);
//        gLib.load();
//        if (!gLib.load()) {
//            finish();
//        }





        // GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
//append garnko ko laki first ma nai + click garna parxa


//        append.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                gestures.addOnGesturePerformedListener(handleGestureListener2);
//
//
//            }
//        });
        gestures.addOnGesturePerformedListener(handleGestureListener);

            gestures.setGestureStrokeAngleThreshold(90.0f);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nepali_hwr) {
            // Handle the nepali_hwr action

        } else if (id == R.id.nav_offline) {
//            Intent intent1 =new Intent(MainActivity.this,Offline_OCR.class);
//            this.startActivity(intent1);

            Intent i =new Intent(this,CaptureImage.class);
            startActivity(i);

        } else if (id == R.id.nav_tutorial) {
            Intent i =new Intent(this,Tutorial.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {
            Intent i =new Intent(this, About.class);
            startActivity(i);

        } else if (id == R.id.nav_history) {
            //Intent i = new Intent(this,ViewDataSet.class);
            //startActivity(i);

        } else if (id == R.id.nav_OCR) {
            Intent i =new Intent(this,MainActivity2.class);
            startActivity(i);

        }
        else if (id == R.id.nav_trainedhere) {
            Intent i =new Intent(this,AddHere.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * our gesture listener
     */

    private GestureOverlayView.OnGesturePerformedListener handleGestureListener = new GestureOverlayView.OnGesturePerformedListener()
    {
        @Override
        public void onGesturePerformed(GestureOverlayView gestureView,
                                       final Gesture gesture) {


            //trained_data file read garna ko lagi
//            AssetManager am = getBaseContext().getAssets();
//            try {
//                InputStream is_trained = am.open("trained_data.txt");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }




              ArrayList<Prediction> predictions = gLib.recognize(gesture);

            //ArrayList<Prediction> predictions = is_trained.recognize(gesture);
            Log.d(TAGa, "Your Text");





//            int result1 = 0;
//            int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//            if (resId > 0) {
//                 result1 = getResources().getDimensionPixelSize(resId);
//            }
//            Log.d("result1", String.valueOf(result1));

            //android screen and height nikalna
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int height = metrics.heightPixels;
            int width = metrics.widthPixels;

            Log.i("height12",String.valueOf(height));
            Log.d("width12", String.valueOf(width));

//            // one prediction needed
//            if (predictions.size() > 0) {
//                Prediction prediction = predictions.get(0);
//                // checking prediction
//                if (prediction.score > 1 ) {
//                    // and action
//               //     TextView  result = (TextView) findViewById(R.id.resulttv);
//                     R1 = prediction.name;
//





                if (predictions.size() > 0 && predictions.get(0).score > 1.0) {


                 R1 = predictions.get(0).name;

                    {
                        //result.setText(R1);
                        // Toast.makeText(MainActivity.this, R1, Toast.LENGTH_SHORT).show();
                        //character ko name preeti font ma save garna ko lagi
                        Typeface my_custom_fonts2 = Typeface.createFromAsset(getAssets(), "fonts/Preeti.ttf");
                        result.setTypeface(my_custom_fonts2);
                        if (result == null) {
                            result.setText(R1);
                        } else {
                            result.append(R1);
                        }


                        //to show the suggestion
                        suggested.setTypeface(my_custom_fonts2);

                        int count = 0;
                        for(int i2=0; i2 <4 ;i2++) {

                            count ++;
                            Prediction prediction = predictions.get(i2);
                            Log.i(TAGa, prediction.name);

                           // while (count < 4) {
                                if (suggested == null) {
                                    // result.setText(R1);
                                    suggested.setText(prediction.name);
                                } else {
                                    // result.append(R1);
                                    suggested.append(prediction.name);
                                    suggested.append(" , ");
                                }
                           // };
                        }


//                    if(R.id.bAppend==0 ){
//                        result.setText(R1);
//                    }
//                    if(R.id.bAppend == 1) {
//                        result.append(R1);
//                    }




                }
            }
          //  TODO: SPACE BUTTON KO LAGI
            space.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    result.append(space1);
                    showToast("Spaced");

                }
            });

            //  TODO: copy BUTTON KO LAGI
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                   // result.isSelected();
                    String text = result.getText().toString();
                    cd = ClipData.newPlainText("text",text);
                    clipboard.setPrimaryClip(cd);
                    showToast("Copied");


                }
            });
            //TODO: BACKsPACE BUTTON KO LAGI
            backspace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringBuffer sb = new StringBuffer(result.getText());
                    sb = sb.deleteCharAt(result.getText().length()-1);
                    result.setText(sb.toString());
                    showToast("BackSpaced");
                }
            });




            //TODO:GESTURE'S IMAGE SAVE GARNA KO LAGI
//            gestureView.setDrawingCacheEnabled(true);
//            Bitmap bm = Bitmap.createBitmap(gestureView.getDrawingCache());
//            File f = new File(Environment.getExternalStorageDirectory()
//                    + File.separator + "signature.png");
//            try {
//                f.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            FileOutputStream os = null;
//            try {
//                os = new FileOutputStream(f);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            try {
//                os = new FileOutputStream(f);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            //compress to specified format (PNG), quality - which is ignored for PNG, and out stream
//            bm.compress(Bitmap.CompressFormat.PNG, 100, os);
//            try {
//                os.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }


    };

    //append garna ko lagi
//    public void append_function(View v)
//    {
//
//        switch (v.getId())   // v is the button that was clicked
//        {
//            case (R.id.bAppend):  // this is the oddball
//                result.append(R1);
//                break;
////            case (R.id.bDelete):  // this is the oddball
////                result.setText(R1);
////                break;
//            default:   // this will run the same code for any button clicked that doesn't have id of button1 defined in xml
//                result.setText(R1);
//                break;
//        }
//    }

//    <!-- append garna ko lagi -->
//    private GestureOverlayView.OnGesturePerformedListener handleGestureListener2 = new GestureOverlayView.OnGesturePerformedListener()
//    {
//        @Override
//        public void onGesturePerformed(GestureOverlayView gestureView,
//                                       Gesture gesture) {
//
//            ArrayList<Prediction> predictions2 = gLib.recognize(gesture);
//            Log.d(TAGa, "Your Text");
//
//            // one prediction needed
//            if (predictions2.size() > 0) {
//                Prediction prediction = predictions2.get(0);
//                // checking prediction
//                if (prediction.score > 1 ) {
//                    // and action
//                    //     TextView  result = (TextView) findViewById(R.id.resulttv);
//                    R2 = prediction.name;
//
//                    //result.setText(R1);
//                    // Toast.makeText(MainActivity.this, R1, Toast.LENGTH_SHORT).show();
//                    //character ko name preeti font ma save garna ko lagi
//                    Typeface my_custom_fonts2 =Typeface.createFromAsset(getAssets(),"fonts/Preeti.ttf");
//                    result.setTypeface(my_custom_fonts2);
//                    //result.setText(R1);
//
//                        result.append(R2);
//
//
//
//
//
//                }
//            }
//
//        }
//
//    };

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT)
                .show();
    }


}
