package com.nptec.mvp.model;

import java.io.Serializable;

public class Sensores implements Serializable {
    private int temperatura;
    private int ph;
    private int rpm;
    private int c1;
    private int c2;
    private int t1;
    private int t2;

    public Sensores() {
    }

    public Sensores(int temperatura, int ph, int rpm, int c1, int c2, int t1, int t2) {
        this.temperatura = temperatura;
        this.ph = ph;
        this.rpm = rpm;
        this.c1 = c1;
        this.c2 = c2;
        this.t1 = t1;
        this.t2 = t2;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public int getPh() {
        return ph;
    }

    public int getRpm() {
        return rpm;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }

    public int getT1() {
        return t1;
    }

    public int getT2() {
        return t2;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public void setT1(int t1) {
        this.t1 = t1;
    }

    public void setT2(int t2) {
        this.t2 = t2;
    }

}
