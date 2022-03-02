package me.luzhuo.lib_core.ui.statusbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActionBarManager {
    private ActionBarManager() { }
    private final static ActionBarManager instance = new ActionBarManager();
    public static ActionBarManager getInstance() {
        return instance;
    }

    public void hide(@NonNull AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) actionBar.hide();
    }
}
