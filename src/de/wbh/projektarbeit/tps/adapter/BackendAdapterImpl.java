package de.wbh.projektarbeit.tps.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.wbh.projektarbeit.tps.model.Arbeitsauftraege;
import de.wbh.projektarbeit.tps.model.Status;

public class BackendAdapterImpl implements BackendAdapter {
	private final Uri mBaseUrl;

	private final HttpClient mClient;

	public BackendAdapterImpl(String pBaseUrl) {
		mBaseUrl = Uri.parse(pBaseUrl);

		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 10000);

		mClient = new DefaultHttpClient(params);
	}

	private String createUri(String pPath) {
		return Uri.withAppendedPath(mBaseUrl, pPath).toString();
	}

	@Override
	public boolean changeStatus(long pArbeitsauftragNummer, Status pStatus)
			throws AdapterException {
		HttpPost request = new HttpPost(createUri("status.php"));
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("nummer", Long
				.toString(pArbeitsauftragNummer)));
		params.add(new BasicNameValuePair("status", pStatus.value));

		try {
			request.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse response = mClient.execute(request);

			StatusLine status = response.getStatusLine();

			if (status.getStatusCode() == HttpStatus.SC_OK) {
				return true;
			} else {
				throw new AdapterException(
						"Unexpected response with status code "
								+ status.getStatusCode());
			}
		} catch (Exception e) {
			throw new AdapterException("Changing status failed!", e);
		}
	}

	@Override
	public Arbeitsauftraege getArbeitsauftraege() throws AdapterException {
		HttpGet request = new HttpGet(createUri("list.php"));

		try {
			HttpResponse response = mClient.execute(request);

			StatusLine status = response.getStatusLine();

			if (status.getStatusCode() == HttpStatus.SC_OK) {
				String json = EntityUtils.toString(response.getEntity());
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();

				return gson.fromJson(json, Arbeitsauftraege.class);
			} else {
				throw new AdapterException(
						"Unexpected response with status code "
								+ status.getStatusCode());
			}
		} catch (Exception e) {
			throw new AdapterException("Receiving tasks failed!", e);
		}
	}

	@Override
	public boolean login(String pUserName, String pPassword)
			throws AdapterException {
		HttpPost request = new HttpPost(createUri("login.php"));
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("name", pUserName));
		params.add(new BasicNameValuePair("password", pPassword));

		try {
			request.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse response = mClient.execute(request);

			StatusLine status = response.getStatusLine();

			if (status.getStatusCode() == HttpStatus.SC_OK) {
				return true;
			} else if (status.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				return false;
			} else {
				throw new AdapterException(
						"Unexpected response with status code "
								+ status.getStatusCode());
			}
		} catch (Exception e) {
			throw new AdapterException("Logging in failed!", e);
		}
	}
}
