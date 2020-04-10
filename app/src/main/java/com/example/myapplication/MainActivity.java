package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import com.google.
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaRecorder;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int count = 0;
    final static int UPDATE_TEXTVIEW = 0;
    private Handler handler;
    Timer timer = null;
    TimerTask timerTask = null;
    boolean isPause = true;
    MediaRecorder mediaRecorder;
    private TextView location_show;
    private TextView time_show;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        time_show = findViewById(R.id.timeshow);
        final TextView location_show = findViewById(R.id.results1);

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
                    button1.setText("Press to Send");
                    location_show.setText("Dr Tan!");
//                    Intent intent = new Intent("MediaStore.Audio.Media.RECORD_SOUND_ACTION");
//                    startActivityForResult(intent,RECORD_AUDIO);

//                record();
//                Intent intent = new Intent("android.media.action.I");
                }

                else{
                    isPause=true;
                    button1.setText("Press to Record");
                    count=0;
                    Toast.makeText(MainActivity.this, "Record sent"
                            ,Toast.LENGTH_SHORT).show();
                    location_show.setText("Jiannan Tan");
//                    mediaRecorder.stop();
//                    mediaRecorder.release();
                }
            }

        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        startTimer();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        return;}
        // 获取所有可用的位置提供器
//        assert locationManager != null;
//        List<String> providerList = locationManager.getProviders(true);
//        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
//            provider = LocationManager.NETWORK_PROVIDER;
//        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
//            provider = LocationManager.GPS_PROVIDER;
//        } else {
//            // 当没有可用的位置提供器时，弹出Toast提示用户
//            Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
//            return;
//        }
        provider = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // 显示当前设备的位置信息
            showLocation(location);
        }
//        else time_show.setText("11:22");

        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
//        time_show.setText("a");

//        handler=new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                if(msg.what==UPDATE_TEXTVIEW){
//                    int minutes=count/60,seconds=count%60;
//                    time_show.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
//                }
//            }
//        };
//        //设置每一秒获取一次location信息
//        assert locationManager != null;
//        locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER,      //GPS定位提供者
//                1000,       //更新数据时间为1秒
//                1,      //位置间隔为1米
//                //位置监听器
//                new LocationListener() {  //GPS定位信息发生改变时触发，用于更新位置信息
//
//                    @Override
//                    public void onLocationChanged(Location location) {
//
//                        //GPS信息发生改变时，更新位置
//                        locationUpdates(location);
//                    }
//                    @Override
//                    //位置状态发生改变时触发
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//                    }
//
//                    @Override
//                    //定位提供者启动时触发
//                    public void onProviderEnabled(String provider) {
//                    }
//
//                    @Override
//                    //定位提供者关闭时触发
//                    public void onProviderDisabled(String provider) {
//                    }
//                });
//        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        locationUpdates(location);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            // 关闭程序时将监听器移除
            locationManager.removeUpdates(locationListener);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            showLocation(location);
        }
    };

    private void showLocation(Location location) {
        String currentPosition = "latitude is " + location.getLatitude() + "\n"+ "longitude is " + location.getLongitude();
        time_show.setText(currentPosition);
    }

//    public void locationUpdates(Location location) {  //获取指定的查询信息
//        //如果location不为空时
//        if (location != null) {
//            StringBuilder stringBuilder = new StringBuilder();        //使用StringBuilder保存数据
//            //获取经度、纬度、等属性值
//            stringBuilder.append("您的位置信息：\n");
//            stringBuilder.append("经度：");
//            stringBuilder.append(location.getLongitude());
//            stringBuilder.append("\n纬度：");
//            stringBuilder.append(location.getLatitude());
////            stringBuilder.append("\n精确度：");
////            stringBuilder.append(location.getAccuracy());
////            stringBuilder.append("\n高度：");
////            stringBuilder.append(location.getAltitude());
////            stringBuilder.append("\n方向：");
////            stringBuilder.append(location.getBearing());
////            stringBuilder.append("\n速度：");
////            stringBuilder.append(location.getSpeed());
////            stringBuilder.append("\n时间：");
////            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH mm ss");    //设置日期时间格式
////            stringBuilder.append(dateFormat.format(new Date(location.getTime())));
//            location_show.setText(stringBuilder);            //显示获取的信息
//        } else {
//            //否则输出空信息
//            location_show.setText("没有获取到GPS信息");
//        }
//    }

//    private void startTimer(){
//        if (timer==null){
//            timer=new Timer();
//        }
//
//        if(timerTask==null){
//            timerTask=new TimerTask() {
//                @Override
//                public void run() {
//                    Message message=new Message();
//                    message.what=UPDATE_TEXTVIEW;
//                    handler.sendMessage(message);
//                    do{
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }while(isPause);
//                    count++;
//                }
//            };
//        }
//
//        if(timerTask!=null&&timer!=null){
//            timer.schedule(timerTask,1000,1000);
//        }
//    }
}


