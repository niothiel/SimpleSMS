package com.niothiel.simplesms;

import com.niothiel.simplesms.store.ConversationStore;

import android.content.Context;

public class SMSApp extends android.app.Application {
	public static String TAG = "SimpleSMS";
	private static SMSApp instance;
	
	public SMSApp() {
		instance = this;
	}
	
	public static Context getContext() {
		return instance.getApplicationContext();
	}
	
	public static ConversationStore getConversationStore() {
		// TODO: Implement it this way.
		return null;
	}
}
