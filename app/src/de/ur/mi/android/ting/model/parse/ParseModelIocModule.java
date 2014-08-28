package de.ur.mi.android.ting.model.parse;

import android.content.Context;

import com.parse.Parse;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.IModelIocModule;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinProvider;

@Module(complete=true,
library=true)
public class ParseModelIocModule implements IModelIocModule {

	private static final String applicationId = "rnklQPqG2yqcKwmXfYqMSqQ2CoF6lGB56sofWiHt";
	private static final String clientKey = "u6OGwXAEEcm1qUZ8n75o5SuDBLo3rLw8kZrsAxCp";
	
	public ParseModelIocModule(Context context) {
		Parse.initialize(context, applicationId, clientKey);
		Parse.enableLocalDatastore(context);
	}

	@Override
	@Provides
	public IPinProvider provideIArticleProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Provides
	public ICategoryProvider provideICategoryProvider() {
		return new ParseCategoryProvider();
	}

}
