package org.koffeinfrei.zueribad.ui.activities;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.BathRepository;

/**
 * This is the base class for the first level activities inside the tabs.
 * 
 * @author alexis.reigel
 *
 */
public abstract class FirstLevelActivity extends Activity {
    public BathRepository bathRepository;
	protected String errorMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		bathRepository = BathRepository.getInstance();
	}
	
//	@Override
//    public void onBackPressed() {
//    	Class<? extends ActivityGroup> currentClass = this.getClass();
//		LocalActivityManager localActivityManager = getLocalActivityManager();
//		Activity currentActivity = localActivityManager.getCurrentActivity();
//
//		//moveTaskToBack(true);
//
//		// don't handle if the current activity if it has a parent
//		// (which is the case for DetailsActivity
////		System.out.println("-->"+currentActivity + "\n" + currentClass);
////		if (currentActivity != null){
////			System.out.println("->"+currentActivity.getClass());
////		}
//		if (currentActivity == null || currentActivity.getParent() == null){//currentActivity.getClass().equals(currentClass)
//	    	super.onBackPressed();
//    	}
//    	else{
//	    	Intent overviewIntent = new Intent(getApplicationContext(), currentClass);
//	        overviewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	        View intentView = localActivityManager.startActivity("overviewActivity", overviewIntent).getDecorView();
//	        setContentView(intentView);
//		    return;
//    	}
//    }
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case Constants.PROGRESS_DIALOG:
        	final ProgressDialog progressDialog = new ProgressDialog(FirstLevelActivity.this);
            //progressDialog.setIcon(R.drawable.icon);
            progressDialog.setMessage(getString(R.string.dialog_downloadingdetails));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            return progressDialog;
        case Constants.ERROR_DIALOG:
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

    public void setCurrentTab(int index) {
        ((TabActivity)getParent()).getTabHost().setCurrentTab(index);
    }

    public void activateTab(int index) {
        ((TabActivity)getParent()).getTabWidget().getChildTabViewAt(index).setEnabled(true);
    }
}
