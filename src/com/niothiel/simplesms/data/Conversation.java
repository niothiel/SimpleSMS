package com.niothiel.simplesms.data;

public class Conversation {
	public long threadId;
	public long date;
	public long msgCount;
	public boolean read;
	public Contact contact;
	
	public Conversation() {
		threadId = -1;
		date = -1;
		msgCount = -1;
		read = false;
		contact = null;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Conversation) {
			Conversation c = (Conversation) o;
			return threadId == c.threadId;
		}
		return super.equals(o);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Conversation[");
		sb.append("name=" + contact.name);
		sb.append(" thread_id=" + threadId);
		sb.append(" msg_count=" + msgCount);
		sb.append(" number=" + contact.number);
		sb.append("]");
		
		return sb.toString();
	}
}