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

import android.app.Activity;
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
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.ui.CollapsiblePanel;
import org.koffeinfrei.zueribad.ui.ViewModifier;

public class DetailsActivity extends Activity {
    
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
            ViewModifier.temperature(waterTemperatureView, bath.getModified());

            lastModifiedView.setText(bath.getFormattedModified());
            ViewModifier.lastModified(lastModifiedView, bath.getModified());
            
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

    private void setCurrentTab(int index) {
        ((TabActivity)getParent()).getTabHost().setCurrentTab(index);
    }

    @Override
    public void onBackPressed() {
        setCurrentTab(Constants.TAB_OVERVIEW_INDEX);
    }
}
