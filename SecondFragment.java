package projects.underhill.threebuttons;

public class SecondFragment extends NFragment {

    public static SecondFragment newInstance(String theName, String next, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit, String text) {
        return (SecondFragment) NFragment.newInstance(theName, next, layout, enter, exit, popEnter, popExit, text);
    }
}
