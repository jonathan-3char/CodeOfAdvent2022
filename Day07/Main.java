package Day7;

import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private final static Integer TOTAL_SPACE = 70000000;
    private final static Integer REQUIRED_SPACE = 30000000;

    public static int partOne(Path path) {
        int size = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            Directory root = new Directory("/");
            Directory curDir = root;
            String prev = null;

            while (scanner.hasNextLine() || prev != null) {
                String line;

                if (prev != null) {
                    line = prev;
                    prev = null;
                } else
                    line = scanner.nextLine();

                String[] instructions = line.split(" ");

                if (instructions.length == 2)  // command instruction
                    prev = handleLS(line, curDir, scanner);
                else
                    curDir = handleCommands(instructions[2], curDir, root);
            }

            return checkSize(root);
        } catch (IOException e) {
            System.out.println(e);
        }

      return -1;
    }
    private static int partTwo(Path path) {
        int size = 0;

        try (Scanner scanner = new Scanner(path, ENCODING)) {
            Directory root = new Directory("/");
            Directory curDir = root;
            String prev = null;

            while (scanner.hasNextLine() || prev != null) {
                String line;

                if (prev != null) {
                    line = prev;
                    prev = null;
                } else
                    line = scanner.nextLine();

                String[] instructions = line.split(" ");

                if (instructions.length == 2)  // command instruction
                    prev = handleLS(line, curDir, scanner);
                else
                    curDir = handleCommands(instructions[2], curDir, root);

            }

            // creating all the directories and files is finished
            ArrayList<Integer> candidates = new ArrayList<>();
            int unusedSpace = TOTAL_SPACE - root.getSize();
            checkSizeCandidate(root, candidates, unusedSpace);

            return Collections.min(candidates);
        } catch (IOException e) {
            System.out.println(e);
        }


        return -1;
    }

    private static void checkSizeCandidate(Directory cur, ArrayList<Integer> candidates, int unusedSpace) {
        ArrayList<Directory> loop = cur.getDirectories();

        if (cur.getSize() >= REQUIRED_SPACE - unusedSpace)
            candidates.add(cur.getSize());

        for (Directory dir : loop)
            checkSizeCandidate(dir, candidates, unusedSpace);
    }

    private static int checkSize(Directory cur) {
        ArrayList<Directory> loop = cur.getDirectories();
        int result = 0;

        if (cur.getSize() <= 100000) {
            result = cur.getSize();
        }

        for (Directory dir : loop)
            result += checkSize(dir);

        return result;
    }


    private static Directory handleCommands(String instruction, Directory curDir, Directory root) {
        if (instruction.equals("/"))
            return root;
        else if (instruction.equals(".."))
            return curDir.getParent();
        else
            return curDir.cd(instruction);
    }


    private static String handleLS(String line, Directory curDir, Scanner scanner) {
        // ls command
        while (scanner.hasNextLine()) {
            String output = scanner.nextLine();

            if (output.startsWith("$"))
                return output;

            if (output.startsWith("dir") && !curDir.directoryExist(output.split(" ")[1])) {
                curDir.addDirectory(output.split(" ")[1]);
            } else if (!curDir.fileExist(output.split(" ")[1])){
                String[] file = output.split(" ");
                curDir.addFile(file[1], Integer.parseInt(file[0]));
            }
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("input-day7");
        int result1 = partOne(path);
        int result2 = partTwo(path);

        System.out.println(result1);
        System.out.println(result2);
    }
}
