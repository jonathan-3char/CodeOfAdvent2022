package Day6;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static int readSignal(Path path, int index) {

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            Pattern pattern = Pattern.compile(".");
            scanner.useDelimiter("");
            List<Character> buffer = new ArrayList<>();

            for (int i = 0; i < index; i++)
                buffer.add(scanner.next().charAt(0));

            if (isUnique(buffer))
                return index;

            while (scanner.hasNext()) {
                String c = scanner.next();

                index++;
                buffer.remove(0);
                buffer.add(c.charAt(0));

                if (isUnique(buffer))
                    return index;
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return 0;
    }

    private static boolean isUnique(List<Character> buffer) {
        Set<Character> unique = new HashSet<>();

        for (Character c : buffer) {
            if (unique.contains(c))
                return false;
            unique.add(c);
        }

        return true;
    }


    public static void main(String[] args) {
        Path path = Paths.get("input-day6");
        int result1 = readSignal(path, 4);
        int result2 = readSignal(path, 14);

        System.out.println(result1);
        System.out.println(result2);
    }

}
