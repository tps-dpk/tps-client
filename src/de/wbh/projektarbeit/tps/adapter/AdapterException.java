package de.wbh.projektarbeit.tps.adapter;

public class AdapterException extends Exception {
	private static final long serialVersionUID = 1643311381232836303L;

	public AdapterException(String pDetailMessage) {
		super(pDetailMessage);
	}

	public AdapterException(String pDetailMessage, Throwable pThrowable) {
		super(pDetailMessage, pThrowable);
	}
}
