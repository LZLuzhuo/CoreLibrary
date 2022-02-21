package me.luzhuo.luzhuoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_second.*
import me.luzhuo.lib_core.app.base.CoreBaseActivity
import me.luzhuo.lib_core.data.UriManager
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
        val url1 = "http://"
        val url12 = "http://luzhuo.me"
        val url123 = "http://luzhuo.me/blog/123"
        val url1234 = "http://luzhuo.me/blog/123?userid=123"
        val url1234_1 = "http://luzhuo.me/blog/123?userid=123&token=456&token=789"
        val url12345 = "http://luzhuo.me/blog/123?userid=123&token=456&token=789#fgm"
        val url12345_3 = "http://luzhuo.me/blog/123?userid=123&token=&token=789#fgm"
        val url12345_1 = "http://luzhuo.me/#/blog/?/123?userid=123&token=456&token=789#fgm"
        val url12345_2 = "http://luzhuo.me/#/blog/?/123?userid=#?123&token=-+456&token=789#-+f_gm"
        val url1124 = "http://luzhuo.me?userid=123"
        val url2 = "//luzhuo.me"
        val url123_1 = "http://luzhuo.me//blog//123"
        val url12345_4 = "http://luzhuo.me//#//blog//?//123?userid=#?123&token=-+456&token=789#-+f_gm"
        val url23 = "//luzhuo.me/blog/123"
        val url2345_4 = "//luzhuo.me//#//blog//?//123?userid=#?123&token=-+456&token=789#-+f_gm"
        val url12345_5 = "http://luzhuo.me/#/?/123?userid=123&token=456&token=789#fgm"
        val url123_5 = "http://luzhuo.me/#/"
        val url123_6 = "http://luzhuo.me/#/index"

        Log.e(TAG, "url1: " + UriManager(url1)) // http:
        Log.e(TAG, "url12: " + UriManager(url12)) // http://luzhuo.me
        Log.e(TAG, "url123: " + UriManager(url123)) // http://luzhuo.me/blog/123
        Log.e(TAG, "url1234: " + UriManager(url1234)) // http://luzhuo.me/blog/123?userid=123
        Log.e(TAG, "url1234_1: " + UriManager(url1234_1)) // http://luzhuo.me/blog/123?userid=123&token=456&token=789
        Log.e(TAG, "url12345: " + UriManager(url12345)) // http://luzhuo.me/blog/123?userid=123&token=456&token=789#fgm
        Log.e(TAG, "url12345_3: " + UriManager(url12345_3)) // http://luzhuo.me/blog/123?userid=123&token=789#fgm
        Log.e(TAG, "url12345_1: " + UriManager(url12345_1)) // http://luzhuo.me/#/blog/?/123?userid=123&token=456&token=789#fgm
        Log.e(TAG, "url12345_2: " + UriManager(url12345_2)) // http://luzhuo.me/#/blog/?/123?userid=%23%3F123&token=-%2B456&token=789#-%2Bf_gm
        Log.e(TAG, "url1124: " + UriManager(url1124)) // http://luzhuo.me?userid=123
        Log.e(TAG, "url2: " + UriManager(url2)) // //luzhuo.me
        Log.e(TAG, "url123_1: " + UriManager(url123_1)) // http://luzhuo.me/blog/123
        Log.e(TAG, "url23: " + UriManager(url23)) // //luzhuo.me/blog/123
        Log.e(TAG, "url12345_4: " + UriManager(url12345_4)) // http://luzhuo.me/#/blog/?/123?userid=%23%3F123&token=-%2B456&token=789#-%2Bf_gm
        Log.e(TAG, "url2345_4: " + UriManager(url2345_4)) // //luzhuo.me/#/blog/?/123?userid=%23%3F123&token=-%2B456&token=789#-%2Bf_gm
        Log.e(TAG, "url12345_5: " + UriManager(url12345_5)) // http://luzhuo.me/#/?/123?userid=123&token=456&token=789#fgm
        Log.e(TAG, "url123_5: " + UriManager(url123_5)) // http://luzhuo.me/#/
        Log.e(TAG, "url123_6: " + UriManager(url123_6)) // http://luzhuo.me/#/index
    }
}