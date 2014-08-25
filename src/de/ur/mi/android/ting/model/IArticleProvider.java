package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.model.Primitives.Category;

public interface IArticleProvider {
	public void GetArticles(Category category, ArticlesRequest request, IArticlesCallback callback);
}
