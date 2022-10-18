package JW_LAB1;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        File file;

        do {
            System.out.print("Enter a directory path: ");
            file = new File(scanner.nextLine());
            System.out.println();
        } while (!correctInputDir(file));

        System.out.print("Enter a main letter to match with: ");
        String mainLetter = new String(scanner.nextLine());
        File fileResult = new File("C:\\Users\\artisoul0\\Desktop\\teset\\resultfile.txt");
        long start = System.currentTimeMillis();

        ExecutorService pool = Executors.newCachedThreadPool();
        Task task = new Task(file, pool, mainLetter, fileResult);
        Future<TaskResult> result = pool.submit(task);

        try {
            result.get();
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.printf("Total time: %d ms%n", System.currentTimeMillis() - start);

        pool.shutdown();
    }

    private boolean correctInputDir(File file) {
        if (!file.exists() || !file.isDirectory()) {
            System.out.println("Provided directory does not exist. Try again");
            return false;
        }
        return true;
    }
}