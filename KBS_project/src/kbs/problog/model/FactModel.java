package kbs.problog.model;

/*import java.util.ArrayList;
import java.util.List;
*/
// TODO: Auto-generated Javadoc
/**
 * The Class FactModel.
 */
public class FactModel {

	/** The fact. */
	private PredicateModel fact = new PredicateModel();
	/**
	 * Gets the fact.
	 *
	 * @return the fact
	 */
	public PredicateModel getFact() {
		return fact;
	}

	/**
	 * Sets the fact.
	 *
	 * @param fact the new fact
	 */
	public void setFact(PredicateModel fact) {
		this.fact.setPredName(fact.getPredName());
		this.fact.setArity(fact.getArity());
		this.fact.setProbability(fact.getProbability());
		this.fact.setArguments(fact.getArguments());
	}

	/**
	 * Addfact.
	 *
	 * @param p the p
	 */
	
}
