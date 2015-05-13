package shan_shine.informus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerGroupSearch extends Fragment {

    Button searchForGroup;
    View v;
    Context context;

    public FollowerGroupSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context= getActivity().getApplicationContext();
        v= inflater.inflate(R.layout.fragment_follower_group_search, container, false);
        searchForGroup = (Button) v.findViewById(R.id.button_go);

        searchForGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Printout.message(context, "Button CLicked");

            }
        });

        return v;
    }


}
