package com.niothiel.simplesms.data;

public class Conversation {
	private long mThreadId;
	private long mDate;
	private long mMsgCount;
	private boolean mRead;
	private Contact mContact;
	
	public Conversation() {
		mThreadId = -1;
		mDate = -1;
		mMsgCount = -1;
		mRead = false;
		mContact = null;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Conversation) {
			Conversation c = (Conversation) o;
			return mThreadId == c.mThreadId;
		}
		return super.equals(o);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Conversation[");
		sb.append("name=" + mContact.getName());
		sb.append(" thread_id=" + mThreadId);
		sb.append(" msg_count=" + mMsgCount);
		sb.append(" number=" + mContact.getNumber());
		sb.append("]");
		
		return sb.toString();
	}

	public long getThreadId() {
		return mThreadId;
	}

	public void setThreadId(long threadId) {
		this.mThreadId = threadId;
	}

	public long getDate() {
		return mDate;
	}

	public void setDate(long date) {
		this.mDate = date;
	}

	public long getMsgCount() {
		return mMsgCount;
	}

	public void setMsgCount(long msgCount) {
		this.mMsgCount = msgCount;
	}

	public boolean isRead() {
		return mRead;
	}

	public void setRead(boolean read) {
		this.mRead = read;
	}

	public Contact getContact() {
		return mContact;
	}

	public void setContact(Contact contact) {
		this.mContact = contact;
	}
}