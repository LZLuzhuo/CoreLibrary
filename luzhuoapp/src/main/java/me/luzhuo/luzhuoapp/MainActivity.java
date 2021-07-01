package me.luzhuo.luzhuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.luzhuo.lib_core.app.base.CoreBaseActivity;
import me.luzhuo.lib_core.ui.dialog.BottomDialog;
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

    public void onClick(View view) {
        // Context context, List<String> menus, List<String> colors, OnMenuItemClick onMenuItemClick
        List<String> title = new ArrayList<>();
        title.add("asdf");
        title.add("asdf");
        title.add("asdf");
        title.add("asdf");
        BottomDialog.instance().showMenu(this, title, null);
        // startActivity(new Intent(this, MainActivity2.class));
    }
}
