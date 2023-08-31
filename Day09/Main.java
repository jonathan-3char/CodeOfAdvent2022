package Day9;

import javafx.util.Pair;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    private static int partTwo(Path path) {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        ArrayList<Pair<Integer, Integer>> rope = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            rope.add(new Pair<>(0, 0));

        set.add(rope.get(0));

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] instructions = line.split(" ");
                Pair<Direction, Integer> movement = null;

                switch (instructions[0]) {
                    case "U" -> movement = new Pair<>(Direction.UP, Integer.parseInt(instructions[1]));
                    case "D" -> movement = new Pair<>(Direction.DOWN, Integer.parseInt(instructions[1]));
                    case "L" -> movement = new Pair<>(Direction.LEFT, Integer.parseInt(instructions[1]));
                    case "R" -> movement = new Pair<>(Direction.RIGHT, Integer.parseInt(instructions[1]));
                }

                moveLongRope(rope, movement, set);
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return set.size();
    }

    /**
     * Used for debugging
     * @param rope
     */
    private static void printRope(ArrayList<Pair<Integer, Integer>> rope) {
        for (int i = 15; i >= -5; i--) {
            for (int j = -11; j <= 14; j++) {
                if (rope.contains(new Pair<>(j, i)))
                    System.out.print(rope.indexOf(new Pair<>(j, i)));
                else if (i == 0 && j == 0)
                    System.out.print("S");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }
    private static int partOne(Path path) {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        Pair<Integer, Integer> head = new Pair<>(0, 0);
        Pair<Integer, Integer> tail = new Pair<>(0, 0);

        set.add(head);

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] instructions = line.split(" ");
                Pair<Direction, Integer> movement = null;
                Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> result = null;

                switch (instructions[0]) {
                    case "R" -> movement = new Pair<>(Direction.RIGHT, Integer.parseInt(instructions[1]));
                    case "L" -> movement = new Pair<>(Direction.LEFT, Integer.parseInt(instructions[1]));
                    case "U" -> movement = new Pair<>(Direction.UP, Integer.parseInt(instructions[1]));
                    case "D" -> movement = new Pair<>(Direction.DOWN, Integer.parseInt(instructions[1]));
                }

                result = move(head, tail, movement, set);
                head = result.getKey();
                tail = result.getValue();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return set.size();
    }

    private static Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move(Pair<Integer, Integer> head, Pair<Integer,
            Integer> tail, Pair<Direction, Integer> movement, Set<Pair<Integer, Integer>> path) {
        for (int i = 0; i < movement.getValue(); i++) {
            int x = head.getKey();
            int y = head.getValue();

            switch (movement.getKey()) {
                case RIGHT -> x++;
                case LEFT -> x--;
                case DOWN -> y--;
                case UP -> y++;
            }

            head = new Pair<>(x, y);

            boolean touching = isTouching(head, tail);
            int tailX = tail.getKey();
            int tailY = tail.getValue();

            if (!touching && isTwoStepsAway(head, tail)) {

                switch (movement.getKey()) {
                    case RIGHT -> tailX++;
                    case LEFT -> tailX--;
                    case DOWN -> tailY--;
                    case UP -> tailY++;
                }

                tail = new Pair<>(tailX, tailY);
                path.add(tail);
            } else if (!touching && tailX != x && tailY != y) {
                // tail needs to move diagonally
                if ((tailX == x - 1 && tailY == y - 2) || (tailX == x - 2 && tailY == y - 1)) {
                    tailX++;
                    tailY++;
                } else if ((tailX == x + 2 && tailY == y - 1) || (tailX == x + 1 && tailY == y - 2)) {
                    tailX--;
                    tailY++;
                } else if ((tailX == x - 1 && tailY == y + 2) || (tailX == x - 2 && tailY == y + 1)) {
                    tailX++;
                    tailY--;
                } else {
                    tailX--;
                    tailY--;
                }

                tail = new Pair<>(tailX, tailY);
                path.add(tail);
            }
        }
        return new Pair<>(head, tail);
    }

    private static void moveLongRope(ArrayList<Pair<Integer,Integer>> rope, Pair<Direction, Integer> movement,
                                     Set<Pair<Integer, Integer>> set) {
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> result = null;

        for (int i = 0; i < movement.getValue(); i++) {
            for (int r = 0; r < rope.size() - 1; r++) {
                Pair<Integer, Integer> first = rope.get(r);
                Pair<Integer, Integer> second = rope.get(r + 1);
                int x = first.getKey();
                int y = first.getValue();

                if (r == 0) {
                    switch (movement.getKey()) {
                        case RIGHT -> x++;
                        case LEFT -> x--;
                        case DOWN -> y--;
                        case UP -> y++;
                    }

                    first = new Pair<>(x, y);
                    rope.set(r, first);
                }

                boolean touching = isTouching(first, second);
                int tailX = second.getKey();
                int tailY = second.getValue();

                if (!touching && isTwoStepsAway(first, second)) {

                    if (tailX + 2 == x)
                        tailX++;
                    else if (tailX - 2 == x)
                        tailX--;
                    else if (tailY + 2 == y)
                        tailY++;
                    else
                        tailY--;

                    second = new Pair<>(tailX, tailY);
                    rope.set(r + 1, second);

                    if (r + 1 == 9)
                        set.add(second);

                } else if (!touching && tailX != x && tailY != y) {
                    // tail needs to move diagonally
                    if ((tailX == x - 1 && tailY == y - 2) || (tailX == x - 2 && tailY == y - 1) ||
                            (tailX == x - 2 && tailY == y - 2)) {
                        tailX++;
                        tailY++;
                    } else if ((tailX == x + 2 && tailY == y - 1) || (tailX == x + 1 && tailY == y - 2) ||
                            (tailX == x + 2 && tailY == y - 2)) {
                        tailX--;
                        tailY++;
                    } else if ((tailX == x - 1 && tailY == y + 2) || (tailX == x - 2 && tailY == y + 1) ||
                            (tailX == x - 2 && tailY == y + 2)) {
                        tailX++;
                        tailY--;
                    } else {
                        tailX--;
                        tailY--;
                    }

                    second = new Pair<>(tailX, tailY);
                    rope.set(r + 1, second);

                    if (r + 1 == 9)
                        set.add(second);
                }
            }
        }

    }
    private static boolean isTwoStepsAway(Pair<Integer, Integer> head, Pair<Integer, Integer> tail) {
        int headX = head.getKey();
        int headY = head.getValue();
        int tailX = tail.getKey();
        int tailY = tail.getValue();


        return (tailY == headY && (tailX == headX - 2 || tailX == headX + 2)) ||
                (tailX == headX && (tailY == headY - 2 || tailY == headY + 2));
    }

    private static boolean isTouching(Pair<Integer, Integer> head, Pair<Integer, Integer> tail) {
        int headX = head.getKey();
        int headY = head.getValue();
        int tailX = tail.getKey();
        int tailY = tail.getValue();

        return head.equals(tail) || (headX == tailX && (headY == tailY + 1 || headY == tailY - 1)) ||
                (headY == tailY && (headX == tailX + 1 || headX == tailX - 1)) ||
                (headX == tailX - 1 && (headY == tailY + 1 || headY == tailY - 1)) ||
                (headX == tailX + 1 && (headY == tailY + 1 || headY == tailY - 1));
    }

    public static void main(String[] args)  {
        Path path = Paths.get("input-day9");
        int result1 = partOne(path);
        int result2 = partTwo(path);

        System.out.println(result1);
        System.out.println(result2);
    }

}
