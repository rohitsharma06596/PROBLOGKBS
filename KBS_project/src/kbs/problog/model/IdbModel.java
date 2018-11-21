package kbs.problog.model;

import java.util.ArrayList;

public class IdbModel {
	
	/** The fact. */
	private PredicateModel fact;
	
	 ArrayList<Double> prob_fact= new ArrayList();
	 
	 public IdbModel (PredicateModel p, ArrayList<Double> uncertainty ) {
		 
		 this.fact = p;
		 this.prob_fact = uncertainty;
	 }

	public PredicateModel getFact() {
		return fact;
	}

	public void setFact(PredicateModel fact) {
		this.fact = fact;
	}

	public ArrayList<Double> getProb_fact() {
		return prob_fact;
	}

	public void setProb_fact(ArrayList<Double> prob_fact) {
		this.prob_fact = prob_fact;
	}
	 
	 

}
