/*
 * MIT License
 *
 * Copyright (c) 2018 Thisseas Xanthopoulos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.thisseasx.file_renamer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
class FileRenamer {

    private static final File root = new File(System.getProperty("user.home") + "\\Desktop\\FileRenamerFiles");
    private static final File entityDir = new File(root, "Entity");
    private static final File mapDir = new File(root, "Map");

    private File dir;
    private final String extension;
    private final String[] beforeNames;
    private final String[] afterNames;
    private boolean afterRename;

    FileRenamer(String... args) throws Exception {
        validateArgs(args);

        this.extension = args[0];
        this.beforeNames = new String[args.length - 1];
        this.afterNames = new String[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            String[] temp = args[i].split("=");
            beforeNames[i - 1] = temp[0];
            afterNames[i - 1] = temp[1];
        }
    }

    private void validateArgs(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Please specify file extension.");
        }

        if (!args[0].startsWith(".") || args[0].length() < 2) {
            throw new Exception("The first argument must be the file extension, starting with a period '.' e.g. .java.");
        }

        if (args.length < 2) {
            throw new Exception("Please specify class names to be generated.");
        }
    }

    void execute() throws Exception {
        processDir(entityDir);
        processDir(mapDir);

        readDir(entityDir);
        readDir(mapDir);
    }

    private void processDir(File dir) throws Exception {
        changeDir(dir);
        renameDir();
        replaceDir();
    }

    private File[] findFiles() throws Exception {
        String fail = "Some files were not found:\n";
        StringBuilder errors = new StringBuilder(fail);

        File[] foundFiles = validateFileNames(errors);

        if (errors.length() > fail.length()) {
            throw new FileNotFoundException(errors.toString());
        }

        return foundFiles;
    }

    private File[] validateFileNames(StringBuilder errors) {
        File parent = dir;
        String[] childNames = afterRename
                ? afterNames
                : beforeNames;

        File[] foundFiles = new File[childNames.length];

        String suffix = "";

        for (int i = 0; i < childNames.length; i++) {
            if (parent.getName().equals("Map"))
                suffix = "Map";

            String foundName = childNames[i] + suffix + extension;
            File found = new File(parent, foundName);

            if (found.exists())
                foundFiles[i] = found;
            else
                errors.append(childNames[i]).append("\n");
        }

        return foundFiles;
    }

    private void renameDir() throws Exception {
        File[] filesForProcessing = findFiles();

        for (int i = 0; i < filesForProcessing.length; i++) {
            renameFile(filesForProcessing[i], i);
        }

        afterRename = true;
    }

    private void replaceDir() throws Exception {
        File[] filesForProcessing = findFiles();

        for (int i = 0; i < filesForProcessing.length; i++) {
            replaceFile(filesForProcessing[i], i);
        }

        afterRename = false;
    }

    private void renameFile(File file, int index) {
        File parent = file.getParentFile();

        String beforeName = beforeNames[index];
        String afterName = afterNames[index];

        String oldName = file.getName();
        String oldNameWithoutExtension = oldName.substring(0, oldName.indexOf("."));

        String newName = oldNameWithoutExtension.replaceAll(beforeName, afterName) + extension;

        File renamed = new File(parent, newName);
        file.renameTo(renamed);
    }

    private void replaceFile(File file, int index) {
        String beforeName = beforeNames[index];
        String afterName = afterNames[index];

        try {
            Path filePath = Paths.get(file.toURI());
            List<String> replacement = Files.lines(filePath)
                    .map(x -> x.replaceAll(beforeName, afterName))
                    .collect(Collectors.toList());

            Files.write(filePath, replacement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDir(File dir) {
        Arrays.stream(dir.listFiles()).forEach(this::readFile);
    }

    private void readFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeDir(File dir) {
        this.dir = dir;
    }
}
