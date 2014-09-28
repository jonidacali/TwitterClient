package com.codepath.apps.basictwitter.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
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
	public static final long MIN_IN_MILLIS = 60*1000;
	public static final long WEEK_IN_MILLIS = 7*24*60*60*1000;
	public static final int FORMAT_ABBREV_RELATIVE = 262144;
	
	//constructor
	public TweetArrayAdapter(Context context, List<Tweet> tweets){
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Date now = new Date(System.currentTimeMillis());		
		
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
		
		ivProfImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		//populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImgUrl(), ivProfImage);
		tvUserName.setText("@" + tweet.getUser().getScreenName());
		tvName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		//tvTimePosted.setText(DateUtils.getRelativeTimeSpanString(tweet.getCreatedAt()*1000, now.getTime(), MIN_IN_MILLIS));

		DateFormat formatter = new SimpleDateFormat("EEE LLL d k:m:s zzz yyyy");
		Date date;
		try {
			date = formatter.parse(tweet.getCreatedAt());
			tvTimePosted.setText(DateUtils.getRelativeTimeSpanString(date.getTime(), now.getTime(), MIN_IN_MILLIS, FORMAT_ABBREV_RELATIVE));
		} catch (ParseException e) {
			tvTimePosted.setVisibility(v.INVISIBLE);
			e.printStackTrace();
		} 
		
		return v;
		
	}

	
}
