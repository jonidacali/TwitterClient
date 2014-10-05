package com.codepath.apps.basictwitter.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.helpers.EndlessScrollListener;
import com.codepath.apps.basictwitter.helpers.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
//	private final int REQUEST_CODE_POST = 80;
//	private final int REQUEST_CODE_REPLY = 120;
	final Context context = this;
	private final int REQUEST_CODE_POST = 80;
//	private final int REQUEST_CODE_REPLY = 120;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		ActiveAndroid.setLoggingEnabled(true); 
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.actions, menu);
	    return true;
	}
	
	public void composeTweet(MenuItem mi){
		Intent i = new Intent(this,ComposeTweetActivity.class);
		startActivityForResult(i, REQUEST_CODE_POST);		
	}
	
	public void replyTweet(){
		
	}
	
	
//	@Override  
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){  
//		super.onActivityResult(requestCode, resultCode, data);                    
//	    if(requestCode==REQUEST_CODE_POST && resultCode == RESULT_OK) {  
//	    	String status = data.getStringExtra("status");
//	    	client.postTweet(status, new JsonHttpResponseHandler(){
//    			@Override
//    			public void onSuccess(JSONObject json) {
//    				Tweet newTweet = Tweet.fromJson(json);        
//    			    aTweets.insert(newTweet, 0);
//    		    	aTweets.notifyDataSetChanged();
//    			}
//    			
//				@Override
//    			public void onFailure(Throwable e, String s) {
//    				Log.d("debug", e.toString());
//    				Log.d("debug", s.toString());
//    			}
//    		});	
//        }  
//	    
//	    if (requestCode == REQUEST_CODE_REPLY && resultCode == RESULT_OK){
//	    	String status = data.getStringExtra("status");
//	    	long in_reply_to_status_id = data.getLongExtra("in_reply_to_status_id", 1L);
//	    	client.replyToTweet(status, in_reply_to_status_id, new JsonHttpResponseHandler(){
//	    		@Override
//    			public void onSuccess(JSONObject json) {
//	    			Toast.makeText(TimelineActivity.this, "Reply posted", Toast.LENGTH_SHORT).show();
//	    			
//    			}
//    			
//				@Override
//    			public void onFailure(Throwable e, String s) {
//					Log.d("debug", e.toString());    			
//					Toast.makeText(TimelineActivity.this, "Failed - try again", Toast.LENGTH_SHORT).show();
//				}
//	    	});
//	    }
//	}
	
}
