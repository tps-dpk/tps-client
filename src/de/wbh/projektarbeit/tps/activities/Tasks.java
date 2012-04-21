package de.wbh.projektarbeit.tps.activities;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.wbh.projektarbeit.tps.BusinessLogic;
import de.wbh.projektarbeit.tps.R;
import de.wbh.projektarbeit.tps.adapter.AdapterException;
import de.wbh.projektarbeit.tps.adapter.BackendAdapter;
import de.wbh.projektarbeit.tps.model.Arbeitsauftraege;
import de.wbh.projektarbeit.tps.model.Arbeitsauftrag;

public class Tasks extends ListActivity {
	private static final String LOG_TAG = Tasks.class.getSimpleName();

	private static final int DIALOG_TECHNICAL_ERROR = 1;

	private View mProgressBar;

	private void initializeList() {
		new LoadTask().execute();
	}

	@Override
	protected void onCreate(Bundle pBundle) {
		super.onCreate(pBundle);

		setContentView(R.layout.tasks);

		mProgressBar = findViewById(R.id.tasks_progress);
	}

	@Override
	protected void onResume() {
		super.onResume();

		initializeList();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Arbeitsauftrag auftrag = (Arbeitsauftrag) l.getItemAtPosition(position);

		Intent i = new Intent(this, Task.class);
		i.putExtra("arbeitsauftrag", auftrag);

		startActivity(i);
	}

	@Override
	protected Dialog onCreateDialog(int pId) {
		switch (pId) {
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

	public void onClickUpdate(View pView) {
		mProgressBar.setVisibility(View.VISIBLE);

		initializeList();
	}

	private class LoadTask extends AsyncTask<Void, Void, Arbeitsauftraege> {
		@Override
		protected Arbeitsauftraege doInBackground(Void... pArguments) {
			BackendAdapter adapter = ((BusinessLogic) Tasks.this
					.getApplication()).getBackendAdapter();
			try {
				return adapter.getArbeitsauftraege();
			} catch (AdapterException e) {
				Log.e(LOG_TAG, "Loading tasks failed!", e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Arbeitsauftraege pResult) {
			super.onPostExecute(pResult);

			mProgressBar.setVisibility(View.GONE);

			if (pResult == null) {
				showDialog(DIALOG_TECHNICAL_ERROR);

				return;
			}

			setListAdapter(new TasksAdapter(Tasks.this,
					pResult.getArbeitsauftraege()));
		}
	}

	private class TasksAdapter extends ArrayAdapter<Arbeitsauftrag> {
		private TasksAdapter(Context pContext, List<Arbeitsauftrag> pTasks) {
			super(pContext, -1, pTasks);
		}

		@Override
		public View getView(int pPosition, View pConvertView, ViewGroup pParent) {
			View rowView = pConvertView;

			if (rowView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = vi.inflate(R.layout.tasks_list_entry, null);
			}

			Arbeitsauftrag task = getItem(pPosition);
			TextView statusView = (TextView) rowView
					.findViewById(R.id.task_entry_status);
			TextView beschreibungView = (TextView) rowView
					.findViewById(R.id.task_entry_beschreibung);
			TextView vonView = (TextView) rowView
					.findViewById(R.id.task_entry_von);
			TextView bisView = (TextView) rowView
					.findViewById(R.id.task_entry_bis);

			String von = DateFormat.getMediumDateFormat(Tasks.this).format(
					task.getVon())
					+ " "
					+ DateFormat.getTimeFormat(Tasks.this)
							.format(task.getVon());
			String bis = DateFormat.getMediumDateFormat(Tasks.this).format(
					task.getBis())
					+ " "
					+ DateFormat.getTimeFormat(Tasks.this)
							.format(task.getBis());
			;

			statusView.setText(task.getStatus());
			beschreibungView.setText(task.getBeschreibung());
			vonView.setText(von);
			bisView.setText(bis);

			return rowView;
		}
	}
}
