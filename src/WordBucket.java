import java.lang.reflect.Array;
import java.util.ArrayList;

public class WordBucket {
    private ArrayList<String> words;
    private ArrayList<String> unique;
    private String mostfreq = "";
    private boolean isUniqueUpdated = false;

    public WordBucket(){
        words = new ArrayList<>();
        unique = new ArrayList<>();
    }

    public void add(String word){
        if(size() == 0){
            mostfreq = word;
        }
        words.add(word);

        if(!unique.contains(word)) unique.add(word);
        if(size() == )
    }

    public void add(String word, int count){
        for (int i = 0; i < count; i++) {
            words.add(word);
        }
    }

    public long getCountOf(String word){
        int count = 0;
        for (String str: words){
            if(str.equals(word)) count++;
        }
        return count;
    }

    public long size(){
        return words.size();
    }

    public long getNumUnique(){
        if(isUniqueUpdated) return unique.size();
        this.unique = getUnique();
        this.isUniqueUpdated = true;
        return unique.size();
    }

    private ArrayList<String> getUnique(){
        ArrayList<String> unique = new ArrayList<>();
        for (String word: words) {
            if(!unique.contains(word)) unique.add(word);
        }
        return unique;
    }

    public String getMostFreq(){
        if(size() == 0) return "";
        String freqword = "";
        long max = 0;
        long num = 0;
        for (String word: words) {
            num = getCountOf(word);
            if(num > max) {
                max = num;
                freqword = word;
            }
        }
        return freqword;
    }

    public ArrayList<String> getTopN(){

    }

}

