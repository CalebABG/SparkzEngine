package com.engine.Interfaces_Extensions;

import java.util.TimerTask;

public class TimerTaskX extends TimerTask {
    private Function function;
    public TimerTaskX(Function function){ this.function = function;}
    public void run() {function.apply();}
}
