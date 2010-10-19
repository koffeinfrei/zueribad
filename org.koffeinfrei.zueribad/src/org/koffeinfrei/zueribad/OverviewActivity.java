package org.koffeinfrei.zueribad;

import java.io.IOException;

import org.koffeinfrei.zueribad.models.BathRepository;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class OverviewActivity extends FirstLevelActivity {
	private static final int PROGRESS_DIALOG = 1;
	private static final int ERROR_DIALOG = 2;
	
	private String errorMessage;
	
	private BathRepository bathRepository;
	
	private ListView bathList;
	private EditText filterText;
	private OverviewListAdapter adapter;
	private GetDetailsTask detailTask;
	private GetListTask listTask;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.overview);
        
        bathRepository = BathRepository.getInstance();
        
        loadListData(); // TODO do this only on demand, or let user set in settings
        
        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);

        bathList = (ListView)findViewById(R.id.list);
        adapter = new OverviewListAdapter(this);
        
        bathList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// after back from details, we are inside firstlevelactivity somehow TODO fix this
				Activity parentActivity = getParent();
				if (parentActivity instanceof MainTabActivity) {
					showDialog(PROGRESS_DIALOG);
				}
				else {
					parentActivity.showDialog(PROGRESS_DIALOG);
				}
				            	
            	detailTask = new GetDetailsTask();
            	detailTask.execute((int)id);
            }
        });
        
        registerForContextMenu(bathList);        

        //addAnimationToListLoading();
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case PROGRESS_DIALOG:
        	final ProgressDialog progressDialog = new ProgressDialog(OverviewActivity.this);
            //progressDialog.setIcon(R.drawable.icon);
            progressDialog.setMessage(getString(R.string.dialog_downloadingdetails));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.setButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {; //TODO i18n
                public void onClick(DialogInterface dialog, int whichButton) {
                	if (detailTask != null){
                		detailTask.cancel(true);
                	}
                	if (listTask != null) {
                		listTask.cancel(true);
                	}
                	progressDialog.dismiss();
                }
                
            });
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

//	private void addAnimationToListLoading() {
//		AnimationSet animationSet = new AnimationSet(true);
//        Animation animation = new AlphaAnimation(0.0f, 1.0f);
//        animation.setDuration(50);
//        animationSet.addAnimation(animation);
//
//        animation = new TranslateAnimation(
//        		Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
//        		Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
//        );
//        animation.setDuration(500);
//        animationSet.addAnimation(animation);
//
//        LayoutAnimationController controller = new LayoutAnimationController(animationSet, 0.5f);
//                
//        bathList.setLayoutAnimation(controller);
//	}

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
            adapter.getFilter().filter(s, new Filter.FilterListener(){
            	public void onFilterComplete(int count) {
            		// force refresh of list and prevent illegalstateexception
            		bathList.setAdapter(adapter); 
                }
            });
        }
    };
	    
    private class GetDetailsTask extends AsyncTask<Integer, Void, Integer> {
		@Override
    	protected Integer doInBackground(Integer... bathId) {
    		bathRepository.get(bathId[0]); // TODO add preload method or something?
    		
    		return bathId[0];
    	}

        protected void onPostExecute(Integer bathId) {
        	Intent detailsIntent = new Intent(getApplicationContext(), DetailsActivity.class)
            	.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        		.putExtra("SelectedItemId", bathId); // TODO add constant
            View intentView = getLocalActivityManager().startActivity("detailsActivity", detailsIntent).getDecorView();
            setContentView(intentView);
            
            // after back from details, we are inside firstlevelactivity somehow TODO fix this
			Activity parentActivity = getParent();
			if (parentActivity instanceof MainTabActivity) {
				dismissDialog(PROGRESS_DIALOG);
			}
			else {
				parentActivity.dismissDialog(PROGRESS_DIALOG);
			}
			
        }
    }
    
    private class GetListTask extends AsyncTask<Void, Void, Void> {
    	@Override
		protected Void doInBackground(Void... params) {
			try {
				bathRepository.init(getApplicationContext());
			} catch (IOException e) {
				e.printStackTrace();
				dismissDialog(PROGRESS_DIALOG);
				errorMessage = getString(R.string.error_loadsettings);
				showDialog(ERROR_DIALOG);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				dismissDialog(PROGRESS_DIALOG);
				errorMessage = getString(R.string.error_loadsettings);
				showDialog(ERROR_DIALOG);
			}
			return null;
		}

        protected void onPostExecute(Void param) {
        	bathList.setAdapter(adapter);
        	
            // after back from details, we are inside firstlevelactivity somehow TODO fix this
			Activity parentActivity = getParent();
			if (parentActivity instanceof MainTabActivity) {
				dismissDialog(PROGRESS_DIALOG);
			}
			else {
				parentActivity.dismissDialog(PROGRESS_DIALOG);
			}
			
        }
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	
    	menu.setHeaderTitle(R.string.title_bath);
    	menu.setHeaderIcon(R.drawable.tab_favorites_white);
    	menu.add(0, 1, 0, R.string.menu_addtofavorites);
    	    	
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    	
    	try {
			bathRepository.addToFavorites(getApplicationContext(), (int)info.id);
		} catch (IOException e) {
			
			errorMessage = getString(R.string.error_savesettings);
			showDialog(ERROR_DIALOG);
			
			e.printStackTrace();
		}
    	
    	return super.onContextItemSelected(item);
    }
    
    private void loadListData() {
    	// after back from details, we are inside firstlevelactivity somehow TODO fix this
		Activity parentActivity = getParent();
		if (parentActivity instanceof MainTabActivity) {
			showDialog(PROGRESS_DIALOG);
		}
		else {
			parentActivity.showDialog(PROGRESS_DIALOG);
		}
		            	
    	listTask = new GetListTask();
    	listTask.execute((Void[])null);
    }
}

