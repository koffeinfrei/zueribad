/*
 *  Koffeinfrei Zueribad
 *  Copyright (C) 2011  Alexis Reigel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        	AlertDialog.Builder builder = new AlertDialog.Builder(FirstLevelActivity.this);
        	builder.setMessage(getString(R.string.dialog_error) + ":\n" + errorMessage)
                    .setCancelable(true)
        	       .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                dialog.cancel();
        	           }
        	       });
            // reset error message for next time
            errorMessage = null;
            
            AlertDialog alertDialog = builder.create();
            // TODO: set title and icon
            alertDialog.show();
            return alertDialog;
        }
        return null;
    }

    public void setCurrentTab(int index) {
        ((TabActivity)getParent()).getTabHost().setCurrentTab(index);
    }

    public void activateTab(int index) {
        setTabEnabled(index, true);
    }

    public void deactivateTab(int index) {
        setTabEnabled(index, false);
    }

    private void setTabEnabled(int index, Boolean enabled){
        ((TabActivity)getParent()).getTabWidget().getChildTabViewAt(index).setEnabled(enabled);
    }
}
