
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

@FunctionalInterface
interface Test
{
    void runTest() throws Exception;
}


public class tests{
    public static void main(String[] args) {
        testDropAt();
    }


    public static void testDropAt(){
        Grid g = new Grid(4);

        // dropAt(8,1)
        Test test1 = () -> {
            g.dropAt(8,1);
            g.print();
        };

        runTest(test1, "");

       // dropAt(9, 3)
       Test test2 = () -> {
           g.dropAt(9,3);
           g.print();
       };

       runTest(test2, "");


       // dropAt(6,2)
       Test test3 = () -> {
           g.dropAt(6,2);
           g.print();
       };

       runTest(test3, "");


       // dropAt(1,1)
       Test test4 = () -> {
           g.dropAt(1,1);
           g.print();
       };

       runTest(test4, "");


       g.dropAt(2,1);
       g.dropAt(3,1);

       /**
        * Grid now looks like:
        * 0 3 0 0
        * 0 2 0 0
        * 0 1 0 0
        * 0 8 6 9
        */
       // test 1x1       
       Grid oneByOne = new Grid(1);
       Test test5 = () -> {
            oneByOne.dropAt(5,0);
            oneByOne.print();

       };

       runTest(test5, "");


       Test test6 = () -> {
           oneByOne.dropAt(9,2);
           oneByOne.print();
       };

       runTest(test6, "");


       Test test7 = () -> {
           g.dropAt(1,1); // this should drop at column 2 since column 1 is full
           g.print();
            /**
            * Grid now looks like:
            * 0 3 0 0
            * 0 2 0 0
            * 0 1 1 0
            * 0 8 6 9
            */
       };

       runTest(test7, "");


       Test test8 = () -> {
           g.dropAt(2,1); // this should drop at column 2 since column 1 is full
           g.print();
            /**
            * Grid now looks like:
            * 0 3 0 0
            * 0 2 2 0
            * 0 1 1 0
            * 0 8 6 9
            */
       };

       runTest(test8, "");


       Test test9 = () -> {
           g.dropAt(3,1); // this should drop at column 2 since column 1 is full
           g.print();

            /**
            * Grid now looks like:
            * 0 3 3 0
            * 0 2 2 0
            * 0 1 1 0
            * 0 8 6 9
            */
       };

       runTest(test9, "");
    }
}
