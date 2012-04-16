package de.wbh.projektarbeit.tps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Arbeitsauftraege implements Serializable {
	private static final long serialVersionUID = -2522301701013105700L;

	private final List<Arbeitsauftrag> arbeitsauftraege = new ArrayList<Arbeitsauftrag>();

	public List<Arbeitsauftrag> getArbeitsauftraege() {
		return arbeitsauftraege;
	}
}
