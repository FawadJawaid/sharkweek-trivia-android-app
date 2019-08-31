package com.tenpearls.sharkweektrivia;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.DialogError;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint({ "NewApi", "ResourceAsColor" })
public class FragmentQuestionData extends Fragment {

	//private static String url = "http://ec2-75-101-221-59.compute-1.amazonaws.com:3000/v1/quiz/1/question?access_token=6oFOkqzpssPSjFKZ3dF713PUkKxcANBiPFpjCzHz_TvPhiDD1qKa3y6AhnzR8ZsivovPTj4-NhWtuVziCPuJVHoAqrD2VXbxv5BmosxkTbZvlcZVLQ6hPx6HpTO_ddluQSR4Wf_TuqV4ZDLyTcWg-kbsAjFq9i2o-ONCHednuXo~&limit=15";
	//private static String url = "https://zubie-qa.appspot.com/api/v1/public/places?fb_access_token=CAACBtntLZB8wBAF5j2hzCiJK9mWNSKc8x9eaMSbXZBly2JJuzpuQVEt2wEMOr9xmJCbxVG1Bd4ysDrhFCSKiQZBVXfDBmfDlaEZBgJaK0BJbYMCx9fr8ofTNzGRJ40qu9UGt5OSql785xRM7eQNlbMHZBpXMl9F0Fet6ZA4ZC4KTMrb2DBqfLkhp318qhw5nd9ixmIa1a3MljxYCjeWN4vES6cx0KHew9SlKITx4TAJKAZDZD";
	
	private static final String TAG_RESULTS = "results";
	private static final String TAG_QUESTIONS = "questions";
	private static final String TAG_CONTENT = "content";
	private static final String TAG_IMAGEURL = "url";
	private static final String TAG_BONUS = "is_bonus";
	private static final String TAG_OPTION = "option_id";
	private static final String TAG_TOT = "is_correct";
	public String op1, op2, op3, op4, img;
	public boolean c1, c2, c3, c4;
	public int k, p, q, r, e, y;
	public String image;
	public String access_token, url;
//	public int i = 0;
//	FragmentQuestionNumber f = new FragmentQuestionNumber();
	
	JSONArray android = null ;
	JSONObject c, h;
	
	
    TextView question;
    ImageView questionPic;
    Button option1, option2, option3, option4;
    ProgressBar spinner;
    private Timer mTimer;
	private TimerTask mTimerTask;
        
    ArrayList<Question> dataList = new ArrayList<Question>();
    ArrayList<Integer> imageHere = new ArrayList<Integer>();
    ArrayList<Integer> bonusHere = new ArrayList<Integer>();
	
	public FragmentQuestionData() {
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        return inflater.inflate(R.layout.fragment_question_data, container, false);
    }

	public void onActivityCreated(Bundle savedInstanceState) {
        
		
		initViews(savedInstanceState); 
		
		new JSONParse().execute();
		Global.bonusQues = bonusHere;
		//gameLogic();
		
	}
	
	 private class JSONParse extends AsyncTask<String, String, JSONObject /*String*/>{
    	 //private ProgressDialog pDialog;
    	@SuppressLint("NewApi")
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
  
            Animation zoomIn = AnimationUtils.loadAnimation(
    				getActivity(), R.anim.zoominout);
            option1.setVisibility(View.VISIBLE);
    		option1.startAnimation(zoomIn);
    		
    		final Handler handler = new Handler();
    		handler.postDelayed(new Runnable() {
    		  @Override
    		  public void run() {
    			option2.setVisibility(View.VISIBLE);
    			Animation zoomIn1 = AnimationUtils.loadAnimation(
        				getActivity(), R.anim.zoominout);
    			//zoomIn1.setStartOffset(800);
    			//zoomIn1.setDuration(800);
			    option2.startAnimation(zoomIn1);
    		  }
    		}, 800);
    		
