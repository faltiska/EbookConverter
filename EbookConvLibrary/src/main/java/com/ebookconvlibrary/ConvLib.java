package com.ebookconvlibrary;

/**
 * Created by greg on 1/6/2016.
 */
public class ConvLib {
    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("EbookConv");
    }

    /**
     * @param mobiFileName path to input file to convert
     * @param epubFileName path for output file
     * @return one of the following error codes
     *   SUCCESS 0
     *   GENERIC_ERROR 1
     *   ERROR_MOBI_INIT_FAILED 2
     *   ERROR_LOADING_MOBI_RAWML 3
     *   ERROR_AZW4_NOT_SUPPORTED 4
     *   ERROR_CREATING_EPUB_ZIP 5
     *   ERROR_WRITING_EPUB_PARTS 6
     *   ENCRYPTED 10
     */
    public static native int mobiToEpubNative(String mobiFileName, String epubFileName);
}