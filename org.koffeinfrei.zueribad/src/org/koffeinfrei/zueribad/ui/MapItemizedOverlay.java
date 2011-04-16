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
}
