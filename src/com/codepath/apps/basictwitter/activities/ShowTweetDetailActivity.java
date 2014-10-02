package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShowTweetDetailActivity extends Activity{
	private TextView tvName;
	private TextView tvUserName;
	private TextView tvBody;
	private TextView tvTimePosted;
	private ImageView ivProfImg;
	private EditText etReply;
	private Tweet originalTweet = new Tweet();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_item);
		
		getActionBar().hide();
		
		tvName = (TextView) findViewById(R.id.tvName);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		ivProfImg = (ImageView) findViewById(R.id.ivProfileImg);
		tvBody = (TextView) findViewById(R.id.tvBody);
		tvTimePosted = (TextView) findViewById(R.id.tvTimePosted);
		ivProfImg = (ImageView) findViewById(R.id.ivProfileImg);
		etReply = (EditText)findViewById(R.id.etReply);
		
		
		//get tweet from intent
		originalTweet = (Tweet) getIntent().getExtras().getParcelable("tweet");
		//populate view 		
		ivProfImg.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(originalTweet.getUser().getProfileImgUrl(), ivProfImg);
		
		tvName.setText(originalTweet.getUser().getName());
		tvUserName.setText("@" + originalTweet.getUser().getScreenName());
		tvBody.setText(originalTweet.getBody());
		etReply.setText("@" + originalTweet.getUser().getScreenName());
		tvTimePosted.setText(originalTweet.getTimePostedLong());
	}
	
	
	public void replyTweet(View v){
		String status = etReply.getText().toString();  
        Intent returnIntent=new Intent();  
        if(status != null){
        	int msgLength = status.length();
        	if(msgLength > 0 && msgLength <= 140) {
	        	returnIntent.putExtra("status",status);
	        	returnIntent.putExtra("in_reply_to_status_id", originalTweet.getUid());
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