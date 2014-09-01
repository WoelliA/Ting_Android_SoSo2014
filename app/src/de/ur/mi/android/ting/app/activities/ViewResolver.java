package de.ur.mi.android.ting.app.activities;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.User;
import android.view.View;

public abstract class ViewResolver<T> {
	public abstract View resolveView(T dataItem);
	
	public static class PinViewResolver extends ViewResolver<Pin>{

		@Override
		public View resolveView(Pin dataItem) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public static class BoardViewResolver extends ViewResolver<Board>{

		@Override
		public View resolveView(Board dataItem) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public static class UserViewResolver extends ViewResolver<User>{

		@Override
		public View resolveView(User dataItem) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
