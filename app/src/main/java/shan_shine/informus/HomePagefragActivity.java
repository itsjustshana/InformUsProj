package shan_shine.informus;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.FragmentTransaction;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import shan_shine.informus.Databases.LogInLogDatabase;


public class HomePagefragActivity extends FragmentActivity implements Communicato {

    Context context = this;
    String loggedInAs;
    InputStream is = null;
    String result = "";
    String hellHell ;
    String sendToDate;
    String sendToGroup;
    String sendToMessage;
    String searchValue;
    String groupWantedToAdd;
    String name;
    String formattedDate;
    CheckForUpdatesService check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        loggedInAs = getIntent().getExtras().getString("loggedInAs");



        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }


        startsService();
        putDialog();

        launchHomePage();


    }

    public void FollowingClick(View v) {
        new task3().execute();

    }

    public void FollowNewGroup(View v)
    {

        Bundle bundle = new Bundle();
        bundle.putString("Logged as", loggedInAs);
        bundle.putString("Groups", null);

        FollowerGroupSearch frag2 = new FollowerGroupSearch();
        frag2.setArguments(bundle);


        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, frag2);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        //new task5().execute();
    }

    public void launchMyGroups(View v) {
        new task2().execute();

    }

    public void createNewGroup(View v) {
        CreateGroup fragment = new CreateGroup();
        Bundle bundle = new Bundle();
        bundle.putString("Logged as", loggedInAs);

        fragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void launchHomePage(View v)
    {


        new task8().execute();

    }
    private void launchHomePage()
    {


        new task8().execute();

    }

    @Override
    public void respondSendToGroup(String data) {
        Printout.message(this, "About to send a message to members of the group " + data+ ", from "+loggedInAs);

        SendMessageToGroup fragment = new SendMessageToGroup();
        Bundle bundle = new Bundle();
        bundle.putString("Logged as", loggedInAs);
        bundle.putString("Group name", data);

        fragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();


    }


    @Override
    public void responsetoCreateMessage(String[] data)
    {
        Printout.message(context, "Got something "+data[0]+" "+data[1]+ ""+data[2]);
        sendToGroup = data[1];
        sendToMessage= data[2];
        sendToDate= data[0];
        //Printout.message(context, "Before");

        new task4().execute();
        startsService();
       // Printout.message(context, "aFTER");
    }



    @Override
    public void searchValAddGroup()
    {

     new task5().execute();
    }


    @Override
    public void joidGroupVal(String data)
    {
        Printout.message(context ,"Received group to join "+data);
        groupWantedToAdd = data;
        new task6().execute();

    }


    @Override
    public void toJoinGroup( String[] data)
    {
        Printout.message(context, "Received to join a group "+ data);

        formattedDate = data[0];
        loggedInAs = data[1];
        name= data[2];


        Printout.message(context, ""+formattedDate+" "+loggedInAs+" "+name );
        new task7().execute();

    }


    public void setNotification()

    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("New Message");
        builder.setContentText("This is new Text");

        Intent i = new Intent(context,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(i);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,builder.build());



    }


 @Override
 public void toViewMessageContent (Message data)
 {
     Printout.message(context, "Got data");

     MessageContent frag1 = new MessageContent();

     Bundle bundle = new Bundle();
     bundle.putString("Logged as", loggedInAs);
     bundle.putString("MessageText", data.getMessageText());
     bundle.putString("Date", data.getDateCreated());
     bundle.putString("GroupId", data.getGroupId());


     MessageDatabase md = new MessageDatabase(context);
     //md.updateRow(data, "T");
     md.deleteTodoItem(data);
     data.setReadRead();

     Printout.message(context, "Dont cry" + data.print());
     String messId = data.getMessageId();
     String messTxt = data.getMessageText();
     String email = data.getEmail();
     String dateCr = data.getDateCreated();
     String groupId = data.getGroupId();
     String rd = data.getRead();

     md.addMessageItemStr(messId,messTxt,email,dateCr,groupId,rd);

     List<Message> messageList = new ArrayList<Message>();

     messageList = md.getAllMessageItems();
     for (Message ti : messageList) {

         String show = ""+ti.getMessageText()+" "+ti.getRead();
         Printout.message(context, show);

     }


     frag1.setArguments(bundle);
     getSupportFragmentManager().beginTransaction()
             .add(R.id.container, frag1).commit();

 }

    //my groups
    class task2 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        InputStream is = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Fetching Groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task2.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("username", loggedInAs));


            String url_select = "http://shanalecia.com/LoadMyGroups.php";


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                //httpPost.setEntity(new UrlEncodedFormEntity(param));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is = httpEntity.getContent();

            } catch (Exception e) {

                //Printout.message(context, "Failed at 1");
                Log.e("log_tag", "Error in http connection " + e.toString());

            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                //Printout.message(context, "Here in function"+result);
               // hellHell = result;

            } catch (Exception e) {
                // TODO: handle exception
               // Printout.message(context, "Failed at 2");
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            //Printout.message(context, "Success :)");
            this.progressDialog.dismiss();
            //Printout.message(context, "The result is" + result);
            hellHell = result;


            Bundle bundle = new Bundle();
            bundle.putString("Logged as", loggedInAs);
            bundle.putString("Groups", result);

            MyGroupsFragment frag2 = new MyGroupsFragment();
            frag2.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, frag2);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    }

    //following groups
    class task3 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        InputStream is = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Fetching Groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task3.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://shanalecia.com/LoadMyFollowedGroups.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("username", loggedInAs));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is = httpEntity.getContent();

            } catch (Exception e) {

                Printout.message(context, "Failed at 1");
                Log.e("log_tag", "Error in http connection " + e.toString());
                //Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                //Printout.message(context, "Here in function"+result);
                // hellHell = result;

            } catch (Exception e) {
                // TODO: handle exception
                Printout.message(context, "Failed at 2");
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            //Printout.message(context, "Success :)");
            this.progressDialog.dismiss();
           // Printout.message(context, "The result is" + result);
            hellHell = result;


            Bundle bundle = new Bundle();
            bundle.putString("Logged as", loggedInAs);
            bundle.putString("Groups", result);


            FollowingFragment frag2 = new FollowingFragment();
            frag2.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, frag2);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    }


