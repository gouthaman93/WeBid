package com.virtusa.adcausporte.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    Intent mainintent;
    ImageView splashimage;
    /**
     * The Constant ACTION_BAR_COLOR.
     */
    //public static final int ACTION_BAR_COLOR = 0xffFFFFFF;
    public static final int ACTION_BAR_COLOR = 0xff1F3C92;
    /**
     * The Constant STATUS_BAR_COLOR.
     */
    public static final int STATUS_BAR_COLOR = 0xdd1F3C92;
    //public static final int STATUS_BAR_COLOR = 0xddFFFFFF;
    private static final int SDK = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        ActionBar aBar = getSupportActionBar();
        // Hide application icon from action bar
        aBar.setDisplayShowHomeEnabled(false);
        // Change action bar color with configured color
        aBar.setBackgroundDrawable(new ColorDrawable(ACTION_BAR_COLOR));
        aBar.setDisplayShowTitleEnabled(true);

        splashimage = (ImageView) findViewById(R.id.splashimage);

        //Animation 1




        //Animation 2
        Animation slide = null;
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                5.0f, Animation.RELATIVE_TO_SELF, 0.0f);


        slide.setDuration(2000);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        splashimage.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(1000);
                animation1.setStartOffset(000);
                animation1.setFillAfter(true);
                //splashimage.startAnimation(animation1);

                splashimage.setAlpha(0.8f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashimage.setAlpha(1.0f);
            }

        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationbarcolor));
        }


        if (android.os.Build.VERSION.SDK_INT >= SDK) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(STATUS_BAR_COLOR);


        }

        if (Build.VERSION.SDK_INT < 16) {
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //show splas for 3 seconds



        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    mainintent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(mainintent);
                    finish();
                }
            }
        };
        timer.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
