package shan_shine.informus;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupsFragment extends Fragment {

    String loggedInAs;
    View v;
    TextView logged;
    Context context;
    InputStream is = null ;
    String result;
    String creator;
    ListView listViewHandle;
    Communicato comm;

    public MyGroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        loggedInAs = getArguments().getString("Logged as");
        result = getArguments().getString("Groups");

        context = getActivity().getApplicationContext();


        v = inflater.inflate(R.layout.fragment_my_groups, container, false);
 listViewHandle = (ListView) v.findViewById(R.id.listView_groups);


        logged = (TextView) v.findViewById(R.id.text_loggedAsMyGroupsPage);
        logged.setText("Logged in as " + loggedInAs);

        final ArrayList<String> groupList = new ArrayList<String>();

        try {
            JSONArray Jarray = new JSONArray(result);
            for(int i=0;i<Jarray.length();i++) {
                JSONObject Jasonobject = null;
                Jasonobject = Jarray.getJSONObject(i);

                creator = Jasonobject.getString("name");
                groupList.add(creator);

               //Printout.message(context, "Creator"+creator);
            }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, groupList);
        listViewHandle.setAdapter(adapter);


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error parsing data "+e.toString());
        }

        listViewHandle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Printout.message(context, "working, Item clicked: "+position +" "+id +groupList.get(position).toString());
                comm.respondSendToGroup(groupList.get(position).toString());

            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicato) getActivity();

    }


    public void onListItemClick ( ListView l, View v, int position, long id )
    {
      Printout.message(context, "working");
    }

    public void onListItemClicker(ListView parent, View view, int position, long id)
    {
        Printout.message(context, "working");
    }


}


