package kbs.problog.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class FactModel.
 */
public class FactModel {

	/** The fact. */
	private List<PredicateModel> fact = new ArrayList<PredicateModel>();

	/**
	 * Gets the fact.
	 *
	 * @return the fact
	 */
	public List<PredicateModel> getFact() {
		return fact;
	}

	/**
	 * Sets the fact.
	 *
	 * @param fact the new fact
	 */
	public void setFact(List<PredicateModel> fact) {
		this.fact = fact;
	}

	/**
	 * Addfact.
	 *
	 * @param p the p
	 */
	public void addfact(PredicateModel p) {
		fact.add(p);
	}
}
