package projects.underhill.threebuttons;

public class SecondFragment extends NFragment {

    public static SecondFragment newInstance(String theName, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit) {
        return (SecondFragment) NFragment.newInstance(theName, layout, enter, exit, popEnter, popExit);
    }
}
