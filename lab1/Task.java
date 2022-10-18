package JW_LAB1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Task implements Callable<TaskResult> {
    private final File file;
    private final ExecutorService pool;

    private final String mainLetter;
    private final File fileResult;

    public Task(File file, ExecutorService pool, String mainLetter, File fileResult) {
        this.file = file;
        this.pool = pool;
        this.mainLetter = mainLetter;
        this.fileResult = fileResult;
    }

    @Override
    public TaskResult call() {

        if (!file.isDirectory()) {
            int matchedWordsCount = FileUtils.findAndMarkMatch(file, mainLetter);
            System.out.printf("File %s : matched %d words%n", file.getAbsolutePath(), matchedWordsCount);
            writeText(fileResult, "File " + file.getAbsolutePath() + " matched words: " +  matchedWordsCount);
            return new TaskResult(matchedWordsCount);
        }

        List<Future<TaskResult>> dirResults = new ArrayList<>();

        for (File file : file.listFiles()) {
            if (file.isDirectory() || (!file.isDirectory() && isTxtFile(file)) && !file.getName().equals("resultfile.txt")) {
                Future<TaskResult> result = pool.submit(new Task(file, pool, mainLetter, fileResult));
                dirResults.add(result);
            }
        }

        TaskResult result = collectResults(dirResults);
        System.out.printf("Directory %s : matched %d words in total%n", file.getAbsolutePath(), result.getMatchedWords());
        writeText(fileResult, "Directory " + file.getAbsolutePath() + " matched words in total: " +  result.getMatchedWords());
        System.out.println(result.getMatchedWords());
        return result;
    }

    private boolean isTxtFile(File file) {
        String[] fileParts = file.toPath().toString().split("\\.");
        return fileParts[fileParts.length - 1].equals("txt");
    }

    private static void writeText(File file, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
            writer.write(text + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TaskResult collectResults(List<Future<TaskResult>> dirResults) {
        TaskResult collected = new TaskResult();
        dirResults.forEach(labResultFuture -> {
            try {
                collected.addMatchedWords(labResultFuture.get().getMatchedWords());
            } catch (InterruptedException | ExecutionException ex) {
                System.out.printf("Error while collecting results: %s%n", ex.getMessage());
                throw new RuntimeException(ex);
            }
        });
        return collected;
    }
}