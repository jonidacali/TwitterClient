package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.activities.ShowTweetDetailActivity;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.helpers.TwitterClient;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TweetsListFragment extends Fragment {
	protected TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	protected ListView lvTweets;
	private Context context;
	private final int REQUEST_CODE_REPLY = 120;
	long maxTweetId = Long.MAX_VALUE;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets =  new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApplication.getRestClient();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		context = getActivity();
		customLoadMoreData(1,0);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
                 customLoadMoreData(totalItemsCount, maxTweetId); 
		    }
    	});
		
		setupViewListener();
		return v;
	}
	
	private void setupViewListener(){
		lvTweets.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long rowId) {
				Tweet tweet = ((Tweet) parent.getItemAtPosition(position));
				Intent i = new Intent(context, ShowTweetDetailActivity.class);	
				i.putExtra("tweet", tweet);
				startActivityForResult(i, REQUEST_CODE_REPLY);
			}
		});
	}
	
	public void customLoadMoreData(long sinceId, long maxId) {    	
    	if(isConnectivityAvailable(getActivity())){
    		this.fireClientRequest(sinceId, maxId, new JsonHttpResponseHandler(){
    			@Override
    			public void onSuccess(JSONArray json) {
    				ArrayList<Tweet> batch = Tweet.fromJSONArray(json);
    				maxTweetId = updateMaxId(batch);
    				addAll(batch);
    				//Save tweets in db
    				Tweet.saveTweetsArrayList(json);
    			}
    			
    			@Override
    			public void onFailure(Throwable e, String s) {
    				Log.d("debug", e.toString());
    				Log.d("debug", s.toString());
    			}	
    		});
		} else {
			this.fireDbRequest(sinceId, maxId);
		}
	}

	private long updateMaxId(ArrayList<Tweet> tweets) {
		//iterate through tweets to find new maxId
		long maxId = 0;
		for(Tweet tweet : tweets){
			maxId = (tweet.getUid() < maxTweetId) ? (tweet.getUid() -1) : maxTweetId; 
		}
		return maxId;
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
	
	public void addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}
	
	public void addTweet(Tweet tweet){
		aTweets.insert(tweet, 0);
	}
	
	abstract public void fireClientRequest(long sinceId, long maxId, JsonHttpResponseHandler handler);

	abstract public void fireDbRequest(long sinceId, long maxId);
}
