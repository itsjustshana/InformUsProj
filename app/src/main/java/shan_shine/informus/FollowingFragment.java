package shan_shine.informus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {
View v;
    String result;
    Context context;
    String groupId;
    ListView listViewHandle;

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= getActivity().getApplicationContext();
        result = getArguments().getString("Groups");
        Printout.message(context, ""+ result);
        // Inflate the layout for this fragment
        v=  inflater.inflate(R.layout.fragment_following, container, false);

        listViewHandle = (ListView) v.findViewById(R.id.listView_groupsFollowed);

        final ArrayList<String> groupList = new ArrayList<String>();

        if (result != null) {

            try {
                JSONArray Jarray = new JSONArray(result);
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    groupId = Jasonobject.getString("groupId");
                    groupList.add(groupId);

                    //Printout.message(context, "Creator"+creator);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, groupList);
                listViewHandle.setAdapter(adapter);


            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            listViewHandle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Printout.message(context, "working, Item clicked: " + position + " " + id + groupList.get(position).toString());


                }
            });
        }

        return v;
    }


}
