package com.niothiel.simplesms.util;

import android.database.Cursor;
import android.util.Log;

public class TempUtil {
	public static void printCur(Cursor c) {
		if(c == null || c.getCount() == 0) {
			Log.d("Cursor Printout", "Invalid cursor or no entries.");
			return;
		}
		
		printColumnNames(c);
		
		c.moveToFirst();
		do {
			StringBuilder entry = new StringBuilder();
			for(int i = 0; i < c.getColumnCount(); i++) {
				entry.append(c.getString(i) + ":");
			}
			Log.d("Cursor Printout", entry.toString());
		} while(c.moveToNext());
	}
	
	public static void printColumnNames(Cursor c) {
		if(c == null || c.getCount() == 0) {
			Log.d("Cursor Printout", "Invalid cursor or no entries");
			return;
		}
		
		StringBuilder headers = new StringBuilder();
		
		for(String s : c.getColumnNames()) {
			headers.append(s + ":");
		}
		Log.d("Cursor Printout", headers.toString());
	}
}