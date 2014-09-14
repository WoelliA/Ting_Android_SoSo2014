package de.ur.mi.android.ting.app.fragments;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.IImageLoader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditPinDetailsFragment extends FragmentBase {

	private IDoneCallback<PinData> listener;
	private PinData pinData;
	private ImageView pinImageView;
	private EditText pinTitleView;
	private EditText pinDescriptionView;
	private TextView linkUrlView;
	private Button changeLinkButton;

	@Inject
	public IImageLoader imageLoader;

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
				EditPinDetailsFragment.this.listener
						.done(EditPinDetailsFragment.this.createPinDataResult());
			}
		});

		View view = this.getView();

		this.pinImageView = (ImageView) view.findViewById(R.id.editpin_image);
		this.pinTitleView = (EditText) view.findViewById(R.id.editpin_title);
		this.linkUrlView = (TextView) view.findViewById(R.id.textview_linktext);
		this.changeLinkButton = (Button) view
				.findViewById(R.id.button_change_link);

		this.linkUrlView.setText(this.pinData.getLinkUrl());
		this.pinDescriptionView = (EditText) view
				.findViewById(R.id.editpin_description);

		if (this.pinData.getImageData().getBitmap() != null) {
			this.pinImageView.setImageBitmap(this.pinData.getImageData()
					.getBitmap());
		} else {
			this.imageLoader.loadImage(this.pinData.getImageData()
					.getimageUrl(), this.pinImageView);
		}
		this.pinTitleView.setText(this.pinData.getTitle());
		this.pinDescriptionView.setText(this.pinData.getDescription());

	}

	private PinData createPinDataResult() {
		return new PinData(this.pinTitleView.getText().toString(),
				this.pinDescriptionView.getText().toString(),
				this.pinData.getImageData(), 
				this.linkUrlView.getText().toString());
	}

	public void setPinData(PinData pinData) {
		this.pinData = pinData;
	}

	public void setOnSendPinClickListener(IDoneCallback<PinData> listener) {
		this.listener = listener;
	}

}
