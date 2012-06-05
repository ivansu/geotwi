package geotwitter.test;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewAdapter extends BaseAdapter {
	private Context mContext;
	private TwitList list;

	public ViewAdapter(Context c, TwitList l) {
		mContext = c;
		list = l;
	}

	public int getCount() {
		return list.GetCount();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TwitInfo _twit = list.get(position);
		
		LinearLayout ln = new LinearLayout(mContext);
		ln.setOrientation(1);
		ln.setBackgroundColor(Color.LTGRAY);
		ln.getBackground().setAlpha(70);
		ln.setLayoutParams(new GridView.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		ln.setGravity(0x10);
		
		LinearLayout.LayoutParams ap1 = new LinearLayout.LayoutParams(
				new LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));

		TextView txt = new TextView(mContext);
		ap1.setMargins(5, 0, 0, 0);
		txt.setLayoutParams(ap1);
		txt.setTextColor(Color.WHITE);
		txt.setTextSize(12);
		
		txt.setText(_twit.GetCreatedDateTime());
		ln.addView(txt);
		
		txt = new TextView(mContext);
		ap1.setMargins(5, 0, 0, 0);
		txt.setLayoutParams(ap1);
		txt.setTextColor(Color.WHITE);
		txt.setTextSize(12);
		
		txt.setText(_twit.GetFromUser());
		ln.addView(txt);
		
		txt = new TextView(mContext);
		ap1.setMargins(5, 0, 0, 0);
		txt.setLayoutParams(ap1);
		txt.setTextColor(Color.WHITE);
		txt.setTextSize(15);
		
		txt.setText(_twit.GetFromUserName());
		ln.addView(txt);
		
		txt = new TextView(mContext);
		ap1.setMargins(5, 0, 0, 0);
		txt.setLayoutParams(ap1);
		txt.setTextColor(Color.WHITE);
		txt.setTextSize(10);
		
		txt.setText(_twit.GetGeo());
		ln.addView(txt);
		
		txt = new TextView(mContext);
		ap1.setMargins(5, 0, 0, 0);
		txt.setLayoutParams(ap1);
		txt.setTextColor(Color.WHITE);
		txt.setTextSize(10);
		
		txt.setText(_twit.GetText());
		ln.addView(txt);
		
		
		return ln;
	}

}
