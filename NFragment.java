package projects.underhill.threebuttons;

import android.support.annotation.NonNull;
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
    String nextFragment = "second_fragment.json";
    //default layout, is empty except for an invisible button and emtp textView
    int mLayout = R.layout.dynamic;
    int animEnter;
    int animExit;
    int animPopEnter;
    int animPopExit;
    TextView mText;
    Button mButton;

    public NFragment() {
    }

    public static NFragment newInstance(String theName, String next, Integer layout, Integer enter, Integer exit, Integer popEnter, Integer popExit, String text) {
        Bundle bundle = new Bundle();
        bundle.putString("Name", theName);
        bundle.putString("Next", next);
        bundle.putInt("Layout", layout);
        bundle.putInt("AnimEnter", enter);
        bundle.putInt("AnimExit", exit);
        bundle.putInt("AnimPopEnter", popEnter);
        bundle.putInt("AnimPopExit", popExit);
        bundle.putString("Extra", text);
        NFragment fragment = new NFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    protected void readBundle(Bundle bundle){
        if(bundle != null) {
            mName = bundle.getString("Name");
            nextFragment = bundle.getString("Next");
            mLayout = bundle.getInt("Layout");
            animEnter = bundle.getInt("AnimEnter");
            animExit = bundle.getInt("AnimExit");
            animPopEnter = bundle.getInt("AnimPopEnter");
            animPopExit = bundle.getInt("AnimPopExit");
            mString = bundle.getString("Extra");
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readBundle(getArguments());
        View v = inflater.inflate(mLayout, container, false);

        mText = v.findViewById(R.id.textView);
        mButton = v.findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed(nextFragment, mString);
            }

        });
        return v;
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
    public void onViewCreated(@NonNull View v,
                              Bundle savedInstanceState) {
        if(mString == null) {mString = " ";}
        mListener.updateTextView(mText, mString);
        mString = mString + " " + mName;
    }

    public void updateText(String text) {
        mString = text;
    }

    public String getFName() {
        return mName;
    }

    public String getMString(){
        return mString;
    }

    public interface AttachListener {
        void buttonPressed(String next, String text);
        void updateTextView(TextView tv, String string);
    }

}
