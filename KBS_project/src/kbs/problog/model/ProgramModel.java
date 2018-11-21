package kbs.problog.model;

import java.util.HashMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgramModel.
 */
public class ProgramModel {
	
	/** The ruleslist. */
	private List<RulesModel> ruleslist;
	
	/** The facts. */
	private List<FactModel> edb;
	
	/** The idb. */
	private List<IdbModel> idb;

	/*
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
	public List<FactModel> getFacts() {
		return edb;
	}

	/**
	 * Sets the facts.
	 *
	 * @param facts the new facts
	 */
	public void setFacts(List<FactModel> facts) {
		edb = facts;
	}
	public void setIdb(IdbModel parmIdb)
	{
		this.idb.add(parmIdb);
	}
	public List<IdbModel> getIdb()
	{
		return idb;
	}
}
