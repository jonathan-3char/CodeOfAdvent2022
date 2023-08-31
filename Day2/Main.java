package Day2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    final static Charset ENCODING = StandardCharsets.UTF_8;
    // distance between A and X is 23
    final static int CHAROFFSET = 23;
    // ASCII of A
    final static int CORRECTCHAROFFSET = 65;

    public static void main(String[] args) throws IOException {
        String fileName = "input-day2";
        int result1 = partOne(fileName);
        int result2 = partTwo(fileName);

        System.out.println(result1);
        System.out.println(result2);
    }

    private static int pointsForMove(char oppMove, char yourMove) {
        int matchResult = (int) oppMove - (int) yourMove + CHAROFFSET;
        int sum = 0;

        switch (yourMove) {
            case 'X'-> sum += 1;
            case 'Y'-> sum += 2;
            case 'Z' -> sum += 3;
        }

        if (matchResult == 0)
            return sum + 3;

        if ((oppMove == 'A' && yourMove == 'Y') || (oppMove == 'B' && yourMove == 'Z') ||
                (oppMove == 'C' && yourMove == 'X'))
            return sum + 6;

        return sum;
    }

    private static int correctPointAllocation(char oppMove, char neededRes) {
        int move = oppMove - CORRECTCHAROFFSET;

        switch (neededRes) {
            case 'X' -> {
                return ((move + 2) % 3) + 1;
            }
            case 'Y' -> {
                return move + 1 + 3;
            }
            case 'Z' -> {
                return ((move + 1) % 3) + 1 + 6;
            }
        }

        return 0;
    }

    static int partOne(String fileName) {
        Path path = Paths.get(fileName);
        int sum = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] moves = line.split(" ");

                sum += pointsForMove(moves[0].charAt(0), moves[1].charAt(0));
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return sum;
    }

    static int partTwo(String file) {
        Path path = Paths.get(file);
        int sum = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] moves = line.split(" ");

                sum += correctPointAllocation(moves[0].charAt(0), moves[1].charAt(0));
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return sum;
    }


}
