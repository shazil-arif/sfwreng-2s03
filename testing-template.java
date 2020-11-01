import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

@FunctionalInterface
interface Test
{
    void runTest() throws Exception;
}

public class tests{
    static int points = 0;
    static ArrayList<String> log = new ArrayList<String>();

    public static void main(String[] args) {

    }

    public static void writeGrade(String[] args){
        try {
            FileWriter fileWriter = new FileWriter("C:/Shazil/Software-Engineering/SFWRENG2S03-Grading/log.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("");
            printWriter.println(Arrays.toString(args));
            for(String s : log) printWriter.println(s);
            printWriter.close();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void writeLog(String[] args){
        try {
            FileWriter fileWriter =  new FileWriter("C:/Shazil/Software-Engineering/SFWRENG2S03-Grading/grades.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for(String arg : args) printWriter.print(arg + ",");
            printWriter.print(points);
            printWriter.close();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public static void runTest(Test test, String description){
        try{
            test.runTest();
            log.add("Passed Test. " + description);
        }
        catch(Exception ex){
            log.add("Failed test: " + description + ". Failed with reason " + ex);
        }
    }

    public static void testDropAt(){
        Test test1 = () -> {
            boolean expected = true;
            boolean actual = true;
            if(expected == actual){
                points++;
            }
            else {
                throw new Exception("Error message, maybe add in the expected vs actual value");
            }
        };
        runTest(test1, "A brief description of the test, perhaps describe the input values and the expected output");
    }
}
