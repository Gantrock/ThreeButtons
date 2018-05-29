package projects.underhill.threebuttons;

import android.support.v4.app.Fragment;

public class FirstFragment extends NFragment{

    public static FirstFragment newInstance(String theName, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit) {
        return (FirstFragment) NFragment.newInstance(theName, layout, enter, exit, popEnter, popExit);
    }
}
