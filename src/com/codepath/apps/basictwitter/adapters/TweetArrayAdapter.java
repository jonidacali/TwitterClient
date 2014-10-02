package com.codepath.apps.basictwitter.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	//constructor
	public TweetArrayAdapter(Context context, List<Tweet> tweets){
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		//Get data from position
		Tweet tweet =  getItem(position);
		
		//Find or inflate template
		View v;
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v= inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		//Find views within template
		ImageView ivProfImage = (ImageView) v.findViewById(R.id.ivProfileImg);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvTimePosted = (TextView) v.findViewById(R.id.tvTimePosted);
		TextView tvRetweets = (TextView) v.findViewById(R.id.tvRetweet);
		TextView tvFavorited = (TextView) v.findViewById(R.id.tvLike);
		
		ivProfImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		//populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImgUrl(), ivProfImage);
		tvUserName.setText("@" + tweet.getUser().getScreenName());
		tvName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvRetweets.setText(String.valueOf(tweet.getRetweetCount()));
		tvFavorited.setText(String.valueOf(tweet.getFavoriteCount()));
		tvTimePosted.setText(tweet.getTimePostedShort());
		
		return v;
	}
}
