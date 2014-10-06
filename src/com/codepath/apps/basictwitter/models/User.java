package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "users")
public class User extends Model implements Parcelable{
	@Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
	@Column(name = "name")
	private String name;
	@Column(name = "screenName")
	private String screenName;
	@Column(name = "tagline")
	private String tagline;
	@Column(name = "profileImgUrl")
	private String profileImgUrl;
	@Column(name = "followersCount")
	private int followersCount;
	@Column(name = "followingCount")
	private int followingCount;

	public User(){
		super();
	}
	
	public User (int uid, String name, String screenName, String tagline, int followersCount, int followingCount, String profileImageUrl){
		super();
		this.uid = uid;
		this.name = name;
		this.screenName =  screenName;
		this.tagline = tagline;
		this.profileImgUrl = profileImageUrl;
		this.followersCount = followersCount;
		this.followingCount = followingCount;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}
	public String getTagline() {
		return tagline;
	}

	public String getProfileImgUrl() {
		return profileImgUrl;
	}
	
	public int getFollowersCount() {
		return followersCount;
	}
	
	public int getFollowingCount() {
		return followingCount;
	}
	public static User fromJson(JSONObject json) {
		User u = new User();
		try{
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.tagline =  json.getString("description");
			u.profileImgUrl = json.getString("profile_image_url");
			u.followersCount = json.getInt("followers_count");
			u.followingCount = json.getInt("friends_count");
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		return u;
	}

	
	 protected User(Parcel in) {
		 uid = in.readLong();
		 name = in.readString();
		 screenName = in.readString();
		 profileImgUrl= in.readString();
	 }
	 
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(uid);		
		dest.writeString(name);
		dest.writeString(screenName);
		dest.writeString(profileImgUrl);
		
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}
		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
