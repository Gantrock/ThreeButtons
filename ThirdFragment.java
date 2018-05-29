package projects.underhill.threebuttons;

public class ThirdFragment extends NFragment {

    public static ThirdFragment newInstance(String theName, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit) {
        return (ThirdFragment) NFragment.newInstance(theName, layout, enter, exit, popEnter, popExit);
    }

}
