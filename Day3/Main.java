package Day3;

import javafx.application.Preloader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    /**
     * each line represents two compartments, the first half and the other half
     * find the item that shows up in both compartments
     * these items have a priority a-z = 1-26 and A-Z = 27-52
     * sum the priority of those items
     */

    final static Charset ENCODING = StandardCharsets.UTF_8;
    final static int LOWERCASEVALUE = 96;
    final static int UPPERCASEVALUE = 38;

    public static void main(String[] args) {
        int result1 = partOne("input-day3");
        int result2 = partTwo("input-day3");
        System.out.println(result1);
        System.out.println(result2);
    }

    public static int partOne(String fileName) {
        Path path = Paths.get(fileName);
        int sum = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sum += findRepeat(line, line.length());
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return sum;
    }

    static int partTwo(String fileName) {
        Path path = Paths.get(fileName);
        int sum = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String[] group = {scanner.nextLine(), scanner.nextLine(), scanner.nextLine()};
                sum += findBadge(group);
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return sum;
    }

    private static int findBadge(String[] group) {
        Map<Character, Integer> map = new HashMap<>();
        int priority = 0;

        for (int i = 0; i < group[0].length(); i++)
            map.putIfAbsent(group[0].charAt(i), 1);

        for (int i = 0; i < group[1].length(); i++)
            map.replace(group[1].charAt(i), 2);

        for (int i = 0; i < group[2].length(); i++)
            if (map.getOrDefault(group[2].charAt(i), 0) == 2) {
                char c = group[2].charAt(i);

                if (c <= 'Z')
                    priority = c - UPPERCASEVALUE;
                else
                    priority = c - LOWERCASEVALUE;

                return priority;
            }

        return priority;
    }

    private static int findRepeat(String containers, int size) {
       int half = (int) Math.floor(size / 2);
       Set<Character> container1 = new HashSet<>();
       int priority = 0;

        for (int i = 0; i < half; i++)
            container1.add(containers.charAt(i));

        for (int i = half; i < size; i++) {
            char c = containers.charAt(i);

            if (container1.contains(c)) {
                if (c <= 'Z')
                    priority = c - UPPERCASEVALUE;
                else
                    priority = c - LOWERCASEVALUE;
                break;
            }
        }

        return priority;
    }
}
