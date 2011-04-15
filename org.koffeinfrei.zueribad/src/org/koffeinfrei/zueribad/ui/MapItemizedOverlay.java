package org.koffeinfrei.zueribad.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.ui.activities.OverviewMapActivity;

import java.util.ArrayList;
import java.util.Hashtable;

public class MapItemizedOverlay extends ItemizedOverlay{

    private Hashtable<Integer, OverlayItem> overlays = new Hashtable<Integer, OverlayItem>();
    private OverviewMapActivity context;

    public MapItemizedOverlay(Drawable drawable) {
        super(boundCenterBottom(drawable));
    }

    public MapItemizedOverlay(Drawable defaultMarker, OverviewMapActivity context) {
      this(defaultMarker);
      this.context = context;
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
    public boolean onTap(final int i) {
        OverlayItem item = overlays.get(i);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.map_dialog);
        TextView text = (TextView) dialog.findViewById(R.id.map_dialog_text);
        text.setText(item.getTitle());

        final Button button = (Button) dialog.findViewById(R.id.map_dialog_detailsbutton);
        button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BathRepository.getInstance().setCurrent(i);
                    context.activateTab(Constants.TAB_DETAILS_INDEX);
                    context.setCurrentTab(Constants.TAB_DETAILS_INDEX);
                    dialog.dismiss();
                }
            });

        dialog.show();
        return true;
    }

    public void addOverlay(int i, OverlayItem overlay) {
        overlays.put(i, overlay);
        populate();
    }
}
