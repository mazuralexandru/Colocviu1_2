package ro.pub.cs.systems.eim.Colocviu1_2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Colocviu1_2Service extends Service {

    private static final String TAG = "Colocviu1_2Service";
    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int sum = intent.getIntExtra("sum", 0);
        Log.d(TAG, "Service started with sum: " + sum);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("ro.pub.cs.systems.eim.Colocviu1_2.SUM_EXCEEDED");
                broadcastIntent.putExtra("currentTime", currentTime);
                broadcastIntent.putExtra("sum", sum);
                sendBroadcast(broadcastIntent);
                Log.d(TAG, "Broadcast sent with currentTime: " + currentTime + " and sum: " + sum);
            }
        }, 2000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        Log.d(TAG, "Service destroyed");
        super.onDestroy();
    }
}