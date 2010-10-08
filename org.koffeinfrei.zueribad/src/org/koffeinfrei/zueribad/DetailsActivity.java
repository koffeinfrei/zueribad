package org.koffeinfrei.zueribad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.models.entities.Bath;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class DetailsActivity extends MapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		TextView titleView = (TextView)findViewById(R.id.details_title);
		ImageView pictureView = (ImageView) findViewById(R.id.details_picture);
		
		SetupSlidingPanel(R.id.details_section_content_address_panel, R.id.details_section_content_address_togglebutton);
		SetupSlidingPanel(R.id.details_section_content_map_panel, R.id.details_section_content_map_togglebutton);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			int id = extras.getInt("SelectedItemId"); // TODO ->constant
			Bath bath = BathRepository.getInstance().get(id);
			
			// title
			titleView.setText(bath.getName());
			
			// picture
			pictureView.setImageBitmap(bath.getPicture());
			
			setMap(bath);
			setAddress(bath);
			
		}
		else{
			titleView.setText("Error: no item found"); // TODO
		}
	}

	private void setAddress(Bath bath) {
		TextView addressView = (TextView) findViewById(R.id.details_section_content_address_address);
		addressView.setText("Wasserwerkstrasse 141\n8037 Zürich\nTelefon 044 362 10 80\nLeitung: Heinrich Stadler\n\nGratisbad");
		
		TextView routeView = (TextView) findViewById(R.id.details_section_content_address_route);
		routeView.setText("Tram 4/13 bis «Dammweg»\nBus 46 bis «Nordstrasse»\nS2/S8/S14 bis Bahnhof Wipkingen\nÖffentliche Parkplätze");
	}

	private void setMap(Bath bath) {
		MapView mapView = (MapView) findViewById(R.id.details_section_content_map);
		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocationName("Wasserwerkstrasse 141, 8037 Zürich", 1);
			
			if (!addresses.isEmpty()){
				Address address = addresses.get(0);
				GeoPoint geoPoint = new GeoPoint(
						(int)(address.getLatitude() * 1000000.0), 
						(int)(address.getLongitude() * 1000000.0));
				mapView.getController().setCenter(geoPoint);
				mapView.setBuiltInZoomControls(true);
			}
			else{
				System.err.println("Address was not found"); // TODO
			}
		} 
		catch (IOException e) {
			System.err.println("Address was not found. " + e.getMessage()); // TODO
		}
	}

	private void SetupSlidingPanel(int slidingPanelId, int toggleButtonId) {
		final CollapsiblePanel panel = (CollapsiblePanel) findViewById(slidingPanelId);
		final Button toggleButton = (Button) findViewById(toggleButtonId);
		
		toggleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleButton.invalidate();
				panel.toggle();
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
