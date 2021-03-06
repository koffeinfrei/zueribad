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

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.ui.GetDetailsTask;
import org.koffeinfrei.zueribad.ui.OverviewListAdapter;
import org.koffeinfrei.zueribad.utils.AndroidI18nException;

public class OverviewActivity extends FirstLevelActivity {
	private ListView bathList;
	private EditText filterText;
	private OverviewListAdapter adapter;
	private GetDetailsTask detailTask;
	private GetListTask listTask;

    private static CharSequence filterTextString;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_list);

        filterText = (EditText) findViewById(R.id.overview_search_box);
        filterText.addTextChangedListener(filterTextWatcher);

        bathList = (ListView)findViewById(R.id.overview_list);
        adapter = new OverviewListAdapter(this);
        
        bathList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//showDialog(Constants.PROGRESS_DIALOG);
				            	
            	detailTask = new GetDetailsTask(OverviewActivity.this);
            	detailTask.execute((int)id);
            }
        });
        
        registerForContextMenu(bathList);

        loadListData();

        // get saved filter text
        if (filterTextString != null && filterTextString != ""){
            filterText.setText(filterTextString);
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
    	final Dialog dialog = super.onCreateDialog(id);
    	
    	switch (id) {
        case Constants.PROGRESS_DIALOG:
        	final ProgressDialog progressDialog = (ProgressDialog) dialog;
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
                public void onCancel(DialogInterface dialogInterface) {
                    assertHasBaths();

                    if (detailTask != null){
                		detailTask.cancel(true);
                	}
                	if (listTask != null) {
                		listTask.cancel(true);
                	}
                }
            });
            return progressDialog;
    	}
        return null;
    }

	private void addAnimationToListLoading() {
		AnimationSet animationSet = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(100);
        animationSet.addAnimation(animation);

        animation = new TranslateAnimation(
        		Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
        		Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        animationSet.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(animationSet, 0.5f);

        bathList.setLayoutAnimation(controller);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        filterText.removeTextChangedListener(filterTextWatcher);
    }
    
    private TextWatcher filterTextWatcher = new TextWatcher() {
    	
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (adapter != null){
                filterTextString = s;
                adapter.getFilter().filter(s, new Filter.FilterListener(){
                    public void onFilterComplete(int count) {
                        // force refresh of list and prevent illegalstateexception
                        bathList.setAdapter(adapter);
                    }
                });
            }
        }
    };
	    
    private class GetListTask extends AsyncTask<Void, Void, Void> {
    	@Override
		protected Void doInBackground(Void... params) {
            try {
				bathRepository.init(getApplicationContext());
			} catch (AndroidI18nException e) {
				Log.e(this.getClass().getSimpleName(), e.getMessage());
                //e.printStackTrace();
				dismissDialog(Constants.PROGRESS_DIALOG);
				errorMessage = getString(e.getResourceStringId());
				//showDialog(Constants.ERROR_DIALOG);
			}
            return null;
		}

        protected void onPostExecute(Void param) {
        	assertHasBaths();

            if (errorMessage != null){
                dismissDialog(Constants.PROGRESS_DIALOG);
                showDialog(Constants.ERROR_DIALOG);
            }
            else{
                bathList.setAdapter(adapter);

                // TODO: find a better way to find out whether the dialog is showing
                try {
                    dismissDialog(Constants.PROGRESS_DIALOG);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
    	boolean isAlreadyFavorite = bathRepository.isFavorite((int)info.id);
    	
    	menu.setHeaderTitle(R.string.title_bath);
    	menu.add(0, 1, 0, R.string.menu_addtofavorites).setEnabled(!isAlreadyFavorite);
    	    	
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    	
    	try {
			bathRepository.addToFavorites(getApplicationContext(), (int)info.id);
            setCurrentTab(Constants.TAB_FAVORITES_INDEX);
		} catch (AndroidI18nException e) {
			
			errorMessage = getString(e.getResourceStringId());
			showDialog(Constants.ERROR_DIALOG);
			
			e.printStackTrace();
		}
    	
    	return super.onContextItemSelected(item);
    }
    
    private void loadListData() {
		if (bathRepository.getAll() == null){
            filterTextString = null;

            if (listTask == null){
                listTask = new GetListTask();
            }
            if (listTask.getStatus() == AsyncTask.Status.PENDING){
                showDialog(Constants.PROGRESS_DIALOG);

                listTask.execute((Void[])null);

                addAnimationToListLoading();
            }
        }
        else{
            bathList.setAdapter(adapter);
        }
    }

    private void assertHasBaths(){
        if (bathRepository.getAll() == null){
            deactivateTab(Constants.TAB_FAVORITES_INDEX);
            deactivateTab(Constants.TAB_OVERVIEW_MAP_INDEX);
            deactivateTab(Constants.TAB_DETAILS_INDEX);
            
            filterText.setVisibility(View.INVISIBLE);
        }
        else {
            activateTab(Constants.TAB_FAVORITES_INDEX);
            activateTab(Constants.TAB_OVERVIEW_MAP_INDEX);
            if (bathRepository.getCurrent() != null){
                activateTab(Constants.TAB_DETAILS_INDEX);
            }
            
            filterText.setVisibility(View.VISIBLE);
        }
    }
}

