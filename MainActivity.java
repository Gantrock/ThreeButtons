package projects.underhill.threebuttons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    int testCount;
    //TextView name;
    //Button nextF;
    //StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        testCount = 0;
        //sb = new StringBuilder();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verify that the fragment container is not null
        if(findViewById(R.id.fragment_container) != null) {

            if(savedInstanceState != null) {
                return;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

            TestFragment first = TestFragment.newInstance("First");

            first.setArguments(getIntent().getExtras());

            transaction.add(R.id.fragment_container, first).commit();

        }

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /*@Override
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
    }*/

    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
