package org.koffeinfrei.zueribad.utils;

/**
 * This class is the common used exception class that holds an id
 * to an android string resource (-> i18n error message)
 *
 * @author alexis.reigel
 *
 */
public class AndroidI18nException extends Exception {
    private int resourceStringId;

    public AndroidI18nException(int resourceStringId) {
        this(resourceStringId, new Exception());
    }

    public AndroidI18nException(int resourceStringId, Throwable throwable) {
        super(throwable);
        this.resourceStringId = resourceStringId;
    }

    public int getResourceStringId() {
        return resourceStringId;
    }
}
