package projects.underhill.threebuttons;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;


public class SecondFragment extends TestFragment {
    String myName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        readBundle(getArguments());
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        android.widget.Button nextF = v.findViewById(R.id.button);
        nextF.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                String testString = "name" + myName;
                transaction.addToBackStack(null);
                TestFragment fragment = TestFragment.newInstance(testString);

                transaction.replace(R.id.fragment_container, fragment);


                transaction.commit();

            }

        });

        updateText(v, myName);
        return v;
    }

}
