package de.wbh.projektarbeit.tps;

import de.wbh.projektarbeit.tps.adapter.BackendAdapter;
import de.wbh.projektarbeit.tps.adapter.BackendAdapterImpl;
import android.app.Application;

public class BusinessLogic extends Application {
	private BackendAdapter mBackendAdapter;

	public BackendAdapter getBackendAdapter() {
		if (mBackendAdapter == null) {
			mBackendAdapter = new BackendAdapterImpl(null);
		}

		return mBackendAdapter;
	}
}
