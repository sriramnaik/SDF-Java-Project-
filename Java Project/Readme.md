# ArbitraryArithmetic

## Overview

*ArbitraryArithmetic* is a Java-based library and CLI tool for performing arithmetic operations on integers and floating-point numbers with *infinite precision. It bypasses the limitations of Java's native numeric types by using **string-based arithmetic*.

---

## Features

- Infinite-precision support for:
  - *Integers* (AInteger.java)
  - *Floating-point numbers* (AFloat.java)
- Operations:
  - Addition (add)
  - Subtraction (sub)
  - Multiplication (mul)
  - Division (div)
  - Modulus (mod)
- Java CLI runner: MyInfArith.java
- Apache Ant-based build automation
- Docker support
- Python helper script for compiling, JAR creation, and CLI execution

---


## Build & Run

### Option 1: Using Apache Ant

#### Compile & Package

bash
ant (to compile)


#### Run the Program

bash



#### Clean Build

bash
ant clean


---

### Option 2: Using the Python Script (run.py)

This script compiles source files, creates the JAR, and optionally runs the program if arguments are passed.

#### Run Without Execution

bash
python3 run.py


#### Run with Arguments

bash
python3 run.py int mul 123456789 987654321


> ✅ Automatically compiles and creates the JAR at build/aarithmetic.jar before running.

---

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


---