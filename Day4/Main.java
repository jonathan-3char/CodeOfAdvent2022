package Day4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    final static Charset ENCODING = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        String file = "input-day4";
        int result1 = partOne(file);
        int result2 = partTwo(file);

        System.out.println(result1);
        System.out.println(result2);
    }

    public static int partOne(String file) {
        Path path = Paths.get(file);
        int overlaps = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine())
                overlaps += computeIfTotalOverlap(scanner.nextLine());

        } catch (IOException e) {
            System.out.println(e);
        }

        return overlaps;
    }

    public static int partTwo(String fileName) {
        Path path = Paths.get(fileName);
        int overlaps = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            while (scanner.hasNextLine())
                overlaps += computeIfOverlap(scanner.nextLine());

        } catch (IOException e) {
            System.out.println(e);
        }

        return overlaps;
    }

    private static int computeIfTotalOverlap(String ranges) {
        String pattern = "(\\d+).(\\d+).(\\d+).(\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(ranges);

        if (m.find()) {
            int startPair1 = Integer.parseInt(m.group(1));
            int endPair1 = Integer.parseInt(m.group(2));
            int startPair2 = Integer.parseInt(m.group(3));
            int endPair2 = Integer.parseInt(m.group(4));

            if ((startPair1 >= startPair2 && endPair1 <= endPair2) ||
                    (startPair2 >= startPair1 && endPair2 <= endPair1))
                return 1;
        }

        return 0;
    }

    private static int computeIfOverlap(String ranges) {
        String pattern = "(\\d+).(\\d+).(\\d+).(\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(ranges);

        if (m.find()) {
            int startPair1 = Integer.parseInt(m.group(1));
            int endPair1 = Integer.parseInt(m.group(2));
            int startPair2 = Integer.parseInt(m.group(3));
            int endPair2 = Integer.parseInt(m.group(4));

            if ((startPair1 <= endPair2 && endPair2 <= endPair1) ||
                    (startPair1 <= startPair2 && startPair2 <= endPair1) ||
                    (startPair2 <= endPair1 && endPair1 <= endPair2))
                return 1;
        }

        return 0;
    }
}
