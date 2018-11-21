package kbs.problog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kbs.problog.model.FactModel;
import kbs.problog.model.IdbModel;
import kbs.problog.model.PredicateModel;
import kbs.problog.model.ProgramModel;

public class InferenceController {
	private ProgramModel program;
	int matchCount;
	HashMap<String, String> tempMap = new HashMap<>();
	HashMap<String, String> tempMap0 = new HashMap<>();
	int factSize;
	int i, j, k = 0;
	boolean mat;
	int bodySize;
	int[] probArr;

	public InferenceController(ProgramModel program) {
		this.program = program;
		factSize = program.getFacts().size();
		bodySize = program.getRuleslist().get(0).getBody().size();
		probArr = new int[bodySize];
		match(program.getRuleslist().get(0).getBody().get(0), program.getFacts().get(0).getFact());
	}

	public void InferIDB(PredicateModel p, int[] prob) {

	}

	public boolean predicateMatching(PredicateModel parmPred, PredicateModel parmFact) {

		if ((parmPred.getArity() != parmFact.getArity()) && (!parmPred.getPredName().equals(parmFact.getPredName())))
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
					System.out.println("Our hash map is empty");
					tempMap.put(parmPred.getArguments().get(i), parmFact.getArguments().get(i));

				}
			}
			matchCount++;
			return true;
		}
	}

	public void match(PredicateModel p, PredicateModel f) {

		mat = this.predicateMatching(p, f);
		if (mat && matchCount != bodySize) {
			j = j + 1;
			k = 0;
			match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());
		}
		if (!mat) {
			k = k + 1;
			match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());
		}
		if (mat && matchCount == bodySize) {
			InferIDB(program.getRuleslist().get(i).getHead(), probArr);
			while (k < factSize - 1) {
				tempMap = tempMap0;
				k++;
				match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());
			}
			if (k == factSize) {
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

		/*
		 * for(int i=0;i<factList.size();i++) {
		 * System.out.println(factList.get(i).getFact()); }
		 */
		/**
		 * for (int i = 0; i < program.getRuleslist().size(); i++) {
		 * 
		 * int bodySize = program.getRuleslist().get(i).getBody().size(); int
		 * probArray[] = new int[bodySize]; // System.out.println(); /* for(int j
		 * =0;j<ruleBody.size();j++) { System.out.print(ruleBody.get(j)); }
		 */
		/*
		 * ArrayList<List<PredicateModel>> outerList = new ArrayList(); boolean isEmpty
		 * = false; for (int j = 0; j < bodySize; j++) {
		 * 
		 * ArrayList<PredicateModel> innerList = new ArrayList<>(); for (int k = 0; k <
		 * factList.size(); k++) { while (!isEmpty) { if
		 * (ruleBody.get(j).getPredName().equals(factList.get(k).getFact().getPredName()
		 * )) { innerList.add(factList.get(k).getFact()); }
		 * 
		 * if (innerList.isEmpty()) { isEmpty = true; outerList.removeAll(innerList); }
		 * if (isEmpty == false) { outerList.add(innerList); } } }
		 * System.out.println(outerList);
		 * 
		 * }
		 */
		/**
		 * ArrayList<List<PredicateModel>> outerList = new ArrayList();
		 * 
		 * boolean isEmpty = false;
		 */
		/*
		 * for (int j = 0; j < bodySize; j++) {
		 * 
		 * ArrayList<PredicateModel> innerList = new ArrayList<>(); for (int k = 0; k <
		 * factList.size(); k++) { while (!isEmpty) { if
		 * (program.getRuleslist().get(i).getBody().get(j).getPredName().equals(factList
		 * .get(k).getFact().getPredName())) {
		 * 
		 * innerList.add(factList.get(k).getFact()); }
		 * 
		 * if (innerList.isEmpty()) { isEmpty = true; outerList.removeAll(innerList); }
		 * if (isEmpty == false) { outerList.add(innerList); } } }
		 * System.out.println(outerList);
		 * 
		 * }
		 */
		/**
		 * int j=0; matchCount = 0; boolean limit=false; while(j<bodySize) { for (int k
		 * = 0; k < factList.size(); k++) { if
		 * (program.getRuleslist().get(i).getBody().get(j).getPredName().equals(factList.get(k).getFact().getPredName()))
		 * { boolean match; tempMap0 = tempMap; match =
		 * predicateMatching(program.getRuleslist().get(i).getBody().get(j),factList.get(k));
		 * if(matchCount==bodySize) {
		 * inferIDB(program.getRuleslist().get(i).getHead(),probArray); tempMap =
		 * tempMap0; matchCount--; } if(match) { j++; } } } }
		 * 
		 * 
		 * }
		 */

		int i, j, k = 0;
		boolean mat;

	}

	/**
	 * Finalise idb.
	 *
	 * @param parmidb the parmidb
	 */
	public void finalise_Idb(IdbModel parmidb) {

		int i = 0;

		while (parmidb.getProb_fact().size() > 1) {
			Double new_prob = ((parmidb.getProb_fact().get(i) + parmidb.getProb_fact().get(i + 1))
					- (parmidb.getProb_fact().get(i) * parmidb.getProb_fact().get(i + 1)));
			parmidb.getProb_fact().remove(i);
			parmidb.getProb_fact().remove(i);
			parmidb.getProb_fact().add(new_prob);
		}
	}

	public void inferIDB(PredicateModel head, List<Double> prob) {
		FactModel tempFact = new FactModel();
		tempFact.setFact(head);
		IdbModel tempIdb;
		List<String> argundv = new ArrayList<>();
		for (int i = 0; i < tempFact.getFact().getArity(); i++) {
			argundv.add(tempMap.get(tempFact.getFact().getArguments().get(i)));
		}
		try {
			if (argundv.size() == tempFact.getFact().getArguments().size()) {
				tempFact.getFact().setArguments(argundv);
			} else {
				System.out.println("The predicate does not have appropriate value in the hash map, it cannot be true");
			}
		} catch (NullPointerException e) {
			System.out.println("The predicate does not have appropriate value in the hash map, it cannot be true");
		}
		tempIdb= new IdbModel(tempFact,prob);
		program.setIdb(tempIdb);
	}
}
