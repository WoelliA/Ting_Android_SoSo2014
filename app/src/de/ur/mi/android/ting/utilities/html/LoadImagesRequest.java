package de.ur.mi.android.ting.utilities.html;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.LoadedImageData;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class LoadImagesRequest {

	private List<String> imageUrls;
	private IImageLoader imageloader;

	private List<SimpleEntry<String, Bitmap>> results;
	private boolean isCancelled;

	public LoadImagesRequest(List<String> imageUrls, IImageLoader imageloader) {
		this.imageUrls = imageUrls;
		this.imageloader = imageloader;
		this.results = new ArrayList<SimpleEntry<String, Bitmap>>();
	}

	public void execute() {
		for (final String url : this.imageUrls) {
			this.imageloader.loadImage(url, new SimpleDoneCallback<Bitmap>() {

				@Override
				public void done(Bitmap result) {
					Log.i("image loaded", url);
					LoadImagesRequest.this.results.add(new SimpleEntry<String, Bitmap>(url, result));
				}
			});
		}
	}

	public boolean isExecuting() {
		boolean executing = !this.isCancelled
				&& this.results.size() < this.imageUrls.size();
		return executing;
	}

	public List<LoadedImageData> getResults(int minWidth, int minHeight) {
		List<LoadedImageData> finalResults = new ArrayList<LoadedImageData>();

		for (SimpleEntry<String, Bitmap> pair : this.results) {
			Bitmap bitmap = pair.getValue();
			if (bitmap != null) {
				int width = bitmap.getScaledWidth(bitmap.getDensity());
				int height = bitmap.getScaledHeight(new DisplayMetrics());
				Log.i("image dimensions", "" + width + " - " + height);
				if (width > minWidth
						&& height > minHeight) {
					finalResults.add(new LoadedImageData(pair.getKey(), pair
							.getValue()));
				}
			}
		}
		return finalResults;
	}

	public void cancel() {
		Log.i("loadimagesrequest", "cancelling");
		this.isCancelled = true;

	}

}
