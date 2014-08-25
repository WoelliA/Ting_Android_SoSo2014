package de.ur.mi.android.ting.model;

public interface IArticleProvider {
	public void GetArticles(Category category, ArticlesRequest request, IArticlesCallback callback);
}
