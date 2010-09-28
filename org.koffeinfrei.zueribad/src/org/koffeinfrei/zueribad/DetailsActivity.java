package org.koffeinfrei.zueribad;

import org.koffeinfrei.zueribad.models.BathRepository;

import android.app.Activity;
import android.os.Bundle;
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
			int position = extras.getInt("SelectedItemPosition");
			System.out.println("details at position: " + position);
			
			titleView.setText(BathRepository.getInstance().getFiltered().get(position).getName());
		}
		else{
			titleView.setText("Error: no item found"); // TODO
		}
	}
	
}
