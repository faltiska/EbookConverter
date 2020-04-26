# Fork of EbookConverter for Android by Greg Kochaniak 

If you are looking for the original, written by Greg Kochaniak, it's here: https://github.com/gregko/EbookConverter

Greg used
    LIBMOBI code from Bartek Fabiszewski (https://github.com/bfabiszewski/libmobi)
and
    FB2 to Epub Converter from Alexey Bobkov (https://code.google.com/archive/p/fb2-to-epub-converter/)
to write and Android-ready ebook conversion library.

This fork offers an IDEA project containing an android library and a test app.
The Library builds the C code and offers an .aar archive you can include in your project.

Use the "assemble" gradle target in the EbookConvLibrary module to build the archive.

I've updated the LIBMOBI code to the last version available on April 26, 2020 in Bartek's repository.
I've also updated the build tools and android versions everywhere.

I have removed the test modules, if you need them, go to Greg's repo.
  
