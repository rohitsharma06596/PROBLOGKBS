package kbs.problog.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgramModel.
 */
public class ProgramModel {
	
	/** The ruleslist. */
	private List<RulesModel> ruleslist;
	
	/** The facts. */
	private FactModel edb;
	
	/** The idb. */
	private FactModel idb;

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
	 * @param ruleslist the new ruleslist
	 */
	public void setRuleslist(List<RulesModel> ruleslist) {
		this.ruleslist = ruleslist;
	}

	/**
	 * Gets the facts.
	 *
	 * @return the facts
	 */
	public FactModel getFacts() {
		return edb;
	}

	/**
	 * Sets the facts.
	 *
	 * @param facts the new facts
	 */
	public void setFacts(FactModel facts) {
		this.edb = facts;
	}

}
