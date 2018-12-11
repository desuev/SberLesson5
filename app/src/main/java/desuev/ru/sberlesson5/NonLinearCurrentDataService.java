package desuev.ru.sberlesson5;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import desuev.ru.sberlesson5.API.ViewCallback;

public class NonLinearCurrentDataService extends Service {

    private IBinder binder;
    private List<ViewCallback> clients;

    public NonLinearCurrentDataService() {
        clients = new ArrayList<>();
        binder = new LocalBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate(){
        updateValue();
    }

    public class LocalBinder extends Binder{
        NonLinearCurrentDataService getService(){
            return NonLinearCurrentDataService.this;
        }
    }

    public void registerListener(ViewCallback obs) {
        clients.add(obs);
    }

    public void unregisterListener(ViewCallback obs) {
        clients.remove(obs);
    }

    public void updateValue(){
        new Thread(() -> {
            while(true) {
                for (ViewCallback e : clients) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    e.update(dateFormat.format(date));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, NonLinearCurrentDataService.class);
        return intent;
    }
}
