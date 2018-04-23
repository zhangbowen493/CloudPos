package cn.wonders.pos_qdg.adapter;

import java.util.ArrayList;

import cn.wonders.pos_qdg.R;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 交易查询列表
 * @author n_n
 *
 */
public class TradeAdapter extends BaseAdapter<String>{

	public TradeAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setData(ArrayList<String> list) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item=mInfalter.inflate(R.layout.lv_trade_item, null);
		TextView number=(TextView) item.findViewById(R.id.tv_trade_item_number);
		TextView time=(TextView) item.findViewById(R.id.tv_trade_item_time);
		TextView money=(TextView) item.findViewById(R.id.tv_trade_item_money);
		TextView upload=(TextView) item.findViewById(R.id.tv_trade_item_upload);
		if(position/2==0){
			number.setTextColor(Color.rgb(253, 177, 78));
			time.setTextColor(Color.rgb(253, 177, 78));
			money.setTextColor(Color.rgb(253, 177, 78));
			upload.setTextColor(Color.rgb(253, 177, 78));
		}
		return item;
	}

	

}
