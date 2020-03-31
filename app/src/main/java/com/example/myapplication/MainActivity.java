package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaRecorder;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int count=0;
    final static int UPDATE_TEXTVIEW=0;
    private Handler handler;
    Timer timer=null;
    TimerTask timerTask=null;
    boolean isPause=true;
    MediaRecorder mediaRecorder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView time_show=findViewById(R.id.timeshow);
        mediaRecorder = new MediaRecorder();
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "You clicked"
//                ,Toast.LENGTH_SHORT).show();
                flag = flag+1;
                if (flag%2  == 0&&flag!=0){

                    isPause=false;
                button1.setText("Press to Send to Server");
                    Intent intent = new Intent("MediaStore.Audio.Media.RECORD_SOUND_ACTION");
                    startActivityForResult(intent,RECOED_AUDIO);

//                record();
//                Intent intent = new Intent("android.media.action.I");
                }

                else{
                    isPause=true;
                    button1.setText("Press to Record");
                    count=0;
                    Toast.makeText(MainActivity.this, "Record sent"
                            ,Toast.LENGTH_SHORT).show();
                    time_show.setText("0:0");
//                    mediaRecorder.stop();
//                    mediaRecorder.release();
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

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void record(){
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        /**
//         * mediaRecorder.setOutputFormat代表输出文件的格式。该语句必须在setAudioSource之后，在prepare之前。
//         * OutputFormat内部类，定义了音频输出的格式，主要包含MPEG_4、THREE_GPP、RAW_AMR……等。
//         */
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        /**
//         * mediaRecorder.setAddioEncoder()方法可以设置音频的编码
//         * AudioEncoder内部类详细定义了两种编码：AudioEncoder.DEFAULT、AudioEncoder.AMR_NB
//         */
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//        /**
//         * 设置录音之后，保存音频文件的位置
//         */
//        File outputImage = new File(getExternalCacheDir(), "a.3pg");
//        try {
//            if (outputImage.exists()) {
//                outputImage.delete();
//            }
//            outputImage.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaRecorder.setOutputFile(outputImage);
//
//        /**
//         * 调用start开始录音之前，一定要调用prepare方法。
//         */
//        try {
//            mediaRecorder.prepare();
//            mediaRecorder.start();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


}


