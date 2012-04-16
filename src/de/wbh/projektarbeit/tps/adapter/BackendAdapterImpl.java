package de.wbh.projektarbeit.tps.adapter;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.net.Uri;

import de.wbh.projektarbeit.tps.model.Arbeitsauftraege;
import de.wbh.projektarbeit.tps.model.Status;

public class BackendAdapterImpl implements BackendAdapter {
	private final Uri mBaseUrl;
	
	private final HttpClient mClient;

	public BackendAdapterImpl(String pBaseUrl) {
		mBaseUrl = Uri.parse(pBaseUrl);
		
		HttpParams params = new BasicHttpParams();
		mClient = new DefaultHttpClient(params);
	}

	@Override
	public boolean changeStatus(long pArbeitsauftragNummer, Status pStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Arbeitsauftraege getArbeitsauftraege() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String pUserName, String pPassword) {
		HttpRequest request = new HttpPost(Uri.withAppendedPath(mBaseUrl, "login.php").toString());
		// request.getParams().
		
		
		// TODO Auto-generated method stub
		return false;
	}
}
