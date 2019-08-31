package com.tenpearls.sharkweektrivia;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi")
public class FragmentAchievements extends Fragment {

	BottomBar bottomBar;
	ImageButton achievementButton, alertsButton, leaderBoardButton;
	public ImageButton ulitmatechampBtn, kidsruleawardBtn, smartypantsBtn, littlemonsterBtn, triviaexpertBtn, masterdisasterBtn;
	TextView heading, ultimateTxtView, kidsruleTxtView, smartypantsTxtView, littlemonsterTxtView, triviaexpertTxtView, masterdisasterTxtView;


	public FragmentAchievements() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_achievements, container,
				false);

	}

	public void onActivityCreated(Bundle savedInstanceState) {

		initViews(savedInstanceState);
	}

	public void initViews(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View viewFragment = getView();
		
		Log.v("COUNTER1", Global.alertsCount+"");

		bottomBar = (BottomBar) viewFragment.findViewById(R.id.bottomBar);

		achievementButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_acheivementsButton);
		achievementButton
				.setImageResource(R.drawable.bottombar_achievements_selected);

		alertsButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_alertButton);

		leaderBoardButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_leaderboardButton);

		alertsButton.setOnClickListener(alertsListener);
		leaderBoardButton.setOnClickListener(leaderBoardListener);
		
		ulitmatechampBtn = (ImageButton) viewFragment.findViewById(
				R.id.ulitmateChampimgView);
		ulitmatechampBtn.setSelected(!ulitmatechampBtn.isSelected());
		
		kidsruleawardBtn = (ImageButton) viewFragment.findViewById(
				R.id.kidsruleawardimgView);
		kidsruleawardBtn.setSelected(!kidsruleawardBtn.isSelected()); 
		
		smartypantsBtn = (ImageButton) viewFragment.findViewById(
				R.id.smartypantsImgView);
		smartypantsBtn.setSelected(!smartypantsBtn.isSelected());
		
		littlemonsterBtn = (ImageButton) viewFragment.findViewById(
				R.id.littlemonsterImgView);
		littlemonsterBtn.setSelected(littlemonsterBtn.isSelected());
		
		triviaexpertBtn = (ImageButton) viewFragment.findViewById(
				R.id.triviaexpertImgView);
		triviaexpertBtn.setSelected(triviaexpertBtn.isSelected());
		
		masterdisasterBtn = (ImageButton) viewFragment.findViewById(
				R.id.masterdisasterImgView);
		masterdisasterBtn.setSelected(masterdisasterBtn.isSelected());
		
		heading = (TextView) viewFragment.findViewById(R.id.achievementsHeading);
		
		ultimateTxtView = (TextView) viewFragment.findViewById(R.id.ulitmateChamptextView);
		kidsruleTxtView = (TextView) viewFragment.findViewById(R.id.kidsruleawardTxtView);
		smartypantsTxtView = (TextView) viewFragment.findViewById(R.id.smartypantsTextView);
		littlemonsterTxtView = (TextView) viewFragment.findViewById(R.id.littlemonsterTextView);
		triviaexpertTxtView = (TextView) viewFragment.findViewById(R.id.triviaexpertTextView);
		masterdisasterTxtView = (TextView) viewFragment.findViewById(R.id.masterdisasterTextView);
		

		ulitmatechampBtn.setOnClickListener(ultimatechampListener);
		kidsruleawardBtn.setOnClickListener(kidsruleawardListener);
		smartypantsBtn.setOnClickListener(smartypantsListener);
		littlemonsterBtn.setOnClickListener(littlemonsterListener);
		triviaexpertBtn.setOnClickListener(triviaexpertListener);
		masterdisasterBtn.setOnClickListener(masterdisasterListener);
		
		Typeface typeFaceBold = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/MondaBold.ttf");
		heading.setTypeface(typeFaceBold);
		
		Typeface typeFace = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/MondaRegular.ttf");
		ultimateTxtView.setTypeface(typeFace);
		kidsruleTxtView.setTypeface(typeFace);
		smartypantsTxtView.setTypeface(typeFace);
		littlemonsterTxtView.setTypeface(typeFace);
		triviaexpertTxtView.setTypeface(typeFace);
		masterdisasterTxtView.setTypeface(typeFace);
	}

	OnClickListener alertsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			alertsButton.setImageResource(R.drawable.bottombar_alerts_selected);

			FragmentManager fm = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment fragment = new FragmentAlerts();

			ft.replace(R.id.activity_main_content_fragment, fragment);
			ft.commit();

		}
	};

	OnClickListener leaderBoardListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			leaderBoardButton
					.setImageResource(R.drawable.bottombar_leaderboards_selected);

			FragmentManager fm = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment fragment = new FragmentLeaderboard();

			ft.replace(R.id.activity_main_content_fragment, fragment);
			ft.commit();

		}
	};
	
	OnClickListener ultimatechampListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			    AchievementsDialogFragment fragment1 = new AchievementsDialogFragment();
			    
			    
			    if(!ulitmatechampBtn.isSelected())
			    {
			    	fragment1.d = R.drawable.achievements_reward_locked;
			    }
			    
			    fragment1.text = "Ultimate Champion";
			    android.app.FragmentManager fm = getActivity().getFragmentManager();
			    fragment1.show(fm, ""); 

		}
	};
	
	OnClickListener kidsruleawardListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			    AchievementsDialogFragment fragment1 = new AchievementsDialogFragment();
			    
			    if(!kidsruleawardBtn.isSelected())
			    {
			    	fragment1.d = R.drawable.achievements_reward_locked;
			    }
			    
			    fragment1.text = "Kids Rule! Award";
			    android.app.FragmentManager fm = getActivity().getFragmentManager();
			    fragment1.show(fm, ""); 

		}
	};
	
	OnClickListener smartypantsListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			    AchievementsDialogFragment fragment1 = new AchievementsDialogFragment();
			    
			    if(!smartypantsBtn.isSelected())
			    {
			    	fragment1.d = R.drawable.achievements_reward_locked;
			    }
			    
			    fragment1.text = "Smarty Pants Badge";
			    android.app.FragmentManager fm = getActivity().getFragmentManager();
			    fragment1.show(fm, ""); 

		}
	};
	
	OnClickListener littlemonsterListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			    AchievementsDialogFragment fragment1 = new AchievementsDialogFragment();
			    
			    if(!littlemonsterBtn.isSelected())
			    {
			    	fragment1.d = R.drawable.achievements_reward_locked;
			    }
			    
			    fragment1.text = "Little Monster";
			    android.app.FragmentManager fm = getActivity().getFragmentManager();
			    fragment1.show(fm, ""); 

		}
	};
	
	OnClickListener triviaexpertListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			    AchievementsDialogFragment fragment1 = new AchievementsDialogFragment();
			    
			    if(!triviaexpertBtn.isSelected())
			    {
			    	fragment1.d = R.drawable.achievements_reward_locked;
			    }
			    
			    fragment1.text = "Trivia Expert";
			    android.app.FragmentManager fm = getActivity().getFragmentManager();
			    fragment1.show(fm, ""); 

		}
	};
	
	OnClickListener masterdisasterListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			    AchievementsDialogFragment fragment1 = new AchievementsDialogFragment();
			    
			    if(!masterdisasterBtn.isSelected())
			    {
			    	fragment1.d = R.drawable.achievements_reward_locked;
			    }
			    
			    fragment1.text = "Master of Disaster";
			    android.app.FragmentManager fm = getActivity().getFragmentManager();
			    fragment1.show(fm, ""); 

		}
	};
}