    		final Handler handler1 = new Handler();
    		handler1.postDelayed(new Runnable() {
    		  @Override
    		  public void run() {
    			  option3.setVisibility(View.VISIBLE);
    			  Animation zoomIn2 = AnimationUtils.loadAnimation(
    	    				getActivity(), R.anim.zoominout);
    			  //zoomIn2.setStartOffset(1200);
      			  //zoomIn2.setDuration(1200);
    			  option3.startAnimation(zoomIn2);
    		  }
    		}, 1600);
    		
    		final Handler handler2 = new Handler();
    		handler2.postDelayed(new Runnable() {
    		  @Override
    		  public void run() {
    			  option4.setVisibility(View.VISIBLE);
    			  Animation zoomIn3 = AnimationUtils.loadAnimation(
    	    				getActivity(), R.anim.zoominout);
    			  //zoomIn3.setStartOffset(1500);
      			  //zoomIn3.setDuration(1500);
    			  option4.startAnimation(zoomIn3);
    		  }
    		}, 2500);
					
            //pDialog = new ProgressDialog(getActivity());
            //pDialog.setMessage("Getting Data ...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
            
    	}
    	
    	@Override
        protected JSONObject /*String*/  doInBackground(String... args) {
    		
    		JSONParser jParser = new JSONParser();

    		// Getting JSON from URL
    		JSONObject json = jParser.getJSONFromUrl(url);
    		//String json = jParser.getJSONFromUrl(url);
    		return json;
    	}
    	 @Override
         protected void onPostExecute(final /*String*/ JSONObject json) {
    		 
    		
    		 //pDialog.dismiss();
    		try {
						h = json.getJSONObject(TAG_RESULTS);
					
    				// Getting JSON Array from URL
    				android = h.getJSONArray(TAG_QUESTIONS);
    			    
    				int i = 0;
    				while(i<android.length()){
    				//for(int i = 0; i < android.length(); i++) {
    				c = android.getJSONObject(i);
    				Log.v("Iterator", i + " ");
    				
    				
    				String ver = c.getString(TAG_CONTENT);
    				Log.v("QUESTION:", ver);
    				String pic = null;
					try {
						pic = c.getString(TAG_IMAGEURL);
						if(pic!=null) {
						img=pic;
						imageHere.add(i);
						Log.v("Image", img);
						}
						else {img=null;
						}
						Log.v("Image", img);
					} catch (Exception e1) {
						//e1.printStackTrace();
					}
					
					boolean bonus;
					try {
						bonus = c.getBoolean(TAG_BONUS);
						if(bonus==true)
						{
							bonusHere.add(i);
						}
						Log.v("Bonus Tag", bonus+"");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
    				
    				/*if(pic==null) {
    					img =null;
    				}
    				else {
    					img=pic;
    				}*/
    				
    				//question.setText(ver + "?");
    				//Log.v("HERE", question + "");
    				
    				
    				 if(c != null){
    		                JSONArray prods = c.getJSONArray("options");
    		                if(prods != null){
    		                    for(int j = 0; j < prods.length();j++) {
    		                        JSONObject innerElem = prods.getJSONObject(j);
    		                        if(innerElem != null) {
    		                            int cat_id = innerElem.getInt(TAG_OPTION);
    		                            String pos = innerElem.getString(TAG_CONTENT);
    		                            final boolean sku = innerElem.getBoolean(TAG_TOT);
    		                            
    		                            if(cat_id==1){//option1.setText(pos);
    		                            	op1=pos;
    		                            	c1=sku;
    		                            }
    		                            else if(cat_id==2){//option2.setText(pos);
    		                            	op2=pos;
    		                            	c2=sku;
    		                            }
    		                            
    		                            else if(cat_id==3){//option3.setText(pos);
    		                            	op3=pos;
    		                            	c3=sku;
    		                            }
    		                            
    		                            else if(cat_id==4){//option4.setText(pos);
    		                            	op4=pos;
    		                            	c4=sku;
    		                            }
    		                            
    		                            
    		                             
    		                        }
    		                       } //for loop j
    		                    }
    		                }
    				  dataList.add(new Question(i+1, ver, img, op1, c1, op2, c2, op3, c3, op4, c4));
    				  Log.v("HERE", ver);
    				  Log.v("Option1",op1);
    				 i++;
    				 } //for android.length()
    				
    			 
    				//} //while
    		}
    		catch (JSONException e) {
    			e.printStackTrace();
    		}

    		
    		/*Intent i = getActivity().getIntent();
    		
    		if((i.getIntExtra("Counter", p) != 0) && (i.getIntExtra("Counter", p) < android.length())) {
    			k = i.getIntExtra("Counter", p);
    		}
    		/*else if(i.getIntExtra("Counter", p) >= dataList.size()) {
    			Intent gameOver = new Intent(getActivity(),
						MenuActivity.class);
    			gameOver.putExtra("gameover", 1);
    			startActivity(gameOver);
    		}*/
    		/*else {
    			k = 0;
    		}
    		
    		Log.v("P COUNTER", i.getIntExtra("Counter", p)+"");
    		Log.v("DataList Size", dataList.size()+"");*/
    		
    		if(dataList.size()==0)
    		{
    			/*if(GameQuestionActivity.mServ != null) {
    			GameQuestionActivity.mServ.stopMusic();
    			GameQuestionActivity.mServ=null;
    			}*/
    			
                new AlertDialog.Builder(getActivity())
				.setTitle("Unable to Connect to the Server!")
				.setMessage("Please, Kill application from Recent Apps and Restart")
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								//Intent a = new Intent(Intent.ACTION_MAIN);
					        	//a.addCategory(Intent.CATEGORY_HOME);
					        	//a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					        	//startActivity(a);
								
								if(Global.finalMusic != null) {
								//AudioManager manager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
								//if(manager.isMusicActive()){
									GameQuestionActivity.mServ.pauseMusic();
					    			//GameQuestionActivity.mServ=null;
					    			}
								
								
								Intent homePlay = new Intent(getActivity(),
										MenuActivity.class);
				    			startActivity(homePlay);
							}

						}).show();
    		}
    		else {
    			Intent i = getActivity().getIntent();
        		
        		if((i.getIntExtra("Counter", p) != 0) && (i.getIntExtra("Counter", p) < android.length())) {
        			k = i.getIntExtra("Counter", p);
        		}
        		/*else if(i.getIntExtra("Counter", p) >= dataList.size()) {
        			Intent gameOver = new Intent(getActivity(),
    						MenuActivity.class);
        			gameOver.putExtra("gameover", 1);
        			startActivity(gameOver);
        		}*/
        		else {
        			k = 0;
        		}
        		
        		Log.v("P COUNTER", i.getIntExtra("Counter", p)+"");
        		Log.v("DataList Size", dataList.size()+"");	
    			
        		if(i.getIntExtra("Lives", r) != 0 ) {
        			q = i.getIntExtra("Lives", r);
        			Log.v("LIVES HERE", q+"");
        		}
        		else {
        			q = 0;
        		}
    		//while(k<android.length())
    		//{
        		e = dataList.get(k).getNum();
        		String nam = dataList.get(k).getContent();
        		image = dataList.get(k).getImage();
        		String o1= dataList.get(k).getOption1();
        		String o2= dataList.get(k).getOption2();
        		String o3= dataList.get(k).getOption3();
	     		String o4= dataList.get(k).getOption4();
	     		
	     		Log.v("LOOPER", k+"");
     		
     		//f.Number.setText(e);
	     		Log.v("Number", e + "");
	     		question.setText(nam);
	     		option1.setText(o1);
	     		option2.setText(o2);
	     		option3.setText(o3);
	     		option4.setText(o4);
	     		
	     		//Log.v("y Value", y+"");
	     		/*for(int t=0; t<3; t++){
	     			Log.v("y Value", y[t]+"");}*/
					
	            for(int l=0; l < imageHere.size(); l++){ 
	     		if(image != null  && (k == imageHere.get(l)))
	     		{
	     			//loadImageFromURL("http://gerberstropicalfish.com/wp-content/uploads/2013/09/dylansharkcloseup_f2d4.jpg", questionPic);
	     			
	     			//View v = getView();
	     			Log.v("k Image Value", k+"");
	     			new DownloadImageTask(questionPic)
	     	        .execute(image);
	     			/*try {
						URL url = new URL(image);
						Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
						questionPic.setImageBitmap(bmp);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}*/
	     			Log.v("Image Here", image);
	     			image=null;
	     		}}
	     		
	     		option1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						  if(dataList.get(k).getCheck1() == true){
						       option1.setBackground(getResources().getDrawable(R.drawable.checked_button1));
						       option1.setTextColor(getResources().getColor(R.color.black));
						       
	
						       if(FragmentSettings.soundCheck.equals("On")) {
						       MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.correct);
						       mp.start();
						       }
						       
						       r=q; //For Lives
						  }
						  else if(dataList.get(k).getCheck1()==false){
							   option1.setTextColor(getResources().getColor(R.color.orange));
							   
							   //Intent game = new Intent(getActivity(),
										///GameQuestionActivity.class);
							   
							   if(FragmentSettings.soundCheck.equals("On")) {
							   MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.incorrect);
						       mp.start();
						       
						       mTimerTask = new TimerTask() {
									@Override
									public void run() {
										MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.lifelost);
									       mp.start();
									}
							   };
							   mTimer = new Timer();
							   mTimer.schedule(mTimerTask, 500);
							   }
						       
							   if(FragmentSettings.vibrationCheck.equals("On")) {
						       Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
						       v.vibrate(500);
							   }
							   
							   r=q+1; //For Lives
							   //game.putExtra("Lives", r);
							   
							   for(int y=0 ; y < bonusHere.size(); y++)
							   {
								   if(bonusHere.get(y) == k) {
									   r = q;  //For Lives
								   }
							   }
							     
							   if(dataList.get(k).getCheck2()==true){
								   option2.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck3()==true){
								   option3.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck4()==true){
								   option4.setTextColor(getResources().getColor(R.color.blue));
							   }
						  }
						
						      //FragmentQuestionNumber f = new FragmentQuestionNumber();      
						      //f.num = e+"";
						      //Log.v("NUMBER HERE I AM", f.num);
						
					    //FragmentQuestionNumber fragment = new FragmentQuestionNumber(); //  object of next fragment
						//Bundle bundle = new Bundle();
						//bundle.putInt("position", e);
						//fragment.setArguments(bundle);
						      
						 //fragment1 = new QuestionNumDialogFragment();
						    //fragment1.num = e+"";	
						
							  p=k+1;
							  //e=e+1;
							  image=null;
							  
							  Log.v("P in Option", p+"");
							  if(p+1 >= dataList.size()) {
						    		Intent gameOver = new Intent(getActivity(),
												MenuActivity.class);
						    	   gameOver.putExtra("gameover", 1);
						    	   startActivity(gameOver);
							  }
							  else {
							  Intent welcome = new Intent(getActivity(),
										GameQuestionActivity.class);
							  welcome.putExtra("Counter", p);
							  welcome.putExtra("Lives", r);
							  welcome.putExtra("position", e);
								startActivity(welcome); }
						
							 
					}
				});
	     		
	     		option2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						  if(dataList.get(k).getCheck2() == true){
						       option2.setBackground(getResources().getDrawable(R.drawable.checked_button1));
						       option2.setTextColor(getResources().getColor(R.color.black));
						       
						       if(FragmentSettings.soundCheck.equals("On")) {
						       MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.correct);
						       mp.start();
						       }
						       
						       r=q;
						  }
						  else if(dataList.get(k).getCheck2()==false){
							   option2.setTextColor(getResources().getColor(R.color.orange));
							   
							   //Intent game = new Intent(getActivity(),
										//GameQuestionActivity.class);
							   
							   if(FragmentSettings.soundCheck.equals("On")) {
							   MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.incorrect);
						       mp.start();
						       
						       mTimerTask = new TimerTask() {
									@Override
									public void run() {
										MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.lifelost);
									       mp.start();
									}
							   };
							   mTimer = new Timer();
							   mTimer.schedule(mTimerTask, 500);
							   }
						       
							   if(FragmentSettings.vibrationCheck.equals("On")) {
						       Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
						       v.vibrate(500);
							   }
							   
							   r=q+1;
							   //game.putExtra("Lives", r);
							   for(int y=0 ; y < bonusHere.size(); y++)
							   {
								   if(bonusHere.get(y) == k) {
									   r = q;
								   }
							   }
							   
							  
							   
							   if(dataList.get(k).getCheck1()==true){
								   option1.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck3()==true){
								   option3.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck4()==true){
								   option4.setTextColor(getResources().getColor(R.color.blue));
							   }
						  }
						  
						  
						  p=k+1;
						  image=null;
						  //e=e+1;
						  Log.v("P in Option", p+"");
						  if(p >= dataList.size()) {
					    		Intent gameOver = new Intent(getActivity(),
											MenuActivity.class);
					    	   gameOver.putExtra("gameover", 1);
					    	   startActivity(gameOver);
						  }
						  else {
						  Intent welcome = new Intent(getActivity(),
									GameQuestionActivity.class);
						  welcome.putExtra("Counter", p);
						  welcome.putExtra("Lives", r);
						  welcome.putExtra("position", e);
							startActivity(welcome); }
					}
	     		});
	     	
	     		option3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						  if(dataList.get(k).getCheck3() == true){
						       option3.setBackground(getResources().getDrawable(R.drawable.checked_button1));
						       option3.setTextColor(getResources().getColor(R.color.black));
						       
						       if(FragmentSettings.soundCheck.equals("On")) {
						       MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.correct);
						       mp.start();
						       }
						       
						       r=q;
						  }
						  else if(dataList.get(k).getCheck3()==false){
							   option3.setTextColor(getResources().getColor(R.color.orange));
							   
							   //Intent game = new Intent(getActivity(),
										//GameQuestionActivity.class);
							   
							   if(FragmentSettings.soundCheck.equals("On")) {
							   MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.incorrect);
						       mp.start();
						       
						       mTimerTask = new TimerTask() {
									@Override
									public void run() {
										MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.lifelost);
									       mp.start();
									}
							   };
							   mTimer = new Timer();
							   mTimer.schedule(mTimerTask, 500);
							   }
						       
						       if(FragmentSettings.vibrationCheck.equals("On")) {
						       Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
						       v.vibrate(500);
						       }
							   
							   r=q+1;
							   //game.putExtra("Lives", r);
							   for(int y=0 ; y < bonusHere.size(); y++)
							   {
								   if(bonusHere.get(y) == k) {
									   r = q;
								   }
							   }
							   
							   
							   if(dataList.get(k).getCheck1()==true){
								   option1.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck2()==true){
								   option2.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck4()==true){
								   option4.setTextColor(getResources().getColor(R.color.blue));
							   }
						  }
						  
						 
						  p=k+1;
						  image=null;
						  //e=e+1;
						  Log.v("P in Option", p+"");
						  if(p >= dataList.size()) {
					    		Intent gameOver = new Intent(getActivity(),
											MenuActivity.class);
					    	   gameOver.putExtra("gameover", 1);
					    	   startActivity(gameOver);
						  }
						  else {
						  Intent welcome = new Intent(getActivity(),
									GameQuestionActivity.class);
						  welcome.putExtra("Counter", p);
						  welcome.putExtra("Lives", r);
						  welcome.putExtra("position", e);
							startActivity(welcome); }
								
					}
	     		});
	     		
	     		option4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						  if(dataList.get(k).getCheck4() == true){
						       option4.setBackground(getResources().getDrawable(R.drawable.checked_button1));
						       option4.setTextColor(getResources().getColor(R.color.black));
						       
						       if(FragmentSettings.soundCheck.equals("On")) {
						       MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.correct);
						       mp.start();
						       }
						       
						       r=q;
						  }
						  else if(dataList.get(k).getCheck4()==false){
							   option4.setTextColor(getResources().getColor(R.color.orange));
							   
							   //Intent game = new Intent(getActivity(),
										//GameQuestionActivity.class);
							   
							   if(FragmentSettings.soundCheck.equals("On")) {
							   MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.incorrect);
						       mp.start();
						       
						       mTimerTask = new TimerTask() {
									@Override
									public void run() {
										MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.lifelost);
									       mp.start();
									}
							   };
							   mTimer = new Timer();
							   mTimer.schedule(mTimerTask, 500);
							   }
						       
						       if(FragmentSettings.vibrationCheck.equals("On")) {
						       Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
						       v.vibrate(500);
						       }
							   
							   r=q+1;
							   //game.putExtra("Lives", r);
							   for(int y=0 ; y < bonusHere.size(); y++)
							   {
								   if(bonusHere.get(y) == k) {
									   r = q;
								   }
							   }
							   
							   if(dataList.get(k).getCheck1()==true){
								   option1.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck2()==true){
								   option2.setTextColor(getResources().getColor(R.color.blue));
							   }
							   if(dataList.get(k).getCheck3()==true){
								   option3.setTextColor(getResources().getColor(R.color.blue));
							   }
						  }
						  
						  
						  p=k+1;
						  image=null;
						  //e=e+1;
						  Log.v("P in Option", p+"");
						  if(p >= dataList.size()) {
					    		Intent gameOver = new Intent(getActivity(),
											MenuActivity.class);
					    	   gameOver.putExtra("gameover", 1);
					    	   startActivity(gameOver);
						 }
						  else { 
						  Intent welcome = new Intent(getActivity(),
									GameQuestionActivity.class);
						  welcome.putExtra("Counter", p);
						  welcome.putExtra("Lives", r);
						  welcome.putExtra("position", e);
							startActivity(welcome); }
					}
	     		});
    	 }
    		//}
        }
    	 
    	/* public boolean loadImageFromURL(String fileUrl, 
    			 ImageView iv){
    			   try {
    			  
    			     URL myFileUrl = new URL (fileUrl);
    			     HttpURLConnection conn =
    			       (HttpURLConnection) myFileUrl.openConnection();
    			     conn.setDoInput(true);
    			     conn.connect();
    			  
    			     InputStream is = conn.getInputStream();
    			     iv.setImageBitmap(BitmapFactory.decodeStream(is));
    			  
    			     return true;
    			  
    			   } catch (MalformedURLException e) {
    			     e.printStackTrace();
    			   } catch (Exception e) {
    			     e.printStackTrace();
    			   }
    			  
    			   return false;
    			 }*/
    	 
    	 private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    		  ImageView bmImage;
    		  
    		  public DownloadImageTask(ImageView bmImage) {
    		      this.bmImage = bmImage;
    		  }
    		  
    		  protected void onPreExecute() {
    	            super.onPreExecute();
    	  
    	            //pDialog = new ProgressDialog(getActivity());
    	            //pDialog.setMessage("Getting Data ...");
    	            //pDialog.setIndeterminate(false);
    	            //pDialog.setCancelable(true);
    	            //pDialog.show();
    	            questionPic.setVisibility(View.VISIBLE);
    	            spinner.setVisibility(View.VISIBLE);
    	            
    	    	}

    		  protected Bitmap doInBackground(String... urls) {
    		      String urldisplay = urls[0];
    		      Bitmap mIcon11 = null;
    		      try {
    		        InputStream in = new java.net.URL(urldisplay).openStream();
    		        mIcon11 = BitmapFactory.decodeStream(in);
    		      } catch (Exception e) {
    		          Log.e("Error", e.getMessage());
    		          e.printStackTrace();
    		      }
    		      return mIcon11;
    		  }

    		  protected void onPostExecute(Bitmap result) {
    			  spinner.setVisibility(View.GONE);
    		      bmImage.setImageBitmap(result);
    		  }
    		}
    	 
	 }
	
	public void initViews(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View v = getView();
		
		question = (TextView) v.findViewById(R.id.questionView);
		questionPic = (ImageView) v.findViewById(R.id.imageContent);
		option1 = (Button) v.findViewById(R.id.possibleAnswer2);
		option2 = (Button) v.findViewById(R.id.possibleAnswer1);
		option3 = (Button) v.findViewById(R.id.possibleAnswer3);
		option4 = (Button) v.findViewById(R.id.possibleAnswer4);
		spinner = (ProgressBar) v.findViewById(R.id.progressBar1);
		spinner.setVisibility(View.GONE);
		questionPic.setVisibility(View.GONE);
		option1.setVisibility(View.GONE);
		option2.setVisibility(View.GONE);
		option3.setVisibility(View.GONE);
		option4.setVisibility(View.GONE);
		
		Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/MondaRegular.ttf");
	    question.setTypeface(typeFace);
	    option1.setTypeface(typeFace);
	    option2.setTypeface(typeFace);
	    option3.setTypeface(typeFace);
	    option4.setTypeface(typeFace);
	    
	    //access_token = getArguments().getString("Final Access Token");
		Log.v("AccessToken in QuestData", Global.accessToken);
		//url = "http://ec2-54-89-82-163.compute-1.amazonaws.com:3000/v1/quiz/1407181003051615/question?access_token=" + Global.accessToken + "&limit=15";
		url = "http://ec2-54-89-82-163.compute-1.amazonaws.com:3000/v1/quiz/" + Global.quizId + "/question?access_token=" + Global.accessToken + "&limit=15";
	}
	
	/*void gameLogic(){
		Intent i = getActivity().getIntent();
		
		if((i.getIntExtra("Counter", p) != 0) && (i.getIntExtra("Counter", p) < android.length())) {
			k = i.getIntExtra("Counter", p);
		}
		else {
			k = 0;
		}
		
		if(i.getIntExtra("Lives", r) !=0 ){
			q = i.getIntExtra("Lives", r);
			Log.v("LIVES HERE", q+"");
		}
		else{
			q = 0;
		}
		//while(k<android.length())
		//{
		e = Global.dataList.get(k+1).getNum();
 		String nam = Global.dataList.get(k+1).getContent();
 		String image = Global.dataList.get(k+1).getImage();
 		String o1= Global.dataList.get(k+1).getOption1();
 		String o2= Global.dataList.get(k+1).getOption2();
 		String o3= Global.dataList.get(k+1).getOption3();
 		String o4= Global.dataList.get(k+1).getOption4();
 		
 		//f.Number.setText(e);
 		Log.v("Number", e + "");
 		question.setText(nam);
 		option1.setText(o1);
 		option2.setText(o2);
 		option3.setText(o3);
 		option4.setText(o4);
 		
 		
 		if(image !=null)
 		{
 			//loadImageFromURL("http://gerberstropicalfish.com/wp-content/uploads/2013/09/dylansharkcloseup_f2d4.jpg", questionPic);
 			
 			//View v = getView();
 			
 			new DownloadImageTask(questionPic)
 	        .execute(image);
 			
 			/*try {
				URL url = new URL(image);
				Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				questionPic.setImageBitmap(bmp);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
 			
 		/*	Log.v("Image Here", image);
 		}
 		
 		option1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				  if(Global.dataList.get(k).getCheck1() == true){
				       option1.setBackground(getResources().getDrawable(R.drawable.checked_button1));
				       option1.setTextColor(getResources().getColor(R.color.black));
				       
				       r=q;
				  }
				  else if(Global.dataList.get(k).getCheck1()==false){
					   option1.setTextColor(getResources().getColor(R.color.orange));
					   
					   //Intent game = new Intent(getActivity(),
								///GameQuestionActivity.class);
					   r=q+1;
					   //game.putExtra("Lives", r);
					   
					   if(Global.dataList.get(k).getCheck2()==true){
						   option2.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(Global.dataList.get(k).getCheck3()==true){
						   option3.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(Global.dataList.get(k).getCheck4()==true){
						   option4.setTextColor(getResources().getColor(R.color.blue));
					   }
				  }
				
				      //FragmentQuestionNumber f = new FragmentQuestionNumber();      
				      //f.num = e+"";
				      //Log.v("NUMBER HERE I AM", f.num);
				
			    //FragmentQuestionNumber fragment = new FragmentQuestionNumber(); //  object of next fragment
				//Bundle bundle = new Bundle();
				//bundle.putInt("position", e);
				//fragment.setArguments(bundle);
				      
				 //fragment1 = new QuestionNumDialogFragment();
				    //fragment1.num = e+"";	
				
					  Intent welcome = new Intent(getActivity(),
								GameQuestionActivity.class);
					  p=k+1;
					  //e=e+1;
					  welcome.putExtra("Counter", p);
					  welcome.putExtra("Lives", r);
					  welcome.putExtra("position", e);
						startActivity(welcome);
				
			}
		});
 		
 		/*option2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				  if(Global.dataList.get(k).getCheck2() == true){
				       option2.setBackground(getResources().getDrawable(R.drawable.checked_button1));
				       option2.setTextColor(getResources().getColor(R.color.black));
				       
				       r=q;
				  }
				  else if(dataList.get(k).getCheck2()==false){
					   option2.setTextColor(getResources().getColor(R.color.orange));
					   
					   //Intent game = new Intent(getActivity(),
								//GameQuestionActivity.class);
					   r=q+1;
					   //game.putExtra("Lives", r);
					   
					   if(dataList.get(k).getCheck1()==true){
						   option1.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(dataList.get(k).getCheck3()==true){
						   option3.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(dataList.get(k).getCheck4()==true){
						   option4.setTextColor(getResources().getColor(R.color.blue));
					   }
				  }
				  
				  Intent welcome = new Intent(getActivity(),
							GameQuestionActivity.class);
				  p=k+1;
				  //e=e+1;
				  welcome.putExtra("Counter", p);
				  welcome.putExtra("Lives", r);
				  welcome.putExtra("position", e);
					startActivity(welcome);
			}
 		});
 	
 		option3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				  if(dataList.get(k).getCheck3() == true){
				       option3.setBackground(getResources().getDrawable(R.drawable.checked_button1));
				       option3.setTextColor(getResources().getColor(R.color.black));
				       
				       r=q;
				  }
				  else if(dataList.get(k).getCheck3()==false){
					   option3.setTextColor(getResources().getColor(R.color.orange));
					   
					   //Intent game = new Intent(getActivity(),
								//GameQuestionActivity.class);
					   r=q+1;
					   //game.putExtra("Lives", r);
					   
					   if(dataList.get(k).getCheck1()==true){
						   option1.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(dataList.get(k).getCheck2()==true){
						   option2.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(dataList.get(k).getCheck4()==true){
						   option4.setTextColor(getResources().getColor(R.color.blue));
					   }
				  }
				  
				  Intent welcome = new Intent(getActivity(),
							GameQuestionActivity.class);
				  p=k+1;
				  //e=e+1;
				  welcome.putExtra("Counter", p);
				  welcome.putExtra("Lives", r);
				  welcome.putExtra("position", e);
					startActivity(welcome);
			}
 		});
 		
 		option4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				  if(dataList.get(k).getCheck4() == true){
				       option4.setBackground(getResources().getDrawable(R.drawable.checked_button1));
				       option4.setTextColor(getResources().getColor(R.color.black));
				       
				       r=q;
				  }
				  else if(dataList.get(k).getCheck4()==false){
					   option4.setTextColor(getResources().getColor(R.color.orange));
					   
					   //Intent game = new Intent(getActivity(),
								//GameQuestionActivity.class);
					   r=q+1;
					   //game.putExtra("Lives", r);
					   
					   if(dataList.get(k).getCheck1()==true){
						   option1.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(dataList.get(k).getCheck2()==true){
						   option2.setTextColor(getResources().getColor(R.color.blue));
					   }
					   if(dataList.get(k).getCheck3()==true){
						   option3.setTextColor(getResources().getColor(R.color.blue));
					   }
				  }
				  
				  Intent welcome = new Intent(getActivity(),
							GameQuestionActivity.class);
				  p=k+1;
				  //e=e+1;
				  welcome.putExtra("Counter", p);
				  welcome.putExtra("Lives", r);
				  welcome.putExtra("position", e);
					startActivity(welcome);
			}
 		});*/
	//}
	
	/* private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public DownloadImageTask(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return mIcon11;
		  }

		  protected void onPostExecute(Bitmap result) {
		      bmImage.setImageBitmap(result);
		  }
		}*/
}
