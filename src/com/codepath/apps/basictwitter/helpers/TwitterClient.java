package com.codepath.apps.basictwitter.helpers;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.view.View;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "ixkibFEI43EQxkfttR7ti3E6R";       // Change this
	public static final String REST_CONSUMER_SECRET = "KFZzWcjprkhDcIS03jx8fp4Nyivl6fwfilrWr8oZdWnHMQggUx"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(long sinceId, long maxId, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params =  new RequestParams();
		if (maxId > 0) {
			params.put("max_id", String.valueOf(maxId));
		} else {
			params.put("since_id", String.valueOf(sinceId));
		}
		client.get(apiUrl, params, handler);
	}

	public void getMentionsTimeline(long sinceId, long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params =  new RequestParams();
		if (maxId > 0) {
			params.put("max_id", String.valueOf(maxId));
		} else {
			params.put("since_id", String.valueOf(sinceId));
		}
		client.get(apiUrl, params, handler);
	}
	
	public void getUserTimeline(long sinceId, long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		client.get(apiUrl, null, handler);
	}
	
	public void getCurrentUser(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}
	
	public void postTweet(String status, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params =  new RequestParams();
		params.put("status", status);
		client.post(apiUrl, params, handler);
	}
	
	public void replyToTweet(String status, long inReplyToStatus, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		if(inReplyToStatus != 0){
			params.put("in_reply_to_status_id", String.valueOf(inReplyToStatus));
		}
		params.put("status", status);
		getClient().post(apiUrl, params, handler);
	}

}