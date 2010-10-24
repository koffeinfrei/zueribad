package org.koffeinfrei.zueribad;

import java.util.ArrayList;

import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
		// TODO Auto-generated method stub
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
			holder.type = (TextView) convertView.findViewById(R.id.overview_listitem_bathtype);
			holder.temperature = (TextView) convertView.findViewById(R.id.overview_listitem_bathtemperature);
			
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Bath bath = getList().get(position);
		holder.name.setText(bath.getName());
		holder.type.setText(bath.getType());
		holder.temperature.setText(bath.getFormattedTemperature().toString());
		
		return convertView;
	}

	protected abstract ArrayList<Bath> getList();
	
	protected static class  ViewHolder {
		TextView name;
		TextView type;
		TextView temperature;
	}

}
