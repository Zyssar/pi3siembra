package utec.pi3.siembrahora;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList; // Import LocaleList
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleManager {

    private static final String LANGUAGE_KEY = "selected_language";

    public static Context setLocale(Context context, String languageCode) {
        persistLanguage(context, languageCode);
        return updateResources(context, languageCode);
    }

    public static String getLanguage(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(LANGUAGE_KEY, "es");
    }

    public static Context updateBaseContext(Context context) {
        String language = getLanguage(context);
        return updateResources(context, language);
    }

    private static void persistLanguage(Context context, String languageCode) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE_KEY, languageCode);
        editor.apply();
    }

    private static Context updateResources(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        // --- THIS IS THE UPDATED PART ---
        // Use the modern "setLocales" instead of the deprecated "setLocale"
        config.setLocales(new LocaleList(locale));
        // --- END OF UPDATE ---

        context = context.createConfigurationContext(config);
        return context;
    }
}