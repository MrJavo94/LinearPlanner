package coffeeServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import strips.Parameter;
import strips.Predicate;
import strips.State;

public class ProblemReader {
	
	private BufferedReader file;
	private State initialState, goalState;
	
	public ProblemReader(String path) throws IOException {
		file = new BufferedReader(new FileReader(path));
		initialState = null;
		goalState = null;
	}
	
	public void readStates() throws IOException {
		String initialText = "";
		String line = file.readLine();
		while (!line.equals("")) {
			initialText += line;
			line = file.readLine();
		}
		String goalText = "";
		while (line != null) {
			goalText += line;
			line = file.readLine();
		}
		
		String[] initStrings = initialText.split("=")[1].split(";");
		String[] goalStrings = goalText.split("=")[1].split(";");
		List<Predicate> initPredicates = new ArrayList<>();
		List<Predicate> goalPredicates = new ArrayList<>();
		
		for (String predString : initStrings) {
			initPredicates.add(createPredicate(predString));
		}
		for (String predString : goalStrings) {
			goalPredicates.add(createPredicate(predString));
		}
		
		initialState = new State(initPredicates);
		goalState = new State(goalPredicates);
	}
	
	private Predicate createPredicate(String s) {
		/* Gets the name and parameters of the string */
		s = s.trim().replace(")","");
		String[] parts = s.split("\\(");
		String name = parts[0];
		if (parts.length > 1) {
			String[] paramParts = parts[1].split(",");
			List<Parameter> params = new ArrayList<>();
			
			/* Converts parameter text to parameter */
			for (String paramString : paramParts) {
				if (paramString.startsWith("o")) {
					params.add(new Parameter("o", paramString));
				} else if (name.equals("Steps")){
					params.add(new Parameter("x", paramString));
				} else {
					params.add(new Parameter("n", paramString));
				}
			}
			
			return new Predicate(name, params);
		} else return new Predicate(name);
		
	}
	
	
	/* Getters and setters */
	
	public State getInitialState() {
		return initialState;
	}
	
	public State getGoalState() {
		return goalState;
	}
	
}