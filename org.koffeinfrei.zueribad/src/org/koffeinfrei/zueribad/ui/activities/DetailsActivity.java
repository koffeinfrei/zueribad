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
import com.google.android.maps.MapActivity;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.ui.CollapsiblePanel;

public class DetailsActivity extends MapActivity {
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        final Bath bath = BathRepository.getInstance().getCurrent();

        // if no data, switch to overview
        if(bath == null)
		{
            setCurrentTab(Constants.TAB_OVERVIEW_INDEX);
        }
        else
        {
            setContentView(R.layout.details);

            TextView titleView = (TextView)findViewById(R.id.details_title);
            TextView waterTemperatureView = (TextView) findViewById(R.id.details_watertemperature);
            TextView lastModifiedView = (TextView) findViewById(R.id.details_lastmodified);
            TextView status = (TextView) findViewById(R.id.details_status);
            ImageView uvIndex = (ImageView) findViewById(R.id.details_uvindex_picture);

            //SetupSlidingPanel(R.id.details_section_content_address_panel, R.id.details_section_content_address_togglebutton);
            //SetupSlidingPanel(R.id.details_section_content_map_panel, R.id.details_section_content_map_togglebutton);

            titleView.setText(bath.getName());
            waterTemperatureView.setText(bath.getFormattedTemperature());
            lastModifiedView.setText(bath.getFormattedModified());
            status.setText(bath.getStatus());

            Drawable uvIndexImage = BathRepository.getInstance().getUvIndexImage();
            if (uvIndexImage != null){
                uvIndex.setImageDrawable(uvIndexImage);
            }

            //setMap(bath);
            //setAddress(bath);

            final Button button = (Button) findViewById(R.id.details_homepagebutton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bath.getUrl()));
                    startActivity(browserIntent);
                }
            });
        }
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

    private void setCurrentTab(int index) {
        ((TabActivity)getParent()).getTabHost().setCurrentTab(index);
    }

    @Override
    public void onBackPressed() {
        setCurrentTab(Constants.TAB_OVERVIEW_INDEX);
    }
}
