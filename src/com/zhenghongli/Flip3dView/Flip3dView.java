package com.zhenghongli.Flip3dView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhenghongli.flip3d.Flip3d;
import com.zhenghongli.flip3d.Flip3d.OnFlip3dAnimationEnd;
import com.zhenghongli.flip3d.Flip3d.OnFlipClick;


public class Flip3dView extends RelativeLayout {

	public interface OnFlipViewClick {
		public void onClick(View view);
	}

	public interface OnFlip3dViewFinish{
		public void onAnimationEnd(View view);
	}
	
	private boolean isFlip = false;

	private OnFlipViewClick onFlipViewClick;

	private Activity activity;

	private Flip3d flip3d;
	
	private OnFlip3dViewFinish onFlip3dViewFinish;

	private ImageView firstImageView, secondImageView;

	public Flip3dView(Context context) {
		super(context);
		activity = (Activity) context;
		flip3d = new Flip3d();
		inti();
	}

	private void inti() {
		setFirstImageView();
		setSecondImageView();
		flip3d.setImage1(firstImageView);
		flip3d.setImage2(secondImageView);
		flip3d.setOnFlip3dAnimationEnd(new OnFlip3dAnimationEnd() {
			
			@Override
			public void onAnimationEnd() {
				onFlip3dViewFinish.onAnimationEnd(Flip3dView.this);
			}
		});
		secondImageView.setVisibility(View.GONE);
	}

	public void setOnFlip3dViewFinish(OnFlip3dViewFinish onFlip3dViewFinish) {
		this.onFlip3dViewFinish = onFlip3dViewFinish;
	}
	
	private void setSecondImageView() {
		firstImageView = new ImageView(activity);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		firstImageView.setLayoutParams(lp);
		this.addView(firstImageView);
	}

	private void setFirstImageView() {
		secondImageView = new ImageView(activity);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		secondImageView.setLayoutParams(lp);
		this.addView(secondImageView);
	}

	public void setFirstViewBackground(int resource) {
		firstImageView.setBackgroundResource(resource);
	}

	public void setSecondViewBackground(int resource) {
		secondImageView.setBackgroundResource(resource);
	}

	public ImageView getFirstImageView() {
		return firstImageView;
	}

	public ImageView getSecondImageView() {
		return secondImageView;
	}

	public void setFlip3d(final int firstDuration, final int firstFromDegree,
			final int firstToDegree, final int secondDuration,
			final int secondFromDegree, final int secondToDegree) {
		flip3d.setFirstDuration(firstDuration);
		flip3d.setFirstFromDegree(firstFromDegree);
		flip3d.setFirstToDegree(firstToDegree);
		flip3d.setSecondDuration(secondDuration);
		flip3d.setSecondFromDegree(secondFromDegree);
		flip3d.setSecondToDegree(secondToDegree);
		flip3d.setIsNormalFlip(false);
	}

	public void setFlip(int duration, float xStartDegree, float xEndDegree,
			float yStartDegree, float yEndDegree, float zStartDegree,
			float zEndDegree) {
		flip3d.setDuration(duration);
		flip3d.setxEndDegree(xEndDegree);
		flip3d.setxStartDegree(xStartDegree);
		flip3d.setyEndDegree(yEndDegree);
		flip3d.setyStartDegree(yStartDegree);
		flip3d.setzEndDegree(zEndDegree);
		flip3d.setzStartDegree(zStartDegree);
		flip3d.setIsNormalFlip(true);
	}

	public void run() {
		
//		Handler handler=new Handler();
//		handler.post(new Runnable() {
//			
//			@Override
//			public void run() {
				flip3d.run();

//				while (!flip3d.getFlipFinish()) {
//		
//				}
//				
//				isFlip=true;
//		
//				flip3d.setFlipFinish(false);
				
//			}
//		});

		
	}

	public void setIsFlip(boolean isFlip) {
		this.isFlip = isFlip;
	}

	public boolean getIsFlip() {
		return isFlip;
	}

	public void setFlipMode(Flip3d.FlipMode flipMode) {
		flip3d.setFlipMode(flipMode);
	}

	public void setOnFlipViewClick(OnFlipViewClick onFlipViewClick) {
		this.onFlipViewClick = onFlipViewClick;
		flip3d.setOnFlipViewClick(new OnFlipClick() {

			public void onClick(View view) {
				// TODO Auto-generated method stub
				Flip3dView.this.onFlipViewClick.onClick(view);
			}
		});
	}

}
