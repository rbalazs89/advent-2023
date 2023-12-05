import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Character.isDigit;

public class Main {

    static ArrayList<Long> seedToSoil = new ArrayList<>();
    static ArrayList<Long> soilToFertilizer = new ArrayList<>();
    static ArrayList<Long> fertilizerToWater = new ArrayList<>();
    static ArrayList<Long> waterToLight = new ArrayList<>();
    static ArrayList<Long> lightToTemperature = new ArrayList<>();
    static ArrayList<Long> temperatureToHumidity = new ArrayList<>();
    static ArrayList<Long> humidityToLocation = new ArrayList<>();
    static ArrayList<Long> seeds = new ArrayList<>();

    public static void main(String[] args) throws {

        List<String> myInput = readFile("src/input2.txt");
        processFile();
        //System.out.println(part1(myInput));
        System.out.println(test(myInput));
    }

    public static long part1 (List<String> input){

        for(int i = 0 ; i < seeds.size(); i ++){
            for(int j = 0 ; j < seedToSoil.size(); j = j + 3){
                if(seeds.get(i) < seedToSoil.get(j + 1) + seedToSoil.get(j + 2) && seeds.get(i) > seedToSoil.get(j + 1)){
                    seeds.set(i, (seeds.get(i) - seedToSoil.get(j + 1)) + seedToSoil.get(j));
                }
            }

            // For soilToFertilizer ArrayList
            for (int j = 0; j < soilToFertilizer.size(); j = j + 3) {
                if (seeds.get(i) < soilToFertilizer.get(j + 1) + soilToFertilizer.get(j + 2)
                        && seeds.get(i) > soilToFertilizer.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - soilToFertilizer.get(j + 1)) + soilToFertilizer.get(j));
                }
            }

