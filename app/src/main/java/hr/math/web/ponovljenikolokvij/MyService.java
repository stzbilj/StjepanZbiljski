package hr.math.web.ponovljenikolokvij;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by stjepan on 2/23/18.
 */

public class MyService extends Service {

    //za timer:
    int counter = 0;

    static final int UPDATE_INTERVAL = 1000;
    private Timer timer = new Timer();
    // do tuda za timer

    private int Fibo(int n){
        if( n == 1 || n == 0) return 1;
        return Fibo(n-1) + Fibo(n-2);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // ovaj servis radi dok ga se ne zaustavi eksplicitno
        // dakle vraca sticky

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        //za timer:
        doSomethingRepeatedly();


        return START_STICKY;
    }

    //za timer
    private void doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(counter >= 20){
                    stopSelf();
                }
                else {
                    Log.d("MyService", String.valueOf(Fibo(counter)));
                    counter++;
                }
            }
        }, 0, UPDATE_INTERVAL);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        //za timer
        if (timer != null){
            timer.cancel();

        }

        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}

