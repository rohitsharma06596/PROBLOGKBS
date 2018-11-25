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

public class InferenceController {
	private ProgramModel program = new ProgramModel();
	int matchCount;
	HashMap<String, String> tempMap = new HashMap<>();
	HashMap<String, String> tempMap0 = new HashMap<>();
	// HashMap<String, String> tempMap1 = new HashMap<>();
	int factSize;
	int i, j, k = 0;
	boolean mat;

	int bodySize;
	List<Double> probArr;

	public InferenceController(ProgramModel program) {
		this.program = program;
		factSize = this.program.getFacts().size();
		bodySize = this.program.getRuleslist().get(0).getBody().size();
		probArr = new ArrayList<>(bodySize);
		match(this.program.getRuleslist().get(0).getBody().get(0), this.program.getFacts().get(0).getFact());
		this.finalise_Idb();
		for (int l = 0; l < this.program.getIdb().size(); l++) {
			System.out.println(
					"In the inference Controller " + this.program.getIdb().get(l).getFact().getFact().getProbability());
		}
		this.finalizeIteration();
		for (int l = 0; l < this.program.getFacts().size(); l++) {
			System.out.println("In the inference Controller " + program.getFacts().get(l).getFact().getProbability());
		}
	}

	public Double disjunction(Double p1, Double p2) {
		Double pro = (p1 + p2) - (p1 * p2);
		System.out.println("Inside disjunction disjuncted probability is: " + pro);
		// return (p1 + p2) - (p1 * p2);
		return pro;
	}

	public void finalise_Idb() {

		for (int j = 0; j < program.getIdb().size(); j++) {
			int i = 0;
			System.out.println("Inside finalise_IDb " + program.getIdb().get(j).getFact().getFact());

			System.out.println("Inside finalise_Idb" + program.getIdb().get(j).getProb_fact());
			while (program.getIdb().get(j).getProb_fact().size() > 1) {
				Double new_prob = disjunction(program.getIdb().get(j).getProb_fact().get(i),
						program.getIdb().get(j).getProb_fact().get(i + 1));
				program.getIdb().get(j).getProb_fact().remove(i);
				program.getIdb().get(j).getProb_fact().remove(i);
				program.getIdb().get(j).getProb_fact().add(new_prob);
			}
			program.getIdb().get(j).getFact().getFact().setProbability(program.getIdb().get(i).getProb_fact().get(0));
			System.out.println("Inside finlizeidb and after disjunction: "
					+ program.getIdb().get(j).getFact().getFact().getProbability());
		}

	}

	/*
	 * public void InferIDB(PredicateModel p, int[] prob) {
	 * 
	 * }
	 */
	public void finalizeIteration() {
		int count = 0;
		List<FactModel> finals = new ArrayList<>();
		for (int i = 0; i < program.getIdb().size(); i++) {
			for (int j = 0; j < program.getFacts().size(); j++)
			{
				if (program.getIdb().get(i).getFact().getFact().getPredName() == program.getFacts().get(j).getFact()
						.getPredName()) {
					{
						boolean match = false;
						for (int k = 0; k < program.getIdb().get(i).getFact().getFact().getArity(); k++) {
							if (program.getIdb().get(i).getFact().getFact().getArguments().get(k)
									.equals(program.getFacts().get(j).getFact().getArguments().get(k))) {
								match = true;
							} else {
								match = false;
								break;
							}
						}
						if(match == true)
						{
							if(program.getIdb().get(i).getFact().getFact().getProbability()==program.getFacts().get(j).getFact().getProbability())
							{
								program.getIdb().remove(i);
							}
							else
							{
								program.getFacts().remove(j);
								count++;
							}
							break;
						}
						else
						{
							continue;
						}
						/*if (k == program.getIdb().get(i).getFact().getFact().getArity()) {
							k--;
							if (match) {
								program.getIdb().remove(k);
							} else if (!match) {
								count++;
							}
							j++;
						}*/
					}
				}
				
			}
			
		}// Akhu idb list edb ma nakhvanu che;
		if (count > 0) {
			for (int i = 0; i < program.getIdb().size(); i++) {
				finals.add(program.getIdb().get(i).getFact());
			}
			program.setFacts(finals);
			/*
			 * for(int i=0;i<program.getIdb().size();i++) {
			 * System.out.println("The final IDB set contains: " +
			 * program.getIdb().get(i).getFact().getFact()); }
			 */
			// System.out.println("Call the next ieration here");
		} else if (count == 0) {
			// System.out.println("The Fix point is found");
			return;
		}
	}

