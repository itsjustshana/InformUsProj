package shan_shine.informus;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import shan_shine.informus.Databases.LogInLogDatabase;

public class CheckForUpdatesService extends Service {
    Thread thread;
    boolean Run;

    String name;

    public CheckForUpdatesService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.e("Service started", "Yes");

        Run = true;


        Printout.message(this, "Service Started");

        LogInLogDatabase db = new LogInLogDatabase(this);
        List<LoginLog> lb = new ArrayList<>();

        lb= db.getAllLoginItems();

        if(lb.isEmpty())
        {
            Printout.message(this, " empty");
            Log.d("kkk","lll");
        }
        else
        {
            Printout.message(this, "no empty");
            Log.d("kkk", "lll");
        }


        thread = new Thread() {
            public void run() {
                while (Run) {
                    try {
                        // do something here
                        Log.d("Workinnffff", "Yes");


                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        Log.d("NO", "local Thread error", e);

                    }
                }
            }
        };
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Run = false;
        super.onDestroy();

        Log.e("Service destroyed", "Yes");
        Printout.message(this, "Service Ended");

        try {
            thread.join();
        } catch (Exception e) {
            Log.d("Creahed", "Crashess");
        }

        // Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }



}
