package com.engine.EngineHelpers;

public class GUIText {
    public static String instructions() {
        return "<html><body>" +
                "<p style='font-size:16px;color:red'> Important Keys + Notes: " +
                "<ul> <li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>C</span> to clear the screen.</li> " +
                "<li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>Esc</span> to exit program. " +
                "<span style='font-size:12px;color:red'>(Press Shift+Ctrl+Q to force close)</span></li>" +
                "<li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>Q</span> to bring this window up again.</li> " +
                "<li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>Shift+Z</span> to restore default or saved settings.</li> " +
                "<li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>Shift+S</span> to open Settings Editor. " +
                "<span style='font-size:13px;color:red'>(Advanced Users)</span></li> " +
                "<li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>Shift+Ctrl+V</span> to open Exception Logger. " +
                "<span style='font-size:13px;color:red'>(Ctrl+C to clear log)</span></li> " +
                "<li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>Ctrl+I</span> to open Stats Panel.</li> " +
                "<li style='font-size:13px'>Press <span style='font-size:15px;color:orange'>Shift+X</span> to open Particle Graph Editor.</li> " +
                "<li style='font-size:13px'><span style='font-size:15px;color:orange'>Ragdoll Mode</span> only supports up to 285 collisions; beyond 285, collisions will turn off.</li> " +
                "</ul></p> <br>" +

                "<p style='font-size:15px;color:blue'> Particle Engine Controls: <br> <ul> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:green'>Click</span> or <span style='font-size:15px;color:green'>" +
                "Click and Drag</span> to create Particles.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>0</span> to toggle particle attraction to the mouse pointer.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>1</span> to increase particle size.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>2</span> to decrease particle size.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>3</span> to decrease amount of particles.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>4</span> to toggle Particle Mode (Normal, Multi, Fireworks, Graph, Ragdoll) <br></li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>5</span> to toggle Thinking Particles (plain color or color change based on" +
                " speed).</li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>6</span> to toggle Link Mode " +
                "(connects each particle: <span style='font-size:13px;color:red'> 100 particles max</span>)</li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>7</span> to bring up Options Menu.</li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>8</span> to pause simulation, press again to resume.</li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>9</span> to bring up Slide Editor.</span></li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>B</span> to toggle connection type for Link Mode.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>F</span> to slow particles.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>G</span> to scatter particles.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>K</span> to toggle collision detection in VPhysics.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>M</span> to toggle Gravity for VPhysics.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>N</span> to toggle Debug Mode for VPhysics.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>R</span> to change the background color.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>S</span> to toggle Engine Graphics Smoothing.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>T</span> to toggle particle friction.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>V</span> to toggle connection type for Gravity Points.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>W</span> to bring up Particle Color Editor.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>UP</span> to increase click and drag amount.</li> " +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>DOWN</span> to decrease click and drag amount.</li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>Right</span> to advance a particle type. </li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>Left</span> to roll back a particle type. </li>" +
                "<li style='font-size:13px'> Press <span style='font-size:15px;color:purple'>SPACE</span> to create Gravity Points! </li> " +
                "</ul></p> " +
                "</body></html>";
    }

    public static String generalOptions() {
        return "<html> " +
                "<p style='font-size:16px;color:red'>General Options: " +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>1 - </span> Change Particle Size.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>2 - </span> Change Drag Amount.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>3 - </span> Change Fireworks Amount.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>4 - </span> Change Particle Type.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>5 - </span> Change Particle Color.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>6 - </span> Change Particle Gravitation.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>7 - </span> Change Particle Size Seed.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>8 - </span> Change Particle Speed Seed.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>9 - </span> Fireworks Options.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>10 - </span> Change Fireworks Particle Type.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>11 - </span> Thinking Particle Color Options.</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>12 - </span> Adjust Particle Safety Amount " +
                "(<span style='font-size:13px;color:red'>Adjust With Caution</span>)!</p>" +
                "<p style='font-size:13px'><span style='font-size:15px;color:blue'>13 - </span> Change Color Cycle Time.</p>" +
                "</p> <br>" +
                "</html>";
    }

