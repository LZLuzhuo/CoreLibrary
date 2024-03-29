package me.luzhuo.luzhuoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.List;

import me.luzhuo.lib_core.app.base.CoreBaseActivity;
import me.luzhuo.lib_core.app.pattern.PatternCheck;
import me.luzhuo.lib_core.app.pattern.RegularType;
import me.luzhuo.lib_core.data.UriManager;
import me.luzhuo.lib_core.math.money.MoneyCalculation;
import me.luzhuo.lib_core.media.audio.AudioManager;
import me.luzhuo.lib_core.media.audio.IAudioCallback;
import me.luzhuo.lib_core.media.camera.CameraManager;
import me.luzhuo.lib_core.media.camera.ICameraCallback;
import me.luzhuo.lib_core.media.video.IVideoRecorderCallback;
import me.luzhuo.lib_core.media.video.VideoRecorderManager;
import me.luzhuo.lib_core.ui.toast.ToastManager;

public class MainActivity extends CoreBaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View view) {
//        UriManager uri = new UriManager("http://qw.qiwangguanjia.cn/#/").removeQueryParameter("token").addQueryParameter("token", "asdfasdf");
//        Log.e(TAG, "uri: " + uri);
        startActivity(new Intent(this, MainActivity2.class));
    }
}
