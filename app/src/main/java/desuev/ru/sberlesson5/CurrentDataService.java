package desuev.ru.sberlesson5;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDataService extends IntentService {


    public static final String MSG = "CONTENT";

    public CurrentDataService() {
        super(CurrentDataService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new Thread(()->{
                while(true){
                    Intent localIntent = new Intent("ru.desuev.SEND_MSG_FILTER");
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    localIntent.putExtra(MSG, dateFormat.format(date));
                    sendBroadcast(localIntent);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, CurrentDataService.class);
    }
}
