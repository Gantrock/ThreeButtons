package projects.underhill.threebuttons;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import projects.underhill.threebuttons.FirstFragment;
import org.json.JSONObject;
import java.lang.reflect.Method;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class MainActivity extends AppCompatActivity implements NFragment.AttachListener{

    final static int LIMIT = 3;
    NFragment mCurrentFragment;
    int containerID;
    String master;
    NFragment startOfFragment;
    int fDex;
    boolean allSeen = false;
    List<NFragment> fList = new ArrayList<>();
    //String fPath = "projects.underhill.threebuttons.NFragment";
    //We could turn this into an custom object to have more intuitive access
    //index 0 : enter, index 1 : exit, index 2 : popEnter, index 3 : popExit
    //int[] fTransitions = new int[4];
    //int fLayout;
    //JSON has three objects classpath, transition, and layout

    private String loadJSON(String filename) {
        String json = null;
        try{
            InputStream is = getAssets().open("json/"+filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jObj = new JSONObject(json);
            String fPath = jObj.getString("classpath");

            JSONObject transitions = jObj.getJSONObject("transition");
            int[] fTransitions = new int[4];
            fTransitions[0] = getResources().getIdentifier(transitions.getString("enter"), "anim", "android");
            fTransitions[1] = getResources().getIdentifier(transitions.getString("exit"), "anim", "android");
            fTransitions[2] = getResources().getIdentifier(transitions.getString("popEnter"), "anim", "android");
            fTransitions[3] = getResources().getIdentifier(transitions.getString("popExit"), "anim", "android");

            int fLayout = this.getResources().getIdentifier(jObj.getString("layout"), "layout", getPackageName());
            buildFragment(fPath, fTransitions, fLayout);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NFragment frag =  FirstFragment.newInstance("",0);
        setContentView(R.layout.activity_main);
        containerID = R.id.coordinatorLayout;
        //loadJSON("first_fragment.json");
        //Log.d("JSON","fPath: " + fPath + " First animation: " + fTransitions[0] + " layout: " + fLayout);
        fDex = 0;

        //Verify that the fragment container is not null
        if(findViewById(R.id.fragment_container) != null) {

            if(savedInstanceState != null) {
                return;
            }
            /*Iterate through every item in assets creating fragments and adding them to fList
            * then add the using the transaction*/
            try {
                AssetManager aM = getAssets();
                String[] assets = aM.list("json");
                for(int i = 0; i < assets.length; i++) {
                    loadJSON(assets[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            fDex = 0;
            NFragment curr = fList.get(fDex);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            int[] anims = curr.getAnimation();
            transaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
            transaction.add(containerID, curr).commit();
        }

    }

    public void buildFragment(String fPath, int[] fTransitions, int fLayout) {
        fDex++;
        String className;
        //Using Reflection to get the Fragment from the classpath
        Class<?> c;
        //Method instance;
        NFragment temp;
        try{
            c = Class.forName(fPath);
            Log.d("FRAGMENT","New Class is of type: " + c.newInstance().getClass().getSimpleName());
            Class[] params = {String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class};
            //instance = c.getDeclaredMethod("newInstance", params);
            //if(mCurrentFragment == null) {
                className = c.newInstance().getClass().getSimpleName();
            //} else {
            //    className = mCurrentFragment.getFName() + " " + c.newInstance().getClass().getSimpleName();
            //}
        temp = (NFragment)NFragment.instantiate(this, fPath);
        mCurrentFragment = temp.newInstance(className, fLayout, fTransitions[0], fTransitions[1], fTransitions[2], fTransitions[3]);
        //mCurrentFragment = (NFragment) instance.invoke(temp,className, fLayout);

        } catch(Exception e){
            android.util.Log.d("EXCEPTION","exception: " + e.toString());
        }
        Log.d("FRAGMENT","mCurrentFragment is: " + mCurrentFragment.getFName() + " added at index " + fDex);
        fList.add(mCurrentFragment);
        /*if(startOfFragment != null) {
            transaction.add(containerID, mCurrentFragment).commit();
            startOfFragment = mCurrentFragment;
        } else if (mCurrentFragment != null) {
            transaction.replace(containerID, mCurrentFragment).commit();
        }*/
    }

    public void buttonPressed() {
        String fInfo = fList.get(fDex).getMString();
        fDex++;
        if(fDex >= fList.size()) {
            fDex = 0;
            fInfo = fInfo + " " + fList.get(fList.size()-1).getFName();
        } else if(fDex > 0) {
            fInfo = fInfo + " " + fList.get(fDex-1).getFName();
        }
        Log.d("FRAGMENTS", "fInfo: " + fInfo + " for fragment: " +fList.get(fDex).getFName());
        NFragment temp = fList.get(fDex);
        if(!allSeen) {
            temp.updateText(fInfo);
            //If we have gone through every fragment and are now restarting the list fDex will be o
            if(fDex == 0) allSeen = true;
        }
        //fList.get(fDex).appendString("");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int[] anims = temp.getAnimation();
        transaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
        transaction.addToBackStack(null);
        transaction.replace(containerID, temp).commit();

        /*NFragment fragment = NFragment.newInstance(name, "R.layout.fragment_first.xml", "");
        mCurrentFragment = fragment;

        transaction.replace(R.id.fragment_container, mCurrentFragment).commit();*/
    }

    public void updateTextView(TextView tv, String s) {
        tv.setText(s);
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
