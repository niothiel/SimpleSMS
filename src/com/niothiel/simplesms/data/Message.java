package com.niothiel.simplesms.data;

public class Message {
	public static final int T_INBOUND = 1;
	public static final int T_OUTBOUND = 2;
	
	public int type;
	public String body;
}
