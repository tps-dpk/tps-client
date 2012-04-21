package de.wbh.projektarbeit.tps.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.wbh.projektarbeit.tps.BusinessLogic;
import de.wbh.projektarbeit.tps.R;
import de.wbh.projektarbeit.tps.adapter.AdapterException;
import de.wbh.projektarbeit.tps.adapter.BackendAdapter;
import de.wbh.projektarbeit.tps.model.Arbeitsauftrag;
import de.wbh.projektarbeit.tps.model.Kunde;
import de.wbh.projektarbeit.tps.model.Status;

public class Task extends Activity {
	private static final String LOG_TAG = Task.class.getSimpleName();

	private static final int DIALOG_STATUS_FAILED_ID = 1;

	private static final int DIALOG_STATUS_PROGRESS_ID = 2;

	private static final int DIALOG_TECHNICAL_ERROR = 3;

	private Arbeitsauftrag mArbeitsauftrag;

	private Button mAcceptButton;

	private Button mDeclineButton;

	private Button mDoneButton;

	private void changeStatus(Status pNewStatus) {
		new ChangeStatusTask(mArbeitsauftrag, pNewStatus).execute();
	}

	private void initializeViews() {
		mAcceptButton = (Button) findViewById(R.id.task_button_accept);
		mDeclineButton = (Button) findViewById(R.id.task_button_decline);
		mDoneButton = (Button) findViewById(R.id.task_button_done);

		String status = mArbeitsauftrag.getStatus();

		if (Status.ANGENOMMEN.value.equals(status)) {
			mAcceptButton.setVisibility(View.GONE);
			mDeclineButton.setVisibility(View.GONE);
			mDoneButton.setVisibility(View.VISIBLE);
		}

		TextView numberView = (TextView) findViewById(R.id.task_number);
		TextView vonView = (TextView) findViewById(R.id.task_von);
		TextView bisView = (TextView) findViewById(R.id.task_bis);
		TextView beschreibungView = (TextView) findViewById(R.id.task_beschreibung);
		TextView kundeNameView = (TextView) findViewById(R.id.task_kunde_name);
		TextView kundeStrasseView = (TextView) findViewById(R.id.task_kunde_strasse);
		TextView kundeHausNrView = (TextView) findViewById(R.id.task_kunde_hausnr);
		TextView kundePlzView = (TextView) findViewById(R.id.task_kunde_plz);
		TextView kundeOrtView = (TextView) findViewById(R.id.task_kunde_ort);
		TextView kundeTelefonView = (TextView) findViewById(R.id.task_kunde_telefon);

		String von = DateFormat.getMediumDateFormat(this).format(
				mArbeitsauftrag.getVon())
				+ " "
				+ DateFormat.getTimeFormat(Task.this).format(
						mArbeitsauftrag.getVon());
		;
		String bis = DateFormat.getMediumDateFormat(this).format(
				mArbeitsauftrag.getBis())
				+ " "
				+ DateFormat.getTimeFormat(Task.this).format(
						mArbeitsauftrag.getBis());
		;

		numberView.setText(Long.toString(mArbeitsauftrag.getNummer()));
		vonView.setText(von);
		bisView.setText(bis);
		beschreibungView.setText(mArbeitsauftrag.getBeschreibung());

		Kunde kunde = mArbeitsauftrag.getKunde();
		kundeNameView.setText(kunde.getName());
		kundeStrasseView.setText(kunde.getStrasse());
		kundeHausNrView.setText(kunde.getHausnummer());
		kundePlzView.setText(kunde.getPlz());
		kundeOrtView.setText(kunde.getOrt());
		kundeTelefonView.setText(kunde.getTelefonnummer());
	}

	@Override
	protected void onCreate(Bundle pBundle) {
		super.onCreate(pBundle);

		setContentView(R.layout.task);

		mArbeitsauftrag = (Arbeitsauftrag) getIntent().getSerializableExtra(
				"arbeitsauftrag");

		initializeViews();
	}

	@Override
	protected Dialog onCreateDialog(int pId) {
		switch (pId) {
		case DIALOG_STATUS_FAILED_ID:
			return new AlertDialog.Builder(this)
					.setTitle("Statusänderung fehlgeschlagen")
					.setMessage(
							"Die Statusänderung ist fehlgeschlagen. Bitte versuchen Sie es erneut.")
					.setPositiveButton("Ok", null).create();
		case DIALOG_STATUS_PROGRESS_ID:
			return ProgressDialog.show(this, null,
					"Statusänderung wird durchgeführt...", false, false);
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

	public void onClickAccept(View pView) {
		showDialog(DIALOG_STATUS_PROGRESS_ID);

		changeStatus(Status.ANGENOMMEN);
	}

	public void onClickDecline(View pView) {
		showDialog(DIALOG_STATUS_PROGRESS_ID);

		changeStatus(Status.ABGELEHNT);
	}

	public void onClickDone(View pView) {
		showDialog(DIALOG_STATUS_PROGRESS_ID);

		changeStatus(Status.ABGESCHLOSSEN);
	}

	private class ChangeStatusTask extends AsyncTask<Void, Void, Boolean> {
		private final Arbeitsauftrag mArbeitsauftrag;

		private final de.wbh.projektarbeit.tps.model.Status mNewStatus;

		public ChangeStatusTask(Arbeitsauftrag pArbeitsauftrag,
				de.wbh.projektarbeit.tps.model.Status pNewStatus) {
			super();

			mArbeitsauftrag = pArbeitsauftrag;
			mNewStatus = pNewStatus;
		}

		@Override
		protected Boolean doInBackground(Void... pParams) {
			BackendAdapter adapter = ((BusinessLogic) Task.this
					.getApplication()).getBackendAdapter();

			try {
				return adapter.changeStatus(mArbeitsauftrag.getNummer(),
						mNewStatus);
			} catch (AdapterException e) {
				Log.e(LOG_TAG,
						"Change status of task " + mArbeitsauftrag.getNummer()
								+ " to " + mNewStatus + " failed!", e);

				return null;
			}
		}

		@Override
		protected void onPostExecute(Boolean pResult) {
			super.onPostExecute(pResult);

			removeDialog(DIALOG_STATUS_PROGRESS_ID);

			if (pResult == null) {
				showDialog(DIALOG_TECHNICAL_ERROR);

				return;
			}

			if (pResult) {
				Toast.makeText(Task.this,
						"Der Status wurde erfolgreich geändert!",
						Toast.LENGTH_LONG).show();

				if (mNewStatus
						.equals(de.wbh.projektarbeit.tps.model.Status.ABGELEHNT)
						|| mNewStatus
								.equals(de.wbh.projektarbeit.tps.model.Status.ABGESCHLOSSEN)) {
					Intent i = new Intent(Task.this, Tasks.class);
					startActivity(i);
					finish();
				} else if (mNewStatus
						.equals(de.wbh.projektarbeit.tps.model.Status.ANGENOMMEN)) {
					mAcceptButton.setVisibility(View.GONE);
					mDeclineButton.setVisibility(View.GONE);
					mDoneButton.setVisibility(View.VISIBLE);
				}
			} else {
				showDialog(DIALOG_STATUS_FAILED_ID);
			}
		}
	}
}
