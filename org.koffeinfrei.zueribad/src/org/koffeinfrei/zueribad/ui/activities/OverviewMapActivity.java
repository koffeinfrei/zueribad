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

import android.app.TabActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.*;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.ui.MapItemizedOverlay;

import java.util.Hashtable;
import java.util.List;

public class OverviewMapActivity extends MapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Hashtable<Integer,Bath> baths = BathRepository.getInstance().getAll();

        if(baths == null)
		{
            setCurrentTab(Constants.TAB_OVERVIEW_INDEX);
        }
        else
        {
            setContentView(R.layout.overview_map);
            
            MapView mapView = (MapView) findViewById(R.id.overview_map_content_map);
            mapView.setBuiltInZoomControls(true);
            mapView.invalidate();

            // set bath markers
            List<Overlay> mapOverlays = mapView.getOverlays();
            // TODO use different markers for different bath types
            Drawable marker = this.getResources().getDrawable(R.drawable.ic_map_marker_beach);
            MapItemizedOverlay itemizedOverlay = new MapItemizedOverlay(marker, mapView, this);

            for (Bath bath : baths.values()) {
                itemizedOverlay.addOverlay(bath.getId(),
                        new OverlayItem(bath.getGeoPoint(), bath.getName(), null));
            }

            mapOverlays.add(itemizedOverlay);

            // center and zoom
            MapController mapViewController = mapView.getController();
            mapViewController.animateTo(Constants.MAP_CENTRE);
            mapViewController.zoomToSpan(itemizedOverlay.getLatSpanE6(), itemizedOverlay.getLonSpanE6());
        }
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

    public void setCurrentTab(int index) {
        ((TabActivity)getParent()).getTabHost().setCurrentTab(index);
    }

    public void activateTab(int index) {
        setTabEnabled(index, true);
    }

    private void setTabEnabled(int index, Boolean enabled){
        ((TabActivity)getParent()).getTabWidget().getChildTabViewAt(index).setEnabled(enabled);
    }

    @Override
    public void onBackPressed() {
        setCurrentTab(Constants.TAB_OVERVIEW_INDEX);
    }
}
