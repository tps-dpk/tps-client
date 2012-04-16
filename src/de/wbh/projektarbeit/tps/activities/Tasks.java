package de.wbh.projektarbeit.tps.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.wbh.projektarbeit.tps.R;

public class Tasks extends ListActivity {
	@Override
	protected void onCreate(Bundle pBundle) {
		super.onCreate(pBundle);

		setContentView(R.layout.tasks);

		setListAdapter(new ArrayAdapter<Object>(this,
				R.layout.tasks_list_entry, new Object[] { "", "" }) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				// return super.getView(position, convertView, parent);

				LayoutInflater li = getLayoutInflater();
				return li.inflate(R.layout.tasks_list_entry, null);

			}

		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Intent i = new Intent(this, Task.class);
		startActivity(i);
	}

	public void onClickUpdate(View pView) {

	}
}
