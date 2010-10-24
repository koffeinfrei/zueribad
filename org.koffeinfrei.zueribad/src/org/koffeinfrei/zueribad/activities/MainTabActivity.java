package org.koffeinfrei.zueribad.activities;

import org.koffeinfrei.zueribad.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * This class is the actual tab container. It creates the
 * tab activities that reside inside each tab.
 * 
 * @author alexis.reigel
 *
 */
public class MainTabActivity extends TabActivity {
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
				NearLocationActivity.class, 
				R.string.tab_title_nearlocation, 
				R.drawable.tab_nearlocation);
	}

	private void createTab(Class<? extends FirstLevelActivity> activityClass, int titleResourceId, int iconResourceId) {
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
