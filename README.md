[![Build Status](https://travis-ci.com/ContractBridge/pbnj.svg?branch=master)](https://travis-ci.com/ContractBridge/pbnj)
[![codecov](https://codecov.io/gh/ContractBridge/pbnj/branch/master/graph/badge.svg)](https://codecov.io/gh/ContractBridge/pbnj)

# pbnj

This repository is a version of [Tis Veugen](http://www.tistis.nl/index.html)'s [PbnJVeri](https://github.com/ContractBridge/PbnJVeri) 
a Java program for verifying whether PBN files obey the standards PBN 1.0, PBN 2.0,
and PBN 2.1. The program is also able to convert PBN files from import format
to export format. The PBN standard and PBN files can be found on the PBN homepages:

* http://www.tistis.nl/pbn
* *http://www.kolumbus.fi/sackab/kgb

The main difference from the original version is that we have added some [Gradle](https://gradle.org/) build goodness so 
that it can support unit testing as well as easier enhancement.

## Getting Started

If you are using IntelliJ, you can run the following command to set up your ide:

```shell script
$> ./gradlew idea
```

## Gradle Lifecycle

Running the unit tests:

```shell script
$> ./gradlew test
```

Running the tests and building the library if they pass:

```shell script
$> ./gradlew build
```

Run the gui application:

```shell script
$> ./gradlew run
```

Run the command line application against a PBN file:

```shell script
$> ./gradlew run --args='src/test/resources/correct_10.pbn'
```

Clean:

```shell script
$> ./gradlew clean
```

For information about the command line instructions, see the [README-ORIGINAL.md](README-ORIGINAL.md).