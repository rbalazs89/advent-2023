import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    static long testAreaX1 = 200000000000000L;
    static long testAreaX2 = 400000000000000L;
    static long testAreaY1 = 200000000000000L;
    static long testAreaY2 = 400000000000000L;

    /*static long testAreaX1 = 7L;
    static long testAreaX2 = 27L;
    static long testAreaY1 = 7L;
    static long testAreaY2 = 27L;*/


    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
    }

    public static int part1(List<String> input){
        ArrayList<ArrayList<Long>> points = new ArrayList<>();
        Pattern pattern = Pattern.compile("-?\\d+");

        for (int i = 0; i < input.size(); i++) {
            Matcher matcher = pattern.matcher(input.get(i));
            ArrayList<Long> point = new ArrayList<>();
            while (matcher.find()) {
                long foundInt = Long.parseLong(matcher.group());
                point.add(foundInt);
            }
            points.add(point);
        }
        int solution = 0;
        int counter = 0;


        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {

                long x0 = points.get(i).get(0);
                long y0 = points.get(i).get(1);
                long v1 = points.get(i).get(3);
                long v2 = points.get(i).get(4);

                long X0 = points.get(j).get(0);
                long Y0 = points.get(j).get(1);
                long V1 = points.get(j).get(3);
                long V2 = points.get(j).get(4);
                if((v2*V1 - V2*v1) == 0){
                    System.out.println("parallellfound");
                    break;
                }


                long X = (Y0-(V2*X0/V1) - y0 + v2*x0/v1) * V1 * v1 / (v2*V1 - V2*v1);
                long Y = (-v2*x0+ v1 * y0 + v2 * X) / v1;
                int direction1V = getDirection((int)v1,(int)v2);
                int direction1P = getDirectionCoordinates((int)x0,(int)y0,(int)X,(int)Y);
                int direction2P = getDirection((int)V1,(int)V2);;
                int direction2V = getDirectionCoordinates((int)X0,(int)Y0,(int)X,(int)Y);

                System.out.println(X);
                System.out.println(Y);

                /*System.out.println(X);
                System.out.println(Y);
                System.out.println(direction1P);
                System.out.println(direction1V);
                System.out.println(direction2P);
                System.out.println(direction2V);*/

                if(testAreaX1<X && testAreaX2>X && testAreaY1<Y && testAreaY2>Y && direction1V == direction1P && direction2P == direction2V){
                    solution ++;
                    System.out.println(counter);
                }

                System.out.println(testAreaX1<X);
                System.out.println(testAreaX2>X);
                System.out.println(testAreaY1<Y);
                System.out.println(testAreaY2>Y);
                System.out.println(direction1V == direction1P);
                System.out.println(direction2P == direction2V);

                counter++;
            }
        }

        return solution;
    }
    public static int getDirection(int v1, int v2){
        if(v1 == 0){
            if(v2 > 0){
                return 0;
            } else if(v2 < 0 ){
                return 4;
            }
        }
        if (v2 == 0){
            if (v1 < 0){
                return 2;
            }else if (v1 > 0) {
                return 6;
            }
        }
        if(v1 > 0){
            if(v2 > 0){
                return 1;
            }if(v2 < 0 ){
                return 3;
            }
        }
        if(v1 < 0){
            if(v2 > 0){
                return 7;
            }
            if (v2 < 0){
                return 5;
            }
        }
        return 0;
    }
    public static int getDirectionCoordinates(int Px, int Py, int x, int y){
        return getDirection(x-Px, y-Py);
    }

    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e){
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}