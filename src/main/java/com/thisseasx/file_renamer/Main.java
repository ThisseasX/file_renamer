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

import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String... args) throws Exception {
        if (!validateArgs(args)) return;

        try {
            FileRenamer fr = new FileRenamer(args);
            fr.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Pass 'help' argument for help");
        }
    }

    private static boolean validateArgs(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
            printHelp();
            return false;
        }
        return true;
    }

    private static void printHelp() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("help.properties")) {
            Properties props = new Properties();
            props.load(is);
            System.out.println(props.getProperty("help"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
