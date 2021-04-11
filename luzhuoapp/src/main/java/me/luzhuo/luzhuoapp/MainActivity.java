package me.luzhuo.luzhuoapp;

import android.os.Bundle;
import android.util.Log;

import me.luzhuo.lib_core.app.base.CoreBaseActivity;
import me.luzhuo.lib_core.media.camera.CameraManager;
import me.luzhuo.lib_core.media.camera.ICameraCallback;
import me.luzhuo.lib_core.ui.toast.ToastManager;

public class MainActivity extends CoreBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastManager.show(this, "Hello core library");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
