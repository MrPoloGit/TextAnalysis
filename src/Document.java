import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Scanner;

public class Document {
    private String text;
    private String worduseData = readFileAsString("Word&Use");
    private ArrayList<String> sentences;
    private ArrayList<String> words;
    private ArrayList<String> vocab;
    private ArrayList<String> letters;
    private int numSyllables = 0;
    private boolean isSentencesUpdated = false;
    private boolean isWordsUpdated = false;
    private boolean isVocabUpdated = false;
    private boolean isLettersUpdated = false;
    private boolean isSyllablasUpdated = false;


    // takes the text file, in a String
    public Document(String text){
        this.text = text;
    }

    public void setText(String newText){
        this.text = newText;
        this.isSentencesUpdated = false;
        this.isWordsUpdated = false;
        this.isLettersUpdated = false;
    }


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

    // Gets the number of sentences after spliting
    public int getNumSentences(){
        if(isSentencesUpdated) return sentences.size();
        this.sentences = TextLib.splitIntoSentences(text);
        this.isWordsUpdated = true;
        return sentences.size();
    }

    // Gets the sentences in a arraylist form
    public ArrayList<String> getSentences(){
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

    // Returns the total number of words used
    public int getNumWords(){
        if(isWordsUpdated) return words.size();
        this.words = getWords();
        this.isWordsUpdated = true;
        return words.size();
    }

    // Returns a ArrayList with all words
    public ArrayList<String> getWords(){
        ArrayList<String> sentences = getSentences();
        ArrayList<String> output = new ArrayList<>();
        for(String sentence: sentences){
            String[] words = sentence.split(" ");
            for (int i = 0; i < words.length; i++) {
                output.add(words[i].trim());
            }
        }
        return output;
    }

    public String[] getWords(String sentence){
        return sentence.split(" ");
    }

    public int getAverageWords(){
        return (getNumWords()/getNumSentences());
    }

    // Returns the average number of letters in words
    public int getAverageLetters(){
        return getNumLetters()/getNumWords();
    }

    private int getNumLetters(){
        if(isLettersUpdated) letters.size();
        this.letters = getLetters();
        this.isLettersUpdated = true;
        return letters.size();
    }

    private ArrayList<String> getLetters(){
        ArrayList<String> sentences = getSentences();
        int numChars = 0;
        for (int i = 0; i < sentences.size(); i++) {
            String[] words = sentences.get(i).split(" ");
            for (int j = 0; j < words.length; j++) {
                numChars += words[j].length();
            }
        }
        return letters;
    }

    // Returns how many DIFFERENT words where used
    public int getVocabSize(){
        if(isVocabUpdated) return vocab.size();
        this.vocab = getVocab();
        this.isVocabUpdated = true;
        return vocab.size();
    }

    private ArrayList<String> getVocab(){
        ArrayList<String> words = getWords();
        ArrayList<String> vocab = new ArrayList<>();
        for (String word: words) {
            if(!vocab.contains(word)) vocab.add(word);
        }
        return vocab;
    }

    public double getFKReadability(){
        int numsentances = getNumSentences();
        int numwords = getNumWords();
        int numsyllables = getNumSyllables();


        double finalcalc = 206.835 - 1.015*(numwords/numsentances) - 84.6*(numsyllables/numwords);
        return finalcalc;
    }

    private int getNumSyllables(){
        if(isSyllablasUpdated) return numSyllables;
        this.numSyllables = calcNumSyllables();
        this.isSyllablasUpdated = true;
        return numSyllables;
    }

    private int calcNumSyllables(){
        int totalcount = 0;
        ArrayList<String> sentances = getSentences();
        for(String sentance: sentances){
            String[] words = getWords(sentance);
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
    public ArrayList<String> stripandfixed(String[] words){
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

    public String stripPuncuation(String word){
        String sword = "";
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i,i+1);
            if(isletter(letter)) sword = sword + letter;
        }
        return sword;
    }

    private boolean isletter(String letter) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        if(alphabet.contains(letter)) return true;
        return false;
    }

    private int syllablesFor(String word) {
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

    private boolean isVowel(String letter){
        return "aeiouy".contains(letter);
    }


    public int getNumApperances(String target){
        ArrayList<String> words = getWords();
        int count = 0;
        for(String word: words){
            if(word.equals(target)) count++;
        }
        return count;
    }

    public boolean coOccurinSentance(String word1, String word2){
        ArrayList<String> sentences = getSentences();
        for(String sentence: sentences){
            String[] words = getWords(sentence);
            ArrayList<String> fixed = stripandfixed(words);
            if(contains(fixed, word1) && contains(fixed, word2)) return true;
        }
        return false;
    }

    public boolean contains(ArrayList<String> words, String target){
        for(String word: words){
            if(word.equals(target)) return true;
        }
        return false;
    }

//    // RETURNS THE NUMBER OF UNUSUAL WORDS IN THE TEXT
//    public int getNumUnusualWords(){
//        ArrayList<String> words = getVocab();
//        int count = 0;
//        for (int i = 0; i < words.size(); i++) {
//            if(isUnusualWord(words.get(i))) count++;
//        }
//        return count;
//    }
//
//    private boolean isUnusualWord(String word){
//        for (int i = 0; i < WordUseData.size(); i++) {
//            if(word.equals(WordUseData.get(i).getWord())){
//                if(WordUseData.get(i).getUsenum() < 500000) return true;
//            }
//        }
//        return false;
//    }
//
//    private ArrayList<UseData> getUseData(){
//        String text = Document.readFileAsString("");
//        String[] lines = breakupUseData();
//        ArrayList<UseData> usedatas = new ArrayList<>();
//        for (int i = 0; i < lines.length; i++) {
//            String[] line = lines[i].split(" ");
//            String word = line[0].trim();
//            int usenum = Integer.parseInt(line[line.length-1].trim());
//            UseData useData = new UseData(word, usenum);
//            usedatas.add(useData);
//        }
//        return usedatas;
//    }
//
//    private String[] breakupUseData(){
//        String[] output = worduseData.split("\\r?\\n");
//        return output;
//    }
//

}
