package kbs.problog.controller;

import java.util.ArrayList;
import java.util.List;

import kbs.problog.model.FactModel;
import kbs.problog.model.PredicateModel;
import kbs.problog.model.ProgramModel;
import kbs.problog.model.RulesModel;

public class InferenceController {
	private ProgramModel program;

	public InferenceController(ProgramModel program) {
		this.program = program;
		subsumption();
	}

	public void subsumption() {
		
		List<FactModel> factList = program.getFacts();
		/*
		 * for(int i=0;i<factList.size();i++) {
		 * System.out.println(factList.get(i).getFact()); }
		 */
		for (int i = 0; i < program.getRuleslist().size(); i++) {
			
			int bodySize = program.getRuleslist().get(i).getBody().size();
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
			ArrayList<List<PredicateModel>> outerList = new ArrayList();
			
			boolean isEmpty = false;
			for (int j = 0; j < bodySize; j++) {

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

			}

		}

	}
}
