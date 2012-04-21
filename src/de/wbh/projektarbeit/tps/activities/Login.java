package de.wbh.projektarbeit.tps.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import de.wbh.projektarbeit.tps.BusinessLogic;
import de.wbh.projektarbeit.tps.R;
import de.wbh.projektarbeit.tps.adapter.AdapterException;
import de.wbh.projektarbeit.tps.adapter.BackendAdapter;

public class Login extends Activity {
	private static final String LOG_TAG = Login.class.getSimpleName();

	private static final int DIALOG_LOGIN_FAILED_ID = 1;

	private static final int DIALOG_LOGIN_PROGRESS_ID = 2;

	private static final int DIALOG_TECHNICAL_ERROR = 3;

	private EditText mNameView;

	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle pBundle) {
		super.onCreate(pBundle);

		setContentView(R.layout.login);

		mNameView = (EditText) findViewById(R.id.login_name);
		mPasswordView = (EditText) findViewById(R.id.login_password);
	}

	@Override
	protected Dialog onCreateDialog(int pId) {
		switch (pId) {
		case DIALOG_LOGIN_FAILED_ID:
			return new AlertDialog.Builder(this)
					.setTitle("Anmeldung fehlgeschlagen")
					.setMessage(
							"Die Anmeldung ist fehlgeschlagen. Bitte überprüfen Sie ihre Eingaben.")
					.setPositiveButton("Ok", null).create();
		case DIALOG_LOGIN_PROGRESS_ID:
			return ProgressDialog.show(this, null,
					"Anmeldung wird durchgeführt...", false, false);
		case DIALOG_TECHNICAL_ERROR:
			return new AlertDialog.Builder(this)
					.setTitle("Technisches Problem")
					.setMessage(
							"Es ist ein unbekanntes technisches Problem aufgetreten. Bitte versuchen Sie es später erneut.")
					.setPositiveButton("Ok", null).create();
		default:
			return super.onCreateDialog(pId);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu pMenu) {
		getMenuInflater().inflate(R.menu.login_menu, pMenu);

		return true;
	}

	public void onClickLogin(View pView) {
		Editable name = mNameView.getText();
		Editable password = mPasswordView.getText();

		if (name == null || name.length() < 1) {
			Toast.makeText(this, "Bitte geben Sie ihren Benutzernamen ein!",
					Toast.LENGTH_LONG).show();
			return;
		} else if (password == null || password.length() < 1) {
			Toast.makeText(this, "Bitte geben Sie ihr Passwort ein!",
					Toast.LENGTH_LONG).show();
			return;
		}

		showDialog(DIALOG_LOGIN_PROGRESS_ID);

		new LoginTask().execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem pItem) {
		if (pItem.getItemId() == R.id.cm_preferences) {
			startActivity(new Intent(this, Preferences.class));

			return true;
		}

		return super.onOptionsItemSelected(pItem);
	}

	private class LoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... pArguments) {
			String name = mNameView.getText().toString();
			String password = mPasswordView.getText().toString();

			BackendAdapter adapter = ((BusinessLogic) Login.this
					.getApplication()).getBackendAdapter();
			try {
				return adapter.login(name, password);
			} catch (AdapterException e) {
				Log.e(LOG_TAG, "Logging in failed!", e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Boolean pResult) {
			super.onPostExecute(pResult);

			removeDialog(DIALOG_LOGIN_PROGRESS_ID);

			if (pResult == null) {
				showDialog(DIALOG_TECHNICAL_ERROR);

				return;
			}

			if (pResult) {
				Toast.makeText(Login.this, "Die Anmeldung war erfolgreich!",
						Toast.LENGTH_LONG).show();

				Intent i = new Intent(Login.this, Tasks.class);
				startActivity(i);
				finish();
			} else {
				showDialog(DIALOG_LOGIN_FAILED_ID);
			}
		}
	}
}
