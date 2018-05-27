package projects.underhill.threebuttons;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestFragment extends Fragment {

    public AttachListener mListener;

    String myName;
    TextView mText;
    Button mButton;

    public TestFragment() {
    }

    public static TestFragment newInstance(String theName) {
        Bundle bundle = new Bundle();
        bundle.putString("Name", theName);
        android.util.Log.d("NEW INSTANCE", "theName: " + theName);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    protected void readBundle(Bundle bundle){
        if(bundle != null) {
            myName = bundle.getString("Name");
            Log.d("BUNDLE", "bundle is read: " + myName);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AttachListener) {
            mListener = (AttachListener) context;
        } else {
            throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        readBundle(getArguments());
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mText = v.findViewById(R.id.text_out);
        //if(mText != null) Log.d("CREATE","mText successfully created");
        mButton = v.findViewById(R.id.button);
        //if(mButton != null) Log.d("CREATE","mButton successfully created");
        Log.d("ON CREATE", "myName: " + myName);
        return v;
    }

    @Override
    public void onViewCreated(View v,
            Bundle savedInstanceState) {
        Log.d("ON CREATED", "myName: " + myName);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                String testString = getName() + " name";
                Log.d("ON CLICK", "testString: " + testString);
                transaction.addToBackStack(null);
                TestFragment fragment = TestFragment.newInstance(testString);
                //if(mText != null) Log.d("MAINTAIN","mText exists");

                transaction.replace(R.id.fragment_container, fragment);

                transaction.commit();
                //sb.append(" tacos");
                //String item = sb.toString();
                //((TextView)findViewById(R.id.result)).setText(fragment.myName);
            }

        });
        //mListener.updateTextView(mText, myName);
        updateText(myName);
    }

    public void updateText(String text) {
        Log.d("CREATE", "updateText called: " + text + " old text: " + mText.getText());
        //TextView textView = v.findViewById(R.id.result);
        mText.setText(text);
    }

    private String getName() {
        Log.d("GET NAME", "theName: " + myName);
        return myName;
    }

    public interface AttachListener {
        public void updateTextView(TextView tv, String s);
    }

}
