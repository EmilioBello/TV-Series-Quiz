package com.quiz.series.tvseriesquiz.util;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.MyApp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by equipo on 28/07/2015.
 */
public class LanguageUtils {

    static HashMap<String, String> languages = null;
    static String defaultLocale = null;
    static String currentLocale = null;
    static String language = null;

    public static void setPreferences(){
        String language = getLocale();

        List<String> language_country = Arrays.asList(language.split("_"));
        Locale locale = new Locale(language_country.get(0), language_country.get(1));
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        MyApp.getContext().getResources().updateConfiguration(config, null);
    }

    /*public static void changeLanguage(String key) {
        updateLocale(key);

        //refresh catalog
        LanguageCatalog catalog = new LanguageCatalog();
        catalog.refreshCatalog();
    }

    public static void updateLocale(String newLocale){
        //update
        setLocale(newLocale);
        setPreferences();
    }

    public static void saveGlobalLanguage(String allLanguages) {
        //save global
        SharedPreferencesUtils preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
        SharedPreferencesUtils.Editor editor = preferences.edit();
        editor.putString(ADConfig.CONFIG_LANGUAGES, allLanguages);
        editor.commit();

        //load map language
        loadLanguages();

        //save default language
        String locale = getLocale();
        editor.putString(ADConstants.SHAREDPREFERENCES_LANGUAGE_DEFAULT, locale);
        editor.putString(ADConstants.SHAREDPREFERENCES_LANGUAGE_PREFERENCES, locale);
        editor.commit();
    }*/


    /*public static void setLocale(String newLocale) {
        currentLocale = newLocale;

        SharedPreferencesUtils preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
        SharedPreferencesUtils.Editor editor = preferences.edit();
        editor.putString(ADConstants.SHAREDPREFERENCES_LANGUAGE_DEFAULT, newLocale);
        editor.putString(ADConstants.SHAREDPREFERENCES_LANGUAGE_PREFERENCES, newLocale);
        editor.commit();
    }

    public static String[] getAllLocales() {
        if (languages == null) {
            loadLanguages();
        }

        String[] locales = new String[languages.size()];

        int i=0;
        for (String key : languages.keySet()) {
            locales[i] = key;
            i++;
        }

        return locales;
    }

    public static String[] getAllLocalesValues() {
        if (languages == null) {
            loadLanguages();
        }

        String[] localesValues = new String[languages.size()];

        int i=0;
        for (String value : languages.values()) {
            localesValues[i] = value;
            i++;
        }

        return localesValues;
    }


    private static void loadLanguages() {
        SharedPreferencesUtils preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
        String allLanguages = preferences.getString(ADConfig.CONFIG_LANGUAGES, "");

        //list languages
        allLanguages = allLanguages.replace(" ", "");
        List<String> desc_languages = Arrays.asList(allLanguages.split(","));

        languages = new HashMap<>();
        for (int i=0; i<desc_languages.size(); ++i) {
            String desc_language = desc_languages.get(i);
            List<String> language = Arrays.asList(desc_language.split(":"));
            if (language.size() == 2) {
                languages.put(language.get(0), language.get(1));

                //default language
                if (i==0) {
                    defaultLocale = language.get(0);
                }
            }
        }
    }*/

    public static String getLocale() {
        if (currentLocale == null) {
            currentLocale = MyApp.getContext().getResources().getConfiguration().locale.getLanguage() + "_" + MyApp.getContext().getResources().getConfiguration().locale.getCountry();
        }

        if (languages == null) {
            return currentLocale;
        } else {
            if (StringUtils.isNullOrEmpty(defaultLocale)) {
                return null;
            } else {
                return languages.containsKey(currentLocale) ? currentLocale : defaultLocale;
            }
        }
    }

    public static String getLanguage(){
        if(language == null){
            language = MyApp.getContext().getResources().getConfiguration().locale.getLanguage();
        }

        return language;
    }
}
