package com.tenpearls.sharkweektrivia;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileAdapter extends ArrayAdapter<UserProfile> {

private ArrayList<UserProfile> language;
	
	public ProfileAdapter(Context context, int resourceId,
			ArrayList<UserProfile> language) {
		super(context, resourceId, language);
		this.language = language; 
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
	    
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.userprofile_listitem, null);
		}
		
		UserProfile lang = language.get(position);

		if (lang != null) {

			TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
			//TextView langName = (TextView) v.findViewById(R.id.txtLangName);
			TextView score = (TextView) v.findViewById(R.id.txtScore);
			//TextView langPopularity = (TextView) v.findViewById(R.id.txtLangPopularity);
		    ImageView forwardImg = (ImageView) v.findViewById(R.id.imgForward);
		    //forwardImg.getLayoutParams().height = 10;
		    //forwardImg.getLayoutParams().width = 10;
		    
		    Typeface typeFace=Typeface.createFromAsset(v.getContext().getAssets(),"fonts/MondaBold.ttf");
		    score.setTypeface(typeFace);
		    
		    Typeface typeFacer=Typeface.createFromAsset(v.getContext().getAssets(),"fonts/MondaRegular.ttf");
		    txtTitle.setTypeface(typeFacer);
		    
		    
			if (txtTitle != null) {
				txtTitle.setText(lang.getTitle());
			}
			if (score != null && lang.getScore()!=0) {
				score.setText(lang.getScore() + "");
			}
			else {
				score.setVisibility(View.GONE);
			}
			if (forwardImg != null && lang.getPicture()!=0) {
				forwardImg.setImageResource(lang.getPicture());
			}
			else {
				forwardImg.setVisibility(View.GONE);
			}
		}

		return v;
	}
}

