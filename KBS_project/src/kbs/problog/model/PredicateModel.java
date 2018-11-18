package kbs.problog.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PredicateModel.
 */
public class PredicateModel {

	/** The pred name. */
	private String predName;
	
	/** The arity. */
	private int arity;
	
	/** The arguments. */
	private List<String> arguments;
	
	/** The probability. */
	private Double probability;

	/**
	 * Gets the pred name.
	 *
	 * @return the pred name
	 */
	public String getPredName() {
		return predName;
	}

	/**
	 * Sets the pred name.
	 *
	 * @param predName the new pred name
	 */
	public void setPredName(String predName) {
		this.predName = predName;
	}

	/**
	 * Gets the arity.
	 *
	 * @return the arity
	 */
	public int getArity() {
		return arity;
	}

	/**
	 * Sets the arity.
	 *
	 * @param arity the new arity
	 */
	public void setArity(int arity) {
		this.arity = arity;
	}

	/**
	 * Gets the arguments.
	 *
	 * @return the arguments
	 */
	public List<String> getArguments() {
		return arguments;
	}

	/**
	 * Sets the arguments.
	 *
	 * @param arguments the new arguments
	 */
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	/**
	 * Gets the probability.
	 *
	 * @return the probability
	 */
	public Double getProbability() {
		return probability;
	}

	/**
	 * Sets the probability.
	 *
	 * @param probability the new probability
	 */
	public void setProbability(Double probability) {
		this.probability = probability;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Predicate [predName=" + predName + ", arity=" + arity + ", arguments=" + arguments + ", probability="
				+ probability + "]";
	}

}
