package shan_shine.informus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import shan_shine.informus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnsubscribeGroup extends Fragment {

View v;
    TextView groupDescr;
    TextView nameOfGroup;
    TextView createdBy;
    String name;
    String description;
    String dateCreated;
    String result;
    String loggedInAs;
    String creator;
    Button leaveGroup;
    Communicato comm;
    Button viewAllmess;



    public UnsubscribeGroup() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicato) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        result = getArguments().getString("Result");
        loggedInAs = getArguments().getString("Logged as");

       v= inflater.inflate(R.layout.fragment_unsubscribe_group, container, false);
        TextView logged = (TextView)v.findViewById(R.id.text_logss);

        logged.setText("Logged in as " + loggedInAs);

        groupDescr = (TextView)v.findViewById(R.id.text_descritionUns);
        nameOfGroup = (TextView)v.findViewById(R.id.text_groupNameUns);
        createdBy= (TextView)v.findViewById(R.id.text_createdByJoinUns);
        leaveGroup = (Button)v.findViewById(R.id.button_leaveGroup);
        viewAllmess = (Button)v.findViewById(R.id.button_viewAllMessages);






        try {
            JSONArray Jarray = new JSONArray(result);
            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject Jasonobject = null;
                Jasonobject = Jarray.getJSONObject(i);

                name = Jasonobject.getString("name");
                description =  Jasonobject.getString("description");
                creator =  Jasonobject.getString("creator");
                dateCreated = Jasonobject.getString("dateCreated");



                nameOfGroup.setText(name);
                createdBy.setText(creator);
                groupDescr.setText(description);

            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error parsing data " + e.toString());
        }



        leaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comm.deleteGroup(name);

            }
        });


        viewAllmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            comm.viewMessages(name);
            }
        });


        return v;





    }







}
