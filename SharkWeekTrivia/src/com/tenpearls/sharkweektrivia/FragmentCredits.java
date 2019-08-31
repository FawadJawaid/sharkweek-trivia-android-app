package com.tenpearls.sharkweektrivia;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentCredits extends Fragment {

	Button termsofuseBtn, privpolicyBtn, imagecreditsBtn;
	TextView developedBy, rightsReserved;
	TextView heading;

	public FragmentCredits() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_credits, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		initViews(savedInstanceState);
	}

	public void initViews(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View viewFragment = getView();

		termsofuseBtn = (Button) viewFragment.findViewById(R.id.termsUsebtn);

		privpolicyBtn = (Button) viewFragment.findViewById(R.id.privacyPolbtn);

		imagecreditsBtn = (Button) viewFragment
				.findViewById(R.id.imgCreditsbtn);

		developedBy = (TextView) viewFragment
				.findViewById(R.id.developedbyText);

		rightsReserved = (TextView) viewFragment
				.findViewById(R.id.rightsReservedText);

		heading = (TextView) viewFragment.findViewById(R.id.creditsHeading);

		termsofuseBtn.setOnClickListener(termsofuseListener);
		privpolicyBtn.setOnClickListener(privpolicyListener);
		imagecreditsBtn.setOnClickListener(imagecreditsListener);

		Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/MondaRegular.ttf");
		Typeface typeFaceBold = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/MondaBold.ttf");
		termsofuseBtn.setTypeface(typeFace);
		privpolicyBtn.setTypeface(typeFace);
		imagecreditsBtn.setTypeface(typeFace);
		developedBy.setTypeface(typeFace);
		rightsReserved.setTypeface(typeFace);
		heading.setTypeface(typeFaceBold);
	}

	OnClickListener termsofuseListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent welcome = new Intent(getActivity(), TermsofUseActivity.class);
			startActivity(welcome);
		}
	};

	OnClickListener privpolicyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent welcome = new Intent(getActivity(),
					PrivacypolicyActivity.class);
			startActivity(welcome);
		}
	};

	OnClickListener imagecreditsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent welcome = new Intent(getActivity(),
					ImagecreditsActivity.class);
			startActivity(welcome);
		}
	};

}
