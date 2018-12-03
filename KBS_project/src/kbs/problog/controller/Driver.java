package kbs.problog.controller;

import kbs.problog.model.ProgramModel;

/**
 * The Class Driver.
 */
public class Driver {
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
		System.out.println("The program ran for " + (System.nanoTime() - startTime) + " nano seconds");

	}

}
