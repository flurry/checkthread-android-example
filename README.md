# Squashing bugs in multithreaded Android code with CheckThread

This is a sample Android app demonstrating how to use the CheckThread static analysis tool.

`libs/threadpolicy.xml` Defines the thread policy for the Android library code, using syntax defined [here](http://www.checkthread.org/threadpolicyxml.html)

`libs` CheckThread annotations jar, analyzer tool and BCEL dependency

`checkthread.xml` Ant script that calls the CheckThread task

`custom_build.xml` Ant script that attaches the analysis step above to Android's `-post-compile` hook.

To run the analysis tool, just build your project using normal Android tools:

    android update project -p .
    ant -lib libs clean debug

To run the analysis tool standalone use:

    ant -f checkthread.xml -lib libs

CheckThread is copyright Joe Conti and released under the MIT license.
BCEL is released under the Apache 2.0 license.
