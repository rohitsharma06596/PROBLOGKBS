package kbs.problog.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import kbs.problog.model.FactModel;
import kbs.problog.model.IdbModel;
import kbs.problog.model.PredicateModel;
import kbs.problog.model.ProgramModel;

public class InferenceController {
	private ProgramModel program = new ProgramModel();
	int matchCount;
	LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();
	LinkedHashMap<String, String> tempMap0 = new LinkedHashMap<>();
	List <Integer> factSelect = new ArrayList<Integer>();
	HashMap<String, String> parmMap = new HashMap<>();
	HashMap<String, String> parmMap0 = new HashMap<>();
	// HashMap<String, String> tempMap1 = new HashMap<>();
	int factSize;
	int i, j, k = 0;
	boolean mat;
	int bodySize;
	List<Double> probArr;

	public InferenceController(ProgramModel program) {
		this.program = program;
		factSize = program.getFacts().size();
		bodySize = program.getRuleslist().get(0).getBody().size();
		probArr = new ArrayList<>(bodySize);
		match(program.getRuleslist().get(0).getBody().get(0), program.getFacts().get(0).getFact(), parmMap, parmMap0);
		this.finalise_Idb();
		/*
		 * for(int l=0;l<program.getIdb().size();l++) {
		 * System.out.println("In the inference Controller "+program.getIdb().get(l).
		 * getFact().getFact().getProbability()); }
		 */
		this.finalizeIteration();
		/*
		 * for(int l=0;l<program.getFacts().size();l++) {
		 * System.out.println("In the inference Controller "+program.getFacts().get(l).
		 * getFact().getProbability()); }
		 */
	}

	public Double disjunction(Double p1, Double p2) {
		Double pro = (p1 + p2) - (p1 * p2);
		// System.out.println("Inside disjunction disjuncted probability is: "+ pro );
		// return (p1 + p2) - (p1 * p2);
		return pro;
	}

