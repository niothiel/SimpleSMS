package com.niothiel.simplesms.data;

public class Contact {
	private long mRecipientId;
	private String mName;
	private String mNumber;
	
	public Contact(long recipientId, String name, String number) {
		mRecipientId = recipientId;
		mName = name;
		mNumber = number;
	}
	
	public static Contact parseCached(String s) {
		String[] line = s.split("\t");
		int recipientId = Integer.parseInt(line[0]);
		String name = line[1];
		String number = line[2];
		return new Contact(recipientId, name, number);
	}
	
	public long getRecipientId() {
		return mRecipientId;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getNumber() {
		return mNumber;
	}
	
	public String getFormatted() {
		StringBuilder s = new StringBuilder();
		s.append(mName);
		s.append(" <");
		s.append(mNumber);
		s.append(">");
		
		return s.toString();
	}
	
	@Override
	public String toString() {
		return mRecipientId + "\t" + mName + "\t" + mNumber;
	}
}
