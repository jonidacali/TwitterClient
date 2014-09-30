package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "users")
public class User extends Model{
	@Column(name = "name")
	private String name;
	@Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
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

}
