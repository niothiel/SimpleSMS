package com.niothiel.simplesms.store;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.niothiel.simplesms.SMSApp;
import com.niothiel.simplesms.data.Message;

public class MessageStore {
	private static final Uri URI = Uri.parse("content://sms/");
	
	private Context mContext;
	private ContentResolver mResolver;
	private String mName;
	private long mThreadId;
	private Adapter mAdapter;
	private ListView mListView;
	
	private ArrayList<Message> mItems;
	
	public MessageStore(long threadId, String name) {
		mContext = SMSApp.getContext();
		mResolver = mContext.getContentResolver();
		mName = name;
		mThreadId = threadId;
		mItems = new ArrayList<Message>(50);
		mAdapter = new Adapter();
		
		// Add a meaningless comment here.
		// Another useless one here.
	}
	
	public void bindView(ListView lv) {
		mListView = lv;
		lv.setAdapter(mAdapter);
	}
	
	public void requery() {
		Cursor c = mResolver.query(URI,
				new String[] { "thread_id", "body", "type"},
				"thread_id=" + mThreadId,
				null,
				"date ASC"
				);
		
		mItems.clear();
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Message m = new Message();
			m.type = c.getInt(2);
			m.body = c.getString(1);
			mItems.add(m);
		}

		markThreadRead();
		mAdapter.notifyDataSetChanged();
		mListView.setSelection(mItems.size() - 1);
	}
	
	private void markThreadRead() {
		ContentValues cv = new ContentValues(1);
		cv.put("read", 1);
		
		mResolver.update(URI,
				cv,
				"read=0 AND thread_id=" + mThreadId,
				null
				);
	}
	
	private Spannable formatMessage(Message msg) {
		SpannableStringBuilder sb = new SpannableStringBuilder();
		if(msg.type == Message.T_INBOUND) {
			sb.append(mName);
		}
		else
			sb.append("Me");
		sb.append(": ");
		sb.append(msg.body);
		
		// TODO: Refactor the shit out of this method.
		sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
				0,
				msg.type == 1 ? mName.length() + 1 : 3,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
				);
		
		return sb;
	}
	
	private class Adapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view;
			if(convertView == null) {
				view = new TextView(mContext);
				view.setTextSize(16);
				view.setTextColor(0xffffffff);
			}
			else {
				view = (TextView) convertView;
			}
			Message msg = mItems.get(position);
			view.setText(formatMessage(msg), BufferType.SPANNABLE);
			
			return view;
		}
	}
}
