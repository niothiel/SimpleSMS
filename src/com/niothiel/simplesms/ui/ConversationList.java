/**
 * USEFUL LINKS:
 * 
 * USEFUL PACKAGES:
 * Git Tree Url:
 * http://android.git.kernel.org/?p=platform/packages/providers/TelephonyProvider.git;a=tree;f=src/com/android/providers/telephony;h=7f7c730cbaa611fb3556f206447eaa0cc41bf9e0;hb=HEAD
 *  
 */

package com.niothiel.simplesms.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.niothiel.simplesms.Benchmarker;
import com.niothiel.simplesms.R;
import com.niothiel.simplesms.SMSApp;
import com.niothiel.simplesms.data.Conversation;
import com.niothiel.simplesms.store.ContactStore;
import com.niothiel.simplesms.store.ConversationStore;
import com.niothiel.simplesms.util.Telephony;

// TODO: Subscribe to SMS intents in AndroidManifest.xml
// TODO: Revamp UI
public class ConversationList extends ListActivity {
	public static final String TAG = SMSApp.TAG + "/ConvList";
	
	private ConversationStore mStore;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Benchmarker.start("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_list_screen);
        LayoutInflater inflater = LayoutInflater.from(this);

        // Add the "New Message" entry into our list.
        ConversationListItem headerView = (ConversationListItem)
                inflater.inflate(R.layout.conversation_list_item, getListView(), false);
        headerView.bind("New Message",
                "Compose new message");
        getListView().addHeaderView(headerView, null, true);
        
        mStore = new ConversationStore();
        mStore.bindView(getListView());
        mStore.update();
        
        //Uri observable = Telephony.MmsSms.CONTENT_CONVERSATIONS_URI;
		//getContentResolver().registerContentObserver(observable, true, new ConversationListObserver());
		//Log.d("SimpleSMS", "Observing URI: " + observable.toString());
        
        Benchmarker.stop("onCreate");
        
        Intent i = getIntent();
        if(i != null)
        	Log.d(TAG, i.getAction() + ":" + i.getType());
        //Cursor c = managedQuery(Uri.parse("content://mms-sms/conversations"),
        //		null, null, null, null);
        //TempUtil.printCur(c);
    }
    
    @Override
	protected void onPause() {
    	Log.d("SimpleSMS", "Called onPause()!");
    	
		ContactStore.exportCache();
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.d("SimpleSMS", "Called onResume()!");
		
		ContactStore.importCache();
		super.onResume();
	}
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Conversation c = null;
		
		if(position > 0) {
			c = mStore.getConversation(position - 1);
		}
		startComposing(c);
	}
    
    private void startComposing(Conversation c) {
    	Intent i = new Intent(this, ComposeMessageActivity.class);
    	
    	// If we are working with a valid conversation, 
    	// populate the intent with conversation information
    	// TODO: Update program to operate through .getData()
    	// or just thread_ids. This will be required later
    	// down the line.
    	if(c != null) {
    		Log.d("SimpleSMS", c.toString());
    		i.putExtra("thread_id", c.threadId);
			i.putExtra("name", c.contact.name);
			i.putExtra("number", c.contact.number);
    	}
    	startActivity(i);
    }
}

/*
Contacts DB fields:

times_contacted
primary_organization
phonetic_name
type
mode
last_time_contacted
_sync_time
_id
_sync_id
number_key
primary_email
name
sort_string
primary_phone
im_account
_sync_account
_sync_version
send_to_voicemail
custom_ringtone
status
_sync_local_id
number
label
display_name
_sync_dirty
im_handle
starred
notes
im_protocol

SMS DB fields:

_id
thread_id
address
person
date
protocol
read
status
type
reply_path_present
subject
body
service_center
*/