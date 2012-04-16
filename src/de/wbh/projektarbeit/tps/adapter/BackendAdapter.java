package de.wbh.projektarbeit.tps.adapter;

import de.wbh.projektarbeit.tps.model.Arbeitsauftraege;
import de.wbh.projektarbeit.tps.model.Status;

public interface BackendAdapter {
	boolean changeStatus(long pArbeitsauftragNummer, Status pStatus);

	Arbeitsauftraege getArbeitsauftraege();

	boolean login(String pUserName, String pPassword);
}
