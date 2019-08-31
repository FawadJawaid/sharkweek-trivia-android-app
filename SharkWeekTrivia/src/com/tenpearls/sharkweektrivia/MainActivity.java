package com.tenpearls.sharkweektrivia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.google.gson.JsonObject;

public class MainActivity extends Activity {

	ImageView splashLogo;
	ImageView splashText;
	ImageButton fbLogin;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	
	private String appKey = "b382065c-96c5-4cdd-8812-69d1b9a2ae97";
	private String appSecret = "3eb23fe2-854d-41a5-a4bf-710654111066";
	private String redirect = "google.com";
	private String android_id;
	private String platform = "iphone";
	private String Access;
	FacebookPost fbpost;
	String fbPostResult, fbAccess;
	JSONObject jsonObj, obj;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
		 Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

	        Session session = Session.getActiveSession();
	        if (session == null) {
	            if (savedInstanceState != null) {
	                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
	            }
	            if (session == null) {
	                session = new Session(this);
	            }
	            Session.setActiveSession(session);
	            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	            }
	        }

	        try {
				updateView();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Animations();
		
		//setListeners();
	}

	private void initUI() {
		setContentView(R.layout.activity_main);
		splashLogo = (ImageView) findViewById(R.id.splashLogo);
		splashText = (ImageView) findViewById(R.id.splashText);
		fbLogin = (ImageButton) findViewById(R.id.fbloginButton);
		
		android_id = Secure.getString(getApplicationContext().getContentResolver(),
	            Secure.ANDROID_ID);
	}

	private void Animations() {		
		Animation RightImageSwipe = AnimationUtils.loadAnimation(this,
				R.anim.right_slide);
		splashText.startAnimation(RightImageSwipe);

		Animation fadeIn = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.fade);
		fbLogin.startAnimation(fadeIn);

		Animation zoomIn = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.zoominout);
		splashLogo.startAnimation(zoomIn);
	}
	
	
	
	private void navigateToPlayScreen() {

		Intent play = new Intent(getApplicationContext(),
				QuizSelectionActivity.class); //Temporary will change it to "QuizSelectionActivity"
		//play.putExtra("ACCESS", fbAccess);
		startActivity(play);
	}
	
	
	@Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

    private void updateView() throws InterruptedException, ExecutionException {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
           
        	Access = session.getAccessToken();
            fbPostResult = new HttpAsyncTask().execute("http://ec2-54-89-82-163.compute-1.amazonaws.com:3000/v1/oauth/facebook").get();
            try {
				jsonObj = new JSONObject(fbPostResult);
				fbAccess = jsonObj.getString("results");
				//fbAccess = obj.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				   /*new AlertDialog.Builder(getApplicationContext())
					.setTitle("Server Not Responding!")
					.setMessage("Please, Contact your Server")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent a = new Intent(Intent.ACTION_MAIN);
							    	a.addCategory(Intent.CATEGORY_HOME);
							    	a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							    	startActivity(a);
								}
								
					}).show();*/
			}
            
            Global.accessToken = fbAccess;
        	Log.v("FINAL RESULT", fbPostResult);
        	Log.v("ACCESS TOKEN", Access);
        	Log.v("FB ACCESS", Global.accessToken);
        	
        	navigateToPlayScreen();
            
        } else {
            
            fbLogin.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            try {
				updateView();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            fbpost = new FacebookPost();
            fbpost.setKey(appKey);
            fbpost.setSecret(appSecret);
            fbpost.setUrl(redirect);
            fbpost.setUdid(android_id);
            fbpost.setPlatform(platform);
            fbpost.setAccToken(Access);
            
            return POST(urls[0],fbpost);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplication(), "Data Sent!", Toast.LENGTH_LONG).show();
       }
    }
	
	public static String POST(String url, FacebookPost fbpost) {
        InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
 
            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("key", fbpost.getKey());
            jsonObject.accumulate("secret", fbpost.getSecret());
            jsonObject.accumulate("redirect_url", fbpost.getUrl());
            jsonObject.accumulate("udid", fbpost.getUdid());
            jsonObject.accumulate("platform", fbpost.getPlatform());
            jsonObject.accumulate("access_token", fbpost.getAccToken());
            
 
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
 
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        Log.v("RESULT", result);
        // 11. return result
        return result;
    }
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
	
	@Override
    public void onBackPressed() {
		
		Intent a = new Intent(Intent.ACTION_MAIN);
    	a.addCategory(Intent.CATEGORY_HOME);
    	a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(a);
	}
}
