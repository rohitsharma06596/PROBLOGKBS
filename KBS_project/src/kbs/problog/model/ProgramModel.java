package kbs.problog.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ProgramModel.
 */
public class ProgramModel {

	/** The ruleslist. */
	private List<RulesModel> ruleslist = new ArrayList<>();

	/** The facts. */
	private List<FactModel> edb = new ArrayList<>();

	/** The idb. */
	private List<IdbModel> idb = new ArrayList<>();;

	/**
	 * Gets the ruleslist.
	 *
	 * @return the ruleslist
	 */
	public List<RulesModel> getRuleslist() {
		return ruleslist;
	}

	/**
	 * Sets the ruleslist.
	 *
	 * @param rulelist the new ruleslist
	 */
	public void setRuleslist(List<RulesModel> rulelist) {
		for (int i = 0; i < rulelist.size(); i++) {
			this.ruleslist.add(rulelist.get(i));
		}
	}

	/**
	 * Gets the facts.
	 *
	 * @return the facts
	 */
	public List<FactModel> getFacts() {
		return edb;
	}

	/**
	 * Sets the facts.
	 *
	 * @param facts the new facts
	 */
	public void setFacts(List<FactModel> facts) {
		int i;
		for (i = 0; i < facts.size(); i++) {
			edb.add(facts.get(i));
		}

	}

	/**
	 * Sets the idb.
	 *
	 * @param parmIdb the new idb
	 */
	public void setIdb(IdbModel parmIdb) {
		this.idb.add(parmIdb);
	}

	/**
	 * Gets the idb.
	 *
	 * @return the idb
	 */
	public List<IdbModel> getIdb() {
		return idb;
	}
}
