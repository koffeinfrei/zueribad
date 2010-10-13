package org.koffeinfrei.zueribad;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.view.View;

/**
 * This is the base class for the first level activities inside the tabs.
 * 
 * @author alexis.reigel
 *
 */
public abstract class FirstLevelActivity extends ActivityGroup {
	@Override
    public void onBackPressed() {
    	Class<? extends ActivityGroup> currentClass = this.getClass();
		LocalActivityManager localActivityManager = getLocalActivityManager();
		Activity currentActivity = localActivityManager.getCurrentActivity();
		
		//moveTaskToBack(true);
		
		// don't handle if the current activity if it has a parent
		// (which is the case for DetailsActivity
//		System.out.println("-->"+currentActivity + "\n" + currentClass);
//		if (currentActivity != null){
//			System.out.println("->"+currentActivity.getClass());
//		}
		if (currentActivity == null || currentActivity.getParent() == null){//currentActivity.getClass().equals(currentClass)
	    	super.onBackPressed();
    	}
    	else{
	    	Intent overviewIntent = new Intent(getApplicationContext(), currentClass);
	        overviewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        View intentView = localActivityManager.startActivity("overviewActivity", overviewIntent).getDecorView();
	        setContentView(intentView);
		    return;
    	}
    }
}
