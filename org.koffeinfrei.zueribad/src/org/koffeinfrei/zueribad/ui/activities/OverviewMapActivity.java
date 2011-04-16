package org.koffeinfrei.zueribad.ui.activities;

import android.app.TabActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
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
        setContentView(R.layout.overview_map);

        Hashtable<Integer,Bath> baths = BathRepository.getInstance().getAll();

        if(baths == null)
		{
            setCurrentTab(Constants.TAB_OVERVIEW_INDEX);
        }
        else
        {
            MapView mapView = (MapView) findViewById(R.id.overview_map_content_map);

            mapView.getController().animateTo(baths.get(0).getGeoPoint());
            mapView.getController().setZoom(15);
            mapView.setBuiltInZoomControls(true);
            mapView.invalidate();

            List<Overlay> mapOverlays = mapView.getOverlays();
            // TODO use different markers for different bath types
            Drawable marker = this.getResources().getDrawable(R.drawable.ic_map_marker_beach);
            MapItemizedOverlay itemizedOverlay = new MapItemizedOverlay(marker, mapView, this);

            for (Bath bath : baths.values()) {
                itemizedOverlay.addOverlay(bath.getId(),
                        new OverlayItem(bath.getGeoPoint(), bath.getName(), null));
            }

            mapOverlays.add(itemizedOverlay);
        }
    }

//	private void setMap(Bath bath) {
//		MapView mapView = (MapView) findViewById(R.id.details_section_content_map);
//		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//		try {
//			List<Address> addresses = geocoder.getFromLocationName("Wasserwerkstrasse 141, 8037 Zürich", 1);
//
//			if (!addresses.isEmpty()){
//				Address address = addresses.get(0);
//				GeoPoint geoPoint = new GeoPoint(
//						(int)(address.getLatitude() * 1000000.0),
//						(int)(address.getLongitude() * 1000000.0));
//				mapView.getController().setCenter(geoPoint);
//				mapView.setBuiltInZoomControls(true);
//			}
//			else{
//				System.err.println("Address was not found"); // TODO
//			}
//		}
//		catch (IOException e) {
//			System.err.println("Address was not found. " + e.getMessage()); // TODO
//		}
//	}

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