	public boolean predicateMatching(PredicateModel parmPred, PredicateModel parmFact) {

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

	public void inferIDB(PredicateModel head, List<Double> prob, HashMap<String, String> hashMap) {

		FactModel tempFact = new FactModel();
		tempFact.setFact(head);
		Double[] probability = prob.toArray(new Double[prob.size()]);
		Arrays.sort(probability);
		double minProb = probability[0];
		double aggProb = minProb * head.getProbability();
		tempFact.getFact().setProbability(aggProb);
		IdbModel tempIdb;

		List<String> argum = new ArrayList<>();
		Set<String> keySet = hashMap.keySet();
		List<String> keys = new ArrayList<>(keySet);
		// argum = tempFact.getFact().getArguments();
		for (int a = 0; a < tempFact.getFact().getArguments().size(); a++) {
			argum.add(tempFact.getFact().getArguments().get(a));
		}
		for (int i = 0; i < argum.size(); i++) {
			if (argum.get(i).equals(keys.get(i))) {
				argum.set(i, hashMap.get(keys.get(i)));
				System.out.println(hashMap.get(keys.get(i)));
			}
		}
		tempFact.getFact().getArguments().clear();
		tempFact.getFact().setArguments(argum);

		int i = 0;
		boolean factMatch = false;
		boolean argMatch = false;
		if (program.getIdb().isEmpty()) {
			tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
			program.setIdb(tempIdb);
		} else {
			for (i = 0; i < program.getIdb().size(); i++) {
				if (tempFact.getFact().getPredName()
						.equals(program.getIdb().get(i).getFact().getFact().getPredName())) {
					factMatch = true;
					// System.out.println(factMatch);
				}
				if (factMatch) {
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
						// System.out.println("Inside infer Idb, the probs are:
						// "+program.getIdb().get(i).getProb_fact());
						System.out.println(
								"Inside infer Idb, the IDB is: " + program.getIdb().get(i).getFact().getFact());
					} else if (i == (program.getIdb().size() - 1) && !argMatch) {
						tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
						program.setIdb(tempIdb);
					}
				} else if (!factMatch && i == (program.getIdb().size() - 1)) {
					tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
					program.setIdb(tempIdb);
				} else {
					continue;
				}

			}
		}

		/*
		 * if (i < program.getIdb().size()) {
		 * program.getIdb().get(i).setProb_fact(aggProb); } else { tempIdb = new
		 * IdbModel(tempFact, tempFact.getFact().getProbability());
		 * program.setIdb(tempIdb); }
		 */
	}

	public void match(PredicateModel p, PredicateModel f) {
		/*
		 * int innerbodySize = program.getRuleslist().get(0).getBody().size(); int
		 * innerfactSize = program.getFacts().size();
		 */
		mat = this.predicateMatching(p, f);
		if (mat) {
			if (matchCount != bodySize) {
				j = j + 1;
				k = 0;
				match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());
			} else {
				if (matchCount == bodySize) {
					inferIDB(program.getRuleslist().get(i).getHead(), probArr, tempMap);

					while (k < factSize) {
						tempMap.putAll(tempMap0); // taklif che ahiya
						matchCount--;
						k++;
						match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());

					}
					i = i + 1;// taklif che
					if (k == factSize) {
						tempMap0 = tempMap;
						probArr.remove(probArr.size() - 1);
						j = j - 1;
						k = 0;
						if (j < 0) {
							i = i + 1;
							if (i == program.getRuleslist().size()) {
								return;
							}
							match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());
							bodySize = program.getRuleslist().get(i).getBody().size();
							j = 0;
						}
					}
				}
			}
		}
		if (!mat) {
			if (k < program.getFacts().size() - 1) {
				k = k + 1;
				match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());
			} else {
				System.out.println("This predicate cannot be true for the current edb");
				j = j - 1;
				k = 0;
				if (j < 0) {
					if (i == program.getRuleslist().size()) {
						return;
					} else {
						i = i + 1;
						j = 0;
						tempMap = new HashMap<String, String>();
						tempMap0 = new HashMap<String, String>();
						// tempMap1 = new HashMap<String, String>();
						// HashMap<String, String> tempMap0 = new HashMap<>();
						bodySize = program.getRuleslist().get(i).getBody().size();
						match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());

					}

				}
			}
		}

	}

}
