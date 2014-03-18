package nz.co.engagenz.copingkete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;



public class SplashActivity extends Activity{
	private ImageView logo = null;
	private AnimationSet animSet = null;
	private Animation logoAnimation1 = null;
	private Animation logoAnimation2 = null;
	private Intent it = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		it = new Intent();
		logo = (ImageView)findViewById(R.id.imageViewLogo);
		animSet = new AnimationSet(true);
		logoAnimation1 = new AlphaAnimation(1.0f, 0.1f);
		logoAnimation1.setDuration(1000);
		logoAnimation2 = new TranslateAnimation(0, 0, 0, -500);
		logoAnimation2.setDuration(1000);
		logoAnimation2.setAnimationListener(new RemoveAnimationListener());
		animSet.setStartOffset(3000);
		animSet.addAnimation(logoAnimation1);
		animSet.addAnimation(logoAnimation2);
		animSet.setFillAfter(true);
		logo.startAnimation(animSet);
	}
	private class RemoveAnimationListener implements AnimationListener {
		public void onAnimationEnd(Animation animation) {
			it.setClass(SplashActivity.this, MainActivity.class);
			startActivity(it);
		}

		public void onAnimationRepeat(Animation animation) {

		}

		public void onAnimationStart(Animation animation) {

		}
	}
	@Override
    protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	finish();
}
}
