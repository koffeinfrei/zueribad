package org.koffeinfrei.zueribad.ui.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.ui.FavoritesListAdapter;
import org.koffeinfrei.zueribad.ui.GetDetailsTask;

import java.io.IOException;

public class FavoritesActivity extends FirstLevelActivity {
	private ListView bathList;
	private FavoritesListAdapter adapter;
	private GetDetailsTask detailTask;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favorites);

        bathList = (ListView)findViewById(R.id.favorites_list);
        adapter = new FavoritesListAdapter(this);
        
        bathList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				showDialog(Constants.PROGRESS_DIALOG);

            	detailTask = new GetDetailsTask(FavoritesActivity.this);
            	detailTask.execute((int)id);
            }
        });
        
        bathList.setAdapter(adapter);
        
        registerForContextMenu(bathList);
    }

	@Override
    protected Dialog onCreateDialog(int id) {
    	final Dialog dialog = super.onCreateDialog(id);
    	
    	switch (id) {
        case Constants.PROGRESS_DIALOG:
        	final ProgressDialog progressDialog = (ProgressDialog) dialog;
            progressDialog.setButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	if (detailTask != null){
                		detailTask.cancel(true);
                	}
                	progressDialog.dismiss();
                }
                
            });
            return progressDialog;
    	}
        return null;
    }
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	
    	menu.setHeaderTitle(R.string.title_bath);
    	menu.setHeaderIcon(R.drawable.tab_favorites_white);
    	menu.add(0, 1, 0, R.string.menu_removefromfavorites);
    	    	
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    	
    	try {
			bathRepository.removeFromFavorites(getApplicationContext(), (int)info.id);
			bathList.setAdapter(adapter);
		} catch (IOException e) {
			
			errorMessage = getString(R.string.error_savesettings);
			showDialog(Constants.ERROR_DIALOG);
			
			e.printStackTrace();
		}
    	
    	return super.onContextItemSelected(item);
    }
}
