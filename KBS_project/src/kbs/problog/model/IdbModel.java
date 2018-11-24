package kbs.problog.model;

import java.util.ArrayList;
import java.util.List;

public class IdbModel {
	
	/** The fact. */
	private FactModel fact = new FactModel();

	List<Double> prob_fact= new ArrayList<Double>();
	 
	 public IdbModel (FactModel f, Double uncertainty ) {
		 this.setFact(f);
		 this.prob_fact.add(uncertainty);
	 }
	 public IdbModel() {
		 
	 }
	 public void setProb(Double prob)
	 {
		 this.fact.getFact().setProbability(prob);
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

	public void setProb_fact(Double prob) {
		this.prob_fact.add(prob);
	}

	 

}
