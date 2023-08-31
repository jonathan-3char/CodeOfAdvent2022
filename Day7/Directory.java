package Day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Directory {
    private final String name;
    private int size;
    private final Directory parent;
    private ArrayList<File> files;
    private ArrayList<Directory> directories;

    Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        size = 0;
        files = new ArrayList<>();
        directories = new ArrayList<>();
    }

    Directory(String name) {
        this(name, null);
    }

    public void addFile(String name, int size) {
        File file = new File(name, size);

        if (!files.contains(file)) {
            this.size += size;
            files.add(file);

            if (parent != null)
                parent.increaseSize(size);
        }
    }

    public boolean fileExist(String name) {
        return files.stream().anyMatch(o -> o.name.equals(name));
    }

    public boolean directoryExist(String name) {
        return directories.stream().anyMatch(o -> o.name.equals(name));
    }

    public void addDirectory(String name) {
        Directory directory = new Directory(name, this);
        directories.add(directory);
    }

    public ArrayList<Directory> getDirectories() {
        return directories;
    }

    public Directory cd(String name) {
        for (Directory dir : directories) {
            if (name.equals(dir.name))
                return dir;
        }

        return null;
    }

    public int getSize() {
        return size;
    }

    private void increaseSize(int size) {
        this.size += size;

        if (parent != null)
            parent.increaseSize(size);
    }

    public Directory getParent() {
        return parent;
    }


    @Override
    public String toString() {
        return "dir " + name;
    }


    private class File {
        private String name;
        private int size;

        File(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof File))
                return false;

            return name.equals(((File) obj).name);
        }

        @Override
        public String toString() {
            return size + " " + name;
        }
    }

}
