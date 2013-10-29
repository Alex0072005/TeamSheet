package com.teamsheet.component;

import com.teamsheet.component.model.Player;

import android.view.View;

/**
 * Interface definition for a callback to be invoked when a player in 
 * FootballFieldView has been clicked.
 * 
 * @author Strup.Alexandr
 */
public interface OnPlayerClickListener {
	void onPlayerClickListener(View view, Player player, boolean isHomeTeam);
}
