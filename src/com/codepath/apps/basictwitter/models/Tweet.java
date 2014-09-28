package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	private String body;
	private long uid;
	private String createdAt;
	private User user;

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static Tweet fromJson(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		//Extract json to populate member vars
		try{
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
			
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	

	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		
		for (int i = 0; i < jsonArray.length() ; i++){
			JSONObject tweetJson = null;
			try{
				tweetJson =  jsonArray.getJSONObject(i);
			} catch (JSONException e){
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	
}
