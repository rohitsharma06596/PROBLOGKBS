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

/**
 * The Class InferenceController.
 */
public class InferenceController {

	/** The program. */
	private ProgramModel program = new ProgramModel();

	/** The match count. */
	int matchCount;
	int count;

	/** The temp map. */
	LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();

	/** The temp map 0. */
	LinkedHashMap<String, String> tempMap0 = new LinkedHashMap<>();

	/** The fact select. */
	List<Integer> factSelect = new ArrayList<Integer>();

	/** The parm map. */
	HashMap<String, String> parmMap = new HashMap<>();

	/** The parm map 0. */
	HashMap<String, String> parmMap0 = new HashMap<>();

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

	/**
	 * Instantiates a new inference controller.
	 *
	 * @param program the program
	 */
	public InferenceController(ProgramModel program) {
		this.program = program;

		while (count >= 0) {
			this.factSize = this.program.getFacts().size();
			this.bodySize = this.program.getRuleslist().get(0).getBody().size();
			probArr = new ArrayList<>();
			i = 0;
			j = 0;
			k = 0;
			parmMap.clear();
			parmMap0.clear();
			match(program.getRuleslist().get(0).getBody().get(0), program.getFacts().get(0).getFact(), parmMap,
					parmMap0);
			this.finalise_Idb();
			/*
			 * for (int l = 0; l < this.program.getIdb().size(); l++) { System.out.println(
			 * "In the inference Controller " +
			 * this.program.getIdb().get(l).getFact().getFact().getProbability()); }
			 */
			this.finalizeIteration();
		}
		/*
		 * for (int l = 0; l < this.program.getFacts().size(); l++) { }
		 * System.out.println("In the inference Controller " +
		 * program.getFacts().get(l).getFact().getProbability()); }
		 */
	}

	/**
	 * Disjunction.
	 *
	 * @param p1 the p 1
	 * @param p2 the p 2
	 * @return the double
	 */
	public Double disjunction(Double p1, Double p2) {
		Double pro = (p1 + p2) - (p1 * p2);
		System.out.println("Inside disjunction disjuncted probability is: " + pro);
		// return (p1 + p2) - (p1 * p2);
		return pro;
	}

	/**
	 * Finalise idb.
	 */
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

