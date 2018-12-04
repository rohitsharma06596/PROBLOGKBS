package kbs.problog.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import kbs.problog.model.FactModel;
import kbs.problog.model.PredicateModel;
import kbs.problog.model.ProgramModel;
import kbs.problog.model.RulesModel;
/**
 * The Class ParseController.
 */
public class ParseController {

	/** The Constant current_Path. */
	public static final String current_Path = System.getProperty("user.dir");

	/** The f. */
	FactModel f = new FactModel();

	/** The r. */
	RulesModel r = new RulesModel();
	/** The rules. */
	List<RulesModel> rules = new ArrayList<RulesModel>();
	
	/** The facts. */
	List<FactModel> facts = new ArrayList<FactModel>();

	/** The prog. */
	ProgramModel prog = new ProgramModel();

	/**
	 * Rule.
	 *
	 * @param line the line
	 */
	public void rule(String line) {
		rule_get_head(line);
	}

	/**
	 * Fact.
	 *
	 * @param line the line
	 */
	public void fact(String line) {

		String pname = line.substring(0, line.indexOf("("));
		PredicateModel p1 = new PredicateModel();
		p1.setPredName(pname);

		String value = line.substring(line.indexOf("(") + 1, line.indexOf(")")).replaceAll("\\s+", "");

		String[] token = value.split(",");
		List<String> listToken = new ArrayList<>();

		for (int i = 0; i < token.length; i++) {
			listToken.add(token[i]);
		}

		p1.setArguments(listToken);
		p1.setArity(listToken.size());
		String prob = line.substring(line.indexOf(": ") + 1);
		prob = prob.substring(0, prob.length() - 1);
		p1.setProbability(Double.parseDouble(prob));
		f.setFact(p1);

	}

	/**
	 * Rule get head.
	 *
	 * @param line the line
	 */
	public void rule_get_head(String line) {
		PredicateModel p = new PredicateModel();
		String pname = line.substring(0, line.indexOf("("));
		p.setPredName(pname);

		String value = line.substring(line.indexOf("(") + 1, line.indexOf(")")).replaceAll("\\s+", "");

		String[] token = value.split(",");
		List<String> listToken = new ArrayList<>();

		for (int i = 0; i < token.length; i++) {
			listToken.add(token[i]);
		}

		p.setArguments(listToken);
		p.setArity(listToken.size());
		String prob = line.substring(line.indexOf(": ") + 1);
		prob = prob.substring(0, prob.length() - 1);
		p.setProbability(Double.parseDouble(prob));
		r.addHead(p);
		if (!(p.getPredName().equals(null))) {
			rule_get_body(line);
		}

	}

	/**
	 * Rule get body.
	 *
	 * @param line the line
	 */
	public void rule_get_body(String line) {

		String line_body = line.substring(line.indexOf(":-") + 3, line.indexOf("."));

		String[] token = line_body.split(", ");

		for (int i = 0; i < token.length; i++) {
			PredicateModel e = new PredicateModel();

			String pname = token[i].substring(0, token[i].indexOf("("));
			String value = token[i].substring(token[i].indexOf("(") + 1, token[i].indexOf(")"));
			e.setPredName(pname);

			String[] token_val = value.split(",");
			List<String> listToken1 = new ArrayList<>();
			for (int j = 0; j < token_val.length; j++) {
				listToken1.add(token_val[j]);
			}
			e.setArguments(listToken1);
			e.setArity(listToken1.size());
			r.setBody(e);
		}

	}

	/**
	 * Instantiates a new parses the controller.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ParseController() throws IOException {

		String line;
		File fileInput = new File(current_Path + File.separator + "input.txt");

		BufferedReader in_buf = new BufferedReader(new FileReader(fileInput));
		while ((line = in_buf.readLine()) != null) {

			if (line.contains(":-")) {
				rule(line);
				rules.add(r);
				r = new RulesModel();
			} else {
				fact(line);
				facts.add(f);
				f = new FactModel();
			}
		}
		prog.setRuleslist(rules);
		prog.setFacts(facts);
		in_buf.close();
		//String choice = JOptionPane.showInputDialog("Please Enter 1 for Naive and 2 for Semi-Naive");
		
		NaiveEval naive = new NaiveEval(prog);
	//	SemiNaiveEval seminaive = new SemiNaiveEval(prog);

	}

}
