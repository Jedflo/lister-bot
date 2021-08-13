
package me.jedflores.listerbot;

public class Functions {


    /**
     * Removes the first word from an input String.
     *
     *
     * @param command
     * @return trimmed command
     */
    public static String getVariables(String command){
        String trimmed = command.substring(command.indexOf(' ')+1);
        return trimmed;
        
    }

    //test
    public static void main(String[] args) {
        System.out.println(getVariables("!Hello World Test!"));
    }
}
