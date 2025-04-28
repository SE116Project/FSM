import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;

public class FSM{
   private static ArrayList<String> symbols=new ArrayList<>();
   private static ArrayList<State> states=new ArrayList<>();
   private static CurrentState currentState=new CurrentState("");
   public static ArrayList<State> getStates(){
       return states;
   }
   public static void addState(State state){
       if(!states.contains(state)){
           states.add(state);
       }else System.out.println("State already exists");
   }
   public static void setStates(ArrayList<State> states){
       FSM.states=states;
   }
   public static ArrayList<String> getSymbols(){
       return symbols;
   }
   public static void setSymbols(ArrayList<String> symbols){
       FSM.symbols=symbols;
   }


   public static void addSymbol(String symbol){
       if(!symbols.contains(symbol)){
           symbols.add(symbol);
       }else System.out.println("Symbol is already in the list");
   }
   public static void removeSymbol(String symbol){
       if(symbols.contains(symbol)){
           symbols.remove(symbol);
       }else System.out.println("Symbol is not in the list");
   }
   public static void printSymbols(){
       int counter=0;
       for(String symbol:symbols){
           counter++;
           System.out.println(counter+". Symbol : "+symbol);
       }
   }
    public static void main(String[] args) throws Exception {


        if(args.length == 0) {
            LocalDateTime now = LocalDateTime.now();
            System.out.println("FSM DESIGNER <versionNo>");
            System.out.println(now);
            System.out.println("?");

        }else if(args.length >1) {
            loadFunction(args[0]);
        }
        else checkForFunctions(args[0]);

        Scanner in = new Scanner(System.in);
        String line;
        while(in.hasNextLine()) {

            line = in.nextLine().trim();

            checkForFunctions(line);
            System.out.println("?");
        }

    }


