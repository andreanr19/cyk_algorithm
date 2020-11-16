package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Grammar {

	/**
	 * s it's the initial variable of the grammar
	 */
	private String s;

	/**
	 * a hash map that contains all the variables of the grammar
	 */
	private HashMap<String, Var> varsSet;

	/**
	 * Creates a new grammar with it's hash map of variables empty
	 */
	public Grammar() {
		this.varsSet = new HashMap<String, Var>();
	}

	/**
	 * This method allows to split what user wrote by dividing it in lines one by
	 * one
	 * 
	 * @param vars is a string that refers to all the grammar
	 * @return an array of String in which each position corresponds to a line of
	 *         the grammar format
	 */
	public String[] splitLines(String vars) {
		return vars.split("\n");
	}

	/**
	 * This method allows to add the variables to the grammar by reading line by
	 * line of what user wrote.
	 * 
	 * @param vrs is a String that corresponds to all the grammar.
	 */
	public void addVarsToTheGrammar(String vrs) {

		String[] lineByLine = splitLines(vrs); // Reads line by line the grammar the user typed

		for (String line : lineByLine) { // Loop though the line by line to get the variable and its productions
			StringTokenizer ps = new StringTokenizer(line.trim()); // clean the unnecessary blank spaces from the String
			String initialVar = ps.nextToken(); // We get the main variable

			Var currentVar = varsSet.get(initialVar); // we get value of the initial variable key
			if (currentVar == null) { // verifies if the value of the key exists
				currentVar = new Var(initialVar);
				if (varsSet.size() == 0) {
					varsSet.put(initialVar, currentVar); // add new production to the main variable
					s = initialVar; // We set the initial variable of the grammar
				} else {
					varsSet.put(initialVar, currentVar); // add a new simple production
				}
			}
			addProductions(ps, currentVar); // add the productions to the current variable

		}

	}

	/**
	 * This method allows to add each production to a single variable.
	 * 
	 * @param st      is a StringTokenizer object which stock all the variables of
	 *                the variable that is going to store them.
	 * @param current is a Variable object which corresponds to the actual variable
	 *                that is going to store the productions in the grammar.
	 */
	public void addProductions(StringTokenizer st, Var current) {
		while (st.hasMoreTokens()) {
			current.addP(st.nextToken());
		}
	}

	/**
	 * This method initialize the first column matrix
	 * 
	 * @param chs       is an array of char that corresponds to the characters of
	 *                  the test string.
	 * @param theMatrix is an string array that corresponds to the cyk matrix.
	 */
	public void iteratorOnTheFirstColumCharacterMatrix(char[] chs, String[][][] theMatrix) {
		for (int j = 0; j < chs.length; j++) {
			ArrayList<String> current = new ArrayList<>();
			for (Iterator<Var> iter = varsSet.values().iterator(); iter.hasNext();) {
				Var v = (Var) iter.next();
				if (v.getPs().contains(chs[j] + "")) {
					current.add(v.getId());
				}
			}
			if (current.size() > 0) {
				String[] outcome = new String[current.size()];
				for (int i = 0; i < outcome.length; i++) {
					outcome[0] = current.get(i);
				}
				theMatrix[j][0] = outcome;
			} else {
				theMatrix[j][0] = null;
			}
		}
	}

	/**
	 * This method verifies if the variables of the grammar produce at least one
	 * single production
	 * 
	 * @param possibleProductions is the HashSet that stores the possible
	 *                            productions of the grammar
	 * @param theMatrix           is an String array that corresponds to the cyk
	 *                            matrix.
	 * @param i                   is an Integer that corresponds to the position of
	 *                            the queue of the matrix.
	 * @param j                   is an Integer that corresponds to the position of
	 *                            the column of the matrix.
	 */
	public void verifyASingleProduction(HashSet<String> possibleProductions, String[][][] theMatrix, int i, int j) {
		if (possibleProductions.size() > 0) {
			ArrayList<String> producingVariables = new ArrayList<>();
			for (Iterator<Var> iter = varsSet.values().iterator(); iter.hasNext();) {
				Var variable = (Var) iter.next();
				boolean found = false;
				for (Iterator<String> iter2 = possibleProductions.iterator(); iter2.hasNext() && !found;) {
					String string = (String) iter2.next();
					if (variable.getPs().contains(string)) {
						found = true;
					}

				}
				if (found) {
					producingVariables.add(variable.getId());
				}
			}

			if (producingVariables.size() > 0) {
				String[] outcome = new String[producingVariables.size()];
				for (int k = 0; k < outcome.length; k++) {
					outcome[k] = producingVariables.get(k);
				}
				theMatrix[i][j] = outcome;
			} else {
				theMatrix[i][j] = null;
			}

		} else {
			theMatrix[i][j] = null;
		}
	}

	public boolean CYK(String testString) {
		boolean isGenerated = false;
		if (testString.length() >= 1) {
			char[] characters = testString.toCharArray();
			String[][][] theMatrix = new String[characters.length][characters.length][0];
			iteratorOnTheFirstColumCharacterMatrix(characters, theMatrix);

			for (int j = 1; j < theMatrix.length; j++) {
				for (int i = 0; i < theMatrix.length - j; i++) {
					HashSet<String> possibleProductions = new HashSet<>();
					for (int k = 0; k < j; k++) {
						String[] variablesIK = theMatrix[i][k];
						String[] variablesIK1JK1 = theMatrix[i + k + 1][j - k - 1];
						if (variablesIK != null && variablesIK1JK1 != null) {

							for (int m = 0; m < variablesIK.length; m++) {
								for (int n = 0; n < variablesIK1JK1.length; n++) {
									possibleProductions.add(variablesIK[m] + variablesIK1JK1[n]);
								}
							}
						}
					}
					verifyASingleProduction(possibleProductions, theMatrix, i, j);

				}
			}

			boolean contains = false;

			String[] variablesValidation = theMatrix[0][theMatrix.length - 1];
			if (variablesValidation != null) {
				for (int i = 0; i < variablesValidation.length && !contains; i++) {
					if (variablesValidation[i].equals(s)) {
						contains = true;
					}
				}

			}
			isGenerated = contains;

		} else {
			return true;
		}

		return isGenerated;
	}

}
