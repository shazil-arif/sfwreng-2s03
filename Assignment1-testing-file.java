

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

@FunctionalInterface
interface Test 
{ 
    void runTest() throws Exception; 
}


public class tests{
    static int findPeakPoints = 0;
    static int isSinkPoints = 0;
    static int findLocalSinkPoints = 0;
    static int rotateMapPoints = 0;
    static ArrayList<String> log = new ArrayList<String>();
    static int[][] zeroByZero = {};
    static int[][] oneByOne = {{10}};
    static int[][] twoByTwo = {{1,2}, {3,4}}; // max at bottom right corner [1,1]
    static int[][] threeByThree = {{1,2,3}, {4,5,9}, {7,8,6}}; // regular 3x3 with 6 and 9 swapped. max at side [1,2]
    static int[][] fourByFour = {{1,2,3,4},{5,6,7,8}, {9,16,11,12}, {13,14,15,10}}; // regular 4x4 with 10 and 16 swapped. max is at [2,1]
    static int[][] fiveByFive = {{1,2,3,4,5},{6,7,8,9,10}, {11,12,13,14,15}, {16,17,25, 19, 20}, {21,22,23,24,18}}; // max at [3,2]

    public static void main(String[] args) throws Exception{
        testFindPeak();
        testIsSink();
        // testFindLocalSink();
        testRotateMap();

        writeLog(args);
        writeGrade(args);
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
            printWriter.print(findPeakPoints + ",");
            printWriter.print(isSinkPoints+ ",");
            printWriter.print(findLocalSinkPoints+ ",");
            printWriter.print(rotateMapPoints + "\n");   
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
    
    public static void testFindPeak() throws Exception{
        Test test1 = () -> {
            final int[] res = Assignment1.findPeak(oneByOne);
            if(res.length == 2){
                if(res[0] == 0 && res[1] == 0) findPeakPoints++;
                else{
                    throw new Exception("Received values for cell" + "[" + res[0] + "," + res[1] + "]");
                } 
            }
            else { 
                throw new Exception("Retuned cell length not equal to 2"); 
            }
        };
        Test test2 = () -> {
            final int[] res2 = Assignment1.findPeak(twoByTwo);
            if(res2.length == 2){
                if(res2[0] == 1 && res2[1] == 1) findPeakPoints++;
                else { 
                    throw new Exception("Received values for cell" + "[" + res2[0] + "," + res2[1] + "]"); 
                }
            }
            else { 
                throw new Exception("Returned cell length not equal to 2"); 
            }
        };
        Test test3 = () -> {
            final int[] res3 = Assignment1.findPeak(threeByThree);
            if(res3.length == 2){
                if(res3[0] == 1 && res3[1] == 2) findPeakPoints++;
                else { 
                    throw new Exception("Received values for cell" + "[" + res3[0] + "," + res3[1] + "]"); 
                }
            }
            else { 
                throw new Exception("Returned cell length not equal to 2"); 
            }
        };
        Test test4 = () -> {
            final int[] res4 = Assignment1.findPeak(fourByFour);
            if(res4.length == 2){
                if(res4[0] == 2 && res4[1] == 1) findPeakPoints++;
                else { 
                    throw new Exception("Received values for cell" + "[" + res4[0] + "," + res4[1] + "]"); 
                }
            }
            else { 
                throw new Exception("Returned cell length not equal to 2"); 
            }
        };
        Test test5 = () -> {
            final int[] res5 = Assignment1.findPeak(fiveByFive);
            if(res5.length == 2){
                if(res5[0] == 3 && res5[1] == 2) findPeakPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + res5[0] + "," + res5[1] + "]"); 
                }
            }
            else { 
                throw new Exception("Returned cell length not equal to 2");
            }
            
        };
        runTest(test1, "test1-testFindPeak() with 1x1 matrix. Expecting [0,0]");
        runTest(test2, "test2-testFindPeak() with 2x2 matrix. Expecting [0,0]");
        runTest(test3, "test3-testFindPeak() with 3x3 matrix. Expecting [1,2]");
        runTest(test4, "test4-testFindPeak() with 4x4 matrix. Expecting [2,1]");
        runTest(test5, "test5-testFindPeak() with 5x5 matrix. Expecting [3,2]");
    }
    public static void testIsSink(){
        int[] cell = {0,0};

        Test test1 = () -> {
            boolean res = Assignment1.isSink(zeroByZero, cell); // should be false
            if(!res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            }
        };

        Test test2 = () -> {
            boolean res = Assignment1.isSink(oneByOne, cell); // should be true
            if(res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            }
        };
        

         /**
         * 75,81,99
         * 90,13,13
         * 17,25,19
         */
        int[][] map = {{75,81,99},{90,13,13},{17,25,19}};
        int[] mid = {1,1};
        
        Test test3 = () -> {
            boolean res = Assignment1.isSink(map, mid); // should be true
            if(res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            }
        };
        


       
        Test test4 = () -> {
            /**
            * 75,81,99
            * 90,1,13
            * 17,25,19
            */
            map[1][1] = 1;
            boolean res = Assignment1.isSink(map, mid); // should be true
            if(res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            }
        };

        
        Test test5 = () -> {
                /**
             * 75,81,99
             * 90,14,13 
             * 17,25,19
             */
            map[1][1] = 14;
            boolean res = Assignment1.isSink(map, mid); // should be false
            if(!res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            }
        };
        


        
        int[] side1 = {1,2};
        int[] side2 = {0,1};
        int[] corner1 = {2,0};
        int[] corner2 = {2,2};
        Test test6 = () -> {
            map[2][2] = 1;
            /**
             * 75,81,99
             * 90,14,13
             * 17,25,1
             *
             * side1 is [1,2] which is 13. this is not a sink
             * side2 is [0,1] which is 81. not a sink
             * corner1 is [2,0] which is 17. not a sink
             * corner2 is [2,2] which is 1.  this is a sink
             */
            boolean res = Assignment1.isSink(map, side1); // should be true
            if(!res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            }
        };

        Test test7 = () -> {
            boolean res = Assignment1.isSink(map, side2); // should be false
            if(!res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res);
            }
        };

        Test test8 = () -> {
            boolean res = Assignment1.isSink(map, corner1); // should be false
            if(!res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            }
        };

        Test test9 = () -> {
            boolean res = Assignment1.isSink(map, corner2); // should be true
            if(res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res);
            }
        };

        int[][] allSame = {{1,1,1}, {1,1,1}, {1,1,1}};
        Test test10 = () -> {
            boolean res = Assignment1.isSink(allSame, mid); // should be true
            if(res) isSinkPoints++;
            else { 
                throw new Exception("Received value " + res); 
            } 
        };

        Test[] allTests = {test1, test2, test3, test4, test5, test6, test7, test8, test9, test10};
        String[] descriptions = {
        "test1 - Test isSink() with 0x0 matrix. Expecting false", 
        "test2 - Test isSink() with 1x1 matrix and cell [0,0]. Expecting true", 
        "test3 - Test isSink() with 3x3 matrix with cell [1,1]. Expecting true",
        "test4 - Test isSink() with 3x3 matrix with cell [1,1]. Expecting true",
        "test5 - Test isSink() with 3x3 matrix with cell [1,1]. Expecting false",
        "test6 - Test isSink() with 3x3 matrix with cell [1,2]. Expecting false",
        "test7 - Test isSink() with 3x3 matrix with cell [0,1]. Expecting false",
        "test8 - Test isSink() with 3x3 matrix with cell [2,0]. Expecting false",
        "test9 - Test isSink() with 3x3 matrix with cell [2,2]. Expecting true",
        "test10 - Test isSink() with 3x3 matrix and all same values and with cell [1,1]. Expecting true",
        };
        for(int i = 0; i < allTests.length; i++) runTest(allTests[i], descriptions[i]);
    }

    public static void testFindLocalSink(){
        int[][] oneDimensional = {{1}};

        /**
         * 1  2  3  4  10
         * 16 5  24 7  11
         * 21 19 31 8  12
         * 13 30 17 16 15
         * 9  36 37 38 14
         */
        int[][] fiveByFiveTest = {{1,2,3,4,10}, {16,5,24,7,11}, {21,19,31,8,12}, {13,30,17,16,15}, {9,336,37,38,14}};

        /**
         * 100 90 85 80 70
         * 200 210 220 230 110
         * 150 160 205 157 135
         * 140 190 265 126 185
         * 130 170 249 175 180
         */
        int[][] fiveByFiveTest2 = {{100, 90, 85, 80, 70}, {200,210,220,230,110}, {150,160,205,157,135}, {140,190,265,126,185}, {130, 170, 249, 185, 180}};

        /**
         * 1 2 3
         * 8 9 4
         * 7 6 5
         */
        int[][] threeByThreeTest = {{1,2,3}, {8,9,4}, {7,6,5}};

        Test test1 = () -> {
            int[] cell = {0,0};
            int[] answer = Assignment1.findLocalSink(oneDimensional, cell);
            if(answer.length == 2){
                if(answer[0] == 0 && answer[1] == 0) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test2 = () -> {
            int[] cell = {0,1};
            int[] expected = {0,0};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}

        };

        Test test3 = () -> {
            int[] cell = {4,4};
            int[] expected = {4,4};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test4 = () -> {
            int[] cell = {3,4};
            int[] expected = {0,0};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test5 =  () -> {
            int[] cell = {3,0};
            int[] expected = {4,0};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test6 = () -> {
            int[] cell = {3,2};
            int[] expected = {0,0};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        // the followings tests now use fiveByFiveTest2 matrix

        Test test7 = () -> {
            int[] cell = {0,0};
            int[] expected = {0,4};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest2,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test8 = () -> {
            int[] cell = {3,4};
            int[] expected = {3,3};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest2,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test9 = () -> {
            int[] cell = {3,1};
            int[] expected = {4,0};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest2,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test10 = () -> {
            int[] cell = {1,3};
            int[] expected = {0,4};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest2,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test11 = () -> {
            int[] cell = {4,4};
            int[] expected = {3,3};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest2,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test12 = () -> {
            int[] cell = {1,0};
            int[] expected = {0,4};
            int[] answer = Assignment1.findLocalSink(fiveByFiveTest2,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        // the following tests now use threeByThreeTest matrix

        Test test13 = () -> {
            int[] cell = {2,0};
            int[] expected = {0,0};
            int[] answer = Assignment1.findLocalSink(threeByThreeTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test14 = () -> {
            int[] cell = {1,0};
            int[] expected = {0,0};
            int[] answer = Assignment1.findLocalSink(threeByThreeTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test test15 = () -> {
            int[] cell = {1,2};
            int[] expected = {0,0};
            int[] answer = Assignment1.findLocalSink(threeByThreeTest,cell);
            if(answer.length == 2){
                if(answer[0] == expected[0] && answer[1] == expected[1]) findLocalSinkPoints++;
                else {
                    throw new Exception("Received values for cell" + "[" + answer[0] + "," + answer[1] + "]"); 
                }
            }
            else { throw new Exception("Received cell has size not equal to 2");}
        };

        Test[] allTests = {test1, test2, test3, test4, test5, test6, test7, test8, test9, test10, test11, test12, test13, test14, test15};
        String[] descriptions = {
        "test1 -  Test findLocalSink()  with 1x1 matrix and cell [0,0].  Expecting [0,0]", 
        "test2-   Test findLocalSink()  with 5x5 matrix and cell [0,1].  Expecting [0,0]", 
        "test3 -  Test findLocalSink()  with 5x5 matrix and cell [4,4].  Expecting [4,4]", 
        "test4 -  Test findLocalSink()  with 5x5 matrix and cell [3,4].  Expecting [0,0]", 
        "test5 -  Test findLocalSink()  with 5x5 matrix and cell [3,0].  Expecting [4,0]", 
        "test6 -  Test findLocalSink()  with 5x5 matrix and cell [3,2].  Expecting [0,0]", 
        "test7 -  Test findLocalSink()  with 5x5 matrix with cell [0,0]. Expecting [0,4]",
        "test8 -  Test findLocalSink()  with 5x5 matrix with cell [3,4]. Expecting [3,3]",
        "test9 -  Test findLocalSink()  with 5x5 matrix with cell [3,1]. Expecting [4,0]",
        "test10 - Test findLocalSink()  with 5x5 matrix with cell [1,3]. Expecting [0,4]",
        "test11 - Test findLocalSink()  with 5x5 matrix with cell [4,4]. Expecting [4.4]",
        "test12 - Test findLocalSink()  with 5x5 matrix with cell [1,0]. Expecting [0,4]",
        "test13 - Test findLocalSink()  with 3x3 matrix with cell [2,0]. Expecting [0,0]",
        "test14 - Test findLocalSink()  with 3x3 matrix with cell [1,0]. Expecting [0,0]",
        "test15 - Test findLocalSink()  with 3x3 matrix with cell [1,2]. Expecting [0,0]",
        };
        for(int i = 0; i < allTests.length; i++) runTest(allTests[i], descriptions[i]);
    }

    public static void testRotateMap() throws Exception{
        Test test1 = () -> {
            Assignment1.rotateMap(zeroByZero);
            if(zeroByZero.length == 0) rotateMapPoints++;
            else { 
                throw new Exception("Non zero length map found"); 
            }
        };

        final int original = oneByOne[0][0];
        Test test2 = () -> {
            Assignment1.rotateMap(oneByOne);
            if(oneByOne.length == 1 && oneByOne[0].length == 1 && oneByOne[0][0] == original) rotateMapPoints++;
            else { 
                throw new Exception("Invalid map rotation"); 
            }
        };

        final int[][] rotated = {{3,9,6}, {2,5,8}, {1,4,7}};
        Test test3 = () -> {
            Assignment1.rotateMap(threeByThree);
            if(equals(threeByThree, rotated)) rotateMapPoints++;
            else {
                throw new Exception("Invalid map rotation"); 
            }
        };
   

        final int[][] rotated2 = {{6,8,7}, {9,5,4}, {3,2,1}};
        Test test4 = () -> {
            Assignment1.rotateMap(threeByThree);
            if(equals(threeByThree, rotated2)) rotateMapPoints++;
            else {
                throw new Exception("Invalid map rotation"); 
            }
        };

        final int[][] rotated3 = {{7,4,1}, {8,5,2}, {6,9,3}};
        Test test5 = () -> {
            Assignment1.rotateMap(threeByThree);
            if(equals(threeByThree, rotated3)) rotateMapPoints++;
            else { 
                throw new Exception("Invalid map rotation"); 
            }

        };

       runTest(test1, "test1 - testRotateMap() with 0x0 matrix. Expecting 0x0 matrix");
       runTest(test2, "test2 - testRotateMap() with 1x1 matrix. Expecting 1x1 matrix");
       runTest(test3, "test3 - testRotateMap() with {{1,2,3}, {4,5,9}, {7,8,6}} . Expecting {{3,9,6}, {2,5,8}, {1,4,7}}");
       runTest(test4, "test4 - testRotateMap() with {{3,9,6}, {2,5,8}, {1,4,7}} . Expecting {{6,8,7}, {9,5,4}, {3,2,1}}");
       runTest(test5, "test5 - testRotateMap() with {{6,8,7}, {9,5,4}, {3,2,1}} . Expecting {{7,4,1}, {8,5,2}, {6,9,3}}");

    }
    public static boolean equals(int[][] original, int[][] updated){
        // helper function to check if 2d arrays are equal
        for(int i = 0; i < original.length; i++){
            for(int j = 0; j < original[i].length; j++){
                if(original[i][j] != updated[i][j]) return false;
            }
        }
        return true;
    }
}
