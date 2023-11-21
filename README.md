# EbookConverter library for Android 

This is based on 2 projects: 
- [EbookConverter](https://github.com/gregko/EbookConverter) by Greg Kochaniak
  - which in turn uses [LIBMOBI](https://github.com/bfabiszewski/libmobi) from Bartek Fabiszewski
- [EpubTools](https://github.com/wting/epub-tools) by Adobe

I have removed the fb2toepub native code, as it was crashing in most of my tests.

I've replaced it the java based converters from Adobe.

I have added an IDEA project containing:
- a new Android Library module
- Greg's original Android app for testing the converter
- the java code from Adobe to handle other types except mobi

The library builds all converters into an .aar archive you can include in your Android project.

Use the "assemble" gradle target in the EbookConvLibrary module to build the archive.

The archives will be in /EbookConvLibrary/build/outputs/aar/:
- EbookConvLibrary-release.aar
- EbookConvLibrary-debug.aar

I've updated the LIBMOBI code to the last version available on April 26, 2020 from Bartek's repository.
I have added several error codes to make mobi2epub debugging easier.
I've also updated the android sdk and build tools versions everywhere.

I have removed the c/c++ test modules, too.
  
