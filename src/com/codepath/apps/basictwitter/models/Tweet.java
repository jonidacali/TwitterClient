package com.codepath.apps.basictwitter.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

@Table(name = "tweets")
public class Tweet extends Model implements Parcelable{
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
	@Column(name = "retweeted")
	private boolean retweeted;
	@Column(name = "favoriteCount")
	private int favoriteCount;
	@Column(name = "favorited")
	private boolean favorited;
	
	private static int TOTAL_TWEETS = 20;
	
	public Tweet (){
		super();
	}
	
	public Tweet (int uid, String body, String createdAt, int retweetCount, int favoriteCount, boolean retweeted, boolean favorited, User user){
		super();
		this.uid = uid;
		this.body = body;
		this.createdAt = createdAt;
		this.retweetCount = retweetCount;
		this.retweeted = retweeted;
		this.favoriteCount = favoriteCount;
		this.favorited = favorited;
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
	
	public static ArrayList<Tweet> getOrderedTweetsArrayList(long since_id, long max_id) {
        String queryArgs = "";
        
        if(since_id > 0){
        	queryArgs += ("uid > " + since_id); 
        }
        if(max_id > 0){
        	if(queryArgs.length() > 0){
        		queryArgs += (" AND ");
        	}
        	queryArgs +=("uid <= " + max_id);
        }
        	
        From query = new Select()
          			.from(Tweet.class)
          			.where(queryArgs)
          			.orderBy("uid DSC")
          			.limit(TOTAL_TWEETS);
          			
		List<Tweet> tweets = query.execute();
		return new ArrayList<Tweet>(tweets);
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(body);
		dest.writeLong(uid);
		dest.writeParcelable(user, flags);
		dest.writeString(createdAt);
		dest.writeInt(retweeted ? 1 : 0);
		dest.writeInt(favorited ? 1 : 0);
		dest.writeInt(retweetCount);
		dest.writeInt(favoriteCount);
		
	}

	public CharSequence getTimePostedLong() {
		Date now = new Date(System.currentTimeMillis());		
		DateFormat formatter = new SimpleDateFormat("EEE LLL d k:m:s zzz yyyy");
		Date date;
		try {
			date = formatter.parse(this.getCreatedAt());
			String fullDate = new SimpleDateFormat("h:mm a - d LLL yyyy").format(date);	
			return fullDate;
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public CharSequence getTimePostedShort() {
		Date now = new Date(System.currentTimeMillis());		
		DateFormat formatter = new SimpleDateFormat("EEE LLL d k:m:s zzz yyyy");
		Date date;
		formatter.setLenient(true);
		String abrevRelTime = "";
		try {
			date = formatter.parse(this.getCreatedAt());
			String relativeDate = DateUtils.getRelativeTimeSpanString(date.getTime(), now.getTime(), DateUtils.SECOND_IN_MILLIS).toString();
			String[] time;
			time = relativeDate.split(" ");
			
			for(int i=0; i< time.length ; i++){
				abrevRelTime += time[i];
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return abrevRelTime;
	}

	
	protected Tweet(Parcel in) {
		body = in.readString();
		uid = in.readLong();
		user = in.readParcelable(User.class.getClassLoader());
		createdAt = in.readString();
		retweetCount = in.readInt();
		retweeted = in.readInt() == 0 ? false : true;
		favoriteCount = in.readInt();
		favorited = in.readInt() == 0 ? false : true;
		}

	public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
		@Override
		public Tweet createFromParcel(Parcel in) {
			return new Tweet(in);
		}
		@Override
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}
	};
}
