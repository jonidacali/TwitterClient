package com.codepath.apps.basictwitter.fragments;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

	@Override
	public void fireClientRequest(long sinceId, long maxId,
			JsonHttpResponseHandler handler) {
		client.getUserTimeline(sinceId, maxId, handler);
	}

	public void fireClientRequest(long sinceId, long maxId, long userId,
			JsonHttpResponseHandler handler) {
		client.getUserTimeline(sinceId, maxId, userId, handler);
	}
	
	@Override
	public void fireDbRequest(long sinceId, long maxId) {
		// TODO Auto-generated method stub

	}

}
