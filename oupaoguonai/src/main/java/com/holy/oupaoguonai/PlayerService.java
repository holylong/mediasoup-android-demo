package com.holy.oupaoguonai;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

public class PlayerService extends Service {
    private String path = "mnt/sdcard/123.mp3";
    private MediaPlayer player;
    private String TAG = "PlayerService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service Create");

        if (player == null) {
            //这里只执行一次，用于准备播放器
            player = new MediaPlayer();
            try {
                //player.setDataSource(path);
                //准备资源
                //player.prepare();
                player.setDataSource("http://www.w3school.com.cn/i/horse.mp3");
                //异步准备
                player.prepareAsync();
                //添加准备好的监听
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //如果准备好了，就会进行这个方法
                        mp.start();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //判断是否处于播放状态
            if (player.isPlaying()){
                player.pause();
            }else {
                player.start();
            }
        }
        Log.e("服务", "准备播放音乐");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Service Bind");
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service Destroy");
    }

    //该方法包含关于歌曲的操作
    public class MyBinder extends Binder {

        //判断是否处于播放状态
        public boolean isPlaying(){
            return player.isPlaying();
        }

        //播放或暂停歌曲
        public void play() {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
            Log.e("服务", "播放音乐");
        }

        //返回歌曲的长度，单位为毫秒
        public int getDuration(){
            return player.getDuration();
        }

        //返回歌曲目前的进度，单位为毫秒
        public int getCurrenPostion(){
            return player.getCurrentPosition();
        }

        //设置歌曲播放的进度，单位为毫秒
        public void seekTo(int mesc){
            player.seekTo(mesc);
        }
    }
}
