package com.virtusa.adcausporte.main;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.virtusa.adcausporte.gcm.*;

import java.io.File;


public class HomeActivity extends ActionBarActivity {

    WebView mainWebview;
    LinearLayout footer;
    ProgressDialog pd;
    boolean isloginLoadedFirsttime = true;
    boolean isFooterVisible = false;
    private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "1098372533451";
    /**
     * The Constant ACTION_BAR_COLOR.
     */
    public static final int ACTION_BAR_COLOR = 0xff1F3C92;
    /**
     * The Constant STATUS_BAR_COLOR.
     */
    public static final int STATUS_BAR_COLOR = 0xdd1F3C92;
    private static final int SDK = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        ActionBar aBar = getSupportActionBar();
        // Hide application icon from action bar
        aBar.setDisplayShowHomeEnabled(false);
        // Change action bar color with configured color
        aBar.setBackgroundDrawable(new ColorDrawable(ACTION_BAR_COLOR));
        aBar.setDisplayShowTitleEnabled(true);


        if (android.os.Build.VERSION.SDK_INT >= SDK) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(STATUS_BAR_COLOR);
        }

        mainWebview = (WebView) findViewById(R.id.webViewMain);
        footer = (LinearLayout) findViewById(R.id.layoutfooterbar);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationbarcolor));
        }

        //pd = ProgressDialog.show(this, "", "Loading...",true);


        footer.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= 19) {
            mainWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mainWebview.getSettings().setUseWideViewPort(true);
        mainWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mainWebview.setVerticalScrollBarEnabled(false);
        mainWebview.setHorizontalScrollBarEnabled(false);
        mainWebview.getSettings().setAppCacheEnabled(false);
        mainWebview.getSettings().setJavaScriptEnabled(true);
        mainWebview.getSettings().setPluginState(WebSettings.PluginState.ON);


        clearCache();
        clearApplicationData();

        mainWebview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                isloginLoadedFirsttime = false;
                return false;
            }

            public void onPageFinished(WebView view, String url) {

                /*if(pd.isShowing()&&pd!=null)
                {
                    pd.dismiss();
                }*/


                invalidateOptionsMenu();
                if(!(mainWebview.getUrl().equals("http://vbid.herokuapp.com/user_login.php"))){
                    //footer.setVisibility(View.VISIBLE);



                    if(!isFooterVisible){

                        Animation slide = null;
                        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                                5.0f, Animation.RELATIVE_TO_SELF, 0.0f);

                        slide.setDuration(2000);
                        slide.setFillAfter(true);
                        slide.setFillEnabled(true);
                        footer.startAnimation(slide);

                        slide.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                        });
                        isFooterVisible = true;
                    }

                }else{

                    if(!isloginLoadedFirsttime) {
                        Animation slide = null;
                        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                                0.0f, Animation.RELATIVE_TO_SELF, 5.0f);


                        slide.setDuration(2000);
                        slide.setFillAfter(true);
                        slide.setFillEnabled(true);
                        footer.startAnimation(slide);

                        slide.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                footer.setVisibility(View.INVISIBLE);
                                isFooterVisible = false;
                            }

                        });
                    }
                }

            }
        });
        mainWebview.setWebChromeClient(new WebChromeClient());
        mainWebview.loadUrl("http://vbid.herokuapp.com/user_login.php");


        //Register for push notification
        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                /*Toast.makeText(getApplicationContext(), registrationId,
                        Toast.LENGTH_SHORT).show();*/
                // SEND async device registration to your back-end server
                // linking user with device registration id
                // POST https://my-back-end.com/devices/register?user_id=123&device_id=abc
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);

                Toast.makeText(getApplicationContext(), "Error Occured while registering the Device",
                        Toast.LENGTH_SHORT).show();

                // If there is an error registering, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off when retrying.
            }
        });



    }

    public boolean registerGCM(){

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_, menu);

        if((mainWebview.getUrl().equals("http://vbid.herokuapp.com/user_login.php"))){
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(false);
            return false;

        }else{
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
            return true;
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mainWebview.canGoBack()) {
            mainWebview.goBack();
            return true;
        } else{
            finish();
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            if(mainWebview.getUrl().equals("http://vbid.herokuapp.com") || mainWebview.getUrl().equals("http://vbid.herokuapp.com/index.php")){
                mainWebview.reload();
                Toast.makeText(getApplicationContext(), "Refreshing home page ... " , Toast.LENGTH_SHORT);
            }else{
                mainWebview.loadUrl("http://vbid.herokuapp.com");
            }

            return true;
        }if (id == R.id.action_logout) {

            mainWebview.loadUrl("http://vbid.herokuapp.com/user_login.php");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearCache(){

        mainWebview.clearCache(true);
        mainWebview.clearHistory();

        getApplicationContext().deleteDatabase("webview.db");
        getApplicationContext().deleteDatabase("webviewCache.db");
    }

    public void clearApplicationData()
    {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


        mainWebview.loadUrl(mainWebview.getUrl());
    }
}