package com.Training.Serialization;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SerialClass implements Serializable {
	
	private static final long serialVersionUID = 12L;
    
	private Date currentTime;
    
    static Date staticTime = Calendar.getInstance().getTime();
    transient Date transientTime = Calendar.getInstance().getTime();

    public SerialClass() {
        currentTime = Calendar.getInstance().getTime();       
    }
    
    public Date getCurrentTime() {
        return currentTime;
    }
}

