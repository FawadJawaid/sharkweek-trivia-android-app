package com.tenpearls.sharkweektrivia;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.widget.LinearLayout;

public class BottomBar extends LinearLayout {

	public BottomBar(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.bottombar_layout, this);
	}

	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
	}

	public BottomBar(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
		initViews(context, attrs);
	}

	private void initViews(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.BottomBar, 0, 0);

		
		 try {
		  
		  } finally { a.recycle(); }
		 

		LayoutInflater.from(context).inflate(R.layout.bottombar_layout, this);

	}

}
