package kbs.problog.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import kbs.problog.model.FactModel;
import kbs.problog.model.IdbModel;
import kbs.problog.model.PredicateModel;
import kbs.problog.model.ProgramModel;
import kbs.problog.model.RulesModel;

/**
 * The Class InferenceController.
 */
public class InferenceController {

	/** The program. */
	private ProgramModel program = new ProgramModel();

	/** The match count. */
	int matchCount;
	int count;
	List<FactModel> edbTag;
	List<FactModel> prevEdb;
	List<RulesModel> ruleFire;
	/** The temp map. */
	HashMap<String, String> tempMap = new HashMap<>();

	/** The temp map 0. */
	HashMap<String, String> tempMap0 = new HashMap<>();

	/** The fact select. */
	List<Integer> factSelect = new ArrayList<Integer>();

	/** The parm map. */

	/** The fact size. */
	// HashMap<String, String> tempMap1 = new HashMap<>();
	int factSize;

	/** The fact push. */
	List<String> factPush = new ArrayList<String>();

	/** The k. */
	int i, j, k = 0;

	/** The mat. */
	boolean mat;

	/** The body size. */
	int bodySize;

	/** The prob arr. */
	List<Double> probArr;
	int iter = 1;

	/**
	 * Instantiates a new inference controller.
	 *
	 * @param program the program
	 */
	public InferenceController(ProgramModel program) {
		this.program = program;
		edbTag = new ArrayList<FactModel>();
		prevEdb = new ArrayList<FactModel>();
		ruleFire = new ArrayList<RulesModel>();
		for (int i = 0; i < program.getRuleslist().size(); i++) {
			RulesModel rule = new RulesModel();
			rule.setHead(program.getRuleslist().get(i).getHead());
			for (int j = 0; j < program.getRuleslist().get(i).getBody().size(); j++) {
				PredicateModel body = new PredicateModel();
				body.setPredName(program.getRuleslist().get(i).getBody().get(j).getPredName());
				body.setArity(program.getRuleslist().get(i).getBody().get(j).getArity());
				// body.setProbability(program.getRuleslist().get(i).getBody().get(j).getProbability());
				body.setArguments(program.getRuleslist().get(i).getBody().get(j).getArguments());
				rule.setBody(body);
				System.out.println("just chck");
			}
			ruleFire.add(rule);
		}
		for (int i = 0; i < program.getFacts().size(); i++) {
			FactModel fact = new FactModel();
			fact.setFact(program.getFacts().get(i).getFact());
			edbTag.add(fact);
		}
		for (int i = 0; i < program.getFacts().size(); i++) {
			prevEdb.add(program.getFacts().get(i));
		}

		List<FactModel> prevIDB = new ArrayList<FactModel>();

		do {
			System.gc();
			this.factSize = this.prevEdb.size();
			this.bodySize = this.ruleFire.get(0).getBody().size();
			probArr = new ArrayList<>();
			i = 0;
			j = 0;
			k = 0;

			System.out.println("iteration number is" + iter);
			iter++;
			matchCount = 0;
			match(this.ruleFire.get(0).getBody().get(0), this.prevEdb.get(0).getFact());
			this.finalise_Idb();

			count = this.finalizeIteration();
			for (int l = 0; l < program.getFacts().size(); l++) {
				System.out.println(program.getFacts().get(l).getFact());
			
			}

		} while (count > 0);

		/*
		 * for (int l = 0; l < this.program.getFacts().size(); l++) {
		 * System.out.println(program.getFacts().get(l).getFact()); }
		 */

		System.out.println(program.getFacts().size());
		

	}

	/**
	 * Disjunction.
	 *
	 * @param p1 the p 1
	 * @param p2 the p 2
	 * @return the double
	 */
	public double disjunctionInd(double p1, double p2) {
		double pro = (p1 + p2) - (p1 * p2);
		// System.out.println("Inside disjunction disjuncted probability is: " + pro);

		return pro;
	}

	public double disjunctionMax(double p1, double p2) {
		double max;
		if (p1 > p2) {
			max = p1;
		} else {
			max = p2;
		}
		return max;

	}

