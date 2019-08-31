package com.tenpearls.sharkweektrivia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class QuestionNumDialogFragment extends DialogFragment {
	
	TextView Number;
	String num = "";
	
	@SuppressLint("NewApi")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		//dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				//WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.fragment_question_number);
		
		dialog.show();
		
		Number = (TextView) dialog.findViewById(R.id.questionNumber);
		Number.setText(num);

		
		return dialog;
	}

}
