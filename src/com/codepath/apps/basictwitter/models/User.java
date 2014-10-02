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
	@Column(name = "profileImgUrl")
	private String profileImgUrl;
	
	public User(){
		super();
	}
	
	public User (int uid, String name, String screenName, String profileImageUrl){
		super();
		this.uid = uid;
		this.name = name;
		this.screenName =  screenName;
		this.profileImgUrl = profileImageUrl;
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

	public String getProfileImgUrl() {
		return profileImgUrl;
	}
	
	public static User fromJson(JSONObject json) {
		User u = new User();
		try{
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImgUrl = json.getString("profile_image_url");
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
