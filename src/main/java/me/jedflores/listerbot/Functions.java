
package me.jedflores.listerbot;

public class Functions {
    
    public static String getVariables(String command){
        String trimmed = command.substring(command.indexOf(' ')+1);
        return trimmed;
        
    }
    
    public static void main(String[] args) {
        System.out.println(getVariables("!Hello World YES!"));
    }
}
