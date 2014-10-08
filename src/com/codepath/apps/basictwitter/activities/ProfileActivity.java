package com.codepath.apps.basictwitter.activities;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.helpers.TwitterClient;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	User user = new User();
	TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);		
		if(getIntent().getExtras() != null){
			user = getIntent().getExtras().getParcelable("user");
			loadProfileInfo(user);	
		} else {
			client = TwitterApplication.getRestClient();
			client.getCurrentUser(new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONObject json) {
					user = User.fromJson(json);
					loadProfileInfo(user);	
				}
//				@Override
//				public void onSuccess(int statusCode, JSONArray json) {
//					// TODO Auto-generated method stub
//					Log.d("debug", Integer.toString(statusCode));
//					Log.d("debug", json.toString());
//				}
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
    				Log.d("debug", s.toString());
				}
				
				@Override
				protected void handleFailureMessage(Throwable e, String s) {
					Toast.makeText(ProfileActivity.this, "error: " + s, Toast.LENGTH_LONG).show();
				}
			});
		}
	}
	
	public void loadProfileInfo(User user){
			populateProfileHeader(user);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			UserTimelineFragment fragment = UserTimelineFragment.newInstance(user);
			ft.replace(R.id.fragmentUserTimeline, fragment);
			ft.commit();
	} 
	
	
//	public void loadProfileInfo() {
//		TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler(){
//			@Override
//			public void onSuccess(JSONObject json) {
//				User u = User.fromJson(json);
//				populateProfileHeader(u);
//			}
//		});
//	}


	protected void populateProfileHeader(User u) {
		getActionBar().setTitle("@" +u.getScreenName());

		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvTweets = (TextView) findViewById(R.id.tvTweetsCount);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfImage = (ImageView) findViewById(R.id.ivProfImage);
		
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvFollowers.setText(Integer.valueOf(u.getFollowersCount()) + " " + getResources().getText(R.string.followers_label));
		tvFollowing.setText(Integer.valueOf(u.getFollowingCount()) + " " + getResources().getText(R.string.following_label));
		tvTweets.setText(Integer.valueOf(u.getTweetsCount()) + " " + getResources().getText(R.string.tweets_label));
		
		ivProfImage.setImageResource(android.R.color.transparent);
		ImageLoader.getInstance().displayImage(u.getProfileImgUrl(), ivProfImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
