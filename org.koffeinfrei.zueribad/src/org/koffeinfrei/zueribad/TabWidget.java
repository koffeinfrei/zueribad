package org.koffeinfrei.zueribad;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabWidget extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Reusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, OverviewActivity.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("overview")
				.setIndicator(getText(R.string.tab_title_overview),
						res.getDrawable(R.drawable.tab_overview))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, FavoritesActivity.class);
		spec = tabHost
				.newTabSpec("favorites")
				.setIndicator(getText(R.string.tab_title_favorites),
						res.getDrawable(R.drawable.tab_favorites))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, NearLocationActivity.class);
		spec = tabHost
				.newTabSpec("nearlocation")
				.setIndicator(getText(R.string.tab_title_nearlocation),
						res.getDrawable(R.drawable.tab_nearlocation))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
}
