package com.niothiel.simplesms.data;

public class Contact {
	public long recipientId;
	public String name;
	public String number;
	
	public Contact(long recipientId, String name, String number) {
		this.recipientId = recipientId;
		this.name = name;
		this.number = number;
	}
	
	public static Contact parseCached(String s) {
		String[] line = s.split("\t");
		int recipientId = Integer.parseInt(line[0]);
		String name = line[1];
		String number = line[2];
		return new Contact(recipientId, name, number);
	}
		
	@Override
	public String toString() {
		return recipientId + "\t" + name + "\t" + number;
	}
	
	public String getFormatted() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" <");
		sb.append(number);
		sb.append(">");
		return sb.toString();
	}
}