	/**
	 * Finalize iteration.
	 */
	public void finalizeIteration() {
		count = 0;
		List<FactModel> finals = new ArrayList<>();
		for (int i = 0; i < program.getIdb().size(); i++) {
			for (int j = 0; j < program.getFacts().size(); j++) {
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
						if (match == true) {
							if (program.getIdb().get(i).getFact().getFact().getProbability() == program.getFacts()
									.get(j).getFact().getProbability()) {
								program.getIdb().remove(i);
							} else {
								program.getFacts().remove(j);
								// count++;
							}
							break;
						} else {
							continue;
						}
						/*
						 * if (k == program.getIdb().get(i).getFact().getFact().getArity()) { k--; if
						 * (match) { program.getIdb().remove(k); } else if (!match) { count++; } j++; }
						 */
					}
				}

			}

		} // Akhu idb list edb ma nakhvanu che;
		count = program.getIdb().size();
		if (count > 0) {
			for (int i = 0; i < program.getIdb().size(); i++) {
				finals.add(program.getIdb().get(i).getFact());
			}
			program.setFacts(finals);
			program.getIdb().clear();
			/*
			 * for(int i=0;i<program.getIdb().size();i++) {
			 * System.out.println("The final IDB set contains: " +
			 * program.getIdb().get(i).getFact().getFact()); }
			 */

		} else if (count == 0) {
			System.out.println("The Fix point is found");
			return;
		}
	}

	/**
	 * Predicate matching.
	 *
	 * @param parmPred    the parm pred
	 * @param parmFact    the parm fact
	 * @param currentHash the current hash
	 * @return true, if successful
	 */
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

	/**
	 * Infer IDB.
	 *
	 * @param head    the head
	 * @param prob    the prob
	 * @param hashMap the hash map
	 */
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

	/**
	 * Match.
	 *
	 * @param p            the p
	 * @param f            the f
	 * @param currentHash  the current hash
	 * @param previousHash the previous hash
	 */
	public void match(PredicateModel p, PredicateModel f, HashMap<String, String> currentHash,
			HashMap<String, String> previousHash) {

		int temparity = 0;
		mat = this.predicateMatching(p, f, currentHash);
		currentHash.clear();
		currentHash.putAll(tempMap);
		previousHash.clear();
		previousHash.putAll(tempMap0);
		if (mat) {
			if (matchCount != bodySize) {
				factSelect.add(k);
				for (int interation = 0; interation < program.getFacts().get(k).getFact().getArity(); interation++) {
					factPush.add(program.getRuleslist().get(i).getBody().get(j).getArguments().get(interation));

				}
				j = j + 1;
				k = 0;
				match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(), currentHash,
						previousHash);
			} else {
				if (matchCount == bodySize) {
					inferIDB(program.getRuleslist().get(i).getHead(), probArr, currentHash);
					factSelect.add(k);
					for (int interation = 0; interation < program.getFacts().get(k).getFact()
							.getArity(); interation++) {
						factPush.add(program.getRuleslist().get(i).getBody().get(j).getArguments().get(interation));

					}
					while (k < factSize - 1) {
						tempMap.clear();
						tempMap.putAll(tempMap0);
						currentHash.clear();
						currentHash.putAll(tempMap);
						matchCount--;
						k++;
						// factSelect.clear();
						// factPush.clear();
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

						if (j < 0) {
							j = 0;
							k = 0;
							i = i + 1;
							factSelect.clear();
							factPush.clear();
							tempMap = new LinkedHashMap<String, String>();
							tempMap0 = new LinkedHashMap<String, String>();
							currentHash.clear();
							currentHash.putAll(tempMap0);
							previousHash.clear();
							previousHash.putAll(tempMap0);
							if (i == program.getRuleslist().size()) {
								currentHash.clear();
								previousHash.clear();
								tempMap.clear();
								tempMap0.clear();
								currentHash.clear();
								factPush.clear();
								factSelect.clear();
								matchCount = 0;

								return;
							}
							match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(),
									currentHash, previousHash);
							/*
							 * bodySize = program.getRuleslist().get(i).getBody().size(); j = 0;
							 */

						}
					} else {
						factSelect.remove(factSelect.size() - 1);
						k = factSelect.get(factSelect.size() - 1) + 1;
						temparity = program.getRuleslist().get(i).getBody().get(j + 1).getArity();
						int iteration3 = temparity;
						while (iteration3 > 0) {
							factPush.remove(factPush.size() - 1);
							iteration3--;

						}
						temparity = program.getRuleslist().get(i).getBody().get(j).getArity();
						iteration3 = temparity;
						while (iteration3 > 0) {
							factPush.remove(factPush.size() - 1);
							iteration3--;
						}
						match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(),
								currentHash, previousHash);	
					}
					
				}
			}
		}
		// else if (!mat)
		else {
			k = k + 1;
			if (k < program.getFacts().size()) {
				// k = k + 1;
				currentHash.clear();
				currentHash.putAll(tempMap0);
				match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(), currentHash,
						previousHash);

			} else {
				// System.out.println("This predicate cannot be true for the current edb");

				j = j - 1;
				if (j < 0) {
					if (i == program.getRuleslist().size() - 1) {
						currentHash.clear();
						previousHash.clear();
						return;
					} else {
						k = 0;
						i = i + 1;
						this.bodySize = program.getRuleslist().get(i).getBody().size();
						factSelect.clear();
						factPush.clear();
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
					for (int iteration = temparity - 1; iteration >= 0; iteration--) {
						for (int iteration2 = temparity - 1; iteration2 >= 0; iteration2--) {
							try {
								if (program.getRuleslist().get(i).getBody().get(j).getArguments().get(iteration)
										.equals(factPush.get(iteration2))) {
									// ERROR Factpush has nothing and it is still trying to equate

									if (factPush.indexOf(factPush.get(iteration2)) == factPush
											.lastIndexOf(factPush.get(iteration2)))
										tempMap.remove(factPush.get(iteration2));
									else
										System.out.println("This comes from previous predicate cannot be removed");
								} else {
									continue;
								}
							} catch (Exception E) {
								continue;
							}

						}
					}
					int iteration3 = temparity;
					while (iteration3 > 0) {
						factPush.remove(factPush.size() - 1);
						iteration3--;
					}
					// factPush = factPush.subList(factPush.size() - temparity, factPush.size());
					currentHash.clear();
					currentHash.putAll(tempMap);
					if (j - 1 < 0) {
						tempMap0.clear();
						previousHash.clear();
					} else {
						tempMap0.clear();
						tempMap0.putAll(tempMap);
						temparity = program.getRuleslist().get(i).getBody().get(j - 1).getArity();
						for (int iteration = temparity - 1; iteration >= 0; iteration--) {
							for (int iteration2 = temparity - 1; iteration2 >= 0; iteration2--) {
								if (program.getRuleslist().get(i).getBody().get(j - 1).getArguments().get(iteration)
										.equals(factPush.get(iteration2))) {
									if (factPush.indexOf(factPush.get(iteration2)) == factPush
											.lastIndexOf(factPush.get(iteration2)))
										tempMap0.remove(factPush.get(iteration2));
									else
										System.out.println("This comes from previous predicate cannot be removed");
								} else {
									continue;
								}

							}

						}
						previousHash.clear();
						previousHash.putAll(tempMap0);

					}
					k = factSelect.get(factSelect.size() - 1) + 1;// get() gets -1 error
					if (k == program.getFacts().size()) {
						j = j - 1;
						if (j < 0) {

							if (i == program.getRuleslist().size() - 1) {
								tempMap.clear();
								tempMap0.clear();
								currentHash.clear();
								previousHash.clear();
								factPush.clear();

								factSelect.clear();
								System.out.println("I am going home!!! not alone!!");
								matchCount = 0;
								return;
								// System.exit(0);;
							}
							i = i + 1;
							this.bodySize = program.getRuleslist().get(i).getBody().size();
							j = 0;
							k = 0;
						}
						// return;
					}
					factSelect.remove(factSelect.size() - 1);
					match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact(),
							currentHash, previousHash);

				}
			}

		}

	}
}
