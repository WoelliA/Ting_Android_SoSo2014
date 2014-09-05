package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;

import javax.activation.MimeType;
import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.utilities.IImageLoader;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareActivity extends BaseActivity {

	@Inject
	public IImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);

		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				handleSendText(intent); // Handle text being sent
			} else if (type.startsWith("image/")) {
				handleSendImage(intent); // Handle single image being sent
			}
		} else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
			if (type.startsWith("image/")) {
				handleSendMultipleImages(intent); // Handle multiple images
													// being sent
			}
		} else {
			// Handle other intents, such as being started from the home screen
		}
	}

	void handleSendText(Intent intent) {
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (sharedText != null) {
			String extension = MimeTypeMap.getFileExtensionFromUrl(sharedText);
			String mimeType = MimeTypeMap.getSingleton()
					.getMimeTypeFromExtension(extension);
			if (mimeType != null && mimeType.startsWith("image")) {
				this.displayImage(sharedText);
			}
			
			// check if it's an URI
			Uri uri = Uri.parse(Uri.encode(sharedText));
			if(uri != null){
				
			}
			TextView tv = (TextView) this.findViewById(R.id.share_text_view);
			tv.setText(sharedText);
		}
	}

	private void displayImage(String sharedText) {
		ImageView iv = (ImageView)this.findViewById(R.id.share_image_view);
		this.imageLoader.loadImage(sharedText, iv);
	}

	void handleSendImage(Intent intent) {
		Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {
			// Update UI to reflect image being shared
		}
	}

	void handleSendMultipleImages(Intent intent) {
		ArrayList<Uri> imageUris = intent
				.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		if (imageUris != null) {
			// Update UI to reflect multiple images being shared
		}
	}
}
