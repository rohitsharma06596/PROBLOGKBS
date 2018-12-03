package kbs.problog.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class IdbModel.
 */
public class IdbModel {

	/** The fact. */
	private FactModel fact = new FactModel();

	/** The prob fact. */
	List<Double> prob_fact = new ArrayList<Double>();

	/**
	 * Instantiates a new idb model.
	 *
	 * @param f the f
	 * @param uncertainty the uncertainty
	 */
	public IdbModel(FactModel f, Double uncertainty) {
		this.setFact(f);
		this.prob_fact.add(uncertainty);
	}

	/**
	 * Instantiates a new idb model.
	 */
	public IdbModel() {

	}

	/**
	 * Sets the prob.
	 *
	 * @param prob the new prob
	 */
	public void setProb(Double prob) {
		this.fact.getFact().setProbability(prob);
	}

	/**
	 * Gets the fact.
	 *
	 * @return the fact
	 */
	public FactModel getFact() {
		return fact;
	}

	/**
	 * Sets the fact.
	 *
	 * @param fact the new fact
	 */
	public void setFact(FactModel fact) {
		this.fact.setFact(fact.getFact());
	}

	/**
	 * Gets the prob fact.
	 *
	 * @return the prob fact
	 */
	public List<Double> getProb_fact() {
		return this.prob_fact;
	}

	/**
	 * Sets the prob fact.
	 *
	 * @param prob the new prob fact
	 */
	public void setProb_fact(Double prob) {
		this.prob_fact.add(prob);
	}

}
