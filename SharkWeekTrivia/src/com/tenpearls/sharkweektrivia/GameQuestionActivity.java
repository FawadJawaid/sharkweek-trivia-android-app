package com.tenpearls.sharkweektrivia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class GameQuestionActivity extends FragmentActivity {

	private Timer mTimer;
	private TimerTask mTimerTask;
	RelativeLayout Game;

	private boolean mIsBound = false;
    public static MusicService mServ;
    
	ImageView life;
	TextView bonusX;
	String tkn;

	/*
	 * private static String url =
	 * "http://ec2-75-101-221-59.compute-1.amazonaws.com:3000/v1/question?access_token=6oFOkqzpssM8FB5qzshqijfSR5Lg8qimOGfuot7pauRSfZnOMdyCLRSgzG5Ll-foY12A_eq_r5HaXo2GNK3ZQsfXvwYzjoLBkIqcJttV2pggDTzQbF7M-gPSHFXzpjKb7pJ88WdSkgxqnGOJNqc56w~~&limit=15"
	 * ;
	 * 
	 * ArrayList<Question> dataList = new ArrayList<Question>(); private static
	 * final String TAG_OS = "results"; private static final String TAG_CAT =
	 * "content"; private static final String TAG_URL = "url"; private static
	 * final String TAG_NAME = "option_id"; private static final String TAG_TOT
	 * = "is_correct"; public String op1, op2, op3, op4, img; public boolean c1,
	 * c2, c3, c4;
	 * 
	 * JSONArray android = null ; JSONObject c;
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_question);

		// new JSONParse().execute();

		// Global.dataList=dataList;
		Game = (RelativeLayout) findViewById(R.id.mainGamelayout);
		life = (ImageView) findViewById(R.id.activity_gamequestion_button_lives);
		bonusX = (TextView) findViewById(R.id.bonusXView);
		Typeface typeFace = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "fonts/MondaBold.ttf");
		bonusX.setTypeface(typeFace);
		bonusX.setVisibility(View.GONE);
		
		if(FragmentSettings.soundCheck.equals("On")) {
		doBindService();
		Intent music = new Intent();
		music.setClass(this,MusicService.class);
		startService(music);
		}
		

		Intent intent = getIntent();
		int intValue = intent.getIntExtra("position", 0);
		//tkn = intent.getStringExtra("Token");
		//Log.v("In GameQuestion Act",tkn);

		for (int i = 0; i < Global.bonusQues.size(); i++) {
			if (Global.bonusQues.get(i) == intValue) {

				/*MediaPlayer mp = MediaPlayer.create(GameQuestionActivity.this, R.raw.bonus);
			    mp.start();*/
				
				bonusX.setVisibility(View.VISIBLE);
				Game.setBackground(getResources().getDrawable(
						R.drawable.background_bonus));
			}
		}

		livesHere();

		FragmentManager fm = GameQuestionActivity.this
				.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		FragmentQuestionNumber fragment = new FragmentQuestionNumber();

		Bundle args = new Bundle();
		args.putInt("number", intValue);
		fragment.setArguments(args);

		ft.add(R.id.activity_gamequestion_fragment, fragment);
		ft.commit();

		mTimerTask = new TimerTask() {

			@Override
			public void run() {

				FragmentManager fm = GameQuestionActivity.this
						.getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();

				FragmentQuestionData fragment = new FragmentQuestionData();
				
				/*if(tkn!=null) {
				Bundle args = new Bundle();
				args.putString("Final Access Token", tkn);
				fragment.setArguments(args); }*/
				
				ft.replace(R.id.activity_gamequestion_fragment, fragment); // When
																			// use
																			// dialog
																			// change
																			// it
																			// to
																			// "add"
				ft.commitAllowingStateLoss(); //IllelgalStateException caught

			}
		};

		mTimer = new Timer();
		mTimer.schedule(mTimerTask, 3000);

	}

	/*
	 * private class JSONParse extends AsyncTask<String, String, JSONObject >{
	 * //private ProgressDialog pDialog;
	 * 
	 * @SuppressLint("NewApi")
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute();
	 * 
	 * //pDialog = new ProgressDialog(getActivity());
	 * //pDialog.setMessage("Getting Data ...");
	 * //pDialog.setIndeterminate(false); //pDialog.setCancelable(true);
	 * //pDialog.show();
	 * 
	 * life = (ImageView) findViewById(R.id.activity_gamequestion_button_lives);
	 * 
	 * Intent intent = getIntent(); int intValue =
	 * intent.getIntExtra("position", 0);
	 * 
	 * livesHere();
	 * 
	 * FragmentManager fm = GameQuestionActivity.this
	 * .getSupportFragmentManager(); FragmentTransaction ft =
	 * fm.beginTransaction();
	 * 
	 * FragmentQuestionNumber fragment = new FragmentQuestionNumber();
	 * 
	 * Bundle args = new Bundle(); args.putInt("number", intValue);
	 * fragment.setArguments(args);
	 * 
	 * ft.add(R.id.activity_gamequestion_fragment, fragment); ft.commit();
	 * 
	 * }
	 * 
	 * @Override protected JSONObject doInBackground(String... args) {
	 * 
	 * JSONParser jParser = new JSONParser();
	 * 
	 * // Getting JSON from URL JSONObject json = jParser.getJSONFromUrl(url);
	 * //String json = jParser.getJSONFromUrl(url); return json; }
	 * 
	 * @Override protected void onPostExecute(final JSONObject json) {
	 * 
	 * 
	 * //pDialog.dismiss(); try { // Getting JSON Array from URL android =
	 * json.getJSONArray(TAG_OS);
	 * 
	 * int i = 0; while(i<android.length()){ //for(int i = 0; i <
	 * android.length(); i++) { c = android.getJSONObject(i); Log.v("Iterator",
	 * i + " ");
	 * 
	 * 
	 * String ver = c.getString(TAG_CAT); Log.v("QUESTION:", ver); String pic;
	 * try { pic = c.getString(TAG_URL); if(pic!=null){ img=pic;} else{img="";}
	 * Log.v("Image", img); } catch (Exception e1) { // TODO Auto-generated
	 * catch block //e1.printStackTrace(); //img=null; }
	 * 
	 * 
	 * /*if(pic==null) { img =null; } else { img=pic; }
	 */

	// question.setText(ver + "?");
	// Log.v("HERE", question + "");

	/*
	 * if(c != null){ JSONArray prods = c.getJSONArray("options"); if(prods !=
	 * null){ for(int j = 0; j < prods.length();j++) { JSONObject innerElem =
	 * prods.getJSONObject(j); if(innerElem != null) { int cat_id =
	 * innerElem.getInt(TAG_NAME); String pos = innerElem.getString(TAG_CAT);
	 * final boolean sku = innerElem.getBoolean(TAG_TOT);
	 * 
	 * if(cat_id==1){//option1.setText(pos); op1=pos; c1=sku; } else
	 * if(cat_id==2){//option2.setText(pos); op2=pos; c2=sku; }
	 * 
	 * else if(cat_id==3){//option3.setText(pos); op3=pos; c3=sku; }
	 * 
	 * else if(cat_id==4){//option4.setText(pos); op4=pos; c4=sku; }
	 * 
	 * 
	 * 
	 * } } //for loop j } } dataList.add(new Question(i+1, ver, img, op1, c1,
	 * op2, c2, op3, c3, op4, c4)); Log.v("HERE", ver); Log.v("Option1",op1);
	 * i++; } //for android.length()
	 * 
	 * 
	 * //} //while } catch (JSONException e) { e.printStackTrace(); }
	 * 
	 * FragmentManager fm = GameQuestionActivity.this
	 * .getSupportFragmentManager(); FragmentTransaction ft =
	 * fm.beginTransaction();
	 * 
	 * FragmentQuestionData fragment = new FragmentQuestionData();
	 * ft.replace(R.id.activity_gamequestion_fragment, fragment); //When use
	 * dialog change it to "add" ft.commit(); } }
	 */

	public void livesHere() {
		Intent mIntent = getIntent();
		int lifeValue = mIntent.getIntExtra("Lives", 0);
		Log.v("LIVES", lifeValue + "");

		if (lifeValue == 1) {
			life.setImageDrawable(getResources().getDrawable(
					R.drawable.lifeleft2));
		} else if (lifeValue == 2) {
			life.setImageDrawable(getResources().getDrawable(
					R.drawable.lifeleft1));
		} else if (lifeValue == 3) {
			life.setImageDrawable(getResources().getDrawable(
					R.drawable.lifeleft0));
		
			Intent gameOver = new Intent(getApplication(), MenuActivity.class);
			gameOver.putExtra("gameover", 1);
			startActivity(gameOver);
		}
	}
	
	private ServiceConnection Scon =new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder
	     binder) {
		mServ = ((MusicService.ServiceBinder)binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
		};

		void doBindService(){
	 		bindService(new Intent(this,MusicService.class),
					Scon,Context.BIND_AUTO_CREATE);
			mIsBound = true;
		}

		void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
	      		mIsBound = false;
			}
		}
		
	@Override	
	public void onDestroy(){
		doUnbindService();
	}
	

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Quitting Game!")
				.setMessage("Do you want to Quit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								Intent gameOver = new Intent(getApplication(),
										MenuActivity.class);
								gameOver.putExtra("gameover", 1);
								startActivity(gameOver);
							}

						}).setNegativeButton("No", null).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_question, menu);
		return true;
	}

}
