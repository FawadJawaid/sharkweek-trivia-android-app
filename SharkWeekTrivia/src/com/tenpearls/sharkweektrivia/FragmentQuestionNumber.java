package com.tenpearls.sharkweektrivia;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentQuestionNumber extends Fragment {

	public TextView Number;
	public TextView QuestionHeading;
	public TextView bonusPoints;
	private Timer mTimer;
	private TimerTask mTimerTask;
	String num = "";
	//int myInt;
	
	public FragmentQuestionNumber() {
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		//Bundle bundle = this.getArguments();
		//myInt = bundle.getInt("position", 0);
		
        return inflater.inflate(R.layout.fragment_question_number, container, false);
    }

	public void onActivityCreated(Bundle savedInstanceState) {

		//super.onActivityCreated(savedInstanceState);
		initView(savedInstanceState);
	}
	
	public void initView(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		View v = getView();
		
		Number = (TextView) v.findViewById(R.id.questionNumber);
		Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/MondaBold.ttf");
		Number.setTypeface(typeFace);
		
		QuestionHeading = (TextView) v.findViewById(R.id.question);
		QuestionHeading.setTypeface(typeFace);
		
		bonusPoints = (TextView) v.findViewById(R.id.bonusPoints);
		bonusPoints.setTypeface(typeFace);
		bonusPoints.setVisibility(View.GONE);
		//num = myInt + "";
		//Bundle bundle = this.getArguments();
		int mt = getArguments().getInt("number", 0);
		num = (mt + 1) + ""; 
		
		for (int i = 0; i < Global.bonusQues.size(); i++) {
			if(Global.bonusQues.get(i) == mt)
			{
				QuestionHeading.setText("   Bonus Question");
				bonusPoints.setVisibility(View.VISIBLE);
				num = "2X";
				Number.setTextColor(getResources().getColor(R.color.whiteBackground));
				Number.setPadding(0, 0, 0, 8);
				bonusPoints.setText("POINTS");
				
				if(FragmentSettings.soundCheck.equals("On")) {
			    mTimerTask = new TimerTask() {
						@Override
						public void run() {
							 MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.bonus);
						     mp.start();
						}
				  };
				  mTimer = new Timer();
				  mTimer.schedule(mTimerTask, 1500);
				}
			     
			}
		}
		
		for (int i = 0; i < Global.bonusQues.size(); i++) {
			if(Global.bonusQues.get(i) == mt-1)
			{
		       num = mt + "";
			}
		}
		
		Number.setText(num);
	}
}
