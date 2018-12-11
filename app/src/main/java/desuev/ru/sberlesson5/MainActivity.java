package desuev.ru.sberlesson5;

import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import desuev.ru.sberlesson5.API.ViewCallback;

public class MainActivity extends AppCompatActivity {

    private IntentFilter filter;
    private BroadcastReceiverForLesson receiver;
    private NonLinearCurrentDataService nonLinearService;

    private TextView lVText1;
    private TextView lVText2;
    private TextView lVText3;

    private TextView lHText1;
    private TextView lHText2;
    private TextView lHText3;

    private Button rBtn1;
    private Button rBtn2;
    private Button rBtn3;
    private Button rBtn4;
    private Button rBtn5;

    private Button cBtn;
    private ProgressBar cProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        init();
    }

    private void initViews(){
        lVText1 = findViewById(R.id.firstVertLinearText);
        lVText2 = findViewById(R.id.secondVertLinearText);
        lVText3 = findViewById(R.id.thirdVertLinearText);

        lHText1 = findViewById(R.id.firstHorLinearText);
        lHText2 = findViewById(R.id.secondHorLinearText);
        lHText3 = findViewById(R.id.thirdHorLinearText);

        rBtn1 = findViewById(R.id.rBtnTopLeftCorner);
        rBtn2 = findViewById(R.id.rBtnTopRightCorner);
        rBtn3 = findViewById(R.id.rBtnBottomLeftCorner);
        rBtn4 = findViewById(R.id.rBtnBottomRightCorner);
        rBtn5 = findViewById(R.id.rBtnCentral);

        cBtn = findViewById(R.id.cBtnRotation);
        cProgressBar = findViewById(R.id.progressBar);
    }

    private void init(){
        receiver = new BroadcastReceiverForLesson(new ViewCallbackForLinears());
        filter = new IntentFilter("ru.desuev.SEND_MSG_FILTER");
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(CurrentDataService.newIntent(MainActivity.this));
        bindService();
        registerReceiver(receiver, filter,  null, null );
    }

    @Override
    protected void onPause(){
        super.onPause();
        unbindService();
        unregisterReceiver(receiver);
    }

    private void bindService(){
        bindService(NonLinearCurrentDataService.newIntent(MainActivity.this), sv, Context.BIND_AUTO_CREATE);
    }

    private void unbindService(){
        unbindService(sv);
    }

    private ServiceConnection sv = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            nonLinearService = ((NonLinearCurrentDataService.LocalBinder)service).getService();
            nonLinearService.registerListener(new ViewCallbackForRAndCLayouts());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private class ViewCallbackForLinears implements ViewCallback{
        @Override
        public void update(String data) {
            lVText1.setText(data);
            lVText2.setText(data);
            lVText3.setText(data);

            lHText1.setText(data);
            lHText2.setText(data);
            lHText3.setText(data);
        }
    }

    private class ViewCallbackForRAndCLayouts implements ViewCallback{
        @Override
        public void update(String data) {
            MainActivity.this.runOnUiThread(()->{
                rBtn1.setText(data);
                rBtn2.setText(data);
                rBtn3.setText(data);
                rBtn4.setText(data);
                rBtn5.setText(data);

                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams)cBtn.getLayoutParams();
                lp.circleAngle += 15;
                cBtn.setLayoutParams(lp);
            });
        }
    }


}
