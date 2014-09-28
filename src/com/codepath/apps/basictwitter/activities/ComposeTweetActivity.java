package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.basictwitter.R;

public class ComposeTweetActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_tweet);
		getActionBar().hide();
		
//		itemToBeEdited = (EditText) findViewById(R.id.todoItem);	
//        updateButton=(Button)findViewById(R.id.btnUpdateItem);  		
//        itemToBeEdited.setText(String.valueOf(itemValue));
//        position = getIntent().getExtras().getInt(ITEM_POSITION);
	}
}
