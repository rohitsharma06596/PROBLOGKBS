package kbs.problog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kbs.problog.model.FactModel;
import kbs.problog.model.PredicateModel;
import kbs.problog.model.ProgramModel;
import kbs.problog.model.RulesModel;

public class InferenceController {
	private ProgramModel program;
	int matchCount;
	HashMap<String, String> tempMap = new HashMap<>();
	HashMap<String, String> tempMap0 = new HashMap<>();
	int i, j, k=0;
	boolean mat;
	int bodySize;
	int[] probArr;

	public InferenceController(ProgramModel program) {
		this.program = program;
		
		bodySize = program.getRuleslist().get(0).getBody().size();
		probArr = new int[bodySize];
		match(program.getRuleslist().get(0).getBody().get(0), program.getFacts().get(0).getFact());
	}
	public boolean predicateMatching(PredicateModel parmPred, PredicateModel parmFact)
	{
		
		if((parmPred.getArity() != parmFact.getArity()) && (!parmPred.getPredName().equals(parmFact.getPredName())))
			return false;
		else
		{
			for( int i = 0 ; i< parmPred.getArity();i++)
			{
				if((tempMap.get(parmPred.getArguments().get(i))).equals(null))
				{
					tempMap.put(parmPred.getArguments().get(i), parmFact.getArguments().get(i));	
				}
				else
				{
					if(tempMap.get(parmPred.getArguments().get(i)).equals(parmFact.getArguments().get(i)))
					{
						continue;
					}
					else
					{
						return false;
					}
				}
			}
		}
		matchCount++;
		return true;
	}

	public void match(PredicateModel p, PredicateModel f) {
		
		mat = this.predicateMatching(p, f);
		if(mat && matchCount!=bodySize)
		{
			j=j+1;
			k=0;
			match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());				
		}
		if(!mat)
		{
			k = k+1;
			match(program.getRuleslist().get(i).getBody().get(j), program.getFacts().get(k).getFact());
		}
		if(mat && matchCount==bodySize)
		{
			InferIDB(program.getRuleslist().get(i).getHead(),probArr);
		}
		
		
		 /* for(int i=0;i<factList.size();i++) {
		  System.out.println(factList.get(i).getFact()); }
		 */
	/**	for (int i = 0; i < program.getRuleslist().size(); i++) {
			
			int bodySize = program.getRuleslist().get(i).getBody().size();
			int probArray[] = new int[bodySize];
			// System.out.println();
			/*
			 * for(int j =0;j<ruleBody.size();j++) { System.out.print(ruleBody.get(j)); }
			 */
		/*	ArrayList<List<PredicateModel>> outerList = new ArrayList();
			boolean isEmpty = false;
			for (int j = 0; j < bodySize; j++) {

				ArrayList<PredicateModel> innerList = new ArrayList<>();
				for (int k = 0; k < factList.size(); k++) {
					while (!isEmpty) {
						if (ruleBody.get(j).getPredName().equals(factList.get(k).getFact().getPredName())) {
							innerList.add(factList.get(k).getFact());
						}

						if (innerList.isEmpty()) {
							isEmpty = true;
							outerList.removeAll(innerList);
						}
						if (isEmpty == false) {
							outerList.add(innerList);
						}
					}
				}
				System.out.println(outerList);

			}*/
		/**	ArrayList<List<PredicateModel>> outerList = new ArrayList();
			
			boolean isEmpty = false;*/
			/*for (int j = 0; j < bodySize; j++) {

				ArrayList<PredicateModel> innerList = new ArrayList<>();
				for (int k = 0; k < factList.size(); k++) {
					while (!isEmpty) {
						if (program.getRuleslist().get(i).getBody().get(j).getPredName().equals(factList.get(k).getFact().getPredName())) {
							
							innerList.add(factList.get(k).getFact());
						}

						if (innerList.isEmpty()) {
							isEmpty = true;
							outerList.removeAll(innerList);
						}
						if (isEmpty == false) {
							outerList.add(innerList);
						}
					}
				}
				System.out.println(outerList);

			}*/
			/**int j=0;
			matchCount = 0;
			boolean limit=false;
			while(j<bodySize)
			{
				for (int k = 0; k < factList.size(); k++)
				{
					if (program.getRuleslist().get(i).getBody().get(j).getPredName().equals(factList.get(k).getFact().getPredName()))
					{
						boolean match;
						tempMap0 = tempMap;
						match = predicateMatching(program.getRuleslist().get(i).getBody().get(j),factList.get(k));
						if(matchCount==bodySize)
						{
							inferIDB(program.getRuleslist().get(i).getHead(),probArray);
							tempMap = tempMap0;
							matchCount--;
						}
						if(match)
						{
							j++;
						}
					}
				}
			}
			

		}*/
		
		int i, j, k=0;
		boolean mat;
		
		
		
		
		
	} 
	public void inferIDB(PredicateModel head, int[] prob)
	{
		
	}
}
