package com.company.DB;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Saver {
    private final String txt = "10k_most_common.txt";
    public void saveOurFile() {
        if (!new File(txt).exists()) {
            try {
                String URL = "https://github.com/berandal666/Passwords/blob/master/10k_most_common.txt";
                URL website = new URL(URL);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(txt);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void checkInFile(String newPassword) throws IOException {
            Scanner sc = new Scanner(Paths.get(txt));
            String newLine;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                Pattern pattern = Pattern.compile(".*" + newPassword + ".*");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()){
                    newLine = line.substring(line.indexOf(newPassword));
                    System.out.println(newLine);
                    throw new IOException();
                }
            }

    }
}
