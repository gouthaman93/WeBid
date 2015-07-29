package com.virtusa.adcausporte.main;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.virtusa.adcausporte.gcm.*;


public class HomeActivity extends ActionBarActivity {

    WebView mainWebview;
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

        if (Build.VERSION.SDK_INT >= 19) {
            mainWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mainWebview.getSettings().setUseWideViewPort(true);
        mainWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mainWebview.setWebViewClient(new WebViewClient());
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

            mainWebview.loadUrl("http://vbid.herokuapp.com");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
