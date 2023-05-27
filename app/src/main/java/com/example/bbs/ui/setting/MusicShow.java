package com.example.bbs.ui.setting;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.Main2Activity;
import com.example.bbs.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.bbs.Main2Activity.mediaPlayer;

public class MusicShow extends AppCompatActivity {

    private List<Music> musics;
    private int Position = 0;
    private Button Stop_Music,start_music,cancel;
    private final int[] music_path = {R.raw.music01,R.raw.music02,R.raw.music03};
    private final String[] music_adult = {"碳碳","哇哦","小天才","哈哈","略略略","嘎嘎嘎"};
    private ProgressBar MusicPlayBar;
    private TextView alreadyMusicTime;
    public static MusicShow instance = null;
    private TextView musicTime;
    private Boolean flag = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_show);
        initData();
        MusicAdapter musicAdapter = new MusicAdapter(this, R.layout.music_item_list, musics);
        //创建 MyAdapter 对象
        ListView listview = findViewById(R.id.list_music);
        //设置 Adapter
        listview.setAdapter(musicAdapter);
        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position!=Position){
                    mediaPlayer.reset();
                    Music music = musics.get(position);
                    mediaPlayer = MediaPlayer.create(Main2Activity.instance,music.getMusic_path());
                    mediaPlayer.start();
                    Toast.makeText(MusicShow.this, "切换歌曲：" + music.getMusic_name(), Toast.LENGTH_SHORT).show();
                    Thread thread = new Thread(null,timeCount,"1");
                    thread.start();
                    musicTime.setText("总时长："+calculateTime(mediaPlayer.getDuration()/1000)+"秒");
                    Position = position;
                }else {
                    Toast.makeText(MusicShow.this, "正在播放本首歌" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        Stop_Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                Toast.makeText(MusicShow.this, "歌曲停止播放", Toast.LENGTH_SHORT).show();
            }
        });

        start_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

//        add_new_music.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //初始化数据
    private void initData() {
        musics = new ArrayList<Music>();
        for (int i = 1; i <= 3; i++) {
            musics.add(new Music("music_name"+i,"作者："+music_adult[i%6],music_path[i%3]));
        }
        start_music = findViewById(R.id.start_music);
        Stop_Music = findViewById(R.id.Stop_Music);
        cancel = findViewById(R.id.cancel);
        MusicPlayBar = findViewById(R.id.MusicPlayBar);
        MusicPlayBar.setMax(100);
        musicTime = findViewById(R.id.Music_timeLength);
        alreadyMusicTime = findViewById(R.id.Music_alreadyTimeLength);
        musicTime.setText("总时长："+calculateTime(mediaPlayer.getDuration()/1000)+"秒");

//        SQLiteOpenHelper helper = MusicSqliteOpenHelper.getMInstance(this);
//        SQLiteDatabase database = helper.getWritableDatabase();
//        if (database.isOpen()) {
//            Cursor cursor = database.rawQuery("select * from musics", null);
//            while (cursor.moveToNext()) {
//                String _musicName = cursor.getString(cursor.getColumnIndex("_musicName"));
//                String _musicAdult = cursor.getString(cursor.getColumnIndex("_musicAdult"));
//                String _musicPath = cursor.getString(cursor.getColumnIndex("_musicPath"));
//                Music music = new Music(_musicName,_musicAdult,_musicPath);
//                musics.add(music);
//            }
//        }
    }

    public String calculateTime(int time) {
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            //分钟在0~9
            if (minute < 10) {
                //判断秒
                if (second < 10) {
                    return "0" + minute + ":" + "0" + second;
                } else {
                    return "0" + minute + ":" + second;
                }
            } else {
                //分钟大于10再判断秒
                if (second < 10) {
                    return minute + ":" + "0" + second;
                } else {
                    return minute + ":" + second;
                }
            }
        } else {
            second = time;
            if (second >= 0 && second < 10) {
                return "00:" + "0" + second;
            } else {
                return "00:" + second;
            }
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    alreadyMusicTime.setText("已播时长："+calculateTime(mediaPlayer.getCurrentPosition()/1000)+"秒");
                    MusicPlayBar.setProgress(mediaPlayer.getCurrentPosition()*100/mediaPlayer.getDuration());
                    break;
            }
            return false;
        }
    });

    Runnable timeCount = new Runnable() {
        int progressValue = 0;
        @Override
        public void run() {
            try {
                while(mediaPlayer.isPlaying()){
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = progressValue++;
                    handler.sendMessage(message);
                    if (progressValue>99) {
                        progressValue = 0;
                    }
                    Thread.sleep(1000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };



}