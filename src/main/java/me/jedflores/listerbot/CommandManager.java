package me.jedflores.listerbot;

public class CommandManager {
    private static String COMMANDS[] = {
            "create list",
            "delete list",
            "rename list",
    };

    private static String extracted_command = "";
    private static String arguements = "";
    private static String operator = "";
    private static String input = "";

    public static void setInput(String user_input){
        input = user_input;
    }

    /***
     * Splits command keywords and arguments from input. input is set by the setInput() method
     */
    public static void split(){
        for (String command:COMMANDS) {
            if(input.startsWith(command)){
                extracted_command = command;
                arguements = input.replace(command+" ","");
            }

        }
    }

    /***
     * returns the input set or nothing when no input is set
     * @return input
     */
    public static String getInput(){
        return input;
    }

    /***
     * returns the keyword command from input if input contains a keyword command.
     * @return keyword command
     */
    public static String getCommand(){
        return extracted_command;
    }

    /***
     * returns the argument from input. this is usually the string found right after the keyword command.
     * @return
     */
    public static String getArguments(){
        return arguements;
    }

    /**
     * returns a string array containing all commands.
     * @return
     */
    public static String[] getAllCommands(){
        return COMMANDS;
    }

    public static void main(String[] args) {
        String input = "general list";
        setInput(input);
        split();
        System.out.println(getCommand());
        System.out.println(getArguments());
        System.out.println(getInput());
    }




}
