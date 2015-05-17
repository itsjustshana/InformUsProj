package shan_shine.informus;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.List;

import shan_shine.informus.Databases.LogInLogDatabase;

public class UpdatesCheck extends Service {

    Thread thread;
    boolean Run;
    String name;
    String result;
    MessageDatabase inbox;
    Context context;

    String messID;
    String messText;
    String email;
    String dateCr;
    String groupID;
    String read;


    public UpdatesCheck() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.

        context = getApplicationContext();

        Log.e("Service started", "Yes");

        Run = true;


        Printout.message(this, "Service Started");

        LogInLogDatabase db = new LogInLogDatabase(this);
        List<LoginLog> lb = new ArrayList<>();

        lb = db.getAllLoginItems();


        if (lb.isEmpty()) {
            Printout.message(this, " empty");
            Log.d("kkk", "lll");
        } else {
            Printout.message(this, "no empty");
            Log.d("kkk", "lll");
        }


        name = lb.get(0).getUsername();
        thread = new Thread() {
            public void run() {
                try {
                    while (Run) {


                        // do something here
                        Log.d("Workinnffff", "Yes");

                        InputStream is = null;
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

                            Log.d("FAILED", "Failed at 1");
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
                            Log.d("FSIL", "Failed at 2");
                            Log.e("log_tag", "Error converting result " + e.toString());
                        }

                        inbox = new MessageDatabase(context);

                        // groupList =new ArrayList<>();


                        if (result != null) {

                            try {
                                JSONArray Jarray = new JSONArray(result);
                                for (int i = 0; i < Jarray.length(); i++) {
                                    JSONObject Jasonobject = null;
                                    Jasonobject = Jarray.getJSONObject(i);

                                    messID = Jasonobject.getString("messageID");
                                    messText = Jasonobject.getString("messageText");
                                    email = Jasonobject.getString("email");
                                    dateCr = Jasonobject.getString("dateCreated");
                                    groupID = Jasonobject.getString("groupId");

                                    Message mess = new Message(messID, messText, groupID, email, dateCr);


                                    if (isInDatabase(messID) == false) {
                                        //  groupList.add(mess);
                                        inbox.addMessageItem(mess);
                                        setNotification(groupID, messText);
                                        //putDialog(groupID, messText);



                                        final Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.alert_xml);
                                        dialog.setTitle("New Message From: " + groupID + " " + ""+messText);
                                        Button btn = (Button) dialog.findViewById(R.id.button_okDialog);
                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        dialog.show();
                                    } else {
                                        Log.d("Indatabase", "YES!");


                                    }
                                }


                            } catch (Exception e) {
                                // TODO: handle exception
                                Printout.message(context, "This is an error message");
                                Log.e("log_tag", "Error parsing data " + e.toString());
                            }


                        } else

                        {
                            Printout.message(context, "Null received");
                        }


                        //____________________________________

                        Thread.sleep(200);
                    }

                }
                catch (Exception e)
                {
                        Log.d("NO", "local Thread error", e);


                }
            }
        };
        thread.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Run = false;
        super.onDestroy();

        Log.e("Service destroyed", "Yes");
        Printout.message(this, "Service Ended");

    }


    private Boolean isInDatabase(String messID) {
        List<Message> listt = new ArrayList<Message>();
        listt = inbox.getAllMessageItems();
        Boolean inDatabase = false;

        for (Message ti : listt) {
            String result = "Message ID: " + ti.getMessageId() + "Message Text: " + ti.getMessageText() + "Message ReadStatus: " + ti.getRead();

            if ((ti.getMessageId().equals(messID)) == true) {
                inDatabase = true;
            }

        }

        return inDatabase;

    }

    public void setNotification(String from, String message)

    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("From: "+from);
        builder.setContentText(""+message);

        Intent i = new Intent(context,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(i);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,builder.build());



    }

    public void putDialog(String from, String message)
    {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_xml);
        dialog.setTitle("New Message From: " + from + " " + ""+message);
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