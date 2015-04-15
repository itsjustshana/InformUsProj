package shan_shine.informus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HomePagePauseWaitService extends Service {

    @Override

    public void onCreate() {

        super.onCreate();

    }

    @Override

    public void onDestroy() {

        super.onDestroy();
 }



    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }


    @Override

    public IBinder onBind(Intent arg0) {

        return null;

    }

}

