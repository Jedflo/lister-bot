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

    public static void split(){
        for (String command:COMMANDS) {
            if(input.startsWith(command)){
                extracted_command = command;
                arguements = input.replace(command+" ","");
            }

        }
    }

    public static String getInput(){
        return input;
    }

    public static String getCommand(){
        return extracted_command;
    }

    public static String getArguements(){
        return arguements;
    }

    public static String[] getAllCommands(){
        return COMMANDS;
    }

    public static void main(String[] args) {
        String input = "general list";
        setInput(input);
        split();
        System.out.println(getCommand());
        System.out.println(getArguements());
        System.out.println(getInput());
    }




}
