package c.restaurant.languagerealtimechange.Helper

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager

import java.util.Locale

object LocaleHelper {
    private  val selected_language = "Locale.Helper.Selected.Language"
    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocal(context, lang)

    }
    fun onAttach(context: Context,defaultLang:String): Context {
        val lang = getPersistedData(context, defaultLang)
        return setLocal(context, lang)

    }

   fun setLocal(context: Context, lang: String?): Context {
        persist(context, lang)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, lang)
        } else updateResourcesLegacy(context, lang)
    }

    private fun updateResourcesLegacy(context: Context, lang: String?): Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = context.resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            configuration.setLayoutDirection(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, lang: String?): Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun persist(context: Context, lang: String?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(selected_language, lang)
        editor.apply()
    }

    private fun getPersistedData(context: Context, lang: String): String? {
        //if already exist fl awl null
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(selected_language, lang)
    }
}
