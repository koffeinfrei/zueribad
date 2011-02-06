package org.koffeinfrei.zueribad.ui.activities;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;

/**
 * This class is the actual tab container. It creates the
 * tab activities that reside inside each tab.
 * 
 * @author alexis.reigel
 *
 */
public class
        MainTabActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		createTab(
				OverviewActivity.class, 
				R.string.tab_title_overview, 
				R.drawable.tab_overview);

		createTab(
				FavoritesActivity.class, 
				R.string.tab_title_favorites, 
				R.drawable.tab_favorites);

        createTab(
				DetailsActivity.class,
				R.string.tab_title_details,
				R.drawable.tab_details);

        // disable details tab on default
        getTabHost().getTabWidget().getChildTabViewAt(Constants.TAB_DETAILS_INDEX).setEnabled(false);
	}

	private void createTab(Class<? extends Activity> activityClass, int titleResourceId, int iconResourceId) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, activityClass).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Initialize a TabSpec for each tab and add it to the TabHost
		TabHost.TabSpec spec = tabHost
				.newTabSpec(activityClass.getName())
				.setIndicator(
                        getText(titleResourceId),
                        getResources().getDrawable(iconResourceId))
				.setContent(intent);
		tabHost.addTab(spec);
	}
}
