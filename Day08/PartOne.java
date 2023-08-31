package Day8;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.getNumericValue;

public class PartOne {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static int readGrid(String fileName) {
        Path path = Paths.get(fileName);
        int[] highestEachCol = new int[0];
        String line;
        ArrayList<ArrayList<Integer>> unaccountedList = new ArrayList<>();
        int total = 0;
        int height = 0;
        int unaccounted = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            if (scanner.hasNextLine()) {
                height++;
                line = scanner.nextLine();
                highestEachCol = new int[line.length()];

                for (int i = 1; i < line.length() - 1; i++) {
                    ArrayList<Integer> realList = new ArrayList<>();

                    highestEachCol[i] = getNumericValue(line.charAt(i));
                    unaccountedList.add(realList);
                }

            }

            while (scanner.hasNextLine()) {
                height++;
                line = scanner.nextLine();

                for (int i = 1; i < line.length() - 1; i++) {
                    if (isVisible(unaccountedList, highestEachCol, i - 1, getNumericValue(line.charAt(i)), line))
                        total++;
                }

            }
        } catch (IOException e) {
            System.out.println(e);
        }


        for (ArrayList<Integer> list : unaccountedList)
            unaccounted += list.size();

        return total + unaccounted + (2 * height) + highestEachCol.length - 2;
    }

    private static boolean isVisibleUp(int[] highestEachCol, int index, int treeHeight) {
        if (highestEachCol[index] >= treeHeight)
            return false;

        highestEachCol[index] = treeHeight;

        return true;
    }

    private static boolean isVisible(ArrayList<ArrayList<Integer>> unaccountedTrees,
                                     int[] row, int index, int treeHeight, String line) {
        // first check if the tree has an up or horizontal visibility
        // if it does then don't add to unaccountedTrees
        unaccountedTrees.get(index).removeIf(t -> t <= treeHeight);


        if (isVisibleUp(row, index + 1, treeHeight) || isVisibleHorizontal(line, index)) {
            return true;
        }

        unaccountedTrees.get(index).add(treeHeight);
        return false;
    }

    private static int scenicPointsHorizontal(String line, int index) {
        int height = getNumericValue(line.charAt(index + 1));
        int rightPoints = 0;
        int leftPoints = 0;

        for (int i = index + 2; i < line.length(); i++) {
            rightPoints++;
            if (height <= getNumericValue(line.charAt(i)))
                break;
        }

        for (int i = index; i >= 0; i--) {
            leftPoints++;
            if (height <= getNumericValue(line.charAt(i)))
                break;
        }

        return rightPoints * leftPoints;
    }

    private static boolean isVisibleHorizontal(String line, int index) {
        // check is visible from the right
        boolean isVisible = true;
        int height = getNumericValue(line.charAt(index + 1));

        for (int i = index + 2; i < line.length(); i++) {
            if (height <= getNumericValue(line.charAt(i))) {
                isVisible = false;
                break;
            }
        }

        if (isVisible)
            return true;

        // check the left
        for (int i = index; i >= 0; i--) {
            if (height <= getNumericValue(line.charAt(i)))
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String fileName = "input-day8";

        int result = readGrid(fileName);
        System.out.println("The amount of visible trees is " + result);
    }
}
