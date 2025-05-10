import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class FSM {
    private static ArrayList<String> symbols = new ArrayList<>();
    private static ArrayList<State> states = new ArrayList<>();
    private static CurrentState currentState = new CurrentState("");

    public static ArrayList<State> getStates() {
        return states;
    }

    public static void addState(State state)throws InvalidStateException {
        for(State s : states) {
            if(s.getStateName().equalsIgnoreCase(state.getStateName())) {
                throw new InvalidStateException("State already exists");
            }
        }
        states.add(state);
    }

    public static void setStates(ArrayList states) {
        FSM.states = states;
    }

    public static ArrayList<String> getSymbols() {
        return symbols;
    }

    public static void setSymbols(ArrayList<String> symbols) {
        FSM.symbols = symbols;
    }

    public static CurrentState getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(CurrentState currentState) {
        FSM.currentState = currentState;
    }

    public static PrintWriter getLogWriter() {
        return logWriter;
    }

    public static void setLogWriter(PrintWriter writer) {
        FSM.logWriter = writer;
    }


    public static void addSymbol(String symbol) {
        if (!symbol.matches("^[a-zA-Z0-9]$")) {
            System.out.println("Warning: invalid symbol " + symbol);
            return;
        }
        if (!symbols.contains(symbol)) {
            symbols.add(symbol);
        } else System.out.println("Symbol is'" + symbol + "' already in the list");
    }

    public static void removeSymbol(String symbol) {
        if (symbols.contains(symbol)) {
            symbols.remove(symbol);
        } else System.out.println("Symbol is not in the list");
    }

    public static void printSymbols() {
        int counter = 0;
        for (String symbol : symbols) {
            counter++;
            System.out.println(counter + ". Symbol : " + symbol);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            LocalDateTime now = LocalDateTime.now();
            System.out.println("FSM DESIGNER v1.0.0");
            log("FSM DESIGNER v1.0.0");
            System.out.println(now);
            log(now.toString());
            System.out.println("?");
            log("?");
        } else if (args.length >= 1) {
            if (args[0].endsWith(".log") || args[0].endsWith(".txt") || args[0].endsWith(".fs")) {
                loadFunction(args[0]);
            }
        } else {
            checkForFunctions(args[0]);
        }

        Scanner in = new Scanner(System.in);
        String line;
        while (in.hasNextLine()) {
            line = in.nextLine().trim();
            if (line.isEmpty()) continue;
            log("?> " + line);
            checkForFunctions(line);
            System.out.println("?");
            log("?");
        }
    }


    public static void exitFunction() {
        System.out.println("TERMINATED BY USER");
        System.exit(0);

    }

    public static void loadFunction(String file) throws IOException {
        states.clear();
        symbols.clear();
        currentState.setStateName("");
        System.out.println("Loading " + file);



        File f = new File(file);
        if (!f.exists() || !f.canRead()) {
            System.out.println("Error: The file cannot be accessed. Please check the file path and permissions.");
            log("Error: The file cannot be accessed. Please check the file path and permissions.");
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            if (file.endsWith(".fs")) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    while (in.available() <= 0) {
                        Object a = in.readObject();
                        if (a.getClass().equals(State.class)) {
                            addState((State) a);
                        } else if (a.getClass().equals(FinalState.class)) {
                            addState((FinalState) a);
                        } else if (a.getClass().equals(InitialState.class)) {
                            addState((InitialState) a);
                        } else if (a.getClass().equals(String.class)) {
                            addSymbol((String) a);
                        }


                    }


                    in.close();
                    return;
                } catch (EOFException eofe) {

                    return;
                }
            }
            while (br.ready()) {
                line = br.readLine();

                checkForFunctions(line);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void checkForFunctions(String line) throws Exception {
        try {

            line = line.trim();
            log(line);
            if (line.equals("")) return;
            boolean checkTransitions = false;
            if (line.contains("TRANSITIONS") && line.contains(",")) {

                if (line.substring(line.indexOf(" ")).equalsIgnoreCase("TRANSITIONS")) {
                    transitionsFunction(line.substring(line.indexOf(" ")));
                    return;
                }

            } else if (line.contains(",")) {
                transitionsFunction(line.trim());
                return;
            }
            if (!line.contains(";")) {
                if (!line.contains(",")) {
                    throw new InvalidCommandException("Invalid command " + line);
                }
            }

            if (line.contains(";")) {
                if (line.charAt(0) == ';') {

                }
                line = line.substring(0, line.indexOf(";"));
                line = line.trim();
                if (line.isEmpty()) return;
                if (!line.contains(",")) {
                    if (!line.contains("EXIT")) {
                        if (!line.contains("CLEAR")) {
                            if (!line.contains("SYMBOLS")) {
                                if (!line.contains("PRINT")) {
                                    if (!line.contains("STATES")) {
                                        if (!line.contains("LOAD")) {
                                            if (!line.contains("TRANSITIONS")) {
                                                if (!line.contains("EXECUTE")) {
                                                    if (!line.contains("INITIAL-STATE")) {
                                                        if (!line.contains("FINAL-STATE")) {
                                                            if (!line.contains("LOG")) {
                                                                if (!line.contains("COMPILE")) {
                                                                    throw new InvalidCommandException("Invalid command : " + line);
                                                                }

                                                            }

                                                        }
                                                    }


                                                }

                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                if (line.length() < 2) {
                }
                if (line.substring(0, 3).compareToIgnoreCase("log") == 0) {
                    if(!line.contains(" ")){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    logFunction(line.substring(3).trim());
                }
                if (line.length() < 4) {
                } else if (line.substring(0, 4).compareToIgnoreCase("load") == 0) {
                    if(!line.contains(" ")){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    loadFunction(line.substring(line.indexOf(" ")).trim());
                } else if (line.substring(0, 4).compareToIgnoreCase("exit") == 0) {
                    if(line.length()!=4){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    exitFunction();
                } else if (line.substring(0, 5).compareToIgnoreCase("PRINT") == 0) {
                    if(line.length()!=5){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    printFunction();
                }
                if (line.length() < 5) {
                } else if (line.substring(0, 5).compareToIgnoreCase("clear") == 0) {
                    if(line.length()!=5){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    clearFunction();
                }
                if (line.length() < 6) {
                } else if (line.substring(0, 6).compareToIgnoreCase("states") == 0) {
                    line = line.trim();
                    if(!line.contains(" ")){
                        if(line.length()==6){
                            statesFunction();
                            return;
                        }
                        throw new InvalidCommandException("Invalid command : " + line);
                    } else
                        statesFunction(line.substring(line.indexOf(" ")).trim());

                }
                if (line.length() < 7) {
                } else if (line.substring(0, 7).compareToIgnoreCase("execute") == 0) {
                    if(!line.contains(" ")){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    executeFunction(line.substring(line.indexOf(" ")).trim());

                } else if (line.substring(0, 7).compareToIgnoreCase("symbols") == 0) {
                    line = line.trim();

                    if (line.compareToIgnoreCase("symbols") == 0) {
                        symbolsFunction();
                    } else
                    if(!line.contains(" ")){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    symbolsFunction(line.substring(line.indexOf(" ")).trim());

                } else if (line.substring(0, 7).compareToIgnoreCase("compile") == 0) {
                    if(line.length()==7){
                        throw new InvalidCommandException("Compile command needs parameter : " + line);
                    }else if(!line.contains(" ")){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    if(line.charAt(7)!=' '){
                        throw new InvalidCommandException("Invalid command : " + line);
                    }
                    compileFunction(line.substring(line.indexOf(" "), line.length()).trim());
                }
                if (line.length() < 11) {
                } else if (line.substring(0, 11).compareToIgnoreCase("final-state") == 0) {
                    if (line.length() < 12)
                        throw new InvalidCommandException("Invalid command : " + line + "Needs parameter");
                    finalStateFunction(line.substring(line.indexOf(" ")));
                } else if (line.substring(0, 11).compareToIgnoreCase("TRANSITIONS") == 0) {
                        if(line.length()==11){
                            throw new InvalidCommandException("Transitions command needs parameter : " + line);
                        }
                    transitionsFunction(line.substring(line.indexOf(" ")).trim());
                } else if (line.substring(0, 13).compareToIgnoreCase("initial-state") == 0) {
                    initialStateFunction(line.substring(line.indexOf(" ")));
                } else if (line.contains(",") && line.contains(";"))
                    throw new InvalidCommandException("Invalid command");

            } else if (line.contains("TRANSITIONS")) {
                if(line.length()==11){
                    throw new InvalidCommandException("TRANSITIONS command needs parameter : " + line);
                }
                if (line.substring(0, 11).compareToIgnoreCase("TRANSITIONS") == 0 && checkTransitions == false) {

                    transitionsFunction(line.substring(line.indexOf(" ")));

                }

            } else {
                throw new InvalidCommandException("Invalid command : " + line);
            }
        } catch (StringIndexOutOfBoundsException e) {

            if (e.getClass().equals(StringIndexOutOfBoundsException.class)) {

            }
        } catch (Exception z) {
            z.printStackTrace();
        }
    }

    public static void symbolsFunction(String line) {
        line = line.trim();
        if (line.contains(" ")) {
            String[] values = line.split(" ");
            for (String symbol : values) {
                addSymbol(symbol);
            }
        } else addSymbol(line);


    }

    public static void symbolsFunction() {
        printSymbols();
    }

    public static void statesFunction(String line) throws InvalidStateException {
        line = line.trim();
        if (line.contains(" ")) {
            String[] values = line.split(" ");
            for (String symbol : values) {
                State state = new State(symbol);
                addState(state);
            }
        } else{
            if(getStates().contains(line)){
                System.out.println();
                throw new InvalidStateException(line +" : state already declared");
            }
            addState(new State(line));}

    }

    public static void statesFunction() {
        int counter = 0;
        for (State state : states) {
            counter++;
            System.out.println("State " + counter + " :" + state.getStateName());
        }

    }

    public static void initialStateFunction(String line) throws InvalidStateException {
        String stateName = line.trim();
        for (State state : states) {
            if (state.getClass().equals(InitialState.class)) {
                System.out.println("Initial state already declared as : " + state.getStateName());
                return;
            }
        }
        boolean stateExists = false;
        if(getStates().contains(stateName)){
            throw new InvalidStateException(stateName+" : already declared");
        }
        for (State state : states) {
            stateExists =false;
            if (state.getStateName().equalsIgnoreCase(stateName)) {
                stateExists = true;
                break;
            }
            if(stateExists){
                throw new InvalidStateException(stateName+" : already declared");
            }
        }


        InitialState initialState = new InitialState(line.trim());
        states.add(initialState);
        System.out.println("Initial state declared : "+initialState.getStateName());

    }

    public static void finalStateFunction(String line) throws InvalidStateException {
        line = line.trim();
        String[] values = line.split(" ");
        boolean stateExists = false;
        for (String symbol : values) {


            for (State state : states) {
                if (state.getStateName().equalsIgnoreCase(symbol)) {
                    throw new InvalidStateException(symbol+" is already declared");
                }
            }


            FinalState finalState = new FinalState(symbol);
            states.add(finalState);
        }

        for (State a : states) {
            if (a instanceof FinalState) {
                System.out.println("Final state declared as :"+a.getStateName());
            }
        }
    }

    public static void transitionsFunction(String data) throws Exception {


        data = data.trim();

        if (data.contains(";")) {
            data = data.substring(0, data.indexOf(";"));
        }
        if (data.charAt(data.length() - 1) == ',') {
            data = data.substring(0, data.length() - 1).trim();
        }

        String[] values = data.split(",");

        String[] data2;
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;

        for (String symbol : values) {
            if (symbol.isEmpty()) continue;
            if (symbol.contains(" ")) {
                symbol = symbol.trim();
            }
            check3 = false;
            check2 = false;
            check1 = false;
            data2 = symbol.split(" ");
            for (String a : data2) {
                a = a.trim();
            }

            if (symbols.contains(data2[0])) {
                check1 = true;
                for (State a : states) {
                    if (a.getStateName().equalsIgnoreCase(data2[1])) {
                        check2 = true;
                    }
                    if (a.getStateName().equalsIgnoreCase(data2[2])) {
                        check3 = true;
                    }
                }
                if (check2 == false || check3 == false) {
                    throw new InvalidStateException("Invalid state transition: " + data2[1] + " -> " + data2[2] + " with symbol: " + data2[0]);
                } else {
                    for (State a : states) {
                        if (a.getStateName().equalsIgnoreCase(data2[1])) {
                            a.addConnectedNode(data2[0], data2[2]);
                        }
                    }
                }

            } else throw new InvalidStateException("Invalid state: " + data2[0]);

        }
    }

    public static void printFunction() {
        for (State a : states) {
            a.printInfo();
        }
        statesFunction();
        symbolsFunction();
    }

    public static void compileFunction(String line) {
        try {
            setCurrentState(new CurrentState(""));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(line));
            for (State a : states) {
                objectOutputStream.writeObject(a);
            }
            for (String a : symbols) {
                objectOutputStream.writeObject(a);
            }

            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void clearFunction() {

        symbols.clear();
        states.clear();
        currentState = new CurrentState("");
        System.out.println("FSM cleared.");

    }

    private static PrintWriter logWriter = null;

    public static void log(String message) {
        if (logWriter != null) {
            logWriter.println(message);
            logWriter.flush();
        }
    }

    public static void logFunction(String data) {
        String filename = data == null ? "" : data.trim();
        if (filename.isEmpty()) {
            if (logWriter != null) {
                logWriter.close();
                logWriter = null;
                System.out.println("STOPPED LOGGING");
                log("STOPPED LOGGING");
            } else {
                System.out.println("LOGGING was not enabled");
                log("LOGGING was not enabled");
            }
            return;
        }
        if (logWriter != null) {
            logWriter.close();
        }
        try {
            logWriter = new PrintWriter(new FileWriter(filename));
            System.out.println("LOGGING to file: " + filename);
            log("LOGGING to file: " + filename);
        } catch (IOException e) {
            logWriter = null;
            System.out.println("Error: cannot write file " + filename);
            log("Error: cannot write file " + filename);
        }
    }

    public static void executeFunction(String data) throws Exception {
        try {
            char[] checks = data.toCharArray();
            for (char c : checks) {
                if (!symbols.contains(String.valueOf(c))) {
                    throw new InvalidSymbolException(c + " is not a valid symbol");
                }
            }
            if (states.isEmpty()) {
                System.out.println("Nothing to execute states are not declared");
                return;
            }
            for (State a : states) {

                if (a.getClass().equals(InitialState.class)) {
                    currentState.setStateName(a.processInput(String.valueOf(data.charAt(0))));
                    for (State b : states) {
                        if (b.getStateName().equalsIgnoreCase(currentState.getStateName())) {
                            currentState.setStateName(b.getStateName());
                        }
                    }
                }
            }
            for (int i = 1; i < data.length(); i++) {
                for (State a : states) {
                    if (a.getStateName().equalsIgnoreCase(currentState.getStateName())) {
                        currentState.setStateName(a.processInput(String.valueOf(data.charAt(i))));
                    }
                }
            }

            String check = currentState.getStateName();
            System.out.println("Current state (last) : " + currentState.getStateName());
            for (State a : states) {
                if (a.getClass().equals(FinalState.class)) {
                    if (check == null) {
                        throw new InvalidSymbolException("Symbol is not declared");
                    }
                    if (a.getStateName().equalsIgnoreCase(check)) {
                        System.out.println("Yes");
                        return;
                    }
                }
            }
            if (states.isEmpty()) return;
            System.out.println("No");
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}