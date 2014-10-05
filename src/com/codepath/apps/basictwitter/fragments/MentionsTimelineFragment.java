package com.codepath.apps.basictwitter.fragments;

import com.loopj.android.http.JsonHttpResponseHandler;


public class MentionsTimelineFragment extends TweetsListFragment {

	@Override
	public void fireClientRequest(long sinceId, long maxId,
			JsonHttpResponseHandler handler) {
		client.getMentionsTimeline(sinceId, maxId, handler);
	}
	
	@Override
	public void fireDbRequest(long sinceId, long maxId) {
		
	}
}
