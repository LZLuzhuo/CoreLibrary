package me.luzhuo.luzhuoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_second.*
import me.luzhuo.lib_core.app.base.CoreBaseActivity
import me.luzhuo.lib_core_ktx.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity2 : CoreBaseActivity(){
    private val TAG = MainActivity2::class.java.simpleName
    private var isShow = true
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_second)
    }

    fun onClose(view: View) {
        toast2("asdf")
        Log.e(TAG, "" + 12.13124123.scale2());
        Log.e(TAG, "" + 12.10.scale2());
        Log.e(TAG, "" + 12.00.scale2());
        Log.e(TAG, "" + 12.456.scale2());
        Log.e(TAG, "" + 12.13124123.scale2(false));
        Log.e(TAG, "" + 12.10.scale2(false));
        Log.e(TAG, "" + 12.00.scale2(false));
        Log.e(TAG, "" + 12.456.scale2(false));
    }
}