package de.ur.mi.android.ting.app.viewResolvers;

import dagger.Module;

@Module(injects = { PinListViewResolver.class,
		SearchResultResolvers.BoardResolver.class,
		SearchResultResolvers.PinResolver.class,
		SearchResultResolvers.UserResolver.class }
,complete = false)
public class _ResolverModule {

}