//Sending Message to a Group
    class task4 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);



        protected void onPreExecute() {
            progressDialog.setMessage("Sending Message Groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task4.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                    String url_select = "http://shanalecia.com/sendNewMessage.php";

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url_select);

                    List<NameValuePair> param = new ArrayList<>(1);
                    param.add(new BasicNameValuePair("dateCreated", sendToDate));
                    param.add(new BasicNameValuePair("messageText", sendToMessage));
                    param.add(new BasicNameValuePair("groupId", sendToGroup));



                        httpPost.setEntity(new UrlEncodedFormEntity(param));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();

                        //Printout.message(context, "Worked?");
                return null;

            }


            catch (Exception e) {

                Printout.message(context, "Failed at 1");

            }
            return null;
        }



        protected void onPostExecute(Void v) {

            this.progressDialog.dismiss();

        }
    }

    class task5 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        InputStream is = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Searching for groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task5.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://shanalecia.com/SearchingGroups.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is = httpEntity.getContent();

            } catch (Exception e) {

                Printout.message(context, "Failed at 1");
                Log.e("log_tag", "Error in http connection " + e.toString());
                //Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
               // Printout.message(context, "Here in function"+result);
                // hellHell = result;

            } catch (Exception e) {
                // TODO: handle exception
                Printout.message(context, "Failed at 2");
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            //Printout.message(context, "Success :)");
            this.progressDialog.dismiss();

            Printout.message(context, "The result is" + result);


             Bundle bundle = new Bundle();
        bundle.putString("Logged as", loggedInAs);
        bundle.putString("Groups", result);

        FollowerGroupSearch frag2 = new FollowerGroupSearch();
        frag2.setArguments(bundle);


        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, frag2);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();


        }
    }

    class task6 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        InputStream is = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Searching for groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task6.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://shanalecia.com/SearchingGroupsWhere.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("searchVal", groupWantedToAdd));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is = httpEntity.getContent();

            } catch (Exception e) {

                Printout.message(context, "Failed at 1");
                Log.e("log_tag", "Error in http connection " + e.toString());
                //Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                // Printout.message(context, "Here in function"+result);
                // hellHell = result;

            } catch (Exception e) {
                // TODO: handle exception
                Printout.message(context, "Failed at 2");
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            //Printout.message(context, "Success :)");
            this.progressDialog.dismiss();

            Printout.message(context, "The result is" + result);


            Bundle bundle = new Bundle();
            bundle.putString("Logged as", loggedInAs);
            bundle.putString("Result", result);

            JoinGroupFragment frag2 = new JoinGroupFragment();
            frag2.setArguments(bundle);


            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, frag2);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();


        }
    }

    //Following to a Group
    class task7 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);



        protected void onPreExecute() {
            progressDialog.setMessage("Joining Group...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task7.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                String url_select = "http://shanalecia.com/joinNewGroup.php";

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url_select);

                List<NameValuePair> param = new ArrayList<>(1);
                param.add(new BasicNameValuePair("dateAdded", formattedDate));
                param.add(new BasicNameValuePair("user", loggedInAs));
                param.add(new BasicNameValuePair("groupId",name));



                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //Printout.message(context, "Worked?");
                return null;

            }


            catch (Exception e) {

                Printout.message(context, "Failed at 1");

            }
            return null;
        }



        protected void onPostExecute(Void v) {

            this.progressDialog.dismiss();

        }
    }

    //Load All the Messages
    class task8 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        InputStream is = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Welcome Back!!!");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task8.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://shanalecia.com/test.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("username", name));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is = httpEntity.getContent();

            } catch (Exception e) {

                Printout.message(context, "Failed at 1");
                Log.e("log_tag", "Error in http connection " + e.toString());
                //Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                // Printout.message(context, "Here in function"+result);
                // hellHell = result;

            } catch (Exception e) {
                // TODO: handle exception
                Printout.message(context, "Failed at 2");
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
           // Printout.message(context, "Success :)");
            this.progressDialog.dismiss();

            //Printout.message(context, "The result is" + result);

            HomePageFrag frag1 = new HomePageFrag();


            Bundle bundle = new Bundle();
            bundle.putString("Logged as", loggedInAs);
            bundle.putString("Result", result);


           // Printout.message(context, "The result is" + result);

            //frag1.setArguments(getIntent().getExtras());
            frag1.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, frag1).commit();

        }
    }


    public void signOutClicked(View v)
    {

        stopsService(v);

     Log.d("Signout Clicked","yes");

        LogInLogDatabase lb = new LogInLogDatabase(context);

        List<LoginLog> kl = new ArrayList<>();
        kl = lb.getAllLoginItems();

        lb.close();

        if (kl.isEmpty())
        {
            Log.d("Yes is empty, ","yes0");


        }
        else
        {
            Log.d("Can sign out", "yes");

            LogInLogDatabase neww = new LogInLogDatabase(context);

            List<LoginLog> datae = new ArrayList<>();
             datae= neww.getAllLoginItems();
            if (datae.isEmpty())
            {
                Log.d("Wonder", "True");
            }
                    else {
                Log.d("Wonder", "False");
                neww.deleteLoginItem2(datae.get(0).getUsername());


                if (neww.getAllLoginItems().isEmpty())
                {
                    Log.d("EMPTY YET?", "yes");

                    MessageDatabase md = new MessageDatabase(context);

try {
    md.deleteAll();
}
catch(Exception e)
{
    Log.d("FAILSSSS", ":(");
}


                    Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
                   // nextScreen.putExtra("loggedInAs", one);
                    startActivity(nextScreen);
                    neww.close();





                    finish();
                }
                else
                {
                    Log.d("EMPTY YET?", "no");
                }

            }


        }
    }

    // Method to start the service




    // Method to start the service
    public void startsService() {
        startService(new Intent(getBaseContext(), UpdatesCheck.class));
    }

    // Method to stop the service
    public void stopsService(View view) {
        stopService(new Intent(getBaseContext(), UpdatesCheck.class));
    }



    public void putDialog()
    {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_xml);
        dialog.setTitle("HWLLO");
        Button btn = (Button) dialog.findViewById(R.id.button_okDialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }






}




