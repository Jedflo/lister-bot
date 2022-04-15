
package me.jedflores.listerbot;

public class StringHandling {


    /**
     * Removes the first word from an input String.
     *
     *
     * @param toBeTrimmed string to be trimmed
     * @return trimmed string
     */
    public static String trim(String toBeTrimmed){
        String trimmed = toBeTrimmed.substring(toBeTrimmed.indexOf(' ')+1);
        return trimmed;
        
    }

    public static String getCommand(String command, String input){
        String extracted_command = "";
        if(input.contains(command) && input.indexOf(command)==0){

        }
        return extracted_command;
    }

    /**
     * trimms multiple words off of an input string.
     * @param toBeTrimmed string to be trimmed
     * @param noOfWords no of words to trim
     * @param delimiter basis for trimming sting
     * @return trimmed string
     */
    public static String trim(String toBeTrimmed, int noOfWords, char delimiter){
        for (int c=0;c<noOfWords;c++) {
            toBeTrimmed = toBeTrimmed.substring(toBeTrimmed.indexOf(delimiter) + 1);
        }
        return toBeTrimmed;
    }

    /**
     * turns string into an array. each element in the array is determined by the delimiter
     * @param string string to be converted into array
     * @param delimiter delimiter to determine when an element ends
     * @return Arrayed string
     */
    public static String[] stringToArray(String string, String delimiter){
        delimiter= new StringBuilder().append('\\').append(delimiter).toString();
        String[] out = string.split(delimiter);

        return out;

    }

    /**
     * turns string into an array. delimiter is '|'
     * @param string string to be converted into array
     * @return Arrayed string
     */
    public static String[] stringToArray(String string){
        String[] out = string.split("\\|");

        return out;

    }




    //test
    public static void main(String[] args) {
/*        String test = "!Hello World Test!";
        String test2 = "!todo code discord bot|improve lister|15/08/2021|";

        System.out.println(trim(test));
        System.out.println(trim(test,5,' '));

        System.out.println("Test 2");
        String [] print = stringToArray(trim(test2));

        for (String a:
             print) {
            System.out.println(a);
        }*/

        String A = "wata";
        System.out.println(A.length());
    }
}
