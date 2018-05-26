package projects.underhill.threebuttons;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestFragment extends Fragment {
    String myName;

    public TestFragment() {
    }

    public static TestFragment newInstance(String theName) {
        Bundle bundle = new Bundle();
        bundle.putString("Name", theName);
        //android.util.Log.d("CREATE", "Fragment successfully created");
        TestFragment fragment = new TestFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    protected void readBundle(Bundle bundle){
        if(bundle != null) {
            myName = bundle.getString("Name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        readBundle(getArguments());
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        Button nextF = v.findViewById(R.id.button);
        nextF.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                String testString = "name" + myName;
                transaction.addToBackStack(null);
                SecondFragment fragment = (SecondFragment) SecondFragment.newInstance(testString);

                transaction.replace(R.id.fragment_container, fragment);


                transaction.commit();
                //sb.append(" tacos");
                //String item = sb.toString();
                //((TextView)findViewById(R.id.result)).setText(fragment.myName);
            }

        });

        updateText(v, myName);
        return v;
    }

    public void updateText(View v, String text) {
        TextView textView = v.findViewById(R.id.result);
        textView.setText(text);
    }

}
