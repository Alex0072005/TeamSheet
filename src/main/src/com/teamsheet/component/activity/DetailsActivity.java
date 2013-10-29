package com.teamsheet.component.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.teamsheet.component.R;

public class DetailsActivity extends Activity{

	public static final String EXTRA_PLAYER_NAME = "com.teamsheet.component.activity.PlayerName";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		TextView txtPlayer = (TextView)findViewById(R.id.txt_player_name);
		txtPlayer.setText(getIntent().getStringExtra(EXTRA_PLAYER_NAME));
	}
}
