package org.example;

public class B extends A {
    public double b;
    public String s;

    private void setB(double b) {
        this.b = b;
    }
    public void setS(String s) {
        this.s = s;
    }

    public B(int a,double b, String s) {
        this.a = a;
        this.b = b;
        this.s = s;
    }
}
