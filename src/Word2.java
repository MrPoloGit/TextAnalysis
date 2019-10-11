public class Word2 {
    String word;
    long freq;

    public Word2(String word, long freq){
        this.word = word;
        this.freq = freq;
    }

    public String getWord(){
        return word;
    }

    public long getFreq(){
        return freq;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setFreq(long freq){
        this.freq = freq;
    }

    public void increment(){
        freq++;
    }
}
