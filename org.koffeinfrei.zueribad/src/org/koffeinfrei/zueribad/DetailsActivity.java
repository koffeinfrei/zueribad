package org.koffeinfrei.zueribad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.models.entities.Bath;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		TextView titleView = (TextView)findViewById(R.id.details_title);
		ImageView pictureView = (ImageView) findViewById(R.id.details_picture);
		MapView mapView = (MapView) findViewById(R.id.details_section_content_map);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			int id = extras.getInt("SelectedItemId"); // TODO ->constant
			Bath bath = BathRepository.getInstance().get(id);
			
			// title
			titleView.setText(bath.getName());
			
			// picture
			pictureView.setImageBitmap(bath.getPicture());
			
			// map
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
			
			// info sections
			ExpandableListView list = (ExpandableListView)findViewById(R.id.details_explist);
			SimpleExpandableListAdapter adapter = 
				new SimpleExpandableListAdapter(
						this,
						createSectionsList(),	// groupData describes the first-level entries
						R.layout.details_section_title,	// Layout for the first-level entries
						new String[] { "title" },	// Key in the groupData maps to display
						new int[] { R.id.details_section_title },		// Data under "colorName" key goes into this TextView
						createChildList(),	// childData describes second-level entries
						R.layout.details_section_content_general,	// Layout for second-level entries
						new String[] { "content1", "content2" },	// Keys in childData maps to display
						new int[] { R.id.details_section_content_general1, R.id.details_section_content_general2 }	// Data under the keys above go into these TextViews
					);
			list.setAdapter(adapter);
		}
		else{
			titleView.setText("Error: no item found"); // TODO
		}
	}
		
	private List<HashMap<String, String>> createSectionsList() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		String[] sections = { 
				"Adresse", 
				"Öffnungszeiten",
				"Lageplan" };
		for (int i = 0; i < sections.length; ++i) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("title", sections[i]);
			result.add(m);
		}
		return result;
	}
	
	private List<ArrayList<HashMap<String, ?>>> createChildList() {
		ArrayList<ArrayList<HashMap<String, ?>>> result = new ArrayList<ArrayList<HashMap<String, ?>>>();

		ArrayList<HashMap<String, ?>> list = new ArrayList<HashMap<String, ?>>();
		
		// address
		HashMap<String, String> child = new HashMap<String, String>();
		child.put("content1", "Wasserwerkstrasse 141\n8037 Zürich\nTelefon 044 362 10 80\nLeitung: Heinrich Stadler\n\nGratisbad");
		child.put("content2", "Tram 4/13 bis «Dammweg»\nBus 46 bis «Nordstrasse»\nS2/S8/S14 bis Bahnhof Wipkingen\nÖffentliche Parkplätze");
		list.add(child);
		result.add(list);
		
		// opening hours
		child = new HashMap<String, String>();
		child.put("content1", "14. Mai bis Mitte September 2011");
		child.put("content2", "");
		list.add(child);
		result.add(list);
		
		 
		
//		for (int i = 0; i < 3; ++i) {
//			// Second-level lists
//			ArrayList<HashMap<String, ?>> secList = new ArrayList<HashMap<String, ?>>();
//			for (int j = 0; j < 2; j += 2) {
//				HashMap<String, String> child = new HashMap<String, String>();
//				child.put("content1", "content 1, " + j);
//				child.put("content2", "content 2," + j);
//				secList.add(child);
//			}
//			result.add(secList);
//		}
		return result;
	}
}
