package com.virtusa.jayasai.webid;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmReceiver;
import com.virtusa.jayasai.gcm.*;


public class Home_Activity extends ActionBarActivity {

    WebView mainWebview;
    private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "193167212796";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        mainWebview = (WebView) findViewById(R.id.webViewMain);

        if (Build.VERSION.SDK_INT >= 19) {
            mainWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        //check for intenet

        mainWebview.loadUrl("https://www.google.lk");

        //Register for push notification
        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Toast.makeText(getApplicationContext(), registrationId,
                        Toast.LENGTH_SHORT).show();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
