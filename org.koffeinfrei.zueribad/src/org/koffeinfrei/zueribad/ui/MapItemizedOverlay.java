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

import android.graphics.drawable.Drawable;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.ui.activities.OverviewMapActivity;

import java.util.Hashtable;

public class MapItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

    private Hashtable<Integer, OverlayItem> overlays = new Hashtable<Integer, OverlayItem>();
    private OverviewMapActivity mapActivity;

    public MapItemizedOverlay(Drawable defaultMarker, MapView mapView, OverviewMapActivity mapActivity) {
        super(boundCenter(defaultMarker), mapView);
        this.mapActivity = mapActivity;
    }

    @Override
    protected OverlayItem createItem(int i) {
        return overlays.get(i);
    }

    @Override
    public int size() {
        return overlays.size();
    }

    @Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		BathRepository.getInstance().setCurrent(index);
        mapActivity.activateTab(Constants.TAB_DETAILS_INDEX);
        mapActivity.setCurrentTab(Constants.TAB_DETAILS_INDEX);

		return true;
	}

    public void addOverlay(int i, OverlayItem overlay) {
        overlays.put(i, overlay);
        populate();
    }

    public void tap(int i){
        onTap(i);
    }
}
