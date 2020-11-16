package model;

import java.util.HashSet;

public class Var {

	private HashSet<String> ps;
	private String idPs;

	public Var(String idPs) {
		this.ps = new HashSet<>();
		this.idPs = idPs;

	}

	public void addP(String p) {
		if (!ps.contains(p)) {
			ps.add(p);
		}
	}

	public HashSet<String> getPs() {
		return ps;
	}

	public void setPs(HashSet<String> ps) {
		this.ps = ps;
	}

	public String getId() {
		return idPs;
	}

	public void setId(String idP) {
		this.idPs = idP;
	}

}
