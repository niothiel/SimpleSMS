package com.niothiel.simplesms.store;

import java.util.ArrayList;

import com.niothiel.simplesms.Benchmarker;
import com.niothiel.simplesms.R;
import com.niothiel.simplesms.SMSApp;
import com.niothiel.simplesms.data.Contact;
import com.niothiel.simplesms.data.Conversation;
import com.niothiel.simplesms.ui.ConversationListItem;
import com.niothiel.simplesms.util.Telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.Html.TagHandler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ConversationStore {
	private static String[] PROJECTION = new String[] {
		"_id",
		"date",
		"message_count",
		"recipient_ids",
		"snippet",
		"read",
		"type"
	};
	
	private static Uri URI = Uri.parse(
		"content://mms-sms/conversations?simple=true");
	
	private ArrayList<Conversation> mConversations;
	private ContentResolver mResolver;
	private LayoutInflater mInflater;
	private Adapter mAdapter;
	
	public ConversationStore(Context ctx) {
		mResolver = ctx.getContentResolver();
		mConversations = new ArrayList<Conversation>(20);
		mInflater = LayoutInflater.from(ctx);
		mAdapter = new Adapter();
		
		Uri observable = Telephony.MmsSms.CONTENT_CONVERSATIONS_URI;
		Log.d("SimpleSMS", "Observing URI: " + observable.toString());
		//mResolver.registerContentObserver(observable, true, new ContentObserver(null));
	}
	
	public void requery() {
		Benchmarker.start("ConvRequery");
		mConversations.clear();
		
		Cursor c = mResolver.query(URI,
				PROJECTION,
				null,
				null,
				null
				);
		
		if(c == null || c.getCount() == 0) {
			return;
		}
		
		c.moveToFirst();
		do {
			Conversation conv = new Conversation();
			conv.setThreadId(c.getLong(0));
			conv.setDate(c.getLong(1));
			conv.setMsgCount(c.getInt(2));
			conv.setRead(c.getInt(5) == 1);
			
			if(!mConversations.contains(conv))
				mConversations.add(conv);
			
			int recipient_id = c.getInt(3);
			Contact recipient = ContactStore.getByRecipientId(recipient_id);
			conv.setContact(recipient);
		} while(c.moveToNext());
		
		c.close();
		mAdapter.notifyDataSetChanged();
		Benchmarker.stop("ConvRequery");
	}
	
	public void bindView(ListView lv) {
		lv.setAdapter(mAdapter);
	}
	
	public Conversation getConversation(int position) {
		return mConversations.get(position);
	}
	
	private class Adapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mConversations.size();
		}

		@Override
		public Object getItem(int position) {
			return mConversations.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mConversations.get(position).getThreadId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.conversation_list_item,
						parent,
						false);
			}
			Conversation c = mConversations.get(position);
			ConversationListItem entry = (ConversationListItem) convertView;
			entry.bind(c);
			return entry;
		}
	}
	
	public static long getOrCreateThreadId(String recipient) {
        Uri.Builder uriBuilder = Uri.parse("content://mms-sms/threadID").buildUpon();
        uriBuilder.appendQueryParameter("recipient", recipient);

        Uri uri = uriBuilder.build();
        Log.v("ComposeMessageAdapter", "getOrCreateThreadId uri: " + uri);
        ContentResolver resolver = SMSApp.getContext().getContentResolver();
        Cursor cursor = resolver.query(
                uri, new String[] { "_id" }, null, null, null);
        Log.v("ComposeMessageAdapter", "getOrCreateThreadId cursor cnt: " + cursor.getCount());
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0);
                } else {
                    Log.e("ComposeMessageAdapter", "getOrCreateThreadId returned no rows!");
                }
            } finally {
                cursor.close();
            }
        }

        Log.e("ComposeMessageAdapter", "getOrCreateThreadId failed with uri " + uri.toString());
        throw new IllegalArgumentException("Unable to find or allocate a thread ID.");
    }
}
