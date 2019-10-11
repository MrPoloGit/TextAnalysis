import oracle.jrockit.jfr.StringConstantPool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class TextLib {

    // Takes the filename and pulls up the file and reads it as a String
    public static String readFileAsString(String filename) {
        Scanner scanner;
        StringBuilder output = new StringBuilder();

        try {
            scanner = new Scanner(new FileInputStream(filename), "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                output.append(line.trim()+"\n");
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return output.toString();
    }

    // Splits the whole block string into sentances
    public static ArrayList<String> splitIntoSentences(String text) {
        ArrayList<String> output = new ArrayList<>();

        Locale locale = Locale.US;
        BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
        breakIterator.setText(text);

        int prevIndex = 0;
        int boundaryIndex = breakIterator.first();
        while(boundaryIndex != BreakIterator.DONE) {
            String sentence = text.substring(prevIndex, boundaryIndex).trim();
            if (sentence.length()>0)
                output.add(sentence);
            prevIndex = boundaryIndex;
            boundaryIndex = breakIterator.next();
        }

        String sentence = text.substring(prevIndex).trim();
        if (sentence.length()>0)
            output.add(sentence);

        return output;
    }

    // Read the Doc as a String
    public static ArrayList<FileInfo> readDocInfo(String filename){
        ArrayList<FileInfo> fileInfoList = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(new FileReader(filename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] Colmuns = line.split(",");
                String filetitle = Colmuns[0].trim();
                double Readability = Double.parseDouble(Colmuns[91].trim());
                double gradelevel = Double.parseDouble(Colmuns[92].trim());

                FileInfo FI = new FileInfo(filetitle, Readability, gradelevel);
                fileInfoList.add(FI);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }
        return fileInfoList;
    }

    public static ArrayList<Word> readSyllablesFile(String filename) {
        Scanner scanner;
        ArrayList<Word> words = new ArrayList<Word>();

        try {
            scanner = new Scanner(new FileReader(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // ------- process the line here -------
                String word = getWordFromLine(line);
                int syllables = getSyllablesFromLine(line);

                // -------------------------------------
                Word w = new Word(word, syllables);
                words.add(w);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }

        return words;
    }

    private static int getSyllablesFromLine(String line) {
        int start = line.indexOf("=") + 1;
        int count = 0;

        for (; start < line.length(); start++) {
            if (line.substring(start, start+1).equals("*")) count++;
        }

        return count+1;
    }

    private static String getWordFromLine(String line) {
        return line.substring(0, line.indexOf("="));
    }

    public static ArrayList<FileInfo> getActualReadAbility(String filename){
        Scanner scanner;
        double readabilty = 0;
        ArrayList<FileInfo> AllFileInfo = new ArrayList<>();

        try {
            scanner = new Scanner(new FileReader(filename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineinfo = line.split(",");
                String name = lineinfo[0].trim();
                double readability = Double.parseDouble(lineinfo[91].trim());
                double grade = Integer.parseInt(lineinfo[92].trim());

                FileInfo fileinfo = new FileInfo(name, readability, grade);
                AllFileInfo.add(fileinfo);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        }
        return AllFileInfo;
    }

    /*
    private static String filenameFix(String filename){
        String newfilename = "";
        ArrayList<Integer> indexofdashs = new ArrayList<>();
        for (int i = 0; i < filename.length(); i++) {
            if("-".equals(filename.substring(i,i+1))) {
                indexofdashs.add(i);
            }
        }
        String nodashes = filename.replaceAll("-"," ");
        for (int i = 0; i < nodashes.length(); i++) {
            if(i == indexofdashs.get(indexofdashs.size()-1)){
                newfilename = newfilename + "-";
            } else {
                newfilename = newfilename + nodashes.substring(i,i+1);
            }
        }

        return newfilename;
    }
    */
}
