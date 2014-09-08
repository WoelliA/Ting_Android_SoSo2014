package de.ur.mi.android.ting.utilities.html;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.ByteArrayBuffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.LoadedImageData;

public class JSoupPinDataParser implements PinDataParser {

	private IImageLoader imageloader;
	private Context context;

	public JSoupPinDataParser(IImageLoader imageloader) {
		this.imageloader = imageloader;
	}
	
	private String readHtml(final URL url) throws IOException {
		URLConnection connection = url.openConnection();
		
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		
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
		AsyncTask<Void, PinData, PinData> task = new AsyncTask<Void, PinData, PinData>() {

			@Override
			protected PinData doInBackground(Void... params) {
				try {		
					String html = JSoupPinDataParser.this.readHtml(url);
                    Log.i("http", html);                    
					Document doc = Jsoup.parse(html);
					return JSoupPinDataParser.this.readParseData(doc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(PinData result) {
				callback.done(result);
			}

		};
		task.execute();
	}

	protected PinData readParseData(Document doc) {
		String title = this.getTitle(doc);
		String description = this.getDescription(doc);
		List<LoadedImageData> images = this.getImages(doc);
		return new PinData(title, description, images);
	}

	private List<LoadedImageData> getImages(Document doc) {
		Elements elementsByTag = doc.getElementsByTag("img");
		List<String> imageUrls = new ArrayList<String>();

		for (Element el : elementsByTag) {
			String url = el.attr("src");
			if (url != null) {
				imageUrls.add(url);
			}
		}

		final LoadImagesRequest request = new LoadImagesRequest(imageUrls,
				this.imageloader);
		request.execute();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				request.cancel();
			}
		}, 5000);
		while (request.isExecuting()) {

		}
		timer.cancel();
		return request.getResults(200, 200);
	}

	private String getDescription(Document doc) {
		return this.getMetaTag(doc, "description");
	}

	String getMetaTag(Document document, String attr) {
		Elements heads = document.getElementsByTag("head");
		if(heads.size() > 0 ){
			Element head = heads.get(0);
			Elements metaElements = head.getElementsByTag("meta");
			for (Element element : metaElements) {
				String name = element.attr("name");
				if (name != null && name.contains(attr)){
					return element.attr("content");
				}
			}
		}		
		return null;
	}

	private String getTitle(Document doc) {
		return this.getMetaTag(doc, "title");
	}

}
