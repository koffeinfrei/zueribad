package org.koffeinfrei.zueribad;

import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.models.entities.Bath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class OverviewListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	
	private BathRepository bathRepository;
	
	public OverviewListAdapter(Context context){
		inflater = LayoutInflater.from(context);
		
		bathRepository = new BathRepository();
	}
	
	@Override
	public int getCount() {
		return bathRepository.getFiltered().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
		convertView = inflater.inflate(R.layout.overview_listview, null);
		holder = new ViewHolder();
		holder.text = (TextView) convertView
		.findViewById(R.id.TextView01);
		holder.text2 = (TextView) convertView
		.findViewById(R.id.TextView02);
		
		convertView.setTag(holder);
		} else {
		holder = (ViewHolder) convertView.getTag();
		}
		
		Bath bath = bathRepository.getFiltered().get(position);
		holder.text.setText(bath.getName());
		holder.text2.setText(bath.getType());
		
		return convertView;
	}

	static class  ViewHolder {
		TextView text;
		TextView text2;
	}

	public Filter getFilter() {
		return new OverviewListFilter(bathRepository);
	}
	
}
