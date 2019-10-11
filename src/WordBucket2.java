import java.util.ArrayList;

public class WordBucket2 {
    private ArrayList<Word2> words;
    private ArrayList<Word2> unique;

    public WordBucket2(){
        words = new ArrayList<>();
        unique = getUnique();
    }

    public void add(String word){
        int loc = getLocationOf(word);
        if(loc == -1){
            words.add(new Word2(word, 1));
        } else{
            Word2 w = words.get(loc);
            w.increment();
        }
        if(loc > -1) reOrganize(loc);
    }

    private int getLocationOf(String w){
        int loc = -1;
        for (int i = 0; i < words.size(); i++) {
            if(words.get(i).getWord().equals(w)) loc = i;
        }
        return loc;
    }

    private void reOrganize(int loc){
        Word2 word = words.remove(loc);
        int i = 0;
        boolean added = false;
        while ((i != words.size()) || added) {
            if (words.get(i).getFreq() < word.getFreq()) {
                words.set(i - 1, word);
                added = true;
            }
            i++;
        }
    }

    public void add(String word, int count){
        int loc = -1;
        for (int i = 0; i < words.size(); i++) {
            if(words.get(i).equals(word)){
                words.get(i).setFreq(words.get(i).freq + count);
                loc = i;
            }
        }
        if(loc > -1) reOrganize(loc);
    }

    public long getCountOf(String word){
        for (Word2 w: words){
            if(w.equals(word)) return w.getFreq();
        }
        return 0;
    }

    public long size(){
        int count = 0;
        for (Word2 w: words) {
            count += w.getFreq();
        }
        return count;
    }

    public long getNumUnique(){
        return getUnique().size();

    }

    private ArrayList<Word2> getUnique(){
        ArrayList<Word2> unique = new ArrayList<>();
        for (Word2 word: words) {
            if(!unique.contains(word.getWord())) unique.add(word);
        }
        return unique;
    }

    public String getMostFreq(){
        return words.get(0).getWord();
    }

    public ArrayList<String> getTopN(int n){
        ArrayList<String> output = new ArrayList<>();
        for (Word2 w: words) {
            output.add(w.getWord());
        }
        return output;
    }
}
