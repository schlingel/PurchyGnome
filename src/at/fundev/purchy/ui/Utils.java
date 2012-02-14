package at.fundev.purchy.ui;

import android.content.Context;

public class Utils {
	public static String format(Context context, int stringId, Object... params) {
		final String formatString = context.getString(stringId);
		return String.format(formatString, params);
	}
}
