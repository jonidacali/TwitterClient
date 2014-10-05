package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.activities.ShowTweetDetailActivity;
import com.codepath.apps.basictwitter.helpers.EndlessScrollListener;
import com.codepath.apps.basictwitter.helpers.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void fireClientRequest(long sinceId, long maxId,
			JsonHttpResponseHandler handler) {
		client.getHomeTimeline(sinceId, maxId, handler);
		
	}
	
	@Override
	public void fireDbRequest(long sinceId, long maxId) {
		
		
	}
	
	
	
	//	private TwitterClient client;
//	long maxTweetId = Long.MAX_VALUE;
//	final Context context = getActivity();
//	private final int REQUEST_CODE_POST = 80;
//	private final int REQUEST_CODE_REPLY = 120;
	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//get instance of twitter client
//		customLoadMoreData("HOME", 1,0);
//		
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//		    @Override
//		    public void onLoadMore(int page, int totalItemsCount) {
//                 customLoadMoreData("HOME", totalItemsCount, maxTweetId); 
//		    }
//    	});
//		
//		
		
//		//Setup OnScrollListener
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//		    @Override
//		    public void onLoadMore(int page, int totalItemsCount) {
//                 customLoadMoreData(totalItemsCount, maxTweetId); 
//		    }
//    	});
//		
//		setupViewListener();
//	}
	
	
//	private void setupViewListener(){
//		lvTweets.setOnItemClickListener(new OnItemClickListener(){
//			@Override
//			public void onItemClick(AdapterView<?> parent, View v, int position, long rowId) {
//				Tweet tweet = ((Tweet) parent.getItemAtPosition(position));
//				Intent i = new Intent(context,ShowTweetDetailActivity.class);	
//				i.putExtra("tweet", tweet);
//				startActivityForResult(i, REQUEST_CODE_REPLY);
//			}
//		});
//	}
//	
//	public void customLoadMoreData(long sinceId, long maxId) {    	
//    	if(isConnectivityAvailable(context)){
//    		timelineTweetsFromApi(sinceId, maxId);
//		} else {
//			timelineTweetsFromDB(sinceId, maxId);
//		}
//	}
//	
//	public void timelineTweetsFromApi(long since_id, long max_id){
//		client.getHomeTimeline(since_id, max_id, new JsonHttpResponseHandler(){
//			@Override
//			public void onSuccess(JSONArray json) {
//				ArrayList<Tweet> batch = Tweet.fromJSONArray(json);
//				maxTweetId = updateMaxId(batch);
//				addAll(batch);
//				//Save tweets in db
//				Tweet.saveTweetsArrayList(json);
//			}
//			
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		});
//	}
//	
//	public void timelineTweetsFromDB(long since_id, long max_id){
//		ArrayList<Tweet> batch = Tweet.getOrderedTweetsArrayList(since_id, max_id);
//		maxTweetId = updateMaxId(batch);
//		addAll(batch);
//	}
//	
//	
//	private long updateMaxId(ArrayList<Tweet> tweets) {
//		//iterate through tweets to find new maxId
//		long maxId = 0;
//		for(Tweet tweet : tweets){
//			long thisTweetId = tweet.getUid(); 
//			maxId = (tweet.getUid() < maxTweetId) ? (tweet.getUid() -1) : maxTweetId; 
//		}
//		return maxId;
//	}
	
//	public static boolean isConnectivityAvailable(Context ctx){
//		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//		if (activeNetwork != null) {
//			return (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE);
//		} else {
//			return false;
//		}
//	 }

}
