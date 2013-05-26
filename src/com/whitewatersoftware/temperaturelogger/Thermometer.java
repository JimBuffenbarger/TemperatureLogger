package com.whitewatersoftware.temperaturelogger;

import android.os.Environment;
import android.util.Log;
import java.util.Vector;
import java.io.*;

public class Thermometer {

    private int dups;
    private int size;
    private Vector<Temperature> temps;

    public Thermometer(int size) {
	dups=0;
	this.size=size;
	temps=new Vector<Temperature>();
    }

    public void write(Temperature temperature) {
	File root=Environment.getExternalStorageDirectory();
	File log=new File(root +"/temperatures");
	try {
	    if (!log.exists())
	     	log.createNewFile();
	    BufferedWriter buf=new BufferedWriter(new FileWriter(log,true)); 
	    buf.append(temperature.toStringLog());
	    buf.newLine();
	    buf.flush();
	    buf.close();
	} catch (Exception e) {}
    }

    public boolean change(float temp) {
	dups++;
	if (temps.size()>0 && temps.firstElement().equal(temp))
	    return false;
	dups=0;
	Temperature temperature=new Temperature(temp);
	temps.add(0,temperature);
	if (temps.size()>size)
	    temps.remove(size);
	write(temperature);
	return true;
    }

    public String toString() {
	String r="";
	int cnt=0;
	float last=0;
	for (Temperature temp: temps) {
	    String flag="";
	    if (cnt==0 && dups>0)
		flag=(dups%2==0 ? " " : "*");
	    else
		flag=(temp.val()<last ? "+" : "-");
	    r+=temp+" "+flag+"\n";
	    last=temp.val();
	    cnt++;
	}
	return r;
    }

    public String toStringWidget() {
	String r="";
	int cnt=0;
	Temperature last=null;;
	for (Temperature temp: temps) {
	    if (cnt==1) {
		r=last.toStringWidget()+" "+
		    (temp.val()<last.val() ? "^" : "v");
		break;
	    }
	    r=temp.toStringWidget();
	    last=temp;
	    cnt++;
	}
	return r;
    }

}
