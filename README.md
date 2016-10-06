![](Resources/enginelogo2.png?raw=true "Engine Logo")

## Synopsis
For the last year I've been working on a Particle Engine written in Java; a program for people who like to explore and create, for people who like to experiment, for education, and most importantly for having fun. It's been one of the most fun things I've done and I've learned so much and I couldn't be more proud of sticking with an idea which I didn't think at first was possible. I'm incredibly happy to be able to share this with you all! Thank you to everyone who has and continues to support me!

### Documentation: [Sparkz Engine Documentation](https://github.com/CalebABG/Sparkz_Engine)
---

## Prerequisites

What you'll need to build your own version or to run the program
```
Java JDK 8 + Java JRE 8 - Minimum due to use of lambda functions
Any text editor of your choice
```
Editors I highly recommend:
[IntelliJ IDEA](https://www.jetbrains.com/idea/) --
[Eclipse](https://eclipse.org/) --
[Atom](https://atom.io/) --
[Sublime Text 3](https://www.sublimetext.com/3)

---

## Getting Started

These instructions will help you prepare your machine for development and testing purposes! :D

### Checking for Java Installation

In order to both run and create your own projects you need to have Java installed. Here's how to check for Java on each system (Windows, Mac, or Linux)

### Step 1:

#### Windows:

Open Command Prompt: ```(Windows key + r)```, then type in the text field  ```cmd``` and press enter.

#### Mac:

Open Terminal: ```(Apple key + space-bar)```, then type in ```Terminal``` and when the Terminal Icon appears press enter.

#### Linux:

Open Terminal (if running Ubuntu or Linux Mint): Keyboard shortcut = ```(Ctrl + Alt + T)```


### Step 2:

In the Command Prompt or Terminal window, enter this command: ```java -version``` and then press enter.

If you get output from the window displaying something along the lines of: ```java version "1.x.x_xxx"```,
where the x's are the specific versions of the Java SE Runtime Environment.

If your version of the JRE is greater than or equal to ```version "1.8.0"``` then you're done :D, you can skip step 3.


### Step 3:

If in the Command Prompt or Terminal window you got something similar to this:
```
'java' is not recognized as an internal or external command, operable program or batch file.
```
don't worry :), all you need is the Java Development Kit (it includes the JRE).

### Obtaining the Java 8 Development Kit:

#### Before you download, you may want to know whether your computer is 32 bit or 64 bit.

To check whether you've got a 32 bit machine or a 64 bit one, first repeat step 1 to obtain a console window.

Then, if you're running Windows type this command into the window ```echo %PROCESSOR_ARCHITECTURE%``` and then press enter.

If you get an output that looks like this: ```AMD64``` then you've got a 64 bit machine; if you get this: ```x86``` then you've got a 32 bit machine.

If you're running Linux* type this command into the window: ```uname -m``` and then press enter.

If you get an output that looks like this: ```x86_64``` then you've got a 64 bit machine; if you get this: ```i686 or i386 or i586``` then you've got a 32 bit machine.

#### JDK Download Link:
[Java JDK 8 - Windows, Mac and Linux](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
##### * If on Linux there is an alternative and quicker, less painful way to download and install the Java 8 JDK; proceed to Installing Needed Software to see the alternative.

---

### Installing Needed Software

If you needed to download the JDK then you'll need to now install it :D

If you're on Windows navigate to the folder where you saved the downloaded Java JDK. Then you'll want to find the JDK installer and double click it to
start the installer. Proceed through the steps provided by the installer to complete your installation.

If you're on Mac navigate to where you

If you're on Linux open a Terminal window (step 1) and then enter these commands into the window separately, line by line and press enter to execute the command.

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
```

Congratulations, you should now have the JDK installed on your Windows, Mac or Linux system(s)!

---

## Acknowledgments

* ...
* ...
* ...

---

## Closing Comments
If you've stuck with reading up to this point I highly appreciate it! I'll keep this short. This project is officially Open Source!
I'll be pushing updates to this Engine as I can, and feel free to fork or port this to other languages! I'd love to see your creations,
be as creative as possible and let no one discourage you from having fun!
