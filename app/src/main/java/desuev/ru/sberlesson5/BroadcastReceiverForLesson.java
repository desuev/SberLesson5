package desuev.ru.sberlesson5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import desuev.ru.sberlesson5.API.ViewCallback;

public class BroadcastReceiverForLesson extends BroadcastReceiver {


    private ViewCallback callback;


    BroadcastReceiverForLesson(ViewCallback _callback){
        callback = _callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        callback.update(intent.getStringExtra(CurrentDataService.MSG));
    }
}
