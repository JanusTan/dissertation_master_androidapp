package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int count=0;
    final static int UPDATE_TEXTVIEW=0;
    private Handler handler;
    Timer timer=null;
    TimerTask timerTask=null;
    boolean isPause=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView time_show=findViewById(R.id.timeshow);
        startTimer();

        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==UPDATE_TEXTVIEW){
                    int minutes=count/60,seconds=count%60;
                    time_show.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                }
            }
        };

        final Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            int flag=1;
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "You clicked"
//                ,Toast.LENGTH_SHORT).show();
                flag = flag+1;
                if (flag%2  == 0&&flag!=0){
                    isPause=false;
                button1.setText("press to stop");
//                Intent intent = new Intent("android.media.action.I");
                }

                else{
                    isPause=true;
                    button1.setText("press to record");
                }
            }

        });




    }

    private void startTimer(){
        if (timer==null){
            timer=new Timer();
        }

        if(timerTask==null){
            timerTask=new TimerTask() {
                @Override
                public void run() {
                    Message message=new Message();
                    message.what=UPDATE_TEXTVIEW;
                    handler.sendMessage(message);
                    do{
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }while(isPause);
                    count++;
                }
            };
        }

        if(timerTask!=null&&timer!=null){
            timer.schedule(timerTask,1000,1000);
        }
    }


}


