package com.zhenghongli.activity;

import com.zhenghongli.Flip3dView.Flip3dView.OnFlipViewClick;
import com.zhenghongli.layout.MainLayout;

import android.R.menu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	private MainLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(mainLayout = new MainLayout(this));

		mainLayout.getFlip3dView().setFlip3d(1000, 0, 90, 1000, 0, 90);
		mainLayout.getFlip3dView().setOnFlipViewClick(new OnFlipViewClick() {

			public void onClick(View view) {
				view.setBackgroundColor(R.drawable.ic_launcher);
			}
		});
	}

}
