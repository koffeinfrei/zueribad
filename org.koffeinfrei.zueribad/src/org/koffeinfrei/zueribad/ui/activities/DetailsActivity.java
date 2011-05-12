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
import android.util.AndroidException;
import android.util.DisplayMetrics;
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
            TextView address = (TextView) findViewById(R.id.details_section_content_address_address);
            TextView route = (TextView) findViewById(R.id.details_section_content_address_route);
            TextView openingHours = (TextView) findViewById(R.id.details_section_content_openinghours_hours);
            TextView season = (TextView) findViewById(R.id.details_section_content_openinghours_season);
            TextView openingHoursInfo = (TextView) findViewById(R.id.details_section_content_openinghours_hoursinfo);
            ImageView picture = (ImageView) findViewById(R.id.details_picture);

            SetupSlidingPanel(
                    R.id.details_section_content_address_panel,
                    R.id.details_section_content_address_togglebuttontext,
                    R.id.details_section_content_address_togglebuttonicon);

            SetupSlidingPanel(
                    R.id.details_section_content_openinghours_panel,
                    R.id.details_section_content_openinghours_togglebuttontext,
                    R.id.details_section_content_openinghours_togglebuttonicon);

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

            address.setText(bath.getAddress() + "\n" + bath.getAddress2());
            route.setText(bath.getRoute());

            openingHours.setText(
                    String.format("%1$s\n%2$s\n%3$s\n%4$s",
                            getString(R.string.text_daily),
                            getString(R.string.text_openinghoursweather1),
                            getString(R.string.text_openinghoursweather2),
                            getString(R.string.text_openinghoursweather3)));
            season.setText(
                    String.format("%1$td. %1$tB - %2$td. %2$tB %1$tY",
                            bath.getSeasonStart(), bath.getSeasonEnd())
            );

            String openingHoursInfoValue = bath.getOpeningHoursInfo();
            if (openingHoursInfoValue != null && !openingHoursInfoValue.equals("")){
                openingHoursInfo.setVisibility(View.VISIBLE);
                openingHoursInfo.setText(openingHoursInfoValue);
            }

            try {
                Drawable bathImage = bath.getBathImage(this);
                picture.setImageDrawable(bathImage);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;
                int height = width * bathImage.getIntrinsicHeight() / bathImage.getIntrinsicWidth();
                picture.getLayoutParams().height = height;
                picture.getLayoutParams().width = width;
            } catch (AndroidException e) {
                // ignore
            }
            //setMap(bath);

            final Button homepageButton = (Button) findViewById(R.id.details_homepagebutton);
            homepageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bath.getUrl()));
                    startActivity(browserIntent);
                }
            });

            final Button showOnMapButton = (Button) findViewById(R.id.details_showonmapbutton);
            showOnMapButton.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View view) {
                    setCurrentTab(Constants.TAB_OVERVIEW_MAP_INDEX);
                    OverviewMapActivity activity = (OverviewMapActivity) ((TabActivity) getParent()).getTabHost().getCurrentView().getContext();
                    activity.tapOverlayItem(BathRepository.getInstance().getCurrent().getId());
                }
            });
        }
	}

	private void SetupSlidingPanel(int slidingPanelId, int toggleButtonId, int toggleImageButtonId) {
		final CollapsiblePanel panel = (CollapsiblePanel) findViewById(slidingPanelId);
		final Button toggleButton = (Button) findViewById(toggleButtonId);
        final Button toggleImageButton = (Button) findViewById(toggleImageButtonId);

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.invalidate();

                // state before animation
                if (panel.isOpen()) {
                    toggleImageButton.setBackgroundResource(R.drawable.ic_toggle_closed);
                } else {
                    toggleImageButton.setBackgroundResource(R.drawable.ic_toggle_open);
                }

                panel.toggle();
            }
        };
        toggleButton.setOnClickListener(onClickListener);
        toggleImageButton.setOnClickListener(onClickListener);
	}

    private void setCurrentTab(int index) {
        ((TabActivity)getParent()).getTabHost().setCurrentTab(index);
    }

    @Override
    public void onBackPressed() {
        setCurrentTab(Constants.TAB_OVERVIEW_INDEX);
    }
}
