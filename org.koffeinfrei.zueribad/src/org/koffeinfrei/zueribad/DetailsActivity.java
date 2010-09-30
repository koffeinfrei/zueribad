package org.koffeinfrei.zueribad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.koffeinfrei.zueribad.models.BathRepository;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		TextView titleView = (TextView)findViewById(R.id.details_title);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			int position = extras.getInt("SelectedItemPosition"); // TODO ->constant
			System.out.println("details at position: " + position);
			
			titleView.setText(BathRepository.getInstance().getFiltered().get(position).getName());
		}
		else{
			titleView.setText("Error: no item found"); // TODO
		}
		
		ExpandableListView list = (ExpandableListView)findViewById(R.id.details_explist);
		SimpleExpandableListAdapter adapter = 
			new SimpleExpandableListAdapter(
					this,
					createSectionsList(),	// groupData describes the first-level entries
					R.layout.details_section_title,	// Layout for the first-level entries
					new String[] { "title" },	// Key in the groupData maps to display
					new int[] { R.id.details_section_title },		// Data under "colorName" key goes into this TextView
					createChildList(),	// childData describes second-level entries
					R.layout.details_section_content,	// Layout for second-level entries
					new String[] { "content1", "content2" },	// Keys in childData maps to display
					new int[] { R.id.details_section_content1, R.id.details_section_content2 }	// Data under the keys above go into these TextViews
				);
		list.setAdapter(adapter);
	}
		
	private List<HashMap<String, String>> createSectionsList() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		String[] sections = { 
				"Adresse und Öffnungszeiten", 
				"Anreise",
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

		ArrayList<HashMap<String, ?>> addressList = new ArrayList<HashMap<String, ?>>();
		HashMap<String, String> addressChild = new HashMap<String, String>();
		addressChild.put("content1", "Wasserwerkstrasse 141\n8037 Zürich\nTelefon 044 362 10 80\nLeitung: Heinrich Stadler");
		addressChild.put("content2", "Gratisbad ");
		addressList.add(addressChild);
		result.add(addressList);
		
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
