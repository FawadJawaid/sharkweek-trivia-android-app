package com.tenpearls.sharkweektrivia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class FragmentSettings extends Fragment {

	Button delAccountBtn;
	TextView heading;
	public static Switch vibrationSwitch, soundSwitch;
	public static String vibrationCheck = "Off", soundCheck = "Off";


	public FragmentSettings() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		initViews(savedInstanceState);
	}

	public void initViews(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View v = getView();

		delAccountBtn = (Button) v.findViewById(R.id.deleteaccountBtn);
		heading = (TextView) v.findViewById(R.id.settingsHeading);
		vibrationSwitch = (Switch) v.findViewById(R.id.vibrationSwitch);
	    soundSwitch = (Switch) v.findViewById(R.id.soundeffectsSwitch);

		Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/MondaRegular.ttf");
		delAccountBtn.setTypeface(typeFace);

		Typeface typeFaceBold = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/MondaBold.ttf");
		heading.setTypeface(typeFaceBold);
		
		vibrationSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
			 
			    if(isChecked) {
			      vibrationCheck = "On";
			    }else {
			      vibrationCheck = "Off";
			    }
			 
			   }
		});
		
		soundSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
			 
			    if(isChecked) {
			      soundCheck = "On";
			    }else {
			      soundCheck = "Off";
			    }
			 
			   }
		});

		delAccountBtn.setOnClickListener(delAccountListener);
	}

	OnClickListener delAccountListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// To Delete Account
		}
	};
}
