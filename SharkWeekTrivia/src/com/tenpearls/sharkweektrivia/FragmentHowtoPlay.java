package com.tenpearls.sharkweektrivia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHowtoPlay extends Fragment {
	
	TextView heading, content;
	
	public FragmentHowtoPlay() {
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        return inflater.inflate(R.layout.fragment_how_to_play, container, false);
    }

	public void onActivityCreated(Bundle savedInstanceState) {

		initViews(savedInstanceState);
	}

	void initViews(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View v = getView();
		
		heading = (TextView) v.findViewById(R.id.howtoplayHeader);
		content = (TextView) v.findViewById(R.id.howtoplayContent);

		Typeface typeFace = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/MondaRegular.ttf");
		Typeface typeFaceBold = Typeface.createFromAsset(
				getActivity().getAssets(), "fonts/MondaBold.ttf");
		content.setTypeface(typeFace);
		heading.setTypeface(typeFaceBold);
	}
}
