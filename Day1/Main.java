package Day1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    final static Charset ENCODING = StandardCharsets.UTF_8;

    public static void main(String args[]) {
        System.out.println(partOne("input-day1"));
        System.out.println(partTwo("input-day1"));
    }

    public static int partOne(String file)  {
        int mostCalories = 0;
        Path path = Paths.get(file);

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            int calories = 0;

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (line.isEmpty()) {
                    calories = 0;
                } else {
                    calories += Integer.parseInt(line);
                    mostCalories = Math.max(mostCalories, calories);
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return mostCalories;
    }

    public static int partTwo(String file) {
        int total;
        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        Path path = Paths.get(file);

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            int calories = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isEmpty()) {
                    queue.add(calories);
                    calories = 0;
                } else {
                    calories += Integer.parseInt(line);
                }
            }

            queue.add(calories);

        } catch (IOException e) {
            System.out.println(e);
        }

        total = queue.remove() + queue.remove() + queue.remove();

        return total;
    }
}
