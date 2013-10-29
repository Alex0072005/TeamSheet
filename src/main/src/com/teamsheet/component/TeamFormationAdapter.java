package com.teamsheet.component;

import java.util.List;

import com.teamsheet.component.model.Player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Common base class that can be used in {@link FootballFieldView} for showing 
 * player's view. TeamFormationAdapter handles the players of one team.
 * 
 * @author Strup.Alexandr
 */
public abstract class TeamFormationAdapter {
	
	private List<Player> mTeam;
	private int mPlayerViewResId;
	private LayoutInflater mInflater;
	
	public void setTeam(Context context, int playerViewResId, List<Player> players) {
		this.mTeam = players;
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mPlayerViewResId = playerViewResId;
	}
	
	
	protected Player getPlayer(int index) {
		return mTeam.get(index);
	}
	
	protected int getCountPlayers() {
		return mTeam.size();
	}
	
	protected boolean isEmpty() {
		return mTeam == null || mTeam.size() < 1;
	}
	
	public View getEmptyView() {
		return mInflater.inflate(mPlayerViewResId, null);
	}
	
	public abstract View getPlayerView(Player player);

}
