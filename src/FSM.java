import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FSM{

    public static void main(String[] args) throws Exception {


        if(args.length == 0) {
            LocalDateTime now = LocalDateTime.now();
            System.out.println("FSM DESIGNER <versionNo>");
            System.out.println(now);
            System.out.println("?");

        }
        else checkForFunctions(args[0]);

        Scanner in = new Scanner(System.in);
        String line;
        while(in.hasNextLine()) {
            line = in.nextLine().trim();
            checkForFunctions(line);

        }

    }


    public static void exitFunction(){}
    public static void loadFunction(String file)throws IOException {
        System.out.println("Loading function");
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            String line;
            while(br.ready()) {
                line = br.readLine();
                checkForFunctions(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void checkForFunctions(String line)throws Exception {
        try{
            line=line.trim();

            if(!line.contains(";")){
                if(!line.contains(",")){
                    throw new InvalidCommandException("Invalid command "+line);
                }
            }
            if(line.contains(";")){

                line=line.substring(0,line.indexOf(";"));
                if(!line.contains(" ")){
                    if(!line.contains(",")){
                        if(!line.contains("EXIT")) {
                            if (!line.contains("CLEAR")) {
                                if (!line.contains("SYMBOLS")) {
                                    if (!line.contains("PRINT")) {
                                        if (!line.contains("STATES")) {
                                            throw new InvalidCommandException("Invalid command : " + line);
                                        }
                                    }
                                }
                            }
                        }}
                }
                if(line.substring(0,3).compareToIgnoreCase("log")==0){
                    logFunction(line.substring(3).trim());
                }
                else if(line.substring(0,4).compareToIgnoreCase("load")==0){

                    loadFunction(line.substring(line.indexOf(" ")).trim());
                }   else if (line.substring(0,4).compareToIgnoreCase("exit")==0) {
                    exitFunction();
                }   else if (line.compareToIgnoreCase("PRINT")==0) {

                    printFunction();
                }
                else if(line.substring(0,5).compareToIgnoreCase("clear")==0){
                    clearFunction();
                }
                else if (line.substring(0,6).compareToIgnoreCase("states")==0) {
                    if(line.compareToIgnoreCase("states")==0){
                        statesFunction();
                    }else
                        statesFunction(line.substring(line.indexOf(" ")).trim());

                }
                else if(line.substring(0,7).compareToIgnoreCase("execute")==0) {
                    executeFunction();
                }
                else if(line.substring(0,7).compareToIgnoreCase("symbols")==0) {
                    if(line.compareToIgnoreCase("symbols")==0){
                        symbolsFunction();
                    }else
                        symbolsFunction(line.substring(line.indexOf(" ")).trim());

                }
                else if (line.substring(0,7).compareToIgnoreCase("compile")==0) {
                    compileFunction();
                }
                else if (line.substring(0,11).compareToIgnoreCase("final-state")==0) {
                    finalStateFunction();
                }
                else if (line.substring(0,11).compareToIgnoreCase("transitions")==0) {
                    transitionsFunction(line.substring(line.indexOf(" ")).trim());
                }
                else if (line.substring(0,13).compareToIgnoreCase("initial-state")==0) {
                    initialStateFunction();
                }else if(line.contains(",")&&line.contains(";"))
                    throw new InvalidCommandException("Invalid command");

            }else{
                if(line.contains("TRANSITIONS")){
                    if(line.substring(0,11).compareToIgnoreCase("TRANSITIONS")==0){
                        transitionsFunction(line.substring(line.indexOf(" ")));
                    }
                }
            }}catch (Exception e) {
            if(e.getClass().equals(StringIndexOutOfBoundsException.class)) {
                throw new InvalidCommandException("Invalid command");
            }
        }
    }
    public static void symbolsFunction(String line){
        System.out.println(line+"belirtçe");
        System.out.println("Symbols function with parameter");
    }
    public static void symbolsFunction(){
        System.out.println("Symbols function");
    }
    public static void statesFunction(String line){
        System.out.println(line);
        System.out.println("States function with parameter");
    }
    public  static void statesFunction(){
        System.out.println("States function");
    }
    public static void initialStateFunction(){
        System.out.println("Initial state function");
    }
    public static void finalStateFunction(){
        System.out.println("Final state function");
    }
    public static void transitionsFunction(String data){
        System.out.println("Transitions function");
    }
    public static void printFunction(){
        System.out.println("Print function");
    }
    public static  void compileFunction(){
        System.out.println("Compile Function");
    }
    public static void clearFunction(){
        System.out.println("Clear Function");
    }
    public static void logFunction(String data){
        System.out.println("Log Function");
    }
    public static  void executeFunction(){
        System.out.println("Execute Function");
    }

}