	public boolean certChange(Double p1, Double p2) {

		if (p1 - p2 > 0.01) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Finalise idb.
	 */
	public void finalise_Idb() {

		for (int j = 0; j < program.getIdb().size(); j++) {
			int i = 0;
			while (program.getIdb().get(j).getProb_fact().size() > 1) {
				double new_prob = disjunctionMax(program.getIdb().get(j).getProb_fact().get(i),
						program.getIdb().get(j).getProb_fact().get(i + 1));
				program.getIdb().get(j).getProb_fact().remove(i);
				program.getIdb().get(j).getProb_fact().remove(i);
				program.getIdb().get(j).getProb_fact().add(new_prob);
			}

			program.getIdb().get(j).getFact().getFact().setProbability(program.getIdb().get(j).getProb_fact().get(0));
			program.getIdb().get(j).getProb_fact().clear();
		}

	}

	/**
	 * Finalize iteration.
	 */
	public int finalizeIteration() {
		count = 0;
		List<FactModel> finals = new ArrayList<>();
		for (int i = 0; i < program.getIdb().size(); i++) {
			for (int j = 0; j < program.getFacts().size(); j++) {
				if (program.getIdb().get(i).getFact().getFact().getPredName()
						.equals(this.program.getFacts().get(j).getFact().getPredName())) {
					{
						boolean match = false;
						for (int k = 0; k < program.getIdb().get(i).getFact().getFact().getArity(); k++) { // check
																											// arity as
																											// well
							if (program.getIdb().get(i).getFact().getFact().getArguments().get(k)
									.equals(program.getFacts().get(j).getFact().getArguments().get(k))) {

								match = true;
							} else {
								match = false;
								break;
							}
						}
						if (match == true) {
							double prob1 = program.getIdb().get(i).getFact().getFact().getProbability();
							double prob2 = program.getFacts().get(j).getFact().getProbability();
							if (prob1 <= prob2) {
								program.getIdb().remove(i);
								i = i - 1;
							} else {
								this.program.getFacts();
								j = j - 1;// If exist in EBD with different probability
								count++;
								continue;
							}
							break;
						} else {
							continue;
						}

					}
				}

			}

		}
		count = program.getIdb().size();
		if (count > 0) {
			for (int i = 0; i < program.getIdb().size(); i++) {
				finals.add(program.getIdb().get(i).getFact());
			}
			this.prevEdb.clear();
			program.setFacts(finals);
			for(int i=0;i<edbTag.size();i++)
			{
				FactModel fact = new FactModel();
				fact.setFact(edbTag.get(i).getFact());
				prevEdb.add(fact);
			}
			for(int i=0;i<finals.size();i++)
			{
				FactModel fact = new FactModel();
				fact.setFact(finals.get(i).getFact());
				prevEdb.add(fact);
			}
			
			program.getIdb().clear();
			//finals.clear();
			ruleFire.clear();
		//	ruleFire = new ArrayList<RulesModel>();
			for(int i=0;i<program.getRuleslist().size();i++)
			{
				for(int j=0;j<program.getRuleslist().get(i).getBody().size();j++)
				{
					for(int k =0;k<finals.size();k++)
					{
						RulesModel rule = new RulesModel();
						rule.setHead(program.getRuleslist().get(i).getHead());
						if(program.getRuleslist().get(i).getBody().get(j).getPredName().equals(finals.get(k).getFact().getPredName()))
						{
							for(int a=0;a<program.getRuleslist().get(i).getBody().size();a++)
							{
								PredicateModel body = new PredicateModel();
								body.setPredName(program.getRuleslist().get(i).getBody().get(a).getPredName());
								body.setArity(program.getRuleslist().get(i).getBody().get(a).getArity());
								// body.setProbability(program.getRuleslist().get(i).getBody().get(j).getProbability());
								body.setArguments(program.getRuleslist().get(i).getBody().get(a).getArguments());
								rule.setBody(body);
								
							}
							ruleFire.add(rule);
							break;
						}
						else
						{
							continue;
						}
					}
				}
			}
			finals.clear();

		} else if (count == 0) {
			System.out.println("The Fix point is found");

		}
		return count;
	}

	/**
	 * Predicate matching.
	 *
	 * @param parmPred the parm pred
	 * @param parmFact the parm fact
	 * @param tempMap  the current hash
	 * @return true, if successful
	 */
	public boolean predicateMatching(PredicateModel parmPred, PredicateModel parmFact) {
		tempMap0.clear();
		tempMap0.putAll(tempMap);
		if ((parmPred.getArity() != parmFact.getArity()) || (!(parmPred.getPredName().equals(parmFact.getPredName()))))
			return false;
		else {
			for (int i = 0; i < parmPred.getArity(); i++) {
				try {
					if ((tempMap.get(parmPred.getArguments().get(i))).equals(null)) {
						tempMap.put(parmPred.getArguments().get(i), parmFact.getArguments().get(i));
					} else {
						if (tempMap.get(parmPred.getArguments().get(i)).equals(parmFact.getArguments().get(i))) {
							continue;
						} else {
							tempMap.clear();
							tempMap.putAll(tempMap0);
							return false;
						}
					}
				} catch (NullPointerException e) {
					// System.out.println("Our hash map is empty");
					tempMap.put(parmPred.getArguments().get(i), parmFact.getArguments().get(i));

				}
			}
			matchCount++;
			probArr.add(parmFact.getProbability());
			// tempMap1 = tempMap;
			return true;
		}
	}

	/**
	 * Infer IDB.
	 *
	 * @param head    the head
	 * @param prob    the prob
	 * @param hashMap the hash map
	 */
	public void inferIDB(PredicateModel head, List<Double> prob) {

		FactModel tempFact = new FactModel();
		tempFact.setFact(head);
		Double[] probability = prob.toArray(new Double[prob.size()]);
		Arrays.sort(probability);
		double minProb = probability[0];
		double aggProb = minProb * head.getProbability();
		tempFact.getFact().setProbability(aggProb);
		IdbModel tempIdb1 = new IdbModel();

		List<String> argum = new ArrayList<>();
		Set<String> keySet = tempMap.keySet();
		List<String> keys = new ArrayList<>(keySet);
		// argum = tempFact.getFact().getArguments();
		for (int a = 0; a < tempFact.getFact().getArguments().size(); a++) {
			argum.add(tempFact.getFact().getArguments().get(a));
		}
		for (int i = 0; i < argum.size(); i++) {
			for (int j = 0; j < keys.size(); j++) {
				if (argum.get(i).equals(keys.get(j))) {
					argum.set(i, tempMap.get(keys.get(j)));
				}
			}
		}
		tempFact.getFact().getArguments().clear();
		tempFact.getFact().setArguments(argum);

		int i = 0;
		boolean factMatch = false;
		boolean argMatch = false;
		if (program.getIdb().isEmpty()) {
			IdbModel tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
			program.setIdb(tempIdb);
		} else {
			int size = program.getIdb().size();
			for (i = 0; i < size; i++) {
				factMatch = false;
				if (tempFact.getFact().getPredName()
						.equals(program.getIdb().get(i).getFact().getFact().getPredName())) {
					factMatch = true;
					// System.out.println(factMatch);
				}
				if (factMatch) {
					argMatch = false;
					for (int j = 0; j < tempFact.getFact().getArity(); j++) {
						if (program.getIdb().get(i).getFact().getFact().getArguments().get(j)
								.equals(tempFact.getFact().getArguments().get(j))) {
							argMatch = true;
							// System.out.println("bsdhfb");
						} else {
							argMatch = false;
							break;
						}
					}
					if (argMatch) {
						program.getIdb().get(i).setProb_fact(tempFact.getFact().getProbability());
						return;
					} else if ((i == size - 1) && !argMatch) {
						IdbModel tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
						program.setIdb(tempIdb);
					}
				} else if (!factMatch && i == (size - 1)) {
					IdbModel tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
					program.setIdb(tempIdb);
				} else {
					continue;
				}

			}
		}

	}

	/**
	 * Match.
	 *
	 * @param p            the p
	 * @param f            the f
	 * @param tempMap      the current hash
	 * @param previousHash the previous hash
	 */
	public void match(PredicateModel p, PredicateModel f) {
		mat = this.predicateMatching(p, f);
		if (mat) {
			factSelect.add(k);
			if (matchCount == bodySize) {
				inferIDB(this.ruleFire.get(i).getHead(), probArr);
				if (k + 1 < factSize) {
					k++;
					probArr.remove(probArr.size() - 1);
					matchCount--;
					tempMap.clear();
					tempMap.putAll(tempMap0);
					factSelect.remove(factSelect.size() - 1);
					// tempMap0.clear();
					match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
				} else {
					if (j > 0) {
						j = j - 1;
						tempMap.clear();
						// tempMap.putAll(tempMap0); //sp
						tempMap0.clear();
						probArr.remove(probArr.size() - 1);
						// System.out.println(factSelect);
						factSelect.remove(factSelect.size() - 1);
						// System.out.println(factSelect);
						k = factSelect.get(factSelect.size() - 1);
						matchCount--;
						if (k + 1 == factSize) {
							j = 0;
							k = 0;

							if (i + 1 == this.ruleFire.size()) {
								tempMap.clear();
								tempMap0.clear();
								matchCount = 0;
								factSelect.clear();
								probArr.clear();
								return;
							} else {
								i = i + 1;
								tempMap.clear();
								tempMap0.clear();
								probArr.clear();
								matchCount = 0;
								factSelect.clear();
								bodySize = this.ruleFire.get(i).getBody().size();
								match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
							}
						} else {
							if (k + 1 < factSize) {
								k++;
								matchCount--;
								probArr.remove(probArr.size() - 1);
								factSelect.remove(factSelect.size() - 1);
								tempMap.clear();
								// sp
								/*
								 * if(j==0) { tempMap0.clear(); } else { tempMap.putAll(tempMap0); }
								 */
								// sp
								tempMap.putAll(tempMap0);
								match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
							}
						}
					} else {
						k = 0;
						if (i + 1 == this.ruleFire.size()) {
							matchCount = 0;
							tempMap.clear();
							tempMap0.clear();
							factSelect.clear();
							probArr.clear();
							return;
						} else {
							i = i + 1;
							j = 0;
							tempMap.clear();
							tempMap0.clear();
							probArr.clear();
							matchCount = 0;
							factSelect.clear();
							bodySize = this.ruleFire.get(i).getBody().size();
							match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
						}
					}
				}
			} else {
				j = j + 1;
				k = 0;
				match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
			}
		} else {
			if (k == factSize - 1) {
				if (j > 0) {
					j = j - 1;
					tempMap.clear();
					// tempMap.putAll(tempMap0);
					tempMap0.clear(); // sp

					k = factSelect.get(factSelect.size() - 1);
					// System.out.println(factSelect+" "+k);

					matchCount--; // can make it zero
					if (k + 1 == factSize) {
						j = 0;
						k = 0;
						if (i + 1 == this.ruleFire.size()) {
							matchCount = 0;
							tempMap.clear();
							tempMap0.clear();
							factSelect.clear();
							probArr.clear();
							return;
						} else {
							i = i + 1;
							tempMap.clear();
							tempMap0.clear();
							probArr.clear();
							matchCount = 0;
							factSelect.clear();
							bodySize = this.ruleFire.get(i).getBody().size();
							match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
						}
					} else {
						k++;
						tempMap.clear();

						match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
					}
				} else {
					k = 0;
					if (i + 1 == this.ruleFire.size()) {
						matchCount = 0;
						tempMap.clear();
						tempMap0.clear();
						factSelect.clear();
						probArr.clear();
						return;
					} else {
						i = i + 1;
						tempMap.clear();
						tempMap0.clear();
						bodySize = this.ruleFire.get(i).getBody().size();
						probArr.clear();
						matchCount = 0;
						factSelect.clear();
						match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());
					}
				}
			} else {
				k = k + 1;
				match(this.ruleFire.get(i).getBody().get(j), this.prevEdb.get(k).getFact());

			}
		}

	}
}
