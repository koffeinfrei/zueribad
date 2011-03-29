package org.koffeinfrei.zueribad.config;

public class Constants {
    public static final boolean IS_EMULATOR = android.provider.Settings.Secure.ANDROID_ID == null;

    public static final String SERVICE_URL = IS_EMULATOR
            ? "http://192.168.1.24/stzh_bath_data.xml"
            : "http://www.stadt-zuerich.ch/stzh/bathdatadownload";

    public static final String STATIC_DATA_FILE_URL = "bathdata_static.xml";

    public static final int PROGRESS_DIALOG = 1;
    public static final int ERROR_DIALOG = 2;
    
    public static final int TAB_OVERVIEW_INDEX = 0;
    public static final int TAB_FAVORITES_INDEX = 1;
    public static final int TAB_DETAILS_INDEX = 2;
}
