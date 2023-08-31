package Day5;

import javafx.application.Preloader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    final static Charset ENCODING = StandardCharsets.UTF_8;
    final static String PATTERN = "(\\d+)\\D+(\\d+)\\D+(\\d+)";

    public static void main(String[] args) {
        Path path = Paths.get("input-day5");
        String result1 = partOne(path);
        String result2 = partTwo(path);
        System.out.println(result1);
        System.out.println(result2);
    }

    public static String partOne(Path path) {
        char[] eachTopStack = new char[9];

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            List<Stack<Character>> shipments = new ArrayList<>();

            for (int i = 0; i < 9; i++)
                shipments.add(new Stack<>());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!addCrates(line, shipments))
                    break;
            }

            scanner.hasNextLine();

            // reverse to get correct order
            for (int i = 0; i < shipments.size(); i++)
                reverseStack(shipments.get(i));

            while (scanner.hasNextLine())
                moveCrates(scanner.nextLine(), shipments);

            for (int i = 0; i < shipments.size(); i++)
                eachTopStack[i] = shipments.get(i).peek();

        } catch (IOException e) {
            System.out.println(e);
        }

        return new String(eachTopStack);
    }

    private static String partTwo(Path path) {
        char[] eachTopStack = new char[9];

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            String line;
            List<Stack<Character>> shipments = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                shipments.add(new Stack<>());
            }

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!addCrates(line, shipments))
                    break;
            }


            scanner.nextLine();
            // reverse to get correct order
            for (int i = 0; i < shipments.size(); i++)
                reverseStack(shipments.get(i));

            while (scanner.hasNextLine())
                upgradeMoveCrates(scanner.nextLine(), shipments);

            for (int i = 0; i < shipments.size(); i++)
                eachTopStack[i] = shipments.get(i).peek();

        } catch (IOException e) {
            System.out.println(e);
        }

        return new String(eachTopStack);
    }

    private static boolean addCrates(String line, List<Stack<Character>> shipments){
        if (line.matches(".*\\d.*"))
            return false;

        int[] position = {1, 5, 9, 13, 17, 21, 25, 29, 33};

        for (int i = 0; i < position.length; i++) {
            char c = line.charAt(position[i]);

            if (c != ' ')
                shipments.get(i).push(c);

        }

        return true;
    }

    private static void upgradeMoveCrates(String instruction, List<Stack<Character>> cargo) {
        String pattern = "(\\d+)\\D+(\\d+)\\D+(\\d+)";
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(instruction);
        Stack<Character> crateStack = new Stack<>();

        if (m.find()) {
            int amount = Integer.parseInt(m.group(1));
            int popStack = Integer.parseInt(m.group(2)) - 1;
            int pushStack = Integer.parseInt(m.group(3)) - 1;

            for (int i = 0; i < amount; i++)
                crateStack.push(cargo.get(popStack).pop());

            while (!crateStack.isEmpty())
                cargo.get(pushStack).push(crateStack.pop());
        }

    }

    private static void moveCrates(String instruction, List<Stack<Character>> cargo) {
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(instruction);

        if (m.find()) {
            int amount = Integer.parseInt(m.group(1));
            int popStack = Integer.parseInt(m.group(2)) - 1;
            int pushStack = Integer.parseInt(m.group(3)) - 1;

            for (int i = 0; i < amount; i++) {
                char move = cargo.get(popStack).pop();
                cargo.get(pushStack).push(move);
            }
        }

    }


    private static <T> void reverseStack(Stack<T> stack){
        Queue<T> temp = new LinkedList<>();

        while (!stack.empty())
            temp.add(stack.pop());

        while (!temp.isEmpty())
            stack.push(temp.remove());
    }
}
