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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFollowedMessagesByGroup extends Fragment {

    TextView top;
    View v;
    Context context;
    String result;
    ListView listViewHandle;

    String messID;
    String messText;
    String email;
    String dateCr;
    String groupID;
    String loggedInAs;

    Communicato comm;
    TextView logged;

    public ViewFollowedMessagesByGroup() {
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

        context= getActivity().getApplicationContext();
        result = getArguments().getString("Result");
        loggedInAs = getArguments().getString("Logged as");
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_view_followed_messages_by_group, container, false);
        top = (TextView)v.findViewById(R.id.text_top);
        logged= (TextView)v.findViewById(R.id.textView21);

        logged.setText("Logged in as " + loggedInAs);




        listViewHandle = (ListView) v.findViewById(R.id.listView_viewMessFoll);

        final List<Message> Messages = new ArrayList<>();
        List<String> ReadString = new ArrayList<>();

        Log.d("Resultt", result);

        if (result != null) {

            try {


                JSONArray Jarray = new JSONArray(result);
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    messID = Jasonobject.getString("messageID");
                    messText = Jasonobject.getString("messageText");
                    dateCr = Jasonobject.getString("dateCreated");
                    groupID = Jasonobject.getString("groupId");
                    email = loggedInAs;



                    Message mess = new Message(messID, messText, email, groupID, dateCr);
                    Messages.add(mess);

                    ReadString.add(""+ groupID+ " "+dateCr+ " "+messText);

                    listViewHandle = (ListView) v.findViewById(R.id.listView_viewMessFoll);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ReadString);
                    listViewHandle.setAdapter(adapter2);


                }

            } catch (Exception e) {
                // TODO: handle exception
                //Printout.message(context, "This is an error message");
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            top.setText("Messages with source: "+groupID);
        }
        else
        {
            top.setText("No Messages with source: "+groupID);
        }



        listViewHandle = (ListView) v.findViewById(R.id.listView_viewMessFoll);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ReadString);
        listViewHandle.setAdapter(adapter2);


        listViewHandle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Message clickedMessage = Messages.get(position);
                comm.toViewMessageContent2(clickedMessage);


            }
        });


        return v;
    }

}
