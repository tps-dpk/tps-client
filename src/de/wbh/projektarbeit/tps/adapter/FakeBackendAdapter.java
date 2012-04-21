package de.wbh.projektarbeit.tps.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.wbh.projektarbeit.tps.model.Arbeitsauftraege;
import de.wbh.projektarbeit.tps.model.Arbeitsauftrag;
import de.wbh.projektarbeit.tps.model.Kunde;
import de.wbh.projektarbeit.tps.model.Status;

public class FakeBackendAdapter implements BackendAdapter {

	@Override
	public boolean changeStatus(long pArbeitsauftragNummer, Status pStatus)
			throws AdapterException {
		return true;
	}

	@Override
	public Arbeitsauftraege getArbeitsauftraege() throws AdapterException {
		Arbeitsauftraege root = new Arbeitsauftraege();
		Kunde kunde = new Kunde("Max Musterkunde", "Lindenstr.", "23", "12345",
				"Berlin", "017867363636");
		Arbeitsauftrag auftrag1 = new Arbeitsauftrag(1, "neu", new Date(),
				new Date(), "Installation einer Heizung", kunde);
		Arbeitsauftrag auftrag2 = new Arbeitsauftrag(2, "angenommen",
				new Date(), new Date(), "Installation eines Klos", kunde);
		Arbeitsauftrag auftrag3 = new Arbeitsauftrag(3, "neu", new Date(),
				new Date(), "Installation einer Spüle", kunde);
		Arbeitsauftrag auftrag4 = new Arbeitsauftrag(4, "angenommen",
				new Date(), new Date(), "Installation einer Waschmaschine",
				kunde);
		List<Arbeitsauftrag> auftraege = new ArrayList<Arbeitsauftrag>();
		auftraege.add(auftrag1);
		auftraege.add(auftrag2);
		auftraege.add(auftrag3);
		auftraege.add(auftrag4);
		root.getArbeitsauftraege().addAll(auftraege);

		return root;
	}

	@Override
	public boolean login(String pUserName, String pPassword)
			throws AdapterException {
		return true;
	}
}
