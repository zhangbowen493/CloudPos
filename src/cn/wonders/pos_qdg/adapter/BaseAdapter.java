package cn.wonders.pos_qdg.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
	protected Context mContext;
	protected ArrayList<T> data;
	protected LayoutInflater mInfalter;
	public BaseAdapter(Context context){
		this.mContext = context;
		this.mInfalter=LayoutInflater.from(context);
		}
	
	public abstract void setData(ArrayList<T> list);
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data!=null)
		return data.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(data!=null)
		return data.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
