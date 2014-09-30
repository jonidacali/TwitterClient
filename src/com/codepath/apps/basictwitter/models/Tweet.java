package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;

@Table(name = "tweets")
public class Tweet extends Model{
	@Column(name = "body")
	private String body;
	@Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
	@Column(name = "createdAt", index=true)
	private String createdAt;
	@Column(name = "user", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
	private User user;
	@Column(name = "retweetCount")
	private int retweetCount;
	@Column(name = "favoriteCount")
	private int favoriteCount;
	
	public Tweet (){
		super();
	}
	
	public Tweet (int uid, String body, String createdAt, int retweetCount, int favoriteCount, User user){
		super();
		this.uid = uid;
		this.body = body;
		this.createdAt = createdAt;
		this.retweetCount = retweetCount;
		this.favoriteCount = favoriteCount;
		this.user = user;
	}

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
	
	public int getRetweetCount(){
		return retweetCount;
	}
	
	public int getFavoriteCount(){
		return favoriteCount;
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
	
	public void setRetweetCount(int retweetCount){
		this.retweetCount = retweetCount;
	}
	
	public void setFavoriteCount(int favoriteCount){
		this.favoriteCount = favoriteCount;
	}
	
	public static Tweet fromJson(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		//Extract json to populate member vars
		try{
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
			tweet.retweetCount = jsonObject.getInt("retweet_count");
			tweet.favoriteCount = jsonObject.getInt("favorite_count");
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
	
	public static void saveTweetsArrayList(JSONArray jsonArray){
		ArrayList<Tweet> tweets;
		tweets = fromJSONArray(jsonArray);
		for(Tweet t : tweets){
			t.saveTweet();
		}
	}

	private void saveTweet() {
		user.save();
		this.save();
	}
	
}
