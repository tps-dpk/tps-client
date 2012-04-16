package de.wbh.projektarbeit.tps.activities;

import de.wbh.projektarbeit.tps.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle pBundle) {
		super.onCreate(pBundle);

		addPreferencesFromResource(R.layout.preferences);
	}
}
