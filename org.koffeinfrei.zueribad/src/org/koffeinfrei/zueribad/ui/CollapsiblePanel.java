package org.koffeinfrei.zueribad.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import org.koffeinfrei.zueribad.R;

public class CollapsiblePanel extends LinearLayout {
	private int speed = 300;
	private Animation collapse = null;
	private Animation expand = null;

	public CollapsiblePanel(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SlidingPanel, 0, 0);

		speed = a.getInt(R.styleable.SlidingPanel_speed, speed);

		a.recycle();

		collapse = AnimationUtils.loadAnimation(context, R.anim.collapse);
		expand = AnimationUtils.loadAnimation(context, R.anim.expand);
		
		setVisibility(GONE);
	}

	public void toggle() {
		AnimationSet set = new AnimationSet(true);

		if (getVisibility() == View.GONE) {
			set.addAnimation(expand);
			set.setAnimationListener(showListener);
		} 
		else {
			set.addAnimation(collapse);
			set.setAnimationListener(hideListener);
		}

		set.setInterpolator(new AccelerateInterpolator(1.0f));
		set.setFillAfter(true);
		set.setFillBefore(true);
		set.setFillEnabled(true);
		startAnimation(set);
	}

	Animation.AnimationListener hideListener = new Animation.AnimationListener() {
		public void onAnimationEnd(Animation animation) {
			ViewGroup.LayoutParams params = getLayoutParams();
			params.height = 0;
			setLayoutParams(params);
			setVisibility(View.GONE);
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
		}
	};
	
	Animation.AnimationListener showListener = new Animation.AnimationListener() {
		public void onAnimationEnd(Animation animation) {
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
			ViewGroup.LayoutParams params = getLayoutParams();
			params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			setLayoutParams(params);
			setVisibility(View.VISIBLE);
		}
	};

}
