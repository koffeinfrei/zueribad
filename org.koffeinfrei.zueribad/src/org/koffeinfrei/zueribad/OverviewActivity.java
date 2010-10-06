package org.koffeinfrei.zueribad;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class OverviewActivity extends FirstLevelActivity {
	private ListView bathList;
	private EditText filterText;
	private OverviewListAdapter adapter;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.overview);
        
        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);

        bathList = (ListView)findViewById(R.id.list);
        adapter = new OverviewListAdapter(this);
        
        bathList.setAdapter(adapter);
        bathList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(getApplicationContext(), DetailsActivity.class);
                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                detailsIntent.putExtra("SelectedItemId", (int)id); // TODO add constant
                View intentView = getLocalActivityManager().startActivity("detailsActivity", detailsIntent).getDecorView();
                setContentView(intentView);
            }
        });
        
        //addAnimationToListLoading();
    }

	private void addAnimationToListLoading() {
		AnimationSet animationSet = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(50);
        animationSet.addAnimation(animation);

        animation = new TranslateAnimation(
        		Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
        		Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(500);
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
            adapter.getFilter().filter(s, new Filter.FilterListener(){
            	public void onFilterComplete(int count) {
            		// force refresh of list and prevent illegalstateexception
            		bathList.setAdapter(adapter); 
                }
            });
        }
    };
}

