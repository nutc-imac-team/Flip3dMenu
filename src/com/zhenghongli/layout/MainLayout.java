package com.zhenghongli.layout;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhenghongli.Flip3dView.Flip3dView;
import com.zhenghongli.activity.R;
import com.zhenghongli.flip3d.Flip3d;

public class MainLayout extends RelativeLayout {
	
	private Flip3dView flip3dView;

	public MainLayout(Context context) {
		super(context);

		flip3dView = new Flip3dView(context);
		LayoutParams lp = new LayoutParams(500, 500);
		flip3dView.setLayoutParams(lp);
		flip3dView.setFirstViewBackground(R.drawable.firefox);
		flip3dView.setSecondViewBackground(R.drawable.firefox_alt);

		LayoutParams lp2 = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.setLayoutParams(lp2);

		this.addView(flip3dView);
		// TODO Auto-generated constructor stub
	}
	
	public Flip3dView getFlip3dView() {
		return flip3dView;
	}
	
}
