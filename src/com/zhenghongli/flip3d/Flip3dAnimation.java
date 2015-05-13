package com.zhenghongli.flip3d;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Flip3dAnimation extends Animation {
	private float mYorderFromDegrees=0;
	private float mYorderToDegrees=0;
	private float mXorderFromDegrees=0;
	private float mXorderToDegrees=0;
	private float mZorderFromDegrees=0;
	private float mZorderToDegrees=0;
	private float mPreTransX=0;
	private float mPreTransY=0;
	private float mPostTransX=0;
	private float mPostTransY=0;
	private Camera mCamera;

	public Flip3dAnimation(float YFromDegrees, float YToDegrees, float centerX,
			float centerY) {
		mYorderFromDegrees = YFromDegrees;
		mYorderToDegrees = YToDegrees;
		mPreTransX = -centerX;
		mPreTransY = -centerY;
		mPostTransX = centerX;
		mPostTransY = centerY;
	}

	public Flip3dAnimation(float XFromDegrees, float XToDegrees,
			float YFromDegrees, float YToDegrees, float ZFromDegrees,
			float ZToDegrees, float preTransX, float preTransY,
			float postTransX, float postTransY) {
		mYorderFromDegrees = YFromDegrees;
		mYorderToDegrees = YToDegrees;
		mXorderFromDegrees = XFromDegrees;
		mXorderToDegrees = XToDegrees;
		mZorderFromDegrees = ZFromDegrees;
		mZorderToDegrees = ZToDegrees;
		mPreTransX = preTransX;
		mPreTransY = preTransY;
		mPostTransX = postTransX;
		mPostTransY = postTransY;

	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float YfromDegrees = mYorderFromDegrees;
		float Ydegrees = YfromDegrees
				+ ((mYorderToDegrees - YfromDegrees) * interpolatedTime);

		final float XfromDegrees = mXorderFromDegrees;
		float Xdegrees = XfromDegrees
				+ ((mXorderToDegrees - XfromDegrees) * interpolatedTime);

		final float ZfromDegrees = mZorderFromDegrees;
		float Zdegrees = ZfromDegrees
				+ ((mZorderToDegrees - ZfromDegrees) * interpolatedTime);

		final Camera camera = mCamera;

		final Matrix matrix = t.getMatrix();

		camera.save();

		camera.rotateY(Ydegrees);
		camera.rotateX(Xdegrees);
		camera.rotateZ(Zdegrees);

		camera.getMatrix(matrix);
		camera.restore();

		matrix.preTranslate(mPreTransX, mPreTransY);
		matrix.postTranslate(mPostTransX, mPostTransY);

	}

}