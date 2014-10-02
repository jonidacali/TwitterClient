package com.codepath.apps.basictwitter.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity{
	private EditText etTweetBody;
	private TextView tvCurrentName;
	private TextView tvCurrentUserName;
	private ImageView ivCurrentProfImg;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_tweet);
		getActionBar().hide();
		etTweetBody = (EditText) findViewById(R.id.etTweetBody);
		tvCurrentName = (TextView) findViewById(R.id.tvCurrentName);
		tvCurrentUserName = (TextView) findViewById(R.id.tvCurrentUserName);
		ivCurrentProfImg = (ImageView) findViewById(R.id.ivCurrentProfImg);
		
		TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				User currentUser =  User.fromJson(json);
				tvCurrentName.setText(currentUser.getName());
				tvCurrentUserName.setText("@" + currentUser.getScreenName());

				ivCurrentProfImg.setImageResource(android.R.color.transparent);
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.displayImage(currentUser.getProfileImgUrl(), ivCurrentProfImg);
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject error) {
				e.printStackTrace();
				Log.e("Error", error.toString());
			}
		});
	}
	
	public void createTweet(View v){
		String status = etTweetBody.getText().toString();  
        Intent returnIntent=new Intent();  
        if(status != null){
        	int msgLength = status.length();
        	if(msgLength > 0 && msgLength <= 140) {
	        	returnIntent.putExtra("status",status);
	        	setResult(RESULT_OK,returnIntent);  
	        	finish();//finishing activity 
        	} else if (msgLength > 140){
        		Toast.makeText(this, getResources().getText(R.string.label_no_tweet), Toast.LENGTH_SHORT).show();
        	}
		} else {
        	Toast.makeText(this, getResources().getText(R.string.label_no_tweet), Toast.LENGTH_SHORT).show();
        } 
	}
}
