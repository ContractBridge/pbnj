# PbnJVeri

This archive contains the Java PbnVerifier, a Java program for verifying whether PBN files obey the standards PBN 1.0, PBN 2.0,
and PBN 2.1 . The program is also able to convert PBN files from import format
to export format. The PBN standard and PBN files can be found on the PBN homepages:

* http://www.tistis.nl/pbn
* *http://www.kolumbus.fi/sackab/kgb

This program has been ported from the PBN Verifier that has been
written in C and processes only PBN 1.0 files. The Java PbnVerifier
is slower than the C PbnVerifier, but the Java PbnVerifier is portable
among platforms.

## INSTALLATION

For using this Java PbnVerifier you need JAVA! I hope that is clear. It means you must have installed Java, and made it operational.

If you don't have Java on your platform, then you can download the Java Runtime Environment (JRE) from:
	http://www.java.com

The contents of the PbnJVeri.zip archive is:

* __PbnJVeri.rme__   
  This file.
* __PbnJVeri.jar__  
  The JAR archive containing the Java class files, and the Java source files.
* __correct_10.pbn__  
  A PBN file with correct PBN games; this file shows what is possible in PBN 1.0; it is used as benchmark.
* __correct_20.pbn__  
  A PBN file with correct PBN games; this file shows the features that have been added in PBN 2.0 .
* __correct_21.pbn__      
  A PBN file with correct PBN games containing the features that have been added in PBN 2.1 .
* __errors_10.pbn__       
  A PBN file with wrong PBN games; this file results in many reported errors; it is used as benchmark.
* __errors_20.pbn__       
  A PBN file with wrong PBN games violating the PBN 2.0 standard.
* __errors_21.pbn__       
  A PBN file with wrong PBN games violating the PBN 2.1 standard.
* __Pbn_Veri.ico__  
  An icon (for Windows users).
 
Please, unzip [PbnJVeri.zip](https://github.com/ContractBridge/PbnJVeri/releases/latest) to retrieve the above files. Windows users are recommended to unzip in directory C:\pbn\verifier.

## EXECUTION

The main Java program is called PbnVerifier.class . This program is NOT an applet, so that it is impossible to run it in a HTML browser. Instead, you can start it by a shell command as follows:
    `java -jar PbnJVeri.jar`

Windows users can also double click the file PbnJVeri.jar to start the program.

## WINDOW BUTTONS 

A window pops up in which you can enter your verification requests.
The buttons and selection boxes have the following meaning:

``` Import     enter an import PBN filename via a file dialog box
 Export     enter an export PBN filename via a file dialog box
 Logging    enter a logging filename via a file dialog box
 Ex=Im      the import PBN filename is copied to the export PBN
            filename; beware that the original import file will
            be overwritten, provided that the Verify process of
            the import PBN file is OK.
 Verify     execute the verification process
 Verbose    the logging file includes all lines of the import file
 only LF    the export file has only LF instead of CRLF; this might
            be usefull for non-PC users, but the resulting file does
            NOT obey the export format!
 PBN 1.0    verify against the PBN 1.0 standard
 PBN 2.0    verify against the PBN 2.0 standard
 PBN 2.1    verify against the PBN 2.1 standard
```
 
When you select a filename via a button, then that filename is copied to the textfield behind the button. You can also enter a filename directly in each textfield.
After entering an import filename, a default filenames is filled for the logging file.

## BATCH MODE

You can also use the PbnVerifier in a batch mode by entering the import filename and one or more options:

```    
java -cp PbnJVeri.jar PbnVerifier [options] <filename> [options]
with arguments:
      <filename>       filename of import PBN file
      -e               export the import PBN file
      -E<export_name>  filename of export PBN file (default PbnJveri.pbn)
      -h               this help
      -i               information about program version
      -L               only LF at line end
      -O<output_name>  filename of verification output (default PbnJveri.log)
      -v               verbose: echo the import PBN file
      -10              use PBN 1.0 version
      -20              use PBN 2.0 version
      -21              use PBN 2.1 version
```

## EPILOG

If you have any questions about the PbnVerifier or PBN in general, then do
not hesitate to contact me. You can find more information about PBN on the
PBN homepages
        http://www.tistis.nl/pbn.
and
        http://www.kolumbus.fi/sackab/kgb

Tis Veugen
tis.veugen@gmail.com
2011-09-12

History list:

Program version 1.0
-------------------
1998-10-11

Program version 1.1
-------------------
Fixed nag checking.
1998-10-25

Program version 1.4
-------------------
Bug fix: Event tags of "#" without earlier event.
Removed applet
1999-04-05

Program version 1.5
-------------------
Don't export Auction tag without calls.
1999-05-17

Program version 1.6
-------------------
Verify scores
1999-05-20

Program version 1.7
-------------------
Already added TagIds for PBN 2.0
1999-06-05

Program version 1.8
-------------------
Only export PBN 1.0
1999-06-06

Program version 1.9
-------------------
Tolerant acceptance of tag values.
1999-06-13

Program version 1.10
--------------------
Prepared for PBN 2.0
Added interpretation of header tags starting with ##.
1999-08-17

Program version 2.0
-------------------
Verifier for PBN 2.0
1999-10-13

Program version 2.1
-------------------
Compiled with Java 1.3.1
Updated the installation instructions
2001-09-01

Program version 2.2
-------------------
Check order in tables
2001-09-27

Program version 2.3
-------------------
Check contract before playing
2002-08-25

Program version 2.4
-------------------
Verifier for PBN 2.1
2007-08-01

Program version 2.5
-------------------
Fixed bug, and added some checks for export format
2009-10-04

Program version 2.6
-------------------
Fixed small bug
2011-05-18

Program version 2.7
-------------------
Fixed bug about export version
2011-09-12

Program version 2.8
-------------------
Fixed bug about bad leadcard
2012-04-17

Program version 2.9
-------------------
Fixed rare bug
2012-04-28
