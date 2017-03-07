package com.engine.J8Helpers.Extensions;

import com.engine.J8Helpers.Interfaces.MouseAdapterX;

import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Consumer;

public class MLAdapter implements MouseAdapterX {
    private Hashtable<String, Consumer<MouseEvent>> functionHash = new Hashtable<>();
    private List<String> acceptableFunctions = Arrays.asList("Clicked", "Pressed", "Released",
                                                                 "Entered", "Exited", "Moved", "Dragged");

    /**
     * <p>
     * Constructor takes a list of functions. It builds a hash-table with functions of the MouseListener and MouseMotionListener interfaces
     * The data structure that holds the functions requires a name for each function which correspond to the functions in the mentioned interfaces,
     * and a lambda expression (Consumer of type MouseEvent).
     * </p>
     * <br>
     * Only acceptable names: "Clicked", "Pressed", "Released","Entered", "Exited", "Moved", "Dragged"
     * Example of MLAStruct object: new MLAStruct("Clicked", e -> {});
    */
    public MLAdapter(MLAStruct... mlaFuntions) {
        for (MLAStruct function : mlaFuntions) {
            if (checkFunctionName(function.getName())) {
                functionHash.put(function.getName(), function.getFunction());
            }
        }
    }

    public boolean addFunction(MLAStruct function) {
        if (checkFunctionName(function.getName())) {
            functionHash.put(function.getName(), function.getFunction());
            return true;
        }
        return false;
    }

    private boolean checkFunctionName(String fun){
        return acceptableFunctions.indexOf(fun) >= 0;
    }

    public void mouseClicked(MouseEvent e){try{functionHash.get("Clicked").accept(e);}catch(NullPointerException f){f.getMessage();}}
    public void mousePressed(MouseEvent e){try{functionHash.get("Pressed").accept(e);}catch(NullPointerException f){f.getMessage();}}
    public void mouseReleased(MouseEvent e){try{functionHash.get("Released").accept(e);}catch(NullPointerException f){f.getMessage();}}
    public void mouseEntered(MouseEvent e){try{functionHash.get("Entered").accept(e);}catch(NullPointerException f){f.getMessage();}}
    public void mouseExited(MouseEvent e){try{functionHash.get("Exited").accept(e);}catch(NullPointerException f){f.getMessage();}}
    public void mouseMoved(MouseEvent e){try{functionHash.get("Moved").accept(e);}catch(NullPointerException f){f.getMessage();}}
    public void mouseDragged(MouseEvent e){try{functionHash.get("Dragged").accept(e);}catch(NullPointerException f){f.getMessage();}}
}