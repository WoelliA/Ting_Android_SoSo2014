package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.ArticlesRequest;
import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.IArticlesCallback;
import de.ur.mi.android.ting.model.Primitives.Category;
import de.ur.mi.android.ting.model.Primitives.Pin;

public class DummyArticleProvider implements IArticleProvider {

	@Override
	public void GetArticles(Category category, ArticlesRequest request,
			IArticlesCallback callback) {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Pin> articles = new ArrayList<Pin>();
		for (int i = 0; i < request.getCount(); i++) {
			articles.add(new DummyPin(i));
		}
		callback.onArticlesReceived(articles);
	}

}
