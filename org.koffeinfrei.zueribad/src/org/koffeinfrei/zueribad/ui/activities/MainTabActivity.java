package org.koffeinfrei.zueribad.ui.activities;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.BathRepository;

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
                DetailsActivity.class,
                R.string.tab_title_details,
                R.drawable.tab_details);

        // disable details tab on default
        getTabHost().getTabWidget().getChildTabViewAt(Constants.TAB_DETAILS_INDEX).setEnabled(false);

        setTabColors(getTabHost());
    }

    private void createTab(Class<? extends Activity> activityClass, int titleResourceId, int iconResourceId) {
        final TabHost tabHost = getTabHost();
        Intent intent = new Intent(this, activityClass).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Initialize a TabSpec for each tab and add it to the TabHost
        TabHost.TabSpec spec = tabHost
                .newTabSpec(activityClass.getName())
                .setIndicator(
                        getText(titleResourceId),
                        getResources().getDrawable(iconResourceId))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTabColors(tabHost);
            }
        });
    }

    private void setTabColors(TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); ++i) {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }

        // TODO find a way to reference from colors.xml, or put in Constants.java
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#66c0c0c0"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_reload_data:
                // temp. switch to fav tab, to switch back to overview, which triggers an update
                if (getTabHost().getCurrentTab() == Constants.TAB_OVERVIEW_INDEX){
                    getTabHost().setCurrentTab(Constants.TAB_FAVORITES_INDEX);
                }

                // clear bath data
                BathRepository.getInstance().clear();

                // call overview activity that will fetch the new data
                getTabHost().setCurrentTab(Constants.TAB_OVERVIEW_INDEX);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
