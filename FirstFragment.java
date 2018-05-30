package projects.underhill.threebuttons;

import android.support.v4.app.Fragment;

public class FirstFragment extends NFragment{

    public static FirstFragment newInstance(String theName, String next, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit,String text) {
        return (FirstFragment) NFragment.newInstance(theName, next, layout, enter, exit, popEnter, popExit, text);
    }
}
