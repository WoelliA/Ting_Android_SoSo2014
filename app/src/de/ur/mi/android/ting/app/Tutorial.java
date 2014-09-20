package de.ur.mi.android.ting.app;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.BrowseBoardsActivity;
import de.ur.mi.android.ting.app.activities.EditBoardActivity;
import de.ur.mi.android.ting.app.activities.EditProfileActivity;
import de.ur.mi.android.ting.app.activities.MainActivity;
import de.ur.mi.android.ting.app.activities.tutorial.BrowseBoardsTutorialActivity;
import de.ur.mi.android.ting.app.activities.tutorial.CreateBoardTutorialActivity;
import de.ur.mi.android.ting.app.activities.tutorial.EditProfileTutorialActivity;
import de.ur.mi.android.ting.app.controllers.CategoriesController;
import de.ur.mi.android.ting.app.controllers.MultiSelectCategoriesController;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.dummy.DelayTask;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.UniqueBase;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.NotifyKind;
import android.content.Context;
import android.content.Intent;

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
	private int nextStepNum = 0;

	private TutorialStep currentStep;

	private Tutorial() {
		TingApp.current().inject(this);

		TingApp.current().addContextChangedListener(this);
		this.steps = new TutorialStep[] {
				new TutorialStep(EditProfileTutorialActivity.class,
						R.string.welcome_dialog_edit_profile),
				new TutorialStep(BrowseBoardsTutorialActivity.class,
						R.string.welcome_dialog_follow_board),
				new TutorialStep(CreateBoardTutorialActivity.class,
						R.string.welcome_dialog_edit_board) };
	}

	public void proceed(Context context) {
		if (this.nextStepNum >= this.steps.length) {
			endTutorial(context);
			return;
		}

		this.currentStep = this.steps[this.nextStepNum];
		this.showDialog(context);
		this.startActivity(context);
		this.nextStepNum++;
	}

	private void endTutorial(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
		this.currentStep = null;
		current = null;
	}

	private void startActivity(Context context) {
		Intent intent = new Intent(context, this.currentStep.getTargetClass());
		intent.putExtra(IS_TUTORIAL_KEY, true);
		context.startActivity(intent);
	}

	private void showDialog(Context context) {
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
		if (currentStep == null) {
			return;
		}
		if (!context.getClass().equals(this.currentStep.getTargetClass())) {
			this.setStep(context);
		}
		DelayTask task = new DelayTask(500) {
			@Override
			protected void onPostExecute(Void result) {
				if (TingApp.isRelease()) {
					Tutorial.this.showDialog(context);
				}
				super.onPostExecute(result);
			}
		};
		task.execute();
	}

	private void setStep(Context context) {
		for (int i = 0; i < steps.length; i++) {
			TutorialStep step = steps[i];
			if (step.getTargetClass().equals(context.getClass())) {
				this.currentStep = step;
				this.nextStepNum = i+1;
				return;
			}
		}
		//endTutorial(context);
	}
}
