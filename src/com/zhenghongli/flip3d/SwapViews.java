package com.zhenghongli.flip3d;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public final class SwapViews implements Runnable {
	
	public interface OnAnimationFinish{
		public void onAnimationFinish(boolean mIsFirstView);
	}
	
	private boolean mIsFirstView;
	private OnAnimationFinish onAnimationFinish;

	int duration;
	
	private float firstFromDegrees, firstToDegrees, secondFromDegrees, secondFirstToDegrees;
	
	private View image1;
	private View image2;

	public SwapViews(boolean isFirstView, View firstView, View secondView,int duration,float firstFromDegrees, float firsTtoDegrees,float secondFromDegrees, float secondFirstToDegrees) {
		mIsFirstView = isFirstView;
		this.image1 = firstView;
		this.image2 = secondView;
		this.duration=duration;
		this.firstFromDegrees=firstFromDegrees;
		this.firstToDegrees=firsTtoDegrees;
		this.secondFromDegrees=secondFromDegrees;
		this.secondFirstToDegrees=secondFirstToDegrees;
	}

	public void run() {
		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;
		Flip3dAnimation rotation;

		if (mIsFirstView) {
			image1.setVisibility(View.GONE);
			image2.setVisibility(View.VISIBLE);
			image2.requestFocus();

			rotation = new Flip3dAnimation(-firstToDegrees, firstFromDegrees, centerX, centerY);
		} else {
			image2.setVisibility(View.GONE);
			image1.setVisibility(View.VISIBLE);
			image1.requestFocus();

			rotation = new Flip3dAnimation(-secondFirstToDegrees, secondFromDegrees, centerX, centerY);
		}

		rotation.setDuration(duration);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new DecelerateInterpolator());

		if (mIsFirstView) {
			image2.startAnimation(rotation);
		} else {
			image1.startAnimation(rotation);
		}
		onAnimationFinish.onAnimationFinish(mIsFirstView);
	}

	public void setOnAnimationFinish(OnAnimationFinish onAnimationFinish) {
		this.onAnimationFinish=onAnimationFinish;

	}
}