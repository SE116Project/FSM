import java.util.*;
import java.io.*;

public class FSM {

    private static Set<String> symbols = new HashSet<>();
    private static Set<String> states = new HashSet<>();
    private static String initialState = null;
    private static Set<String> finalStates = new HashSet<>();
    private static Map<String, Map<String, String>> transitions = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("FSM DESIGNER v1.0 - " + new Date());
        if (args.length == 1) {
            executeCommandsFromFile(args[0]);
        }
        while (true) {
            System.out.print("? ");
            String line = scanner.nextLine().trim();
            try {
                processCommand(line);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void executeCommandsFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                processCommand(line);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename);
        }
    }

    private static void processCommand(String line) throws Exception {
        if (line.isEmpty() || line.startsWith(";")) {
            return; // Yorum satırı ya da boş satır
        }
        if (!line.endsWith(";")) {
            throw new Exception("Command must end with ';'");
        }

        line = line.substring(0, line.length() - 1).trim(); // ';' sil

        if (line.equalsIgnoreCase("EXIT")) {
            System.out.println("TERMINATED BY USER");
            System.exit(0);
        } else if (line.toUpperCase().startsWith("SYMBOLS")) {
            symbolsCommand(line);
        } else if (line.toUpperCase().startsWith("STATES")) {
            statesCommand(line);
        } else if (line.toUpperCase().startsWith("INITIAL-STATE")) {
            initialStateCommand(line);
        } else if (line.toUpperCase().startsWith("FINAL-STATES")) {
            finalStatesCommand(line);
        } else if (line.toUpperCase().startsWith("TRANSITIONS")) {
            transitionsCommand(line);
        } else if (line.toUpperCase().startsWith("EXECUTE")) {
            executeFSM(line);
        } else if (line.equalsIgnoreCase("CLEAR")) {
            clearAll();
        } else if (line.equalsIgnoreCase("PRINT")) {
            printFSM();
        } else {
            throw new Exception("Unknown command: " + line);
        }
    }

    // ===== Komut Fonksiyonları =====

    private static void symbolsCommand(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length == 1) {
            System.out.println("Symbols: " + symbols);
        } else {
            for (int i = 1; i < parts.length; i++) {
                String sym = parts[i];
                if (!sym.matches("[a-zA-Z0-9]")) {
                    System.out.println("Warning: Invalid symbol ignored: " + sym);
                } else {
                    if (!symbols.add(sym.toUpperCase())) {
                        System.out.println("Warning: Symbol already exists: " + sym);
                    }
                }
            }
        }
    }

    private static void statesCommand(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length == 1) {
            System.out.println("States: " + states);
            System.out.println("Initial State: " + initialState);
            System.out.println("Final States: " + finalStates);
        } else {
            for (int i = 1; i < parts.length; i++) {
                String st = parts[i];
                if (!st.matches("[a-zA-Z0-9]+")) {
                    System.out.println("Warning: Invalid state ignored: " + st);
                } else {
                    if (!states.add(st.toUpperCase())) {
                        System.out.println("Warning: State already exists: " + st);
                    }
                    if (initialState == null) {
                        initialState = st.toUpperCase();
                    }
                }
            }
        }
    }

    private static void initialStateCommand(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length != 2) {
            System.out.println("Error: Initial state must be specified");
            return;
        }
        initialState = parts[1].toUpperCase();
        states.add(initialState);
        System.out.println("Initial state set to: " + initialState);
    }

    private static void finalStatesCommand(String line) {
        String[] parts = line.split("\\s+");
        for (int i = 1; i < parts.length; i++) {
            String st = parts[i];
            finalStates.add(st.toUpperCase());
            states.add(st.toUpperCase());
        }
    }

    private static void transitionsCommand(String line) {
        String[] parts = line.substring(11).split(",");
        for (String transition : parts) {
            String[] items = transition.trim().split("\\s+");
            if (items.length != 3) {
                System.out.println("Warning: Invalid transition format: " + transition);
                continue;
            }
            String symbol = items[0].toUpperCase();
            String from = items[1].toUpperCase();
            String to = items[2].toUpperCase();
            transitions.putIfAbsent(from, new HashMap<>());
            transitions.get(from).put(symbol, to);
        }
    }

    private static void executeFSM(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length != 2) {
            System.out.println("Error: EXECUTE must be followed by an input string");
            return;
        }
        String input = parts[1];
        String currentState = initialState;
        System.out.print(currentState + " ");
        for (char ch : input.toCharArray()) {
            String symbol = String.valueOf(ch).toUpperCase();
            if (!symbols.contains(symbol)) {
                System.out.println("\nError: Invalid symbol: " + symbol);
                return;
            }
            if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(symbol)) {
                currentState = transitions.get(currentState).get(symbol);
                System.out.print(currentState + " ");
            } else {
                System.out.println("\nNO");
                return;
            }
        }
        if (finalStates.contains(currentState)) {
            System.out.println("\nYES");
        } else {
            System.out.println("\nNO");
        }
    }

    private static void clearAll() {
        symbols.clear();
        states.clear();
        finalStates.clear();
        transitions.clear();
        initialState = null;
        System.out.println("FSM cleared.");
    }

    private static void printFSM() {
        System.out.println("Symbols: " + symbols);
        System.out.println("States: " + states);
        System.out.println("Initial State: " + initialState);
        System.out.println("Final States: " + finalStates);
        System.out.println("Transitions: " + transitions);
    }
}
