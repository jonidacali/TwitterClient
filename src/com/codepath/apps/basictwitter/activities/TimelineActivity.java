package com.codepath.apps.basictwitter.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;

public class TimelineActivity extends FragmentActivity {
	final Context context = this;
	private final int REQUEST_CODE_POST = 80;
	private final int REQUEST_CODE_REPLY = 120;
	
	TweetsListFragment home = null;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		ActiveAndroid.setLoggingEnabled(true); 		
		setupTabs();
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
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
	
	public void viewProfile(MenuItem mi){
		Intent i = new Intent(this, ProfileActivity.class);		
		startActivity(i);
	}
	
	public void replyTweet(){
		
	}
	
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data){  
		super.onActivityResult(requestCode, resultCode, data);                    
	    if(requestCode==REQUEST_CODE_POST && resultCode == RESULT_OK) {  
	    	Tweet tweet = data.getParcelableExtra("tweet");
	    	getHomeTimelineFragment();
	    	if (home != null) {
	    		home.addTweet(tweet);
	    	}
        }  
	    
	    if (requestCode == REQUEST_CODE_REPLY && resultCode == RESULT_OK){
	    	
	    	
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
	    }
	}
	
	public void getHomeTimelineFragment(){
		if (home == null) {
			home = (TweetsListFragment) getSupportFragmentManager().findFragmentByTag("first");
		}
	}
	
}
