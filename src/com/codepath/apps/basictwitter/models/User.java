package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name;
	private long uid;
	private String screenName;
	private String profileImgUrl;
	
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

}
