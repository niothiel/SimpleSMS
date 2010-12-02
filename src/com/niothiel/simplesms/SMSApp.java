package com.niothiel.simplesms;

import android.content.Context;

public class SMSApp extends android.app.Application {
	private static SMSApp instance; 
	
	public SMSApp() {
		instance = this;
	}
	
	public static Context getContext() {
		return instance.getApplicationContext();
	}
}
