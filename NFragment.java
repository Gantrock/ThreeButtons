package projects.underhill.threebuttons;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.content.Context;
import android.util.Log;


/**
 * A placeholder fragment containing a simple view.
 */
public class NFragment extends Fragment {

    public AttachListener mListener;

    String mName;
    String mLayout;
    String mTransitions;
    TextView mText;
    Button mButton;

    public NFragment() {
    }

    public static NFragment newInstance(String theName, String layout, String transition) {
        Bundle bundle = new Bundle();
        bundle.putString("Name", theName);
        bundle.putString("Layout", layout);
        bundle.putString("Transition", transition);
        NFragment fragment = new NFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    protected void readBundle(Bundle bundle){
        if(bundle != null) {
            mName = bundle.getString("Name");
            mLayout = bundle.getString("Layout");
            mTransitions = bundle.getString("Transition");
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
        //View v = inflater.inflate(R.layout.fragment_main, container, false);
        LinearLayout v = new LinearLayout(getActivity());
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        v.setOrientation(LinearLayout.VERTICAL);
        v.setBaselineAligned(false);
        //v.addView to add more objects
        Button tempB = new Button(getActivity());
        //tempB.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tempB.setText("Button");
        v.addView(tempB);
        mButton = tempB;

        TextView tempTV = new TextView(getActivity());
        //tempTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.addView(tempTV);
        mText = tempTV;

        /*
        mText = v.findViewById(R.id.textView);
        //if(mText != null) Log.d("CREATE","mText successfully created");
        mButton = v.findViewById(R.id.button);
        */
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed();
            }

        });
        //if(mButton != null) Log.d("CREATE","mButton successfully created");
        Log.d("ON CREATE", "myName: " + mName);
        return (View) v;
    }

    @Override
    public void onViewCreated(View v,
            Bundle savedInstanceState) {
        Log.d("ON CREATED", "myName: " + mName);
        mListener.updateTextView(mText, mName);
        //updateText(myName);
    }

    public void updateText(String text) {
        Log.d("CREATE", "updateText called: " + text + " old text: " + mText.getText());
        //TextView textView = v.findViewById(R.id.result);
        mText.setText(text);
    }

    public String getName() {
        Log.d("GET NAME", "theName: " + mName);
        return mName;
    }

    public interface AttachListener {
        public void buttonPressed();
        public void updateTextView(TextView tv, String s);
    }

}