            // For fertilizerToWater ArrayList
            for (int j = 0; j < fertilizerToWater.size(); j = j + 3) {
                if (seeds.get(i) < fertilizerToWater.get(j + 1) + fertilizerToWater.get(j + 2)
                        && seeds.get(i) > fertilizerToWater.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - fertilizerToWater.get(j + 1)) + fertilizerToWater.get(j));
                }
            }

            // For waterToLight ArrayList
            for (int j = 0; j < waterToLight.size(); j = j + 3) {
                if (seeds.get(i) < waterToLight.get(j + 1) + waterToLight.get(j + 2)
                        && seeds.get(i) > waterToLight.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - waterToLight.get(j + 1)) + waterToLight.get(j));
                }
            }

            // For lightToTemperature ArrayList
            for (int j = 0; j < lightToTemperature.size(); j = j + 3) {
                if (seeds.get(i) < lightToTemperature.get(j + 1) + lightToTemperature.get(j + 2)
                        && seeds.get(i) > lightToTemperature.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - lightToTemperature.get(j + 1)) + lightToTemperature.get(j));
                }
            }

            // For temperatureToHumidity ArrayList
            for (int j = 0; j < temperatureToHumidity.size(); j = j + 3) {
                if (seeds.get(i) < temperatureToHumidity.get(j + 1) + temperatureToHumidity.get(j + 2)
                        && seeds.get(i) > temperatureToHumidity.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - temperatureToHumidity.get(j + 1)) + temperatureToHumidity.get(j));
                }
            }

            // For humidityToLocation ArrayList
            for (int j = 0; j < humidityToLocation.size(); j = j + 3) {
                if (seeds.get(i) < humidityToLocation.get(j + 1) + humidityToLocation.get(j + 2)
                        && seeds.get(i) > humidityToLocation.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - humidityToLocation.get(j + 1)) + humidityToLocation.get(j));
                }
            }
        }

        Collections.sort(seeds);
        return seeds.get(0);
    }

    public static long test (List<String> input){

        //List<Long> seeds = Arrays.asList(new Long[]{79L, 14L, 55L, 13L});
        //List<Long> seeds = Arrays.asList(new Long[]{14L});

        System.out.println(fertilizerToWater);

        for(int i = 0 ; i < seeds.size(); i ++){
            for(int j = 0 ; j < seedToSoil.size(); j = j + 3){
                if(seeds.get(i) < seedToSoil.get(j + 1) + seedToSoil.get(j + 2) && seeds.get(i) >= seedToSoil.get(j + 1)){
                    seeds.set(i, (seeds.get(i) - seedToSoil.get(j + 1)) + seedToSoil.get(j));
                    break;
                }
            }

            // For soilToFertilizer ArrayList
            for (int j = 0; j < soilToFertilizer.size(); j = j + 3) {
                if (seeds.get(i) < soilToFertilizer.get(j + 1) + soilToFertilizer.get(j + 2)
                        && seeds.get(i) >= soilToFertilizer.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - soilToFertilizer.get(j + 1)) + soilToFertilizer.get(j));
                    break;
                }
            }

            // For fertilizerToWater ArrayList
            for (int j = 0; j < fertilizerToWater.size(); j = j + 3) {
                if (seeds.get(i) < fertilizerToWater.get(j + 1) + fertilizerToWater.get(j + 2)
                        && seeds.get(i) >= fertilizerToWater.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - fertilizerToWater.get(j + 1)) + fertilizerToWater.get(j));
                    break;
                }
            }

            // For waterToLight ArrayList
            for (int j = 0; j < waterToLight.size(); j = j + 3) {
                if (seeds.get(i) < waterToLight.get(j + 1) + waterToLight.get(j + 2)
                        && seeds.get(i) >= waterToLight.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - waterToLight.get(j + 1)) + waterToLight.get(j));
                    break;
                }
            }

            // For lightToTemperature ArrayList
            for (int j = 0; j < lightToTemperature.size(); j = j + 3) {
                if (seeds.get(i) < lightToTemperature.get(j + 1) + lightToTemperature.get(j + 2)
                        && seeds.get(i) >= lightToTemperature.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - lightToTemperature.get(j + 1)) + lightToTemperature.get(j));
                    break;
                }
            }

            // For temperatureToHumidity ArrayList
            for (int j = 0; j < temperatureToHumidity.size(); j = j + 3) {
                if (seeds.get(i) < temperatureToHumidity.get(j + 1) + temperatureToHumidity.get(j + 2)
                        && seeds.get(i) >= temperatureToHumidity.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - temperatureToHumidity.get(j + 1)) + temperatureToHumidity.get(j));
                    break;
                }
            }

            // For humidityToLocation ArrayList
            for (int j = 0; j < humidityToLocation.size(); j = j + 3) {
                if (seeds.get(i) < humidityToLocation.get(j + 1) + humidityToLocation.get(j + 2)
                        && seeds.get(i) >= humidityToLocation.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - humidityToLocation.get(j + 1)) + humidityToLocation.get(j));
                    break;
                }
            }
        }

        Collections.sort(seeds);
        return seeds.get(0);
    }

    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }

    public static void processFile(){
        List<String> input = readFile("src/input2.txt");
        ArrayList<Integer> mapping = new ArrayList<>();
        for(int i = 0; i < input.size(); i ++){
            if(Objects.equals(input.get(i), "seed-to-soil map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "soil-to-fertilizer map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "fertilizer-to-water map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "water-to-light map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "light-to-temperature map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "temperature-to-humidity map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "humidity-to-location map:")){
                mapping.add(i);
            }
        }
        mapping.add(input.size());

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);

            if(i == 0){
                for (int j = 0; j < line.length(); j++) {
                    if (Character.isDigit(line.charAt(j))) {
                        int k = j;
                        while (k < line.length() && Character.isDigit(line.charAt(k))) {
                            k++;
                        }
                        seeds.add(Long.valueOf(line.substring(j, k)));
                        j = k;
                    }
                }
            }
            if(mapping.get(0).equals(i)){
                i++;
                for (int l = i; l < mapping.get(1) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            seedToSoil.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(1).equals(i)){
                i++;
                for (int l = i; l < mapping.get(2) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            soilToFertilizer.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(2).equals(i)){
                i++;
                for (int l = i; l < mapping.get(3) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            fertilizerToWater.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(3).equals(i)){
                i++;
                for (int l = i; l < mapping.get(4) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            waterToLight.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(4).equals(i)){
                i++;
                for (int l = i; l < mapping.get(5) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            lightToTemperature.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(5).equals(i)){
                i++;
                for (int l = i; l < mapping.get(6) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            temperatureToHumidity.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(6).equals(i)){
                i++;
                for (int l = i; l < mapping.get(7); l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            humidityToLocation.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
        }
    }
}