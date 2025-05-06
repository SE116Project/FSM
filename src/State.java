import java.io.Serializable;
import java.util.HashMap;

public class State implements Serializable{
    private String stateName;
    private HashMap<String,String> connectedNodes;
    public State(String stateName) {
        this.stateName = stateName;
        connectedNodes = new HashMap<String,String>();
    }
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    public HashMap getConnectedNodes() {
        return connectedNodes;
    }
    public void setConnectedNodes(HashMap connectedNodes) {
        this.connectedNodes = connectedNodes;
    }
    public void addConnectedNode(String i,String j) {
        if(!connectedNodes.containsKey(j)) {
            connectedNodes.put(i,j);
        }else System.out.println("Node already connected");
    }
    public void removeConnectedNode(String node) {
        if(connectedNodes.containsKey(node)) {
            connectedNodes.remove(node);
        }else System.out.println("Node not connected");
    }
    public void printInfo(){
        for(String i:connectedNodes.keySet()){
            System.out.println(stateName+"("+i+")"+"-->"+connectedNodes.get(i));
        }

    }
    public String processInput(String input) {
        String output = connectedNodes.get(input);
        System.out.println(stateName+"("+input+")"+"-->"+output);
       return output;
    }

}
