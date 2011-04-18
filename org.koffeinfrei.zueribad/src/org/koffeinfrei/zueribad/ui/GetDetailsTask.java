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

package org.koffeinfrei.zueribad.ui;

import android.os.AsyncTask;
import org.koffeinfrei.zueribad.config.Constants;
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
		this.activity.bathRepository.get(bathId[0]);
		
		return bathId[0];
	}

    protected void onPostExecute(Integer bathId) {
        BathRepository.getInstance().setCurrent(bathId);

        activity.activateTab(Constants.TAB_DETAILS_INDEX);
        activity.setCurrentTab(Constants.TAB_DETAILS_INDEX);

		//activity.dismissDialog(Constants.PROGRESS_DIALOG);
    }
}