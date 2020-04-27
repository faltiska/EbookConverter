# Fork of EbookConverter for Android by Greg Kochaniak 

If you are looking for the original, written by Greg Kochaniak, it's here: https://github.com/gregko/EbookConverter

Greg used LIBMOBI from Bartek Fabiszewski (https://github.com/bfabiszewski/libmobi) to write and Android-ready ebook conversion library.

This fork offers an IDEA project containing a new Android Library module and Greg's test app.
The library builds the C code and the java interface into an .aar archive you can include in your project.

Use the "assemble" gradle target in the EbookConvLibrary module to build the archive.
The result will be in /EbookConvLibrary/build/outputs/aar/, look for EbookConvLibrary-release.aar or EbookConvLibrary-debug.aar

I've updated the LIBMOBI code to the last version available on April 26, 2020 in Bartek's repository.
I have added several error codes to make debugging easier.
I've also updated the android sdk and build tools versions everywhere.

I have removed the FB2 code, it was crashing in most of my tests.
I have removed the test modules, too.
  
