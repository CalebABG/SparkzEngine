package com.engine.J8Helpers.Extensions;

import com.engine.J8Helpers.Interfaces.Function;

import java.util.TimerTask;

public class TimerTaskX extends TimerTask {
    private Function function;
    public TimerTaskX(Function function){ this.function = function;}
    public void run() {function.apply();}
}
