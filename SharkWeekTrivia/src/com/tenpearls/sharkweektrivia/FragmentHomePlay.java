package com.tenpearls.sharkweektrivia;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentHomePlay extends Fragment {

	BottomBar bottomBar;
	ImageButton achievementButton, alertsButton, leaderBoardButton;
	ImageView playLogo;
	TextView quizName;
	Button play;
	//Button howtoplay;
	String accessToken;
	BadgeView badge3;
	MenuActivity men = new MenuActivity();
    int test = 0, counter;
	
	private Timer mTimer;
	private TimerTask mTimerTask;
	
	private static final String TAG_RESULTS = "results";
	private static final String TAG_ALERTS = "alerts";
	public String image;
	public String urlforAlertsCount = "http://ec2-54-89-82-163.compute-1.amazonaws.com:3000/v1/alerts?access_token=" + Global.accessToken;
	JSONArray android = null ;
	JSONObject c, h;
	
	
	
	public FragmentHomePlay() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.fragment_home_play, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		if(Global.alertsCheck==true) {
		new JSONParse().execute();
		}
		
		initViews(savedInstanceState);
	}

	public void initViews(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View viewFragment = getView();
		
		//accessToken = getArguments().getString("Access Token");
		//Log.v("In Home Play", accessToken);
		 
		bottomBar = (BottomBar) viewFragment.findViewById(R.id.bottomBar);
		
		alertsButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_alertButton);
		/*if(Global.alertsCheck==true)
		{
		test = 1;	
		badge3 = new BadgeView(getActivity(), alertsButton);
	    badge3.setText(counter + "");
	    badge3.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
    	badge3.setBadgeMargin(15, 2);
    	//TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
        //anim.setInterpolator(new BounceInterpolator());
        //anim.setDuration(1000);
	    //badge3.toggle(anim, null);
		badge3.toggle(true); }*/
	    
		
		
		achievementButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_acheivementsButton);
		
		leaderBoardButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_leaderboardButton);
		
		quizName = (TextView) viewFragment.findViewById(R.id.quizNameTxt);
		quizName.setText(Global.quizName);

		alertsButton.setOnClickListener(alertsListener);
		achievementButton.setOnClickListener(achievementsListener);
		leaderBoardButton.setOnClickListener(leaderBoardListener);
		
		//howtoplay = (Button) viewFragment.findViewById(R.id.howtoPlay);
		//howtoplay.setOnClickListener(howtoplayListener);
		
		play = (Button) viewFragment.findViewById(R.id.playButton);
		play.setOnClickListener(playListener);
		
		playLogo = (ImageView) viewFragment.findViewById(R.id.playMainLogo);
		
		Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/MondaBold.ttf");
	    play.setTypeface(typeFace);
	    //howtoplay.setTypeface(typeFace);
	    quizName.setTypeface(typeFace);
	}

	OnClickListener alertsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			alertsButton.setImageResource(R.drawable.bottombar_alerts_selected);
			
			if(test==1) {
			badge3.toggle(false);
			Global.alertsCheck=false;
			}

			FragmentManager fm = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment fragment = new FragmentAlerts();

			ft.replace(R.id.activity_main_content_fragment, fragment);
			ft.commit();

		}
	};

	OnClickListener achievementsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			achievementButton
					.setImageResource(R.drawable.bottombar_achievements_selected);

			FragmentManager fm = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment fragment = new FragmentAchievements();

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
	
	/*OnClickListener howtoplayListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			
			//FragmentManager fm = getActivity().getSupportFragmentManager();
			//FragmentTransaction ft = fm.beginTransaction();

			//Fragment fragment = new FragmentHowtoPlay1();

			//ft.replace(R.id.fragmenthome_content_fragment, fragment);
			//ft.commit();
			
			Intent welcome = new Intent(getActivity(),
					HowtoPlayActivity.class);
			startActivity(welcome);
		    

		}
	};*/
	

	OnClickListener playListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			
			//FragmentManager fm = getActivity().getSupportFragmentManager();
			//FragmentTransaction ft = fm.beginTransaction();

			//Fragment fragment = new FragmentHowtoPlay1();

			//ft.replace(R.id.fragmenthome_content_fragment, fragment);
			//ft.commit();
			final Animation zoomOutLogo = AnimationUtils.loadAnimation(
					getActivity(), R.anim.zoomout);
			
			Animation zoomOutBtn = AnimationUtils.loadAnimation(
					getActivity(), R.anim.zoomout_playbtn);
			
			//Animation slideDown = AnimationUtils.loadAnimation(
					//getActivity(), R.anim.slide_down);
			
			//howtoplay.startAnimation(slideDown);
			playLogo.startAnimation(zoomOutLogo);
			play.startAnimation(zoomOutBtn);
			
			mTimerTask = new TimerTask() {

				@Override
				public void run() {
			Intent welcome = new Intent(getActivity(),
					GameQuestionActivity.class);
			/*if(accessToken != null){
			welcome.putExtra("Token", accessToken);}*/
			startActivity(welcome);

				}
			};

			mTimer = new Timer();
			mTimer.schedule(mTimerTask, 500);

		}
	};

	 private class JSONParse extends AsyncTask<String, String, JSONObject /*String*/>{
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();  
    	}
    	
    	@Override
        protected JSONObject doInBackground(String... args) {
    		
    		JSONParser jParser = new JSONParser();

    		// Getting JSON from URL
    		JSONObject json = jParser.getJSONFromUrl(urlforAlertsCount);
    		return json;
    	}
    	
    	 @Override
         protected void onPostExecute(final JSONObject json) {
    		try {
    				// Getting JSON Object from URL;
    			    h = json.getJSONObject(TAG_RESULTS);
    			
    				android = h.getJSONArray(TAG_ALERTS);
    			
    				for(int i = 0; i < android.length(); i++) {
    				JSONObject c = android.getJSONObject(i);
    				
    			    counter++;
    				}
    				
    				Global.alertsCount = counter;
    				Log.v("COUNTER", counter+""); 
    				
    				if(Global.alertsCheck==true) {
    					test = 1;	
    					badge3 = new BadgeView(getActivity(), alertsButton);
    					badge3.setText(Global.alertsCount + "");
    					badge3.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
    					badge3.setBadgeMargin(15, 2);
    					badge3.toggle(true); 
    				}
    			    
    		}
    		catch (JSONException e) {
    			e.printStackTrace();
    		}
    	 }
	 }
}
