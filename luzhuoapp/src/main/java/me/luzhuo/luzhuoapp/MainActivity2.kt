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
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_second)
    }

    fun onClose(view: View) {
        val textview = ""
        (textview as? TextView)?.text = "asdfasdf"
        Log.e("TAG", "");
    }
}