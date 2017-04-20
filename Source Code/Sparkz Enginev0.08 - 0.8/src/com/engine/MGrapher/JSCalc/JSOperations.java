package com.engine.MGrapher.JSCalc;

import static com.engine.MGrapher.JSCalc.JSCalc.*;

public class JSOperations {
    public static void x_squared() {
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText(Math.pow(x, 2) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static void x_cubed() {
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText(Math.pow(x, 3) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static void square_root() {
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText(Math.sqrt(x) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static void one_over_x() {
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText((1.0D / x) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static void ln_x() {
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText(Math.log(x) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static void log_x() {
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText(Math.log10(x) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static void nfactorial() {
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText(factorial(x) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static void e_to_x(){
        try {
            double x = evaluateExpr(textPane.getText());
            resultsPane.setText(Math.pow(Math.E, x) + "");
        }catch (Exception x){resultsPane.setText(x.getMessage());}
    }

    public static double factorial(double n) {
        return Math.ceil(Math.sqrt(6.283185307179586D * n) * Math.pow(n / 2.718281828459045D, n));
    }
}
