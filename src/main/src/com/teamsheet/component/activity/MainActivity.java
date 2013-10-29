package com.teamsheet.component.activity;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamsheet.component.FootballFieldView;
import com.teamsheet.component.OnPlayerClickListener;
import com.teamsheet.component.R;
import com.teamsheet.component.TeamFormationAdapter;
import com.teamsheet.component.model.Player;

public class MainActivity extends Activity {

	class ButtonFormationAdapter extends TeamFormationAdapter {
		
		@Override
		public View getPlayerView(Player player) {
			Button btnPlayer = (Button)getEmptyView();
			btnPlayer.setText(player.getName());
			return btnPlayer;
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		List<Player> homeTeam = getHomeTeam();
		List<Player> awayTeam = getAwayTeam();
		
		ButtonFormationAdapter homeFormation = new ButtonFormationAdapter();
		homeFormation.setTeam(this, R.layout.item_home_player, homeTeam);
		ButtonFormationAdapter awayFormation = new ButtonFormationAdapter();
		awayFormation.setTeam(this, R.layout.item_away_player, awayTeam);
		
		FootballFieldView ffView = (FootballFieldView)findViewById(R.id.football_field);
		ffView.setHomeTeamFormation(homeFormation);
		ffView.setAwayTeamFormation(awayFormation);
		ffView.setOnPlayerClickListener(new OnPlayerClickListener() {
			
			@Override
			public void onPlayerClickListener(View view, Player player, boolean isHomeTeam) {
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
				intent.putExtra(DetailsActivity.EXTRA_PLAYER_NAME, player.getName());
				startActivity(intent);
			}
		});
	}
	
	
	private List<Player> getHomeTeam() {
		Player player1 = new Player();
		player1.setName("Player 1");
		player1.setPositionX(50);
		player1.setPositionY(45);
		
		Player player2 = new Player();
		player2.setName("Player 2");
		player2.setPositionX(13);
		player2.setPositionY(35);
		
		Player player3 = new Player();
		player3.setName("Player 3");
		player3.setPositionX(38);
		player3.setPositionY(35);
		
		Player player4 = new Player();
		player4.setName("Player 4");
		player4.setPositionX(62);
		player4.setPositionY(35);
		
		Player player5 = new Player();
		player5.setName("Player 5");
		player5.setPositionX(87);
		player5.setPositionY(35);
		
		Player player6 = new Player();
		player6.setName("Player 6");
		player6.setPositionX(38);
		player6.setPositionY(25);
		
		Player player7 = new Player();
		player7.setName("Player 7");
		player7.setPositionX(62);
		player7.setPositionY(25);
		
		Player player8 = new Player();
		player8.setName("Player 8");
		player8.setPositionX(25);
		player8.setPositionY(15);
		
		Player player9 = new Player();
		player9.setName("Player 9");
		player9.setPositionX(50);
		player9.setPositionY(15);
		
		Player player10 = new Player();
		player10.setName("Player 10");
		player10.setPositionX(75);
		player10.setPositionY(15);
		
		Player player11 = new Player();
		player11.setName("Player 11");
		player11.setPositionX(50);
		player11.setPositionY(5);
		
		return Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11);
	}
	
	private List<Player> getAwayTeam() {
		Player player1 = new Player();
		player1.setName("Player 1");
		player1.setPositionX(50);
		player1.setPositionY(55);
		
		Player player2 = new Player();
		player2.setName("Player 2");
		player2.setPositionX(13);
		player2.setPositionY(65);
		
		Player player3 = new Player();
		player3.setName("Player 3");
		player3.setPositionX(38);
		player3.setPositionY(65);
		
		Player player4 = new Player();
		player4.setName("Player 4");
		player4.setPositionX(62);
		player4.setPositionY(65);
		
		Player player5 = new Player();
		player5.setName("Player 5");
		player5.setPositionX(87);
		player5.setPositionY(65);
		
		Player player6 = new Player();
		player6.setName("Player 6");
		player6.setPositionX(38);
		player6.setPositionY(75);
		
		Player player7 = new Player();
		player7.setName("Player 7");
		player7.setPositionX(62);
		player7.setPositionY(75);
		
		Player player8 = new Player();
		player8.setName("Player 8");
		player8.setPositionX(25);
		player8.setPositionY(85);
		
		Player player9 = new Player();
		player9.setName("Player 9");
		player9.setPositionX(50);
		player9.setPositionY(85);
		
		Player player10 = new Player();
		player10.setName("Player 10");
		player10.setPositionX(75);
		player10.setPositionY(85);
		
		Player player11 = new Player();
		player11.setName("Player 11");
		player11.setPositionX(50);
		player11.setPositionY(95);
		
		return Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11);
	}

}
