package de.ur.mi.android.ting.app.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.LoadedImageData;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.html.PinDataParser;

public class ShareController {

	private IShareSetupView view;
	private PinDataParser pindataParser;
	private IImageLoader imageLoader;

	private Board selectedBoard;

	private PinData selectedPinData;

	public ShareController(PinDataParser pindataParser, IImageLoader imageLoader) {
		this.pindataParser = pindataParser;
		this.imageLoader = imageLoader;
	}

	public void resolveShareType(String text) {

	}

	public void setView(IShareSetupView view) {
		this.view = view;
	}

	public void handleSendText(final String text) {
		if (this.view == null) {
			return;
		}
		if (text == null) {
			this.view.displayError(IShareSetupView.NO_TEXT);
			return;
		}

		// check if it's an URL
		try {
			// check if it's an URL ending with image extension
			// -> no HTML to retrieve but simple image
			URL url = new URL(text);
			String extension = MimeTypeMap.getFileExtensionFromUrl(text);
			String mimeType = MimeTypeMap.getSingleton()
					.getMimeTypeFromExtension(extension);
			if (mimeType != null && mimeType.startsWith("image")) {
				this.imageLoader.loadImage(text,
						new SimpleDoneCallback<Bitmap>() {

							@Override
							public void done(Bitmap result) {
								ShareController.this.view
										.addDisplayPinnableImage(new PinData(
												"", "", new LoadedImageData(
														text, result)));
							}
						});
			} else {
				// retrieve pin data
				this.pindataParser.getPinData(url,
						new SimpleDoneCallback<PinData>() {

							@Override
							public void done(PinData result) {
								if (result == null) {
									ShareController.this.view
											.displayError(IShareSetupView.LOAD_ERROR);
									return;
								}
								ShareController.this.view
										.addDisplayPinnableImage(result);
							}
						});
			}

		} catch (MalformedURLException e) {
			this.view.displayError(IShareSetupView.NO_URL);
			e.printStackTrace();
		}

	}

	public void handleSendImage(final Uri uri) {
		this.imageLoader.loadImage(uri.toString(),
				new SimpleDoneCallback<Bitmap>() {

					@Override
					public void done(Bitmap result) {
						ShareController.this.view
								.addDisplayPinnableImage(new PinData("", "",
										new LoadedImageData(uri.toString(),
												result)));
					}
				});

	}

	public void handleSendMultipleImages(ArrayList<Uri> parcelableArrayListExtra) {
		// TODO Auto-generated method stub

	}

	public void onPinImageSelected(PinData selectedItem) {
		this.selectedPinData = selectedItem;

	}

	public void onBoardSelected(Board selectedBoard) {
		this.selectedBoard = selectedBoard;
	}

	public PinData getPinData() {
		return this.selectedPinData;
	}

	public void createPin(PinData result) {
		// TODO Auto-generated method stub

	}

}
