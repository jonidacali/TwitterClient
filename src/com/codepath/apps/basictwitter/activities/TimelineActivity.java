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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.helpers.EndlessScrollListener;
import com.codepath.apps.basictwitter.helpers.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	long maxTweetId = Long.MAX_VALUE;
	private final int REQUEST_CODE = 80;
	final Context context = this;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		ActiveAndroid.setLoggingEnabled(true); 
		
		//get instance of twitter client
		client = TwitterApplication.getRestClient();
		customLoadMoreData(1,0);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets =  new TweetArrayAdapter(context, tweets);
		//attach adapter to listView
		lvTweets.setAdapter(aTweets);
		
		//Setup OnScrollListener
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
                 customLoadMoreData(totalItemsCount, maxTweetId); 
		    }
    	});
	}
	
	public void customLoadMoreData(long sinceId, long maxId) {    	
    	if(isConnectivityAvailable(context)){
    		timelineTweetsFromApi(sinceId, maxId);
		} else {
			timelineTweetsFromDB();
		}
	}
	
	public void timelineTweetsFromApi(long since_id, long max_id){
		client.getHomeTimeline(since_id, max_id, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				ArrayList<Tweet> batch = Tweet.fromJSONArray(json);
				maxTweetId = updateMaxId(batch);
				aTweets.addAll(batch);
				//Save tweets in db
				Tweet.saveTweetsArrayList(json);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void timelineTweetsFromDB(){
		
	}
	private long updateMaxId(ArrayList<Tweet> tweets) {
		//iterate through tweets to find new maxId
		long maxId = 0;
		for(Tweet tweet : tweets){
			long thisTweetId = tweet.getUid(); 
			maxId = (tweet.getUid() < maxTweetId) ? (tweet.getUid() -1) : maxTweetId; 
		}
		return maxId;
	}

	public void replyTweet(){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.photos, menu);
	    return true;
	}
	
	public void composeTweet(MenuItem mi){
		Intent i = new Intent(this,ComposeTweetActivity.class);
		startActivityForResult(i, REQUEST_CODE);		
	}
	
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data){  
		super.onActivityResult(requestCode, resultCode, data);                    
	    if(requestCode==REQUEST_CODE && resultCode == RESULT_OK) {  
	    	String status = data.getStringExtra("status");
	    	client.postTweet(status, new JsonHttpResponseHandler(){
    			@Override
    			public void onSuccess(JSONObject json) {
    				Tweet newTweet = Tweet.fromJson(json);        
    			    aTweets.insert(newTweet, 0);
    		    	aTweets.notifyDataSetChanged();
    			}
    			
				@Override
    			public void onFailure(Throwable e, String s) {
    				Log.d("debug", e.toString());
    				Log.d("debug", s.toString());
    			}
    		});
        }  
	}  
	
	public static boolean isConnectivityAvailable(Context ctx){
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null) {
			return (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE);
		} else {
			return false;
		}
	 }
	
}
