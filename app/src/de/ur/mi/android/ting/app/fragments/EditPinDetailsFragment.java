package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditPinDetailsFragment extends FragmentBase {

	private IDoneCallback<PinData> listener;
	private PinData pinData;
	private ImageView pinImageView;
	private EditText pinTitleView;
	private EditText pinDescriptionView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_editpindetails, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.initUi();
	}

	private void initUi() {
		Button pinItButton = (Button) this.getView().findViewById(
				R.id.editpin_pinbutton);
		pinItButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (EditPinDetailsFragment.this.listener == null) {
					return;
				}
				EditPinDetailsFragment.this.listener.done(EditPinDetailsFragment.this.createPinDataResult());
			}
		});

		View view = this.getView();
		this.pinImageView = (ImageView) view.findViewById(R.id.editpin_image);
		this.pinTitleView = (EditText) view.findViewById(R.id.editpin_title);
		this.pinDescriptionView = (EditText) view
				.findViewById(R.id.editpin_description);

		this.pinImageView.setImageBitmap(this.pinData.getImageData()
				.getBitmap());
		this.pinTitleView.setText(this.pinData.getTitle());
		this.pinDescriptionView.setText(this.pinData.getDescription());
	}

	private PinData createPinDataResult() {
		return new PinData(this.pinTitleView.getText().toString(),
				this.pinDescriptionView.getText().toString(),
				this.pinData.getImageData());
	}

	public void setPinData(PinData pinData) {
		this.pinData = pinData;
	}

	public void setOnSendPinClickListener(IDoneCallback<PinData> listener) {
		this.listener = listener;
	}

}
