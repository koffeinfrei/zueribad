package org.koffeinfrei.zueribad.config;

public class Constants {
    public static final boolean IS_EMULATOR = android.provider.Settings.Secure.ANDROID_ID == null;

    public static final String SERVICE_URL = IS_EMULATOR
            ? "http://10.0.2.2/stzh_bath_data.xml"
            : "http://www.stadt-zuerich.ch/stzh/bathdatadownload";

    public static final int PROGRESS_DIALOG = 1;
    public static final int ERROR_DIALOG = 2;
    
    public static final int TAB_OVERVIEW_INDEX = 0;
    public static final int TAB_FAVORITES_INDEX = 1;
    public static final int TAB_DETAILS_INDEX = 2;

    public static final String SAVE_STATE_FILTER_TEXT = "SaveStateFilterText";
}
