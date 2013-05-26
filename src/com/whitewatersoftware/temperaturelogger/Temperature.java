package com.whitewatersoftware.temperaturelogger;

import java.util.Date;
import java.util.Formatter;

public class Temperature {

    private float temp;
    private Date date;

     public Temperature(float temp) {
	this.temp=temp;
 	date=new Date();
    }

    public boolean equal(float temp) {
	return temp==this.temp;
    }

    public float val() {
	return temp*9/5+32;
    }

    public String toString() {
	return new Formatter().format("%tT",date)+" "
	      +new Formatter().format("%5.1f",val());
    }

    public String toStringLog() {
	return new Formatter().format("%tF",date)+" "
	      +new Formatter().format("%tT",date)+" "
	      +new Formatter().format("%5.1f",val());
    }

    public String toStringWidget() {
	return ""+new Formatter().format("%3.0f",val());
    }

}
