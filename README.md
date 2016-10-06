![](Resources/enginelogo2.png?raw=true "Engine Logo")

## Synopsis
The Sparkz Engine is a one of a kind particle engine written in Java; a program for people who like to explore and create, for people who like to experiment, for education, and most importantly for having fun!

It has many features which encourage exploration and experimentation. With four distinct modes: Normal, Multi, Fireworks, Graph, and Ragdoll, just when you think you've discovered everything it just keeps on giving!

### Normal Mode
Lets you create thousands of colorful particles! Each particle has the ability to think, to determine its color based on its velocity. The particles can have up to 5 colors for which they can change their color to based on their velocity. The color is determined by this evaluation:
```
Color c = null;
if ((vx >= 1 || vx <= -1) || (vy >= 1 || vy <= -1)) {c = thinkingColors[0];}
if ((vx >= 2 || vx <= -2) || (vy >= 2 || vy <= -2)) {c = thinkingColors[1];}
if ((vx >= 3 || vx <= -3) || (vy >= 3 || vy <= -3)) {c = thinkingColors[2];}
if ((vx >= 4 || vx <= -4) || (vy >= 4 || vy <= -4)) {c = thinkingColors[3];}
if ((vx >= 5 || vx <= -5) || (vy >= 5 || vy <= -5)) {c = thinkingColors[4];}
if (c == null) c = thinkingColors[0]; return c;
```
Now if you've not coded before don't panic, the code may look daunting but it's actually not too bad :), let's step through what its doing. If the particles velocity is between 1 and -1 then the particle gets assigned a color from ```index 0``` of the ```thinkingColors``` array. If its velocity is between 2 and -2 then it gets assigned a color from ```index 1``` of the ```thinkingColors``` array. Each particle continues to evaluate this up to velocity being between 5 and -5 to determine its color.

Normal mode also lets you spawn up to 9 different particle types: ```Particle, Gravity Point, Emitter, Flux, Q.E.D, Ion, Black Hole, Duplex, and Portal```, which can interact between each other depending on each others particle type.

### Multi Mode
A pretty simplistic mode, it lets you spawn multiples of certain particle types at a time.

### Fireworks Mode
Lets you create realistic fireworks. Clicking at a certain point on the screen will launch a particle at an ```(x, y)``` coordinate, where x is half of the width of the screen and y being the height of the screen, and accelerating in the direction of the angle between the ```(x, y)``` coordinate and the position of the mouse cursor. In this mode each particle has a life amount, determining when it should explode to create the fireworks effect. Just as in the regular mode each particle has thinking colors, and in this mode that feature is extended. The launch particle has thinking colors and in addition the fireworks from the particle have thinking colors as well, which you can customize! You're also able to control many other features of how the fireworks work, altering the ```size, speed, jitter, wind, and type``` of the fireworks.

### Graph Mode
Built for visualizing equations or functions :D, a very handy feature, one which I personally use to help understand exactly what's going on in a particular function. In this mode the equation / function parser is powered by Javas JavaScript Script Engine. You are able to enter virtually any function (as long as it's in terms of y=) and be able to graph it. It has many handy functions such as: ```Sin, Cos, Tan, Asin, Acos, Atan, Log, Sqrt(square root), Abs(absolute value)```; just to name a few.

### Ragdoll Mode
A mode for simulating realistic physics using [Verlet Integration](https://en.wikipedia.org/wiki/Verlet_integration). In this mode you're able to create different elements which model real life object that are affected by the real worlds forces, such as gravity and friction. The elements are: ```Points, Sticks, IK Chains, Boxes, Elastic Meshes, Solid Meshes and Cloth```.

---

### Developing this Engine has been one of the most fun things I've done and I've learned so much and I couldn't be more proud of sticking with an idea which I didn't think at first was possible. I'm incredibly happy to be able to share this with you all! Thank you to everyone who has and continues to support me!

### If you'd like a more in depth understanding of any of the methods or functions within the Engine check out its documentation: [Sparkz Engine Documentation](https://github.com/CalebABG/Sparkz_Engine) :), thank you once again!

---

## Prerequisites

What you'll need to build your own version or to run the program
```
Java JDK 8 + Java JRE 8 - Minimum due to use of lambda functions
Any text editor or IDE of your choice
```
#### Editors and IDE's I highly recommend:
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Eclipse](https://eclipse.org/)
* [Atom](https://atom.io/)
* [Sublime Text 3](https://www.sublimetext.com/3)

---

## Getting Started

These instructions will help you prepare your machine for development and testing purposes! :D

### Checking for Java Installation

In order to both run and create your own projects you need to have Java installed. Here's how to check for Java on each system (Windows, Mac, or Linux)

### Step 1:

#### Windows:

Open Command Prompt: ```(Windows Key + R)```, then type in the text field  ```cmd``` and press enter.

#### Mac:

Open Terminal: ```(Apple Key + Space-bar)```, then type in ```Terminal``` and when the Terminal Icon appears press enter.

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

---

### Obtaining the Java 8 Development Kit:

#### Before you download, you may want to know whether your computer is 32 bit or 64 bit.

To check whether you've got a 32 bit machine or a 64 bit one, first repeat step 1 to obtain a console window.

Then, if you're running Windows type this command into the window ```echo %PROCESSOR_ARCHITECTURE%``` and then press enter.

If you get an output that looks like this: ```AMD64``` then you've got a 64 bit machine; if you get this: ```x86``` then you've got a 32 bit machine.

If you're running Linux* type this command into the window: ```uname -m``` and then press enter.

If you get an output that looks like this: ```x86_64``` then you've got a 64 bit machine; if you get this: ```i686 or i386 or i586``` then you've got a 32 bit machine.

##### * If on Linux there is an alternative and quicker, less painful way to download and install the Java 8 JDK; proceed to Installing Needed Software to see the alternative.

#### JDK Download Link:
[Java JDK 8 - Windows, Mac and Linux](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

---

### Installing Needed Software

If you needed to download the JDK then you'll need to now install it :D

If you're on Windows navigate to the folder where you saved the downloaded Java JDK. Then you'll want to find the JDK installer and double click it to
start the installer. Proceed through the steps provided by the installer to complete your installation.

If you're on Mac navigate to where you downloaded the JDK. Then double click on the .dmg file for the JDK to mount the volume. You can click ```skip``` on the
dialog boxes saying ```checking``` and ```verifying```. Once the volume opens double click the .pkg installer to start the JDK installation. Proceed through the steps provided by the installer to complete your installation.

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
