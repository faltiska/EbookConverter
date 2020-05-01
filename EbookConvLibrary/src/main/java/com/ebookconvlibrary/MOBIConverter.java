package com.ebookconvlibrary;

import android.util.Log;

public class MOBIConverter {
    private static final String LOG_TAG = "LIBMOBI";

    /**
     * Converts the MOBI file identified by mobiFileName into an EPUB and saves the result in epubFileName.
     * @param mobiFileName path to input file to convert
     * @param epubFileName path for output file
     */
    public static void convert(String mobiFileName, String epubFileName) throws Exception {
        int result = ConvLib.mobiToEpubNative(mobiFileName, epubFileName);
        if (result != 0) {
            String msg = "Native conversion library returned error code " + result;
            Log.e(LOG_TAG, msg);
            throw new Exception(msg);
        }
    }
}
