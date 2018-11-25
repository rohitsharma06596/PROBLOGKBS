package kbs.problog.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesModel.
 */
public class RulesModel {

	/** The head. */
	PredicateModel head = new PredicateModel();
	
	/** The body. */
	private List<PredicateModel> body = new ArrayList<PredicateModel>();
	// private Double probability;

	/**
	 * Gets the head.
	 *
	 * @return the head
	 */
	public PredicateModel getHead() {
		return head;
	}

	/**
	 * Sets the head.
	 *
	 * @param head the new head
	 */
	public void setHead(PredicateModel head) {
		this.head.setPredName(head.getPredName());
		this.head.setArity(head.getArity());
		this.head.setProbability(head.getProbability());
		this.head.setArguments(head.getArguments());
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public List<PredicateModel> getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param e the new body
	 */
	public void setBody(PredicateModel e) {
		this.body.add(e);
	}

	/**
	 * Adds the head.
	 *
	 * @param p the p
	 */
	public void addHead(PredicateModel p) {
		this.head.setPredName(p.getPredName());
		this.head.setArity(p.getArity());
		this.head.setProbability(p.getProbability());
		this.head.setArguments(p.getArguments());
	}

}
