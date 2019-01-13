package c.restaurant.languagerealtimechange

import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import c.restaurant.languagerealtimechange.Helper.LocaleHelper
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"en"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView=findViewById(R.id.txview)

        //init paper first
        Paper.init(this)
        //Default language in english
        var language: String? =null
        if(language==null)
            Paper.book().write("language","en")
        updateView((Paper.book().read("language"))as String)
    }

    private fun updateView(lang: String) {

        var context=LocaleHelper.setLocal(this,lang)
        var resources: Resources=context.resources
        textView.text = resources.getString(R.string.Hello)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId==R.id.lang_en){
            Paper.book().write("language","en")
            updateView((Paper.book().read("language"))as String)
        }
        else if(item!!.itemId==R.id.lang_ar){
            Paper.book().write("language","ar")
            updateView((Paper.book().read("language"))as String)
        }
        return true
    }
}
