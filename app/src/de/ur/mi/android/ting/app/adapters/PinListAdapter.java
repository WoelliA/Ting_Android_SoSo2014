package de.ur.mi.android.ting.app.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.Primitives.Pin;


public class PinListAdapter extends ArrayAdapter<Pin>{

	private ArrayList<Pin> PinList;
	private Context context;
	
	public PinListAdapter(Context context, ArrayList<Pin> Pins){
		super(context, R.id.pin_layout, Pins);
		
		this.context = context;
		this.PinList = Pins;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.pin_layout, parent);

		}

		Pin Pin = PinList.get(position);

		if (Pin != null) {
			TextView headline = (TextView) v.findViewById(R.id.pin_headline);
			TextView content = (TextView) v.findViewById(R.id.pin_content);
			ImageView picture = (ImageView) v.findViewById(R.id.pin_picture);
			
			headline.setText(Pin.getTitle());
			content.setText(Pin.getDescription());
			
			// TODO: Uri to drawable
//			picture.setImageDrawable(Pin.getImageUri());
		}

		return v;
	}
	
}
