package com.teamsheet.component;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.teamsheet.component.model.Player;

/**
 * A view that shows players on football field. The players
 * come from the {@link TeamFormationAdapter} associated with this view.
 *
 * @attr ref com.teamsheet.component.R.styleable#FootballFieldView_orientation
 * @attr ref com.teamsheet.component.R.styleable#FootballFieldView_horizontalBackground
 * @attr ref com.teamsheet.component.R.styleable#FootballFieldView_verticalBackground
 * 
 * @author Strup.Alexandr
 */
public class FootballFieldView extends FrameLayout {
	
	// Aspect ratio of football field (105x68m)
	private static final float ASPECT_RATIO = 1.544f;	
	private static final int BLANK_RES = 0;
	
	private Map<View, Player> mMapViewPlayer;
	private int mWidth;
	private int mHeight;
	private OnPlayerClickListener mPlayerClickListener;
	private Orientation mOrientation;
	private int mHorizontalBackgroundResId;
	private int mVerticalBackgroundResId;

	public enum Orientation {
		VERTICAL, 
		HORIZONTAL, 
		ROTATABLE;
		
		static Orientation fromInt(int value) {
			switch(value) {
			case 0:
				return VERTICAL;
			case 1:
				return HORIZONTAL;
			case 2:
				return ROTATABLE;
			}
			throw new IllegalArgumentException();
		}
	}
	
	public FootballFieldView(Context context) {
		super(context);
		mMapViewPlayer = new HashMap<View, Player>();;
	}
	
	public FootballFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mMapViewPlayer = new HashMap<View, Player>();
		
		TypedArray attrArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FootballFieldView, 0, 0);
		try {
			mOrientation = Orientation.fromInt(attrArray.getInt(R.styleable.FootballFieldView_orientation, 2));
			setHorizontalBackground(attrArray.getResourceId(R.styleable.FootballFieldView_horizontalBackground, BLANK_RES));
			setVerticalBackground(attrArray.getResourceId(R.styleable.FootballFieldView_verticalBackground, BLANK_RES));
		} finally {
			attrArray.recycle();
		}
	}
	
	private boolean isVertical() {
		return mOrientation == Orientation.VERTICAL
			|| mOrientation == Orientation.ROTATABLE
			&& getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	public void setHomeTeamFormation(TeamFormationAdapter teamFormation) {
		addTeamPlayers(teamFormation, true);
	}
	
	public void setAwayTeamFormation(TeamFormationAdapter teamFormation) {
		addTeamPlayers(teamFormation, false);
	}
	
	private void addTeamPlayers(TeamFormationAdapter teamFormation, final boolean isHomeTeam) {
		if (!teamFormation.isEmpty()) {
			for(int i = 0; i < teamFormation.getCountPlayers(); i++) {
				final Player player = teamFormation.getPlayer(i);
				final View view = teamFormation.getPlayerView(player);
				mMapViewPlayer.put(view, player);
				addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				view.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (mPlayerClickListener != null) {
							mPlayerClickListener.onPlayerClickListener(view, player, isHomeTeam);
						}
					}
				});
				
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// save aspect ratio of football field
		if (isVertical()) {
			int maxWidth = getMeasuredWidth();
			mHeight = getMeasuredHeight();
			mWidth = (int)(mHeight / ASPECT_RATIO);
			if (mWidth > maxWidth) {
				mWidth = maxWidth;
				mHeight = (int)(mWidth * ASPECT_RATIO);
			}
			if (mOrientation == Orientation.ROTATABLE) {
				setBackgroundResource(mVerticalBackgroundResId);
			}
		} else {
			int maxHeight = getMeasuredHeight();
			mWidth = getMeasuredWidth();
			mHeight = (int)(mWidth / ASPECT_RATIO);
			if (mHeight > maxHeight) {
				mHeight = maxHeight;
				mWidth = (int)(mHeight * ASPECT_RATIO);
			}
			if (mOrientation == Orientation.ROTATABLE) {
				setBackgroundResource(mHorizontalBackgroundResId);
			}
		}
		
		setMeasuredDimension(mWidth, mHeight);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		int childCount = getChildCount();
		
		for (int i = 0; i < childCount; i++ ) {
			View playerView = getChildAt(i);
			Player player = mMapViewPlayer.get(playerView);
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)playerView.getLayoutParams();
			if (player != null) {
				
				Point absolutePoint = relativeToAbsolute(player.getPoint());
				Point playerPoint = centeringPlayerView(absolutePoint, playerView);
				
				lp.leftMargin = playerPoint.x;
				lp.topMargin = playerPoint.y;
				lp.width = FrameLayout.LayoutParams.WRAP_CONTENT;
				lp.height = FrameLayout.LayoutParams.WRAP_CONTENT;
			}			
		}
		
	}
	
	private Point relativeToAbsolute(Point relativePoint) {
		int absoluteX, absoluteY;
		if (isVertical()) {
			absoluteX = relativePoint.x * mWidth / 100;
			absoluteY = relativePoint.y * mHeight / 100;
		} else {
			absoluteX = relativePoint.y * mWidth / 100;
			absoluteY = (100 - relativePoint.x) * mHeight / 100;
		}
		return new Point(absoluteX, absoluteY);
	}
	
	private Point centeringPlayerView(Point absolutePosition, View playerView) {
		int playerWidth = playerView.getMeasuredWidth();
		int playerHeight = playerView.getMeasuredHeight();
		
		Point resultPoint = new Point(0,0);
		resultPoint.x = absolutePosition.x - playerWidth / 2;
		resultPoint.y = absolutePosition.y - playerHeight / 2;
		
		//don't get out of bounds
		resultPoint.x = Math.max(resultPoint.x, 0);
		resultPoint.y = Math.max(resultPoint.y, 0);
		resultPoint.x = Math.min(resultPoint.x, mWidth - playerWidth);
		resultPoint.y = Math.min(resultPoint.y, mHeight - playerHeight);
		
		return resultPoint;
	}

	public int getHorizontalBackground() {
		return mHorizontalBackgroundResId;
	}

	public void setHorizontalBackground(int horizontalBackground) {
		this.mHorizontalBackgroundResId = horizontalBackground;
		if (horizontalBackground != BLANK_RES && mOrientation == Orientation.HORIZONTAL) {
			setBackgroundResource(horizontalBackground);
		}
	}

	public int getVerticalBackground() {
		return mVerticalBackgroundResId;
	}

	public void setVerticalBackground(int verticalBackground) {
		this.mVerticalBackgroundResId = verticalBackground;
		if (verticalBackground != BLANK_RES && mOrientation == Orientation.VERTICAL) {
			setBackgroundResource(verticalBackground);
		}
	}

	public OnPlayerClickListener getOnPlayerClickListener() {
		return mPlayerClickListener;
	}

	public void setOnPlayerClickListener(OnPlayerClickListener playerClickListener) {
		this.mPlayerClickListener = playerClickListener;
	}
	
	
	
	
	

}
