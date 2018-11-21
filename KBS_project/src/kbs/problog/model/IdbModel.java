package kbs.problog.model;

import java.util.ArrayList;

public class IdbModel {
	
	/** The fact. */
	private FactModel fact;

	ArrayList<Double> prob_fact= new ArrayList();
	 
	 public IdbModel (FactModel f, ArrayList<Double> uncertainty ) {
		 
		 this.fact = f;
		 this.prob_fact = uncertainty;
	 }

	public FactModel getFact() {
		return fact;
	}

	public void setFact(FactModel fact) {
		this.fact = fact;
	}

	public ArrayList<Double> getProb_fact() {
		return prob_fact;
	}

	public void setProb_fact(ArrayList<Double> prob_fact) {
		this.prob_fact = prob_fact;
	}

	 

}
