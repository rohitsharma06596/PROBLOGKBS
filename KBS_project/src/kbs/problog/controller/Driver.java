package kbs.problog.controller;
import kbs.problog.model.ProgramModel;

public class Driver {

	// public static final String current_Path = System.getProperty("user.dir");

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	
	public static void main(String[] args) throws Exception {
		final long startTime = System.nanoTime();		
		ProgramModel program = new ProgramModel();
		ParseController p = new ParseController();
		System.out.println("The program ran for "+ (System.nanoTime() - startTime) +" nano seconds");
		
		//InferenceController ic = new InferenceController(program);

	}

}
