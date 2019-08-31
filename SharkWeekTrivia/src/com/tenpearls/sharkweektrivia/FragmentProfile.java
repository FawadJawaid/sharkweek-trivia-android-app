package com.tenpearls.sharkweektrivia;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("HandlerLeak")
public class FragmentProfile extends ListFragment {

	BottomBar bottomBar;
	ImageButton achievementButton, alertsButton, leaderBoardButton;
	TextView heading, userName;
	ImageView userImage;
	String user_ID, profileName;
	
	private ArrayList<UserProfile> lang = new ArrayList<UserProfile>();
	private Runnable viewParts;
	private ProfileAdapter lang_adapter;

	public FragmentProfile() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setAdapters();
		definingThreads();

		return inflater.inflate(R.layout.fragment_profile, container, false);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			lang.add(new UserProfile("Rank", 2, 0));
			lang.add(new UserProfile("Top Score", 80, 0));
			lang.add(new UserProfile("Times Played", 32, 0));
			lang.add(new UserProfile("Friends", 23, 0));
			lang.add(new UserProfile("Everyone",
					0, R.drawable.forward_arrow));
			lang.add(new UserProfile("Friends",
					0, R.drawable.forward_arrow));

			setAdapters();
		}
	};

	private void definingThreads() {
		viewParts = new Runnable() {
			public void run() {
				handler.sendEmptyMessage(0);
			}
		};
		Thread thread = new Thread(null, viewParts, "MagentoBackground");
		thread.start();
	}

	private void setAdapters() {

		lang_adapter = new ProfileAdapter(getActivity(),
				R.layout.userprofile_listitem, lang);
		setListAdapter(lang_adapter);
	}
	

	private void setListeners()
	{
		/*getListAdapter().setOnItemClickListener(new OnItemClickListener() {
	    	 
	    	 public void onItemClick(AdapterView<?> parent, View v, int position, long id){
	    		 
	    		 Intent full_Image = new Intent(getActivity(), FullImageActivity.class);
	    		 full_Image.putExtra("id", position);
	    		 startActivity(full_Image);
	    	 }
		});*/
		
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {

		if(position == 4) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment fragment = new FragmentLeaderboard();
			Global.pagerValue = 0;
		
			ft.replace(R.id.activity_main_content_fragment, fragment);
			ft.commit();
		}
		
		if(position == 5) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment fragment = new FragmentLeaderboard();
			Global.pagerValue = 1;
			
			ft.replace(R.id.activity_main_content_fragment, fragment);
			ft.commit();
		}
		
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		initViews(savedInstanceState);
	}


	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, null);

		ListView listScorings = (ListView) view
				.findViewById(R.id.profileScoringlistView);

		UserProfile[] items = { new UserProfile("Current Score  ", 490),
				new UserProfile("Top Score        ", 80),
				new UserProfile("Times Played ", 60),
				new UserProfile("Friends            ", 55) };

		ArrayAdapter<UserProfile> adapter = new ArrayAdapter<UserProfile>(
				getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1, items);

		listScorings.setAdapter(adapter);

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		initViews(savedInstanceState);
	}*/

	public void initViews(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View viewFragment = getView();

		bottomBar = (BottomBar) viewFragment.findViewById(R.id.bottomBar);

		alertsButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_alertButton);

		achievementButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_acheivementsButton);

		leaderBoardButton = (ImageButton) bottomBar.getRootView().findViewById(
				R.id.bottombar_leaderboardButton);

		heading = (TextView) viewFragment.findViewById(R.id.profileHeading);

		userName = (TextView) viewFragment.findViewById(R.id.userName);

		userImage = (ImageView) viewFragment.findViewById(R.id.userImg);

		alertsButton.setOnClickListener(alertsListener);
		achievementButton.setOnClickListener(achievementsListener);
		leaderBoardButton.setOnClickListener(leaderBoardListener);

		Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/MondaRegular.ttf");
		Typeface typeFaceBold = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/MondaBold.ttf");
		userName.setTypeface(typeFace);
		heading.setTypeface(typeFaceBold);

		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			Request.executeMeRequestAsync(session,
					new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								Response response) {

							final String fbId = user.getId();
							String fbName = user.getName();
							String gender = user.asMap().get("gender")
									.toString();
							String email = user.asMap().get("email").toString();

							final Handler handler = new Handler();

							Thread th = new Thread(new Runnable() {
								public void run() {
									try {
										final Bitmap bitmap = getFacebookProfilePicture(fbId);
										handler.post(new Runnable() {

											public void run() {
												userImage
														.setImageBitmap(bitmap);
											}
										});

									} catch (IOException e) {
										e.printStackTrace();
									}

								}
							});
							th.start();

							Log.v("User ID", fbId);
							Log.v("fb", fbName);
							userName.setText(fbName);
						}
					});

		}

	}

	public static Bitmap getFacebookProfilePicture(String userID)
			throws IOException {
		Bitmap bitmap;

		URL imageURL = new URL("https://graph.facebook.com/" + userID
				+ "/picture?type=large");
		bitmap = BitmapFactory.decodeStream(imageURL.openConnection()
				.getInputStream());

		return bitmap;
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
}
