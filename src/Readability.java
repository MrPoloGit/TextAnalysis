import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Readability {
    public static void main(String[] args) {
        ArrayList<FileInfo> docs = TextLib.readDocInfo("data/Texts/allfeatures-ose-final.csv");
        double totalError = 0;

        for(FileInfo doc: docs){
            String filename = doc.getFilename();
            String text = TextLib.readFileAsString("data/Texts/AllTexts/" + filename);
            ArrayList<String> sentances = TextLib.splitIntoSentences(text);


            double prediction = FKReadability(sentances);
            double error = (prediction - doc.getReadability());
            totalError += Math.abs(error);
        }
        System.out.println("Average error is: " + totalError/docs.size());




        // TODO:  Break each sentence into words.                                                   DONE
        // TODO:  Force to lower-case and strip out all puctuation for doing syllable counts.       DONE
    }

    // Figure out readability
    public static double FKReadability(ArrayList<String> sentences){
        int numwords = totalwords(sentences);
        int numsentances = sentences.size();
        int numsyllables = totalsyllables(sentences);


        double finalcalc = 206.835 - 1.015*(numwords/numsentances) - 84.6*(numsyllables/numwords);
        return finalcalc;
    }

    public static int totalwords(ArrayList<String> sentences){
        int numwords = 0;
        for(String sentance: sentences){
            numwords += countWordsinSentance(sentance);
        }
        return numwords;
    }



    // Counts the words in the sentance, by seeing if the length of it is more than 0
    public static int countWordsinSentance(String sentence){
        String[] words = breakSentanceintoWords(sentence);
        int count = 0;
        for(String word: words){
            if(word.length() != 0) count++;
        }
        return count;
    }

    // uses .split() method
    public static String[] breakSentanceintoWords(String sentence){
        return sentence.split(" ");
    }

    private static int totalsyllables(ArrayList<String> sentances){
        int totalcount = 0;
        for(String sentance: sentances){
            String[] words = breakSentanceintoWords(sentance);
            ArrayList<String> fixedwords = stripandfixed(words);
            int syllables = 0;
            for(String test: fixedwords){
                syllables += syllablesFor(test);
            }
            totalcount += syllables;
        }
        return totalcount;
    }

    // Force lower-case, and strips all puccuation
    public static ArrayList<String> stripandfixed(String[] words){
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < words.length; i++){
            if(words[i].length() != 0){
                String word = words[i].toLowerCase();
                word = stripPuncuation(word);
                result.add(word);
            }
        }
        return result;
    }

    // Removes Puncuation
    public static String stripPuncuation(String word){
        String sword = "";
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i,i+1);
            if(isletter(letter)) sword = sword + letter;
        }
        return sword;
    }

    // Tests if the String is a letter
    private static boolean isletter(String letter) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        if(alphabet.contains(letter)) return true;
        return false;
    }

    private static void testSyllableMethod() {
        ArrayList<Word> words = TextLib.readSyllablesFile("data/syllables.txt");

        double right = 0;
        for (Word w : words) {
            String word = w.getWord();
            int prediction = syllablesFor(word);

            if (prediction == w.getSyllables()) right++;
        }

        System.out.println("You got " + (right/words.size()) + " right");
    }

    private static int syllablesFor(String word) {
        boolean inVowelChain = false;
        int boundaries = 0;

        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i+1);
            if (isVowel(letter)) {
                if (!inVowelChain) {
                    inVowelChain = true;
                    boundaries++;
                }
            } else {
                inVowelChain = false;
            }
        }

        return boundaries;
    }

    private static boolean isVowel(String letter) {
        return "aeiouy".contains(letter);
    }
}
