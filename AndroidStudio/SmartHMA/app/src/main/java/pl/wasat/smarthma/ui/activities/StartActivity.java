package pl.wasat.smarthma.ui.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.ui.frags.common.StartFragment;
import pl.wasat.smarthma.ui.frags.common.StartFragment.OnStartFragmentListener;

public class StartActivity extends FragmentActivity implements
        OnStartFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (savedInstanceState == null) {
            StartFragment startFrag = StartFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, startFrag).commit();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

/*    @Override
    public void onStartFragmentConnectionSetup() {
        // TODO Auto-generated method stub

    }*/


}
