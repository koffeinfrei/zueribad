package org.koffeinfrei.zueribad.ui;

import android.os.AsyncTask;
import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.ui.activities.FirstLevelActivity;

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