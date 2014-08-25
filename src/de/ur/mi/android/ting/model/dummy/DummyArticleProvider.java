package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.Article;
import de.ur.mi.android.ting.model.ArticlesRequest;
import de.ur.mi.android.ting.model.Category;
import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.IArticlesCallback;

public class DummyArticleProvider implements IArticleProvider {

	@Override
	public void GetArticles(Category category, ArticlesRequest request,
			IArticlesCallback callback) {
		ArrayList<Article> articles = new ArrayList<Article>();
		for (int i = 0; i < request.getCount(); i++) {
			articles.add(new DummyArticle(i));
		}
		callback.onArticlesReceived(articles);
	}

}