	public void finalise_Idb() {

		for (int j = 0; j < program.getIdb().size(); j++) {
			int i = 0;
			// System.out.println("Inside finalise_IDb
			// "+program.getIdb().get(j).getFact().getFact());

			// System.out.println("Inside
			// finalise_Idb"+program.getIdb().get(j).getProb_fact());
			while (program.getIdb().get(j).getProb_fact().size() > 1) {
				Double new_prob = disjunction(program.getIdb().get(j).getProb_fact().get(i),
						program.getIdb().get(j).getProb_fact().get(i + 1));
				program.getIdb().get(j).getProb_fact().remove(i);
				program.getIdb().get(j).getProb_fact().remove(i);
				program.getIdb().get(j).getProb_fact().add(new_prob);
			}
			// System.out.println("Inside finlizeidb and after disjunction: "+
			// program.getIdb().get(j).getFact().getFact().getProbability());
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

			// while(program.getIdb().get(i).getFact().getFact().getPredName()==program.getFacts().get(j).getFact().getPredName())
			{
				boolean match = false;
				for (int k = 0; k < program.getIdb().get(i).getFact().getFact().getArity(); k++) {
					if (program.getIdb().get(i).getFact().getFact().getArguments().get(k) == program.getFacts().get(j)
							.getFact().getArguments().get(k)) {
						match = true;
					} else {
						match = false;
						break;
					}
				}
				if (match) {
					program.getIdb().remove(k);
				} else if (!match) {
					count++;
				}
				j++;
			}

		}
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

	public boolean predicateMatching(PredicateModel parmPred, PredicateModel parmFact,
			HashMap<String, String> currentHash) {
		tempMap0.clear();
		tempMap0.putAll(currentHash);
		if ((parmPred.getArity() != parmFact.getArity()) || (!(parmPred.getPredName().equals(parmFact.getPredName()))))
			return false;
		else {
			for (int i = 0; i < parmPred.getArity(); i++) {
				try {
					if ((currentHash.get(parmPred.getArguments().get(i))).equals(null)) {
						currentHash.put(parmPred.getArguments().get(i), parmFact.getArguments().get(i));
					} else {
						if (currentHash.get(parmPred.getArguments().get(i)).equals(parmFact.getArguments().get(i))) {
							continue;
						} else {
							return false;
						}
					}
				} catch (NullPointerException e) {
					// System.out.println("Our hash map is empty");
					currentHash.put(parmPred.getArguments().get(i), parmFact.getArguments().get(i));

				}
			}
			matchCount++;
			probArr.add(parmFact.getProbability());
			tempMap.clear();
			tempMap.putAll(currentHash);
			// tempMap1 = tempMap;
			return true;
		}
	}

	public void inferIDB(PredicateModel head, List<Double> prob) {

		FactModel tempFact = new FactModel();
		tempFact.setFact(head);
		Double[] probability = prob.toArray(new Double[prob.size()]);
		Arrays.sort(probability);
		Double minProb = probability[0];
		Double aggProb = minProb * head.getProbability();
		tempFact.getFact().setProbability(aggProb);
		IdbModel tempIdb;

		List<String> argundv = new ArrayList<>();
		Set<String> keySet = tempMap.keySet();
		List<String> keys = new ArrayList<>(keySet);
		argundv = tempFact.getFact().getArguments();
		for (int i = 0; i < argundv.size(); i++) {
			if (argundv.get(i) == keys.get(i)) {
				argundv.set(i, tempMap.get(keys.get(i)));
			}
		}
		tempFact.getFact().setArguments(argundv);

		int i = 0;
		boolean factMatch = false;
		boolean argMatch = false;
		if (program.getIdb().isEmpty()) {
			tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
			program.setIdb(tempIdb);
			
		}
		for (i = 0; i < program.getIdb().size(); i++) {
			if (tempFact.getFact().getPredName() == program.getIdb().get(i).getFact().getFact().getPredName()) {
				factMatch = true;
			}
			if (factMatch) {
				for (int j = 0; j < tempFact.getFact().getArity(); j++) {
					if (program.getIdb().get(i).getFact().getFact().getArguments().get(j) == tempFact.getFact()
							.getArguments().get(j)) {
						argMatch = true;
					} else {
						argMatch = false;
						break;
					}
				}
				if (argMatch) {
					program.getIdb().get(i).setProb_fact(tempFact.getFact().getProbability());
					// System.out.println("Inside infer Idb, the probs are:
					// "+program.getIdb().get(i).getProb_fact());
					// System.out.println("Inside infer Idb, the IDB is:
					// "+program.getIdb().get(i).getFact().getFact());
				} else {
					continue;
				}
			} else if (!factMatch && i == (program.getIdb().size())) {
				tempIdb = new IdbModel(tempFact, tempFact.getFact().getProbability());
				program.setIdb(tempIdb);
			}

		}

		/*
		 * if (i < program.getIdb().size()) {
		 * program.getIdb().get(i).setProb_fact(aggProb); } else { tempIdb = new
		 * IdbModel(tempFact, tempFact.getFact().getProbability());
		 * program.setIdb(tempIdb); }
		 */
	}

	public void match(PredicateModel p, PredicateModel f, HashMap<String, String> currentHash,
			HashMap<String, String> previousHash) {
		/*
		 * int innerbodySize = program.getRuleslist().get(0).getBody().size(); int
		 * innerfactSize = program.getFacts().size();
		 */
		int temparity = 0;
		mat = this.predicateMatching(p, f, currentHash);
		currentHash.clear();
		currentHash.putAll(tempMap);
		previousHash.clear();
		previousHash.putAll(tempMap0);
		if (mat) {
			if (matchCount != bodySize) {
				j = j + 1;
				factSelect.add(k);
				k=0;
				match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(), currentHash,
						previousHash);
			} else {
				if (matchCount == bodySize) {
					// inferIDB(program.getRuleslist().get(i).getHead(), probArr);
					factSelect.add(k);
					while (k < factSize - 1) {
						tempMap.clear();
						tempMap.putAll(tempMap0);
						currentHash.clear();
						currentHash.putAll(tempMap);
						matchCount--;
						k++;
						match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(),
								currentHash, previousHash);

					}
					// i = i + 1;
					if (k == factSize - 1) {
						tempMap.clear();
						tempMap.putAll(previousHash);
						currentHash.clear();
						currentHash.putAll(tempMap);
						probArr.remove(probArr.size() - 1);
						j = j - 1;
						k = 0;
						if (j < 0) {
							j = 0;
							i = i + 1;
							factSelect.clear();
							tempMap = new LinkedHashMap<String, String>();
							tempMap0 = new LinkedHashMap<String, String>();
							currentHash.clear();
							currentHash.putAll(tempMap0);
							previousHash.clear();
							previousHash.putAll(tempMap0);
							if (i == program.getRuleslist().size()) {
								currentHash.clear();
								previousHash.clear();
								return;
							}
							match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(),
									currentHash, previousHash);
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
				currentHash.clear();
				currentHash.putAll(tempMap0);
				match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(),
						currentHash, previousHash);
			} else {
				System.out.println("This predicate cannot be true for the current edb");

				j = j - 1;
				if (j < 0) {
					if (i == program.getRuleslist().size() - 1) {
						currentHash.clear();
						previousHash.clear();
						return;
					} else {
						k = 0;
						i = i + 1;
						factSelect.clear();
						j = 0;
						tempMap = new LinkedHashMap<String, String>();
						tempMap0 = new LinkedHashMap<String, String>();
						currentHash.clear();
						currentHash.putAll(tempMap0);
						previousHash.clear();
						previousHash.putAll(tempMap0);
						// tempMap1 = new HashMap<String, String>();
						// HashMap<String, String> tempMap0 = new HashMap<>();
						bodySize = program.getRuleslist().get(i).getBody().size();
						match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(),
								currentHash, previousHash);

					}

				} else {
					tempMap.clear();
					tempMap.putAll(previousHash);
					temparity = program.getRuleslist().get(i).getBody().get(j).getArity();
					for (int iteration = 0; iteration < temparity; iteration++) {
						tempMap.remove(program.getRuleslist().get(i).getBody().get(j).getArguments().get(iteration));
					}
					currentHash.clear();
					currentHash.putAll(tempMap);
					if (j-1 < 0)
					{
						tempMap0.clear();
						tempMap0.putAll(tempMap);
					}
					else
					{
						tempMap0.clear();
						tempMap0.putAll(tempMap);
						temparity = program.getRuleslist().get(i).getBody().get(j-1).getArity();
						for (int iteration = 0; iteration < temparity; iteration++) {
							tempMap0.remove(program.getRuleslist().get(i).getBody().get(j-1).getArguments().get(iteration));
						}
						
					}
					previousHash.clear();
					previousHash.putAll(tempMap0);
					k=factSelect.get(factSelect.size()-1)+1;
					if(k == program.getFacts().size())
					{
						j = j-1;
						if(j<0)
						{
							j=0;
						}
						return;
					}
					factSelect.remove(factSelect.size()-1);
					 match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(), currentHash, previousHash);

				}
			}
		}

	}

}
