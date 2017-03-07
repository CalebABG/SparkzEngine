package com.engine.J8Helpers.Extensions;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class MLAStruct {
    private String name;
    private Consumer<MouseEvent> function;

    public MLAStruct(String name, Consumer<MouseEvent> function) {
        this.name = name;
        this.function = function;
    }

    public String getName() {return name;}
    public Consumer<MouseEvent> getFunction() {return function;}
}
