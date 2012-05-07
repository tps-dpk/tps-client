package de.wbh.projektarbeit.tps;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import de.wbh.projektarbeit.tps.adapter.BackendAdapter;
import de.wbh.projektarbeit.tps.adapter.BackendAdapterImpl;

public class BusinessLogic extends Application {
	private BackendAdapter mBackendAdapter;

	public BackendAdapter getBackendAdapter() {
		if (mBackendAdapter == null) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			String baseUrl = preferences.getString("backend_url", null);

			// mBackendAdapter = new FakeBackendAdapter();
			mBackendAdapter = new BackendAdapterImpl(baseUrl);
		}

		return mBackendAdapter;
	}
}
