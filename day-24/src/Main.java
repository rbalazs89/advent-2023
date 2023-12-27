import java.io.IOException;
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

        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {

                double x0 = points.get(i).get(0);
                double y0 = points.get(i).get(1);
                double v1 = points.get(i).get(3);
                double v2 = points.get(i).get(4);

                double X0 = points.get(j).get(0);
                double Y0 = points.get(j).get(1);
                double V1 = points.get(j).get(3);
                double V2 = points.get(j).get(4);
                if((v2*V1 - V2*v1) == 0){
                    continue;
                }

                double X = (Y0-(V2*X0/V1) - y0 + v2*x0/v1) * V1 * v1 / (v2*V1 - V2*v1);
                double Y = (-v2*x0+ v1 * y0 + v2 * X) / v1;

                double direction1V = getDirection(v1,v2);
                double direction1P = getDirectionCoordinates(x0,y0,X,Y);
                double direction2P = getDirection(V1,V2);
                double direction2V = getDirectionCoordinates(X0,Y0,X,Y);

                /*System.out.println(X);
                System.out.println(Y);
                System.out.println(direction1P);
                System.out.println(direction1V);
                System.out.println(direction2P);
                System.out.println(direction2V);*/

                if(testAreaX1<X && testAreaX2>X && testAreaY1<Y && testAreaY2>Y && direction1V == direction1P && direction2P == direction2V){
                    solution ++;
                }

                /*System.out.println(testAreaX1<X);
                System.out.println(testAreaX2>X);
                System.out.println(testAreaY1<Y);
                System.out.println(testAreaY2>Y);
                System.out.println(direction1V == direction1P);
                System.out.println(direction2P == direction2V);*/

            }
        }

        return solution;
    }
    public static double getDirection(double v1, double v2){
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
    public static double getDirectionCoordinates(double Px, double Py, double x, double y){
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