    public static String particleDrawOptions() {
        return "<html> " +
                "<p style='font-size:16px;color:red'>Options: " +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>1 - </span> Rectangle(without fill).</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>2 - </span> Circle(without fill).</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>3 - </span> 3D Rectangle(without fill).</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>4 - </span> Numbers.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>5 - </span> Custom Text.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>6 - </span> 3D Rectangle(with fill).</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>7 - </span> Circle(with fill).</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>8 - </span> Smiley Face.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>9 - </span> Music Note(1).</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>10 - </span> Music Note(2).</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>11 - </span> Hearts.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>12 - </span> Cent Symbol.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>13 - </span> Copyright Symbol.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>14 - </span> Trademark Symbol.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>15 - </span> Infinity Symbol.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>16 - </span> Kappa Symbol.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>17 - </span> Spade Symbol.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>18 - </span> Club Symbol.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>19 - </span> Diamond Symbol.</p>" +
                "</p> <br>" +
                "</html>";
    }

    public static String particleGraphInstructions() {
        return "<html><body>" +
                "<p style='font-size:18px;color:black;text-align:center'>How to Use:</p>" +
                "<br>" +
                "<div><p style='font-size:13px;color:black;text-align:center'>" +
                "<span style='font-size:15px;color:red'>Please type out all functions and operators.</span>" +
                "<br>For example: <span style='color:blue'>2sin(x) = 2*sin(x)</span><br>" +
                "<br>" +
                "<span>More complicated functions also follow this:<br> " +
                "<span style='color:blue'>tan(x)(2x-2)<sup>2</sup> - cos(2x) = tan(x)*pow((2*x-2),2) - cos(2*x)</span></span>" +
                "</p></div>" +
                "<br>" +
                "<p style='font-size:18px;color:black;text-align:center'>Functions + Constants Available: </p>" +
                "<ul>" +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>e</span> = Euler's Constant.</li>" +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>pi</span> = π.</li>" +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>sin(x)</span> - computes the sine of an angle x.</li>" +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>cos(x)</span> - computes the cosine of an angle x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>tan(x)</span> - computes the tangent of an angle x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>asin(x)</span> - computes the arc sine of an angle x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>acos(x)</span> - computes the arc cos of an angle x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>atan(x)</span> - computes the arc tangent of an angle x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>log(x)</span> - computes the natural logarithm (base e) of x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>sqrt(x)</span> - computes the square root of x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>abs(x)</span> - computes the absolute value of x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>exp(x)</span> - computes Euler's number e raised to the power of x.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>signum(x)</span> - computes the sign of x. Ex. -1/1 = -1 & 1/1 = +1</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>pow(x, y)</span> - computes x raised to the y. Ex. pow(2, 2) = 2<sup>2</sup> = 4</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>mod(x, y)</span> - computes the modulus (remainder) of x = dividend, and y = divisor.</li> " +
                "<li style='font-size:13px'> <span style='font-size:15px;color:orange'>rand(max, min)</span> - computes a random number between max and min.</li> " +
                "</ul>" +
                "</body></html>";
    }

    public static String particleGravitationOptions() {
        return "<html> " +
                "<p style='font-size:16px;color:red'>Particle Gravitation Options: " +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>0 - </span> Default force.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>1 - </span> Cosine and Sine of force.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>2 - </span> Arc Tangent of force.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>3 - </span> Horizontal Wave!</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>4 - </span> Vertical Wave!</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>5 - </span> Spirals!</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>6 - </span> Particle Repellent</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>7 - </span> Organic Forces!</p>" +
                "</p> <br>" +
                "</html>";
    }

    public static String particleSizeSeedOptions() {
        return "<html> " +
                "<p style='font-size:16px;color:red'>Particle Size Seed Options: " +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>1 - </span> Click Size.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>2 - </span> Fireworks Size.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>3 - </span> Drag Size.</p>" +
                "</p> <br>" +
                "</html>";
    }

    public static String particleSpeedSeedOptions() {
        return "<html> " +
                "<p style='font-size:16px;color:red'> Particle Speed Seed Options: " +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>1 - </span> Click Speed.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>2 - </span> Fireworks Speed.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>3 - </span> Drag Speed.</p>" +
                "</p> <br>" +
                "</html>";
    }

    public static String realFireworksOptions() {
        return "<html> " +
                "<p style='font-size:16px;color:red'> Real Fireworks Options: " +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>1 - </span> Change Wind Amount.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>2 - </span> Change Particle Life.</p>" +
                "<p style='font-size:13px'><span style='font-size:12px;color:blue'>3 - </span> Change Jitter Amount.</p>" +
                "</p> <br>" +
                "</html>";
    }
}