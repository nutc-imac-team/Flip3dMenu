package com.zhenghongli.flip3d;

import com.zhenghongli.flip3d.SwapViews.OnAnimationFinish;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Flip3d {
	
	public interface OnFlip3dAnimationEnd{
		public void onAnimationEnd();
	}

	public enum FlipMode {
		LEFT_SIDE, RIGHT_SIDE, BOTTOM_SIDE, TOP_SIDE
	}

	public interface OnFlipClick {
		public void onClick(View view);
	}
	
	private OnFlip3dAnimationEnd onFlip3dAnimationEnd;

	private boolean isNormalFlip = true;
	private boolean flipFinish = false;
	private View firstView;
	private View secondView;

	private FlipMode flipMode;

	private int firstDuration, secondDuration, firstFromDegree, firstToDegree,
			secondFromDegree, secondToDegree;

	private int duration;
	private float xStartDegree, xEndDegree, yStartDegree, yEndDegree,
			zStartDegree, zEndDegree;

	private SwapViews swapViews;

	private boolean isFirstImage = true;

	private OnFlipClick onFlipClick;
	
	//可設定角度與軸的的3d flip

	private void applyRotation(int duration, float xStartDegree,
			float xEndDegree, float yStartDegree, float yEndDegree,
			float zStartDegree, float zEndDegree) {

		float preTransX = 0;
		float preTransY = 0;
		float postTransX = 0;
		float postTransY = 0;

		// Find the center of image
		switch (flipMode) {
		case TOP_SIDE:
			preTransX = -firstView.getWidth() / 2.0f;
			preTransY = 0;
			postTransX = firstView.getWidth() / 2.0f;
			postTransY = 0;

			break;
		case BOTTOM_SIDE:
			preTransX = -firstView.getWidth() / 2.0f;
			preTransY = -firstView.getHeight();
			postTransX = firstView.getWidth() / 2.0f;
			postTransY = firstView.getHeight();

			break;
		case LEFT_SIDE:
			preTransX = 0;
			preTransY = -firstView.getHeight() / 2.0f;
			postTransX = 0;
			postTransY = firstView.getHeight() / 2.0f;

			break;
		case RIGHT_SIDE:
			preTransX = -firstView.getWidth();
			preTransY = -firstView.getHeight() / 2.0f;
			postTransX = firstView.getWidth();
			postTransY = firstView.getHeight() / 2.0f;

			break;

		default:
			preTransX = -firstView.getWidth() / 2.0f;
			preTransY = -firstView.getHeight() / 2.0f;
			postTransX = firstView.getWidth() / 2.0f;
			postTransY = firstView.getHeight() / 2.0f;

			break;
		}

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation

		Flip3dAnimation rotation = new Flip3dAnimation(xStartDegree,
				xEndDegree, yStartDegree, yEndDegree, zStartDegree, zEndDegree,
				preTransX, preTransY, postTransX, postTransY);

		rotation.setDuration(duration);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				Log.e("test", "onAnimationStart");

			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				Log.e("test", "onAnimationRepeat");
			}

			public void onAnimationEnd(Animation animation) {
				Log.e("test", "onAnimationEnd");
				flipFinish=true;
				onFlip3dAnimationEnd.onAnimationEnd();
			}
		});
		
		firstView.startAnimation(rotation);
	}
	
	//已中心翻轉的3d flip

	private void applyRotation(float start, float end) {
		// Find the center of image
		float centerX = firstView.getWidth() / 2.0f;
		float centerY = firstView.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Flip3dAnimation rotation = new Flip3dAnimation(start, end,
				centerX, centerY);
		rotation.setDuration(firstDuration);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation animation) {

				firstView.post(swapViews = new SwapViews(isFirstImage,
						firstView, secondView, secondDuration, firstFromDegree,
						firstToDegree, secondFromDegree, secondToDegree));
				swapViews.setOnAnimationFinish(new OnAnimationFinish() {

					public void onAnimationFinish(boolean mIsFirstView) {
						isFirstImage = !isFirstImage;
					}
				});
				onFlip3dAnimationEnd.onAnimationEnd();
			}
		});

		if (isFirstImage) {
			firstView.startAnimation(rotation);
		} else {
			secondView.startAnimation(rotation);
		}

	}
	
	public void setOnFlip3dAnimationEnd(
			OnFlip3dAnimationEnd onFlip3dAnimationEnd) {
		this.onFlip3dAnimationEnd = onFlip3dAnimationEnd;
	}
	
	public boolean getFlipFinish(){
		return flipFinish;
	}
	
	public void setFlipFinish(boolean flipFinish) {
		this.flipFinish = flipFinish;
	}
	
	public void run(){
		applyRotation(duration, xStartDegree, xEndDegree,
				yStartDegree, yEndDegree, zStartDegree, zEndDegree);
	}

	public void setFlipMode(FlipMode flipMode) {
		this.flipMode = flipMode;
	}

	public void setIsNormalFlip(boolean isNormalFlip) {
		this.isNormalFlip = isNormalFlip;
	}

	public void setOnFlipViewClick(OnFlipClick onFlipClick) {

		this.onFlipClick = onFlipClick;

		firstView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

//				if (isNormalFlip) {
//					applyRotation(duration, xStartDegree, xEndDegree,
//							yStartDegree, yEndDegree, zStartDegree, zEndDegree);
//				} else {
//					if (isFirstImage) {
//						applyRotation(firstFromDegree, firstToDegree);
//					} else {
//						applyRotation(secondFromDegree, secondToDegree);
//					}
//				}
				Flip3d.this.onFlipClick.onClick(view);
			}
		});
	}

	public void setImage1(ImageView firstImageView) {
		firstView = firstImageView;
	}

	public void setImage2(ImageView secondImageView) {
		secondView = secondImageView;
	}

	public void setFirstDuration(int firstDuration) {
		this.firstDuration = firstDuration;
	}

	public void setFirstFromDegree(int firstFromDegree) {
		this.firstFromDegree = firstFromDegree;
	}

	public void setFirstToDegree(int firstToDegree) {
		this.firstToDegree = firstToDegree;
	}

	public void setSecondDuration(int secondDuration) {
		this.secondDuration = secondDuration;
	}

	public void setSecondFromDegree(int secondFromDegree) {
		this.secondFromDegree = secondFromDegree;
	}

	public void setSecondToDegree(int secondToDegree) {
		this.secondToDegree = secondToDegree;
	}

	public void setxEndDegree(float xEndDegree) {
		this.xEndDegree = xEndDegree;
	}

	public void setxStartDegree(float xStartDegree) {
		this.xStartDegree = xStartDegree;
	}

	public void setyEndDegree(float yEndDegree) {
		this.yEndDegree = yEndDegree;
	}

	public void setyStartDegree(float yStartDegree) {
		this.yStartDegree = yStartDegree;
	}

	public void setzEndDegree(float zEndDegree) {
		this.zEndDegree = zEndDegree;
	}

	public void setzStartDegree(float zStartDegree) {
		this.zStartDegree = zStartDegree;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}