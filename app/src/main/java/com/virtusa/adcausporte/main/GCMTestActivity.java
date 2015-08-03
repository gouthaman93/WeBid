package com.virtusa.adcausporte.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.virtusa.adcausporte.gcm.GCMClientManager;
import com.virtusa.adcausporte.main.R;

public class GCMTestActivity extends ActionBarActivity {

    private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "1098372533451";

    private EditText regCode;
    private Button btnSendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcmtest);

        regCode = (EditText) findViewById(R.id.editTextcode);
        btnSendCode = (Button) findViewById(R.id.buttonsendcode);

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
                txtIntent .setType("text/plain");
                txtIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, "Reg code");
                txtIntent .putExtra(android.content.Intent.EXTRA_TEXT, regCode.getText().toString());
                startActivity(Intent.createChooser(txtIntent ,"Share"));
            }
        });

        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Toast.makeText(getApplicationContext(), registrationId,
                        Toast.LENGTH_SHORT).show();
                regCode.setText(registrationId);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gcmtest, menu);
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
