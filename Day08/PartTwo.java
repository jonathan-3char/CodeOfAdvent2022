package Day8;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.getNumericValue;

public class PartTwo {

    private final static Charset ENCODING = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        String fileName = "input-day8";
        int result = readGrid(fileName);

        System.out.println("Highest scenic score is: " + result);

    }

    private static int readGrid(String fileName) {
        Path path = Paths.get(fileName);
        ArrayList<String> grid = new ArrayList<>();
        int highestScenicScore = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine())
                grid.add(scanner.nextLine());
        } catch (IOException e) {
            System.out.println(e);
        }

        for (int i = 0; i < grid.size(); i++) {
            String row = grid.get(i);

            for (int j = 0; j < row.length(); j++) {
                int scenicScore = calculateHorizontalScenicScore(row, j);

                if (scenicScore != 0) {
                    scenicScore *= calculateVerticalScore(grid, j, i);
                    highestScenicScore = Math.max(highestScenicScore, scenicScore);
                }
            }
        }

        return highestScenicScore;
    }

    private static int calculateVerticalScore(ArrayList<String> grid, int col, int row) {
        int upScore = 0, downScore = 0;
        int fixedTree = getNumericValue(grid.get(row).charAt(col));

        for (int i = row - 1; i >= 0; i--) {
            int upTree = getNumericValue(grid.get(i).charAt(col));
            upScore++;

            if (upTree >= fixedTree)
                break;
        }

        if (upScore == 0)
            return 0;

        for (int i = row + 1; i < grid.size(); i++) {
            int downTree = getNumericValue(grid.get(i).charAt(col));
            downScore++;

            if (downTree >= fixedTree)
                break;
        }

        return upScore * downScore;
    }

    private static int calculateHorizontalScenicScore(String row, int index) {
        int rightScore = 0, leftScore = 0;
        int fixedTree = getNumericValue(row.charAt(index));

        for (int i = index + 1; i < row.length(); i++) {
            int rightTree = getNumericValue(row.charAt(i));
            rightScore++;

            if (rightTree >= fixedTree)
                break;
        }

        if (rightScore == 0)
            return 0;

        for (int i = index - 1; i >= 0; i--) {
            int leftTree = getNumericValue(row.charAt(i));
            leftScore++;

            if (leftTree >= fixedTree)
                break;
        }

        return rightScore * leftScore;
    }
}
