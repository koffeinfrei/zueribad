package org.koffeinfrei.zueribad;

import org.koffeinfrei.zueribad.activities.DetailsActivity;
import org.koffeinfrei.zueribad.activities.FirstLevelActivity;
import org.koffeinfrei.zueribad.activities.MainTabActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

public class GetDetailsTask extends AsyncTask<Integer, Void, Integer> {

	private final FirstLevelActivity activity;

	/**
	 * @param activity
	 */
	public GetDetailsTask(FirstLevelActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Integer doInBackground(Integer... bathId) {
		this.activity.bathRepository.get(bathId[0]); // TODO add preload method or something?
		
		return bathId[0];
	}

    protected void onPostExecute(Integer bathId) {
    	Intent detailsIntent = new Intent(this.activity.getApplicationContext(), DetailsActivity.class)
        	.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    		.putExtra("SelectedItemId", bathId); // TODO add constant
        View intentView = this.activity.getLocalActivityManager().startActivity("detailsActivity", detailsIntent).getDecorView();
        this.activity.setContentView(intentView);
        
        // after back from details, we are inside firstlevelactivity somehow TODO fix this
		Activity parentActivity = this.activity.getParent();
		if (parentActivity instanceof MainTabActivity) {
			activity.dismissDialog(FirstLevelActivity.PROGRESS_DIALOG);
		}
		else {
			parentActivity.dismissDialog(FirstLevelActivity.PROGRESS_DIALOG);
		}
		
    }
}