package com.example.ken.helloworld;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "com.example.ken.helloworld.EXTRA_MESSAGE";
    private ShareActionProvider mShareActionProvider;
    private Intent shareIntent = new Intent(Intent.ACTION_SEND);
    private EditText sendTo;
    private MainActivity _this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _this = this;
        setContentView(R.layout.activity_main);

        sendTo = (EditText) findViewById(R.id.sendTo);
        shareIntent.setType("text/plain");

        sendTo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                EditText sendTo = (EditText) findViewById(R.id.sendTo);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "It Works! And Lives!");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, sendTo.getText().toString());
                setShareIntent(shareIntent);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setOnShareTargetSelectedListener(
                new ShareActionProvider.OnShareTargetSelectedListener() {
                    @Override
                    public boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent) {
                        Toast.makeText(_this, intent.getStringExtra(android.content.Intent.EXTRA_TEXT).toString(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
        return true;
    }

    public void sendMessage(View view) {
        TextView textResult = (TextView) findViewById(R.id.textResult);
        textResult.setText("Sent message to " + sendTo.getText() + ".");
        Intent displayMessage = new Intent(this, DisplayMessageActivity.class);
        displayMessage.putExtra(EXTRA_MESSAGE, sendTo.getText().toString());
        startActivity(displayMessage);
        setShareIntent(displayMessage);
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

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
