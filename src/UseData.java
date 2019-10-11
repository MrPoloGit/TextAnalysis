public class UseData {
    String word;
    int usenum;

    public UseData(String word, int usenum){
        this.word = word;
        this.usenum = usenum;
    }

    public String getWord(){
        return word;
    }

    public int getUsenum(){
        return usenum;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setUsenum(int usenum){
        this.usenum = usenum;
    }
}
