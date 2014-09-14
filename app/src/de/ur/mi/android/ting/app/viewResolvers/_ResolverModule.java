package de.ur.mi.android.ting.app.viewResolvers;

import dagger.Module;

@Module(injects = { PinViewResolver.class,
		SearchResultResolvers.BoardResolver.class,
		SearchResultResolvers.PinSearchResultViewResolver.class,
		SearchResultResolvers.UserResolver.class, 
		CategoryViewResolver.class,
		BoardViewResolver.class ,
		BoardPinViewResolver.class}
, complete = false)
public class _ResolverModule {

}
