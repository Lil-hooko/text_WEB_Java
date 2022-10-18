package JW_LAB1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileUtils {
    public static void generateFiles() throws IOException{
        File file1 = new File("C:\\Users\\artisoul0\\Desktop\\teset\\file1.txt");
        forceCreate(file1);
        writeText(file1, """
                The United States media landscape has been characterised by the centrality of large-scale cultural industries since the development of the penny press in the 1830s. For a while in the nineteenth century big urban newspapers were the largest manufacturing companies in the country. This trend continued with the rise of Hollywood, commercial broadcasting and associated industries like music recording and advertising. \s""");


        File file2 = new File("C:\\Users\\artisoul0\\Desktop\\teset\\file2.txt");
        forceCreate(file2);
        writeText(file2, """
                For a number of decades in the mid-twentieth century a fairly stable equilibrium existed in the media system, with strong stable markets that made the dominant media companies highly profitable and very influential as social institutions. Newspapers, broadcast companies and magazines all invested heavily in newsrooms and the profession of journalism grew in number, autonomy and influence. Journalism was characterised by a low level of "political parallelism," with the "objectivity norm" dominating journalistic ethics and most news organisations avoiding identification with particular political parties or tendencies.
                """);


        File resultFile = new File("C:\\Users\\artisoul0\\Desktop\\teset\\resultfile.txt");
        forceCreate(resultFile);
        writeText(resultFile, """
                
                """);

    }
    private static void forceCreate(File file) throws IOException {
        if (file.exists()) {
            Files.delete(file.toPath());
        }
        Files.createFile(file.toPath());
    }

    private static void writeText(File file, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int findAndMarkMatch(File file, String mainLetter) {
        int count = 0;
        int deletedWords = 0;
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))
        ) {
            StringBuilder result = new StringBuilder();
            String[] words = null;
            String line;
            while ((line = reader.readLine()) != null) {

                words = line.split(" ");
                for (int j = 0; j < words.length; j++) {
                    if (mainLetter.equalsIgnoreCase(getFirstLetterWord(words[j]))) {
                        count++;
                    }
                }

                result.append(count);
            }

            writer.write(result.toString());
        } catch (IOException ex) {
            throw new RuntimeException("Error while matching words from %s : %s".formatted(file.getAbsolutePath(), ex.getMessage()));
        }
        return count;
    }

    public static String getFirstLetterWord(String word) {
        String[] letters = word.split("");
        return letters[0];
    }
}
