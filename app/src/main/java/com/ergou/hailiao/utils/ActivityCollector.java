package com.ergou.hailiao.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();

	public static void AddActivity(Activity activity) {
		activities.add(activity);
	}

	public static void RemoveActivity(Activity activity) {
		activities.remove(activity);
	}

	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}

		}
	}
}
