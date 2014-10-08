package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	private User user;
	
	public static UserTimelineFragment newInstance(User user) {
		UserTimelineFragment listFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        listFragment.setArguments(args);
        return listFragment;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getArguments().getParcelable("user") != null){
			user = getArguments().getParcelable("user");
		} else {
			user = null;
		}
	}
	
	@Override
	public void fireClientRequest(long sinceId, long maxId,
			JsonHttpResponseHandler handler) {
		client.getUserTimeline(sinceId, maxId, user, handler);
	}

	@Override
	public void fireDbRequest(long sinceId, long maxId) {

	}

}
