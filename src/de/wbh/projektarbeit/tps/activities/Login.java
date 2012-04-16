package de.wbh.projektarbeit.tps.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import de.wbh.projektarbeit.tps.BusinessLogic;
import de.wbh.projektarbeit.tps.R;
import de.wbh.projektarbeit.tps.adapter.BackendAdapter;

public class Login extends Activity {
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
							"Die Anmeldung ist fehlgeschlagen. Bitte versuchen Sie es erneut.")
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

	@Override
	protected void onResume() {
		super.onResume();
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

	private class LoginTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... pArguments) {
			String name = mNameView.getText().toString();
			String password = mPasswordView.getText().toString();

			BackendAdapter adapter = ((BusinessLogic) Login.this
					.getApplication()).getBackendAdapter();
			adapter.login(name, password);

			return null;
		}

		@Override
		protected void onPostExecute(Void pResult) {
			super.onPostExecute(pResult);

			removeDialog(DIALOG_LOGIN_PROGRESS_ID);

			Intent i = new Intent(Login.this, Tasks.class);
			startActivity(i);
			finish();
		}
	}
}
