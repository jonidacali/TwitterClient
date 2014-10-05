package com.codepath.apps.basictwitter.fragments;

import com.loopj.android.http.JsonHttpResponseHandler;


public class MentionsTimelineFragment extends TweetsListFragment {

	@Override
	public void fireClientRequest(long sinceId, long maxId,
			JsonHttpResponseHandler handler) {
		client.getMentionsTimeline(sinceId, maxId, handler);
		
	}
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//get instance of twitter client
//		customLoadMoreData("MENTIONS", 1,0);
//
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//		    @Override
//		    public void onLoadMore(int page, int totalItemsCount) {
//                 customLoadMoreData("MENTIONS", totalItemsCount, maxTweetId); 
//		    }
//    	});
//	}

	@Override
	public void fireDbRequest(long sinceId, long maxId) {
		
	}
}
