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

package org.koffeinfrei.zueribad.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;

import java.util.ArrayList;

public abstract class ListAdapter extends BaseAdapter {
	
private LayoutInflater inflater;
	
	protected BathRepository bathRepository;
	
	public ListAdapter(Context context){
		inflater = LayoutInflater.from(context);
		
		bathRepository = BathRepository.getInstance();
	}
	
	@Override
	public int getCount() {
		return getList().size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return (long)getList().get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.overview_listview, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.overview_listitem_bathname);
			holder.temperature = (TextView) convertView.findViewById(R.id.overview_listitem_bathtemperature);
			
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Bath bath = getList().get(position);
		holder.name.setText(bath.getName());
		//holder.type.setText(bath.getType());
		holder.temperature.setText(bath.getFormattedTemperature().toString());
		
		return convertView;
	}

	protected abstract ArrayList<Bath> getList();
	
	protected static class  ViewHolder {
		TextView name;
		TextView temperature;
	}

}