    public static void exitFunction(){
        System.out.println("TERMINATED BY USER");
        System.exit(0);

    }
    public static void loadFunction(String file)throws IOException {
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            String line;
            while(br.ready()) {
                line = br.readLine();

                checkForFunctions(line);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static void checkForFunctions(String line)throws Exception {
        try{

            line=line.trim();
            if(line.equals(""))return;
            boolean checkTransitions = false;
            if(line.contains("TRANSITIONS")&&line.contains(",")) {

                if(line.substring(line.indexOf(" ")).equalsIgnoreCase("TRANSITIONS")) {
                    transitionsFunction(line.substring(line.indexOf(" ")));
                    return;
                }

            }else if(line.contains(",")){
                transitionsFunction(line.trim());
                return;
            }
            if(!line.contains(";")){
                if(!line.contains(",")){
                    throw new InvalidCommandException("Invalid command "+line);
                }
            }

            if(line.contains(";")){
                if(line.charAt(0)==';'){

                }
                line=line.substring(0,line.indexOf(";"));
                line=line.trim();
                if(line.isEmpty())return;
                    if(!line.contains(",")){
                        if(!line.contains("EXIT")) {
                            if (!line.contains("CLEAR")) {
                                if (!line.contains("SYMBOLS")) {
                                    if (!line.contains("PRINT")) {
                                        if (!line.contains("STATES")) {
                                            if(!line.contains("LOAD")) {
                                                if(!line.contains("TRANSITIONS")) {
                                                    if(!line.contains("EXECUTE")) {
                                                     if(!line.contains("INITIAL-STATE")) {
                                                         if(!line.contains("FINAL-STATE")) {
                                                             if(!line.contains("LOG")) {
                                                                 if(!line.contains("COMPILE")) {
                                                                     throw new InvalidCommandException("Invalid command : " + line);
                                                                 }

                                                             }

                                                             }}


                                                    }

                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }}

                if(line.length()<2){}
                if(line.substring(0,3).compareToIgnoreCase("log")==0){
                    logFunction(line.substring(3).trim());
                }if(line.length()<4){}
                else if(line.substring(0,4).compareToIgnoreCase("load")==0){

                    loadFunction(line.substring(line.indexOf(" ")).trim());
                }   else if (line.substring(0,4).compareToIgnoreCase("exit")==0) {
                    exitFunction();
                }   else if (line.compareToIgnoreCase("PRINT")==0) {

                    printFunction();
                }
                if(line.length()<5){}
                else if(line.substring(0,5).compareToIgnoreCase("clear")==0){
                    clearFunction();
                }
                if(line.length()<6){}
                else if (line.substring(0,6).compareToIgnoreCase("states")==0) {
                    line=line.trim();
                    if(line.compareToIgnoreCase("states")==0){
                        statesFunction();
                    }else
                        statesFunction(line.substring(line.indexOf(" ")).trim());

                }
                if(line.length()<7){}
                else if(line.substring(0,7).compareToIgnoreCase("execute")==0) {
                    executeFunction(line.substring(line.indexOf(" ")).trim());
                }
                else if(line.substring(0,7).compareToIgnoreCase("symbols")==0) {
                    line=line.trim();
                    if(line.compareToIgnoreCase("symbols")==0){
                        symbolsFunction();
                    }else
                        symbolsFunction(line.substring(line.indexOf(" ")).trim());

                }
                else if (line.substring(0,7).compareToIgnoreCase("compile")==0) {
                    compileFunction();
                }
                if(line.length()<11){}
                else if (line.substring(0,11).compareToIgnoreCase("final-state")==0) {
                    if(line.length()<11)throw new InvalidCommandException("Invalid command : " + line+"Needs parameter");
                    finalStateFunction(line.substring(line.indexOf(" ")));
                }
                else if (line.substring(0,11).compareToIgnoreCase("TRANSITIONS")==0) {

                    transitionsFunction(line.substring(line.indexOf(" ")).trim());
                }
                else if (line.substring(0,13).compareToIgnoreCase("initial-state")==0) {
                    initialStateFunction(line.substring(line.indexOf(" ")));
                }else if(line.contains(",")&&line.contains(";"))
                    throw new InvalidCommandException("Invalid command");

            }else if(line.contains("TRANSITIONS")){

                    if(line.substring(0,11).compareToIgnoreCase("TRANSITIONS")==0&& checkTransitions==false){

                        transitionsFunction(line.substring(line.indexOf(" ")));

                    }

            }else {throw new InvalidCommandException("Invalid command : "+line);}
        }catch (StringIndexOutOfBoundsException e) {

            if(e.getClass().equals(StringIndexOutOfBoundsException.class)) {

            }
            }catch (Exception z){
            z.printStackTrace();
            }
    }
    public static void symbolsFunction(String line){
       line=line.trim();
       if(line.contains(" ")){
           String[] values=line.split(" ");
           for(String symbol:values){
               addSymbol(symbol);
           }
       }else addSymbol(line);


    }
    public static void symbolsFunction(){
        printSymbols();
    }
    public static void statesFunction(String line){
        line=line.trim();
        if(line.contains(" ")){
            String[] values=line.split(" ");
            for(String symbol:values){
                State state=new State(symbol);
                addState(state);
            }
        }else addState(new State(line));

    }
    public  static void statesFunction(){
       int counter=0;
       for(State state:states){
           counter++;
           System.out.println("State "+counter+" :"+state.getStateName());
       }

    }
    public static void initialStateFunction(String line){

       InitialState initialState=  new InitialState(line.trim());
       states.add(initialState);
        System.out.println(initialState.getStateName());
    }
    public static void finalStateFunction(String line){
       line=line.trim();
       String[] values=line.split(" ");
       for(String symbol:values){
           FinalState finalState=  new FinalState(symbol);
           addState(finalState);
       }

        for(State a:states){
            if(a.getClass().equals(FinalState.class)){
                System.out.println(a.getStateName());
            }
        }
    }
    public static void transitionsFunction(String data)throws Exception{


        data=data.trim();

        if(data.contains(";")){
            data=data.substring(0,data.indexOf(";"));
        }
        if(data.charAt(data.length()-1)==','){
            data=data.substring(0,data.length()-1).trim();
        }
        System.out.println(data);
        String[] values=data.split(",");

        String[] data2;
        boolean check1=false;
        boolean check2=false;
        boolean check3=false;

        for(String symbol:values){
            if(symbol.isEmpty())continue;
            if(symbol.contains(" ")){
                symbol=symbol.trim();
            }
            check3=false;
            check2=false;
            check1=false;
            data2=symbol.split(" ");
            for(String a:data2){
                a=a.trim();
            }

            System.out.println(data2[0]);
            if(symbols.contains(data2[0])){
                check1=true;
                for(State a:states){
                    if(a.getStateName().equalsIgnoreCase(data2[1])){
                        check2=true;
                    }
                    if(a.getStateName().equalsIgnoreCase(data2[2])){
                        check3=true;
                    }
                }
                if(check2==false||check3==false){
                    throw new InvalidStateException();
                }else {
                    for(State a:states){
                        if(a.getStateName().equalsIgnoreCase(data2[1])){
                            a.addConnectedNode(data2[0],data2[2]);
                        }
                    }
                }
            }else throw new InvalidSymbolException();
        }
    }
    public static void printFunction(){
       for(State a:states){
        a.printInfo();
       }
       statesFunction();
       symbolsFunction();
    }
    public static  void compileFunction(){
        System.out.println("Compile Function");
    }
    public static void clearFunction(){

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
    public static  void executeFunction(String data){
       int counter=0;
       for(State a:states){

            if(a.getClass().equals(InitialState.class)){
                currentState.setStateName(a.processInput(String.valueOf(data.charAt(counter))));
                for(State b:states){
                    if(b.getStateName().equalsIgnoreCase(currentState.getStateName())){
                        currentState.setStateName(b.getStateName());
                    }
                }
                counter++;
            }
        }
       for(int i=1;i<data.length();i++){
           for(State a:states){
               if(a.getStateName().equals(currentState.getStateName())){
                   currentState.setStateName(a.processInput(String.valueOf(data.charAt(i))));

               }
           }
       }

       String check=currentState.getStateName();
       for(State a:states){
           if(a.getClass().equals(FinalState.class)){
               if(check.equals(a.getStateName())){
                   System.out.println("Yes");
                   return;
               }
           }
       }
       System.out.println("No");


    }

}