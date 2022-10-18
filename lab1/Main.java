package JW_LAB1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{

        try {
            FileUtils.generateFiles();
        } catch (IOException ex) {
            System.out.printf("Cannot generate files: %s%n", ex.getMessage());
        }

        System.out.printf("%nLab1 started%n%n");

        new Runner().run();

        System.out.printf("%nLab1 finished%n");


    }



}


