package projects.underhill.threebuttons;

public class ThirdFragment extends NFragment {

    public static ThirdFragment newInstance(String theName, String next, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit, String text) {
        return (ThirdFragment) NFragment.newInstance(theName, next, layout, enter, exit, popEnter, popExit, text);
    }

}
