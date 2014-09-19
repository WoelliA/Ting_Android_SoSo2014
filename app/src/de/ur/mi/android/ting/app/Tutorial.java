package de.ur.mi.android.ting.app;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.AbstractMap.SimpleEntry;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.EditBoardActivity;
import de.ur.mi.android.ting.app.activities.EditProfileActivity;
import de.ur.mi.android.ting.app.activities.MainActivity;
import de.ur.mi.android.ting.app.activities.SelectCategoriesActivity;
import de.ur.mi.android.ting.model.dummy.DelayTask;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.NotifyKind;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Tutorial implements IChangeListener<Context> {
	private static Tutorial current;

	public static final String IS_TUTORIAL_KEY = "istutorial";

	public static Tutorial current() {
		if (current == null) {
			current = new Tutorial();
		}
		return current;
	}

	public static Tutorial getTutorial(Intent intent) {
		if (intent != null && intent.getExtras() != null) {
			boolean isTutorial = intent.getExtras().getBoolean(IS_TUTORIAL_KEY);
			if (isTutorial) {
				return Tutorial.current();
			}
		}
		return null;
	}

	private TutorialStep[] steps;
	private int currentStepNum = 0;

	private TutorialStep currentStep;

	private Tutorial() {
		//
		// new TutorialStep(BrowseBoardsActivity.class,
		// R.string.welcome_dialog_follow_board),
		// new TutorialStep(SelectCategoriesActivity.class,
		// R.string.welcome_dialog_select_interests),

		TingApp.current().addContextChangedListener(this);
		this.steps = new TutorialStep[] {
				new TutorialStep(EditProfileActivity.class,
						R.string.welcome_dialog_edit_profile),

				new TutorialStep(EditBoardActivity.class,
						R.string.welcome_dialog_edit_board) };
	}

	public void proceed(Context context,
			SimpleEntry<String, ? extends Serializable>... parameters) {
		if (this.currentStepNum >= this.steps.length) {
			Intent intent = new Intent(context, MainActivity.class);
			if (parameters != null) {
				for (SimpleEntry<String, ? extends Object> simpleEntry : parameters) {
					Serializable value = (Serializable) simpleEntry.getValue();
					intent.putExtra(simpleEntry.getKey(), value);
				}
			}
			context.startActivity(intent);
			this.currentStep = null;
			current = null;
			return;
		}

		this.currentStep = this.steps[this.currentStepNum];
		this.ShowDialog(context);
		this.StartActivity(context);
		this.currentStepNum++;
	}

	private void StartActivity(Context context) {
		Intent intent = new Intent(context, this.currentStep.getTargetClass());
		intent.putExtra(IS_TUTORIAL_KEY, true);
		context.startActivity(intent);
	}

	private void ShowDialog(Context context) {
		if (this.currentStep != null && !this.currentStep.getWasDialogShown()) {
			if (this.currentStep.getTargetClass().equals(context.getClass())) {
				this.currentStep.setWasDialogShown(true);
				Notify.current().show(R.string.welcome_dialog_title,
						this.currentStep.getDialogContentId(), NotifyKind.INFO);
			}
		}
	}

	@Override
	public void onChange(final Context context) {
		DelayTask task = new DelayTask(500) {
			@Override
			protected void onPostExecute(Void result) {
				Tutorial.this.ShowDialog(context);
				super.onPostExecute(result);
			}
		};
		task.execute();
	}
}
