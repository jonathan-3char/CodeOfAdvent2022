package Day10;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static int partOne(Path path) {
        HashMap<Integer, Integer> laterTask = new HashMap<>();
        int cycle = 1;
        int X = 1;
        int sum = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] instruction = line.split(" ");

                if (instruction.length == 2) {
                    // add instruction
                    int add = Integer.parseInt(instruction[1]);

                    if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220)
                        sum += (X * cycle);

                    cycle++;

                    if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220)
                        sum += (X * cycle);

                    cycle++;
                    X += add;
                } else {
                    if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220)
                        sum += (X * cycle);

                    cycle++;

                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return sum;
    }

    // Part two:
    /*
    The CRT is 40 wide and 6 high position 0-39
    the X represents the middle pixel of the sprite (the sprite is 3)

    If the sprite is in position such that one of the three pixels
     */

    private static void partTwo(Path path) {
       int cycle = 0;
       int X = 1;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] instructions = line.split(" ");

                if (instructions.length == 2) {
                    int add = Integer.parseInt(instructions[1]);

                    shouldPrint(cycle, X);
                    cycle++;
                    shouldPrint(cycle, X);
                    cycle++;
                    X += add;
                } else {
                    shouldPrint(cycle, X);
                    cycle++;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void shouldPrint(int cycle, int X) {
        if (cycle % 40 >= X - 1 && cycle % 40 <= X + 1)
            System.out.print("#");
        else
            System.out.print(".");

        if (cycle == 39 || cycle == 79 || cycle == 119 || cycle == 159 || cycle == 199 || cycle == 239)
            System.out.println();
    }

    public static void main(String[] args) {
        Path path = Paths.get("input-day10");
        int result = partOne(path);

        System.out.println(result);
        partTwo(path);
    }
}
