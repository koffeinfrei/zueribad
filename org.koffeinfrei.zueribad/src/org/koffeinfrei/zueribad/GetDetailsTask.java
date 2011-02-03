package org.koffeinfrei.zueribad;

import android.app.TabActivity;
import org.koffeinfrei.zueribad.activities.DetailsActivity;
import org.koffeinfrei.zueribad.activities.FirstLevelActivity;
import org.koffeinfrei.zueribad.activities.MainTabActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import org.koffeinfrei.zueribad.models.BathRepository;

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
        BathRepository.getInstance().setCurrent(bathId);
    	activity.setCurrentTab(FirstLevelActivity.TAB_DETAILS_INDEX);

		activity.dismissDialog(FirstLevelActivity.PROGRESS_DIALOG);
    }
}