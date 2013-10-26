package com.testresizingviews;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

public class MainActivity extends Activity {

    private View mContainerView;
    private int mContainerHeight = -1;
    private int mContainerWidth = -1;
    private int mContainerExpandedHeight = -1;
    private boolean mExpanded = false;
    private Button mButton;

    private Animation.AnimationListener mAnimlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        mAnimlistener = new RedViewAnimListener();

        mContainerView = findViewById(R.id.containerView);
        mButton = (Button)findViewById(R.id.animButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContainerHeight < 0) {
                    mContainerHeight = mContainerView.getMeasuredHeight();
                    mContainerWidth = mContainerView.getMeasuredWidth();
                    mContainerExpandedHeight = mContainerHeight + dpToPx(100);
                }
                animate();
            }
        });
    }



    private void animate() {
        if(!mExpanded) {
            Animation animationContainer = new ResizeViewAnimation(mContainerView, mContainerWidth, mContainerHeight, mContainerWidth, mContainerExpandedHeight);
            animationContainer.setDuration(1500);
            animationContainer.setFillAfter(true);
            animationContainer.setAnimationListener(mAnimlistener);
            mContainerView.startAnimation(animationContainer);
        } else {
            Animation animationContainer = new ResizeViewAnimation(mContainerView, mContainerWidth, mContainerExpandedHeight, mContainerWidth, mContainerHeight);
            animationContainer.setDuration(1500);
            animationContainer.setFillAfter(true);
            animationContainer.setAnimationListener(mAnimlistener);
            mContainerView.startAnimation(animationContainer);
        }

    }

    private class RedViewAnimListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mExpanded = !mExpanded;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private int dpToPx(int dp){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
