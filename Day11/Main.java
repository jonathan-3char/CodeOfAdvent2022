package Day11;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static long partOne(int rounds) {
        Path path = Paths.get("input-day11");
        ArrayList<Monkey> monkeys = new ArrayList<>();

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<Long> items = getItems(scanner.nextLine());
                String[] operations = getOperations(scanner.nextLine());
                int divisible = getNumFromString(scanner.nextLine());
                int trueMonkey = getNumFromString(scanner.nextLine());
                int falseMonkey = getNumFromString(scanner.nextLine());
                Monkey monkey = new Monkey(items, operations[0], operations[1], divisible, trueMonkey, falseMonkey);

                monkeys.add(monkey);

                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.isItemsEmpty()) {
                    long item = monkey.inspectItem();
                    item = Math.floorDiv(item, 3);
                    monkeys.get(monkey.whoToThrow(item)).receiveItem(item);
                }
            }
        }



        return calculateMonkeyBusiness(monkeys);
    }

    private static Long partTwo(int rounds) {
        Path path = Paths.get("input-day11");
        ArrayList<Monkey> monkeys = new ArrayList<>();
        Long superModulo = 1L;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<Long> items = getItems(scanner.nextLine());
                String[] operations = getOperations(scanner.nextLine());
                int divisible = getNumFromString(scanner.nextLine());
                int trueMonkey = getNumFromString(scanner.nextLine());
                int falseMonkey = getNumFromString(scanner.nextLine());
                Monkey monkey = new Monkey(items, operations[0], operations[1], divisible, trueMonkey, falseMonkey);

                superModulo *= divisible;
                monkeys.add(monkey);

                if (scanner.hasNextLine()) // skip empty line
                    scanner.nextLine();
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.isItemsEmpty()) {
                    Long item = monkey.inspectItem();
                    item = item % superModulo;
                    monkeys.get(monkey.whoToThrow(item)).receiveItem(item);
                }
            }
        }

        return calculateMonkeyBusiness(monkeys);
    }

    private static int getNumFromString(String line) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);

        if (m.find())
            return Integer.parseInt(m.group());

        return -1;
    }
    private static String[] getOperations(String line) {
        String[] group = line.split(" ");
        return new String[]{group[6], group[7]};
    }

    private static ArrayList<Long> getItems(String line) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        ArrayList<Long> items = new ArrayList<>();

        while (m.find()) {
            items.add(Long.parseLong(m.group()));
        }

        return items;
    }


    private static Long calculateMonkeyBusiness(ArrayList<Monkey> monkeys) {
        Long firstActiveMonkey = 0L, secondActiveMonkey = 0L;

        for (Monkey monkey : monkeys) {
            Long value = monkey.getInspections();
            if (value > secondActiveMonkey) {
                if (value > firstActiveMonkey) {
                    secondActiveMonkey = firstActiveMonkey;
                    firstActiveMonkey = value;
                } else {
                    secondActiveMonkey = value;
                }
            }
        }

        return firstActiveMonkey * secondActiveMonkey;
    }



    public static void main(String[] args) {
        long result = partOne(20);
        System.out.println(result);
        Long result2 = partTwo(10_000);
        System.out.println(result2);
    }


}
