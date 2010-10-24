package org.koffeinfrei.zueribad;

import org.koffeinfrei.zueribad.models.BathRepository;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This is the base class for the first level activities inside the tabs.
 * 
 * @author alexis.reigel
 *
 */
public abstract class FirstLevelActivity extends ActivityGroup {
	protected static final int PROGRESS_DIALOG = 1;
	protected static final int ERROR_DIALOG = 2;
	protected String errorMessage;
	protected BathRepository bathRepository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		bathRepository = BathRepository.getInstance();
	}
	
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
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case PROGRESS_DIALOG:
        	final ProgressDialog progressDialog = new ProgressDialog(FirstLevelActivity.this);
            //progressDialog.setIcon(R.drawable.icon);
            progressDialog.setMessage(getString(R.string.dialog_downloadingdetails));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            return progressDialog;
        case ERROR_DIALOG:
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage(getString(R.string.dialog_error) + errorMessage)
        	       .setCancelable(false)
        	       .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                dialog.cancel();
        	           }
        	       });
        	return builder.create();
        }
        return null;
    }
}
