package kbs.problog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgramModel.
 */
public class ProgramModel {
	
	/** The ruleslist. */
	private List<RulesModel> ruleslist = new ArrayList();
	
	/** The facts. */
	private List<FactModel> edb = new ArrayList();
	
	/** The idb. */
	private List<IdbModel> idb = new ArrayList(); ;

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
	public void setRuleslist(List<RulesModel> rulelist) {
		//this.ruleslist = rulelist;
		for(int i=0;i<rulelist.size();i++)
		{
			ruleslist.add(rulelist.get(i));
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
		for(i=0;i<facts.size();i++) {
			edb.add(facts.get(i));
		}
		for(i=0;i<facts.size();i++) {
			System.out.println("this is edb"+edb.get(i).getFact());
		}
		
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
