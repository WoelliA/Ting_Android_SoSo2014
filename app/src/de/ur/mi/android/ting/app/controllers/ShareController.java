package de.ur.mi.android.ting.app.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.LoadedImageData;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.html.PinDataParser;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.Notify.LoadingContext;

public class ShareController {

	private static final int minWidth = 100;
	private static final int minHeight = 100;

	private IShareSetupView view;
	private PinDataParser pindataParser;
	private IImageLoader imageLoader;

	private Board selectedBoard;
	private IPinProvider pinProvider;

	public ShareController(PinDataParser pindataParser,
			IImageLoader imageLoader, IPinProvider pinProvider) {
		this.pindataParser = pindataParser;
		this.imageLoader = imageLoader;
		this.pinProvider = pinProvider;
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
									return;
								}
								LoadedImageData imageData = result
										.getImageData();
								if (imageData == null) {
									return;
								}
								Bitmap bitmap = imageData.getBitmap();
								if (bitmap == null) {
									return;
								}
								if (bitmap.getWidth() < minWidth
										|| bitmap.getHeight() < minHeight) {
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

	public void onBoardSelected(Board selectedBoard) {
		this.selectedBoard = selectedBoard;
	}

	public void createPin(PinData result) {
		final LoadingContext loading = Notify.current().notifyLoading(
				R.string.sending_pin_dialog_title);
		this.pinProvider.createPin(result, this.selectedBoard,
				new SimpleDoneCallback<Void>() {

					@Override
					public void done(Void result) {
						loading.close();
					}
				});

	}
}
