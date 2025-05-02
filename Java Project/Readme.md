
# Arbitrary Precision Arithmetic Library

## Overview

This Java project implements an arbitrary-precision arithmetic library that supports mathematical operations on integers and floating-point numbers beyond the limits of standard data types. It includes two main classes:

- `AInteger`: Represents large integers with arbitrary precision.
- `AFloat`: Represents large floating-point numbers with arbitrary precision.

This library is developed as part of the CS1023 Software Development Fundamentals course project.

## Features

- Support for arbitrary-size integers and floating-point numbers
- Basic arithmetic operations: `+`, `-`, `*`, `/`
- Input/output from strings
- Internal normalization and error checking
- Modular structure with unit tests

## Project Structure
```
  • Java Project
      – arbitraryarithmetic
          ∗ AFloat.java
          ∗ AInteger.java
          ∗ commonMethod.java
          ∗ aarithmetic.jar
      – MyInfArith.java
      – build.xml
      – build
          ∗ arbitraryarithmetic
            · AFloat.class
            · AInteger.java
            · commonMethod.java
          ∗ MyInfArith.class
      – Script.py
      – compile.py
      – default-test-case.txt
      – dockerfile
      – Latex documentation
      – README.md
```
## Build & Run

### Option 1: Using Apache Ant

#### Compile & Package

bash
ant compile

#### Clean Build

bash
ant clean

we cant run using ant (limitation)

---

### Option 2: Using the Python Script (run.py)

This script compiles source files, creates the JAR, and optionally runs the program if arguments are passed.
To 
```bash
python3 Script.py
```

### Option 3: Using Docker

#### Build Docker Image

bash
docker build -t java-inf-arith .


#### Run Program

bash
docker run --rm java-inf-arith float div 244727.15202 75964.3891

---

## Example CLI Usage

bash
java -cp build MyInfArith int add 1234567891234567898883 9876543219876388854321






