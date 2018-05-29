package projects.underhill.threebuttons;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String mString;
    int mLayout = R.layout.dynamic;
    int animEnter;
    int animExit;
    int animPopEnter;
    int animPopExit;
    TextView mText;
    Button mButton;

    public NFragment() {
    }

    public static NFragment newInstance(String theName, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit) {
        Bundle bundle = new Bundle();
        bundle.putString("Name", theName);
        bundle.putInt("Layout", layout);
        bundle.putInt("AnimEnter", enter);
        bundle.putInt("AnimExit", exit);
        bundle.putInt("AnimPopEnter", popEnter);
        bundle.putInt("AnimPopExit", popExit);
        NFragment fragment = new NFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    protected void readBundle(Bundle bundle){
        if(bundle != null) {
            mName = bundle.getString("Name");
            mLayout = bundle.getInt("Layout");
            animEnter = bundle.getInt("AnimEnter");
            animExit = bundle.getInt("AnimExit");
            animPopEnter = bundle.getInt("AnimPopEnter");
            animPopExit = bundle.getInt("AnimPopExit");
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
        View v = inflater.inflate(mLayout, container, false);
        /*LinearLayout v = new LinearLayout(getActivity());
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
        mText = tempTV;*/


        mText = v.findViewById(R.id.textView);
        mButton = v.findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed();
            }

        });
        return (View) v;
    }

    public int[] getAnimation(){
        int[] transitions = new int[4];
        transitions[0] = animEnter;
        transitions[1] = animExit;
        transitions[2] = animPopEnter;
        transitions[3] = animPopExit;
        return transitions;
    }
    @Override
    public void onViewCreated(View v,
            Bundle savedInstanceState) {
        if(mString == null) {mString = " ";}
        Log.d("NFRAGMENT","View Created with class name: " + mName);
        mListener.updateTextView(mText, mString);
    }

    public void updateText(String text) {
        mString = text;
    }

    public String getFName() {
        Log.d("GET NAME", "theName: " + mName);
        return mName;
    }

    public String getMString(){
        return mString;
    }

    public interface AttachListener {
        public void buttonPressed();
        public void updateTextView(TextView tv, String string);
    }

}
