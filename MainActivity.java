package projects.underhill.threebuttons;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class MainActivity extends AppCompatActivity implements NFragment.AttachListener{

    NFragment mCurrentFragment;
    boolean defaultUsed = false;
    int containerID;
    int fDex;
    String defaultJSON = "first_fragment.json";
    boolean allSeen = false;
    List<NFragment> fList = new ArrayList<>();

    //JSON has three objects classpath, transition, and layout



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerID = R.id.coordinatorLayout;
        fDex = 0;

        //Verify that the fragment container is not null
        if(findViewById(containerID) != null) {

            if(savedInstanceState != null) {
                return;
            }
            loadJSON(defaultJSON, "");
            /*Iterate through every item in assets creating fragments and adding them to fList
            * then add the using the transaction*/
            /*try {
                AssetManager aM = getAssets();
                String[] assets = aM.list("json");
                for(int i = 0; i < assets.length; i++) {
                    loadJSON(assets[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
           /* fDex = 0;
            NFragment curr = fList.get(fDex);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            int[] anims = curr.getAnimation();
            transaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
            transaction.add(containerID, curr).commit();*/
        }

    }

    /**
     *
     * @param filename
     * @param text
     */
    private void loadJSON(String filename, String text) {
        String json;
        try{
            InputStream is = getAssets().open("json/"+filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jObj = new JSONObject(json);
            String fPath = jObj.getString("classpath");
            String fNext = jObj.getString("nextFragment");

            JSONObject transitions = jObj.getJSONObject("transition");
            int[] fTransitions = new int[4];
            fTransitions[0] = getResources().getIdentifier(transitions.getString("enter"), "anim", "android");
            fTransitions[1] = getResources().getIdentifier(transitions.getString("exit"), "anim", "android");
            fTransitions[2] = getResources().getIdentifier(transitions.getString("popEnter"), "anim", "android");
            fTransitions[3] = getResources().getIdentifier(transitions.getString("popExit"), "anim", "android");

            int fLayout = this.getResources().getIdentifier(jObj.getString("layout"), "layout", getPackageName());
            buildFragment(fPath, fNext, fTransitions, fLayout, text);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fPath
     * @param fTransitions
     * @param fLayout
     */
    public String buildFragment(String fPath, String next, int[] fTransitions, int fLayout, String text) {
        String className = " ";
        //Using Reflection to get the Fragment from the classpath
        Class<?> c;
        try{
            c = Class.forName(fPath);
            className = c.newInstance().getClass().getSimpleName();
            //if(text.length() > 0){text = className + text;}
            //text = className + text;
            NFragment temp = (NFragment) getSupportFragmentManager().findFragmentByTag(className);
            if(temp == null/*!getSupportFragmentManager().popBackStackImmediate(className, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE))*/) {
                NFragment mFragment = NFragment.newInstance(className, next, fLayout, fTransitions[0], fTransitions[1], fTransitions[2], fTransitions[3], text);
                Log.d("BUILD FRAGMENT", "Fragment created: " + className +" className for verification " + className);
                addFragment(mFragment, className);
            } else {
                Log.d("BUILD FRAGMENT", "Fragment found: " + className);
                replaceFragment(className);
            }

        } catch(Exception e){
            android.util.Log.d("EXCEPTION","exception: " + e.toString());
        }
        return className;
    }

    /**
     *
     * @param fragment
     */
    public void addFragment(NFragment fragment, String name){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //Log.d("ADD FRAGMENT","Tag is: " + fragment.getTag());
        int[] anims = fragment.getAnimation();
        transaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
        if(getSupportFragmentManager().getBackStackEntryCount() == 0 && !defaultUsed) {
            transaction.add(containerID, fragment).commit();
            defaultUsed = true;
        } else {
            //transaction.addToBackStack(name);
            transaction.replace(containerID, fragment, name).addToBackStack(null).commit();
            //Log.d("TESTING ","Is this your fragment: " + getSupportFragmentManager().findFragmentByTag(name));
        }
    }

    /**
     *
     * @param fragmentName
     */
    public void replaceFragment(String fragmentName) {
        NFragment temp = (NFragment) getSupportFragmentManager().findFragmentByTag(fragmentName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int[] anims = temp.getAnimation();
        transaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
        transaction.replace(containerID, temp, fragmentName).addToBackStack(null).commit();
    }
    /**
     *
     */
    public void buttonPressed(String next, String text) {
        loadJSON(next, text);
        Log.d("BUTTON PRESS", "Next fragment is: " + next + " Text so far "+ text);
        //loadJSON(next);


        /*String fInfo = fList.get(fDex).getMString();
        fDex++;
        if(fDex >= fList.size()) {
            fDex = 0;
            fInfo = fInfo + " " + fList.get(fList.size()-1).getFName();
        } else if(fDex > 0) {
            fInfo = fInfo + " " + fList.get(fDex-1).getFName();
        }
        NFragment temp = fList.get(fDex);
        if(!allSeen) {
            temp.updateText(fInfo);
            //If we have gone through every fragment and are now restarting the list fDex will be o
            if(fDex == 0) allSeen = true;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int[] anims = temp.getAnimation();
        transaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
        transaction.addToBackStack(null);
        transaction.replace(containerID, temp).commit();
*/
    }

    /**
     *
     * @param tv
     * @param s
     */
    public void updateTextView(TextView tv, String s) {
        tv.setText(s);
    }

    /**
     * 
     */
    @Override
    public void onBackPressed(){

        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            //moveTaskToBack(true);
            super.onBackPressed();
        }
    }
}
