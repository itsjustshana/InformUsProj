package shan_shine.informus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFrag extends Fragment {

    String loggedInAs;
    TextView logged;
    View v;

    public HomePageFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loggedInAs = getArguments().getString("loggedInAs");
        


       v = inflater.inflate(R.layout.fragment_home_page, container, false);

        logged = (TextView) v.findViewById(R.id.text_loggedInAs);
        logged.setText("Logged in as "+loggedInAs);
        return v;

    }


}
