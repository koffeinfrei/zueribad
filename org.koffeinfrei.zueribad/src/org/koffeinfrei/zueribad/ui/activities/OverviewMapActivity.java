package org.koffeinfrei.zueribad.ui.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.maps.*;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.ui.CollapsiblePanel;
import org.koffeinfrei.zueribad.ui.MapItemizedOverlay;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class OverviewMapActivity extends MapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_map);

        Hashtable<Integer,Bath> baths = BathRepository.getInstance().getAll();

        MapView mapView = (MapView) findViewById(R.id.overview_map_content_map);

        mapView.getController().animateTo(baths.get(0).getGeoPoint());
        mapView.getController().setZoom(17);
		mapView.setBuiltInZoomControls(true);
        mapView.invalidate();

        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.about);
        MapItemizedOverlay itemizedOverlay = new MapItemizedOverlay(drawable, this);

        itemizedOverlay.addOverlay(baths.get(0).getId(), new OverlayItem(baths.get(0).getGeoPoint(), baths.get(0).getName(), null));
        mapOverlays.add(itemizedOverlay);
    }

//	private void setAddress(Bath bath) {
//		TextView addressView = (TextView) findViewById(R.id.details_section_content_address_address);
//		addressView.setText("Wasserwerkstrasse 141\n8037 Zürich\nTelefon 044 362 10 80\nLeitung: Heinrich Stadler\n\nGratisbad");
//
//		TextView routeView = (TextView) findViewById(R.id.details_section_content_address_route);
//		routeView.setText("Tram 4/13 bis «Dammweg»\nBus 46 bis «Nordstrasse»\nS2/S8/S14 bis Bahnhof Wipkingen\nÖffentliche Parkplätze");
//	}

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
