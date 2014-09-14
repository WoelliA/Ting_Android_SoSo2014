package de.ur.mi.android.ting.utilities.html;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.util.ByteArrayBuffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.LoadedImageData;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class JSoupPinDataParser implements PinDataParser {

	private IImageLoader imageloader;
	private Context context;

	public JSoupPinDataParser(IImageLoader imageloader) {
		this.imageloader = imageloader;
	}

	private String readHtml(final URL url) throws IOException {
		URLConnection connection = url.openConnection();

		BufferedInputStream bis = new BufferedInputStream(
				connection.getInputStream());

		ByteArrayBuffer baf = new ByteArrayBuffer(50);
		int current = 0;
		while ((current = bis.read()) != -1) {
			baf.append((byte) current);
		}

		/* Convert the Bytes read to a String. */
		String tempString = new String(baf.toByteArray());
		return tempString;
	}

	@Override
	public void getPinData(final URL url, final IDoneCallback<PinData> callback) {
		AsyncTask<Void, Void, Document> task = new AsyncTask<Void, Void, Document>() {

			@Override
			protected Document doInBackground(Void... params) {
				try {
					String html = JSoupPinDataParser.this.readHtml(url);
					Log.i("http", html);
					Document doc = Jsoup.parse(html);
					return doc;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Document result) {
				if (result != null) {
					JSoupPinDataParser.this.readParseData(url.toString(),
							result, callback);
				}
				super.onPostExecute(result);
			}
		};
		task.execute();
	}

	protected void readParseData(final String url, Document doc,
			final IDoneCallback<PinData> callback) {
		final String title = this.getTitle(doc);
		final String description = this.getDescription(doc);
		List<String> images = this.getImages(doc);
		for (final String imageUrl : images) {
			Log.i("image load", imageUrl);
			this.imageloader.loadImage(imageUrl,
					new SimpleDoneCallback<Bitmap>() {

						@Override
						public void done(Bitmap result) {
							if (result == null) {
								return;
							}
							PinData pinData = new PinData(title, description,
									new LoadedImageData(imageUrl, result), url);
							callback.done(pinData);
						}
					});
		}
	}

	private List<String> getImages(Document doc) {
		Elements elementsByTag = doc.getElementsByTag("img");
		List<String> imageUrls = new ArrayList<String>();

		for (Element el : elementsByTag) {
			String url = el.attr("src");
			if (url != null) {
				imageUrls.add(url);
			}
		}
		return imageUrls;
	}

	private String getDescription(Document doc) {
		return this.getMetaTag(doc, "description");
	}

	String getMetaTag(Document document, String attr) {
		Elements heads = document.getElementsByTag("head");
		if (heads.size() > 0) {
			Element head = heads.get(0);
			Elements metaElements = head.getElementsByTag("meta");
			for (Element meta : metaElements) {
				String name = meta.attr("name");
				if (name != null && name.contains(attr)) {
					return meta.attr("content");
				}
			}
		}
		return null;
	}

	private String getTitle(Document doc) {
		String title = doc.title();
		return title == null ? this.getMetaTag(doc, "title") : title;
	}
}
