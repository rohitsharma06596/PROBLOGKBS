package kbs.problog.model;

import java.util.ArrayList;
import java.util.List;

public class IdbModel {
	
	/** The fact. */
	private FactModel fact;

	List<Double> prob_fact= new ArrayList();
	 
	 public IdbModel (FactModel f, List<Double> uncertainty ) {
		 
		 this.fact = f;
		 this.prob_fact = uncertainty;
	 }

	public FactModel getFact() {
		return fact;
	}

	public void setFact(FactModel fact) {
		this.fact = fact;
	}

	public List<Double> getProb_fact() {
		return this.prob_fact;
	}

	public void setProb_fact(List<Double> prob_fact) {
		this.prob_fact = prob_fact;
	}

	 

}
