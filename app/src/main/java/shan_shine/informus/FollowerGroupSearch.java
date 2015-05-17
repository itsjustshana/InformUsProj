package shan_shine.informus;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerGroupSearch extends Fragment {

    Button searchForGroup;
    EditText search;
    View v;
    Context context;
    String searchValue;
    String result;
    String name;
    ListView listViewHandle;
    TextView gh;

    Communicato comm;


    public FollowerGroupSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        result = getArguments().getString("Groups");

        context= getActivity().getApplicationContext();
        //Printout.message(context,groups);

        v= inflater.inflate(R.layout.fragment_follower_group_search, container, false);
        searchForGroup = (Button) v.findViewById(R.id.button_go);
        search = (EditText) v.findViewById(R.id.editText_search);
        gh= (TextView)v.findViewById(R.id.text_resultStat);


        listViewHandle = (ListView) v.findViewById(R.id.listView_searchResults);

        searchForGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Call();
                String searchVal = search.getText().toString();

                Log.d("Search Value :",searchVal);

                if(searchVal.matches(""))
                {
                    Log.d("Search Value :","null");
                    comm.searchValAddGroup();
                }
                else
                {
                    Log.d("Stringgggg", searchVal);
                    comm.joinnGroupVal(searchVal);
                }

            }
        });


        final ArrayList<String> groupList = new ArrayList<String>();

        if (result != null) {

            try {
                JSONArray Jarray = new JSONArray(result);
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    name = Jasonobject.getString("name");
                    groupList.add(name);

                    //Printout.message(context, "Creator"+creator);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, groupList);
                listViewHandle.setAdapter(adapter);


            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            if (groupList.isEmpty())
            {
                Log.d("Group Empty:", "Yes");
                gh.setText("No Results");


            }

            listViewHandle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Printout.message(context, "working, Item clicked: " + position + " " + id + groupList.get(position).toString());
                    comm.joidGroupVal(groupList.get(position).toString());

                }
            });
        }

        return v;
    }
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicato) getActivity();

    }

    private void Call()
    {
        comm.searchValAddGroup();
    }





}
