public class FileInfo {
    private String filename;
    private double readability;
    private double grade;

    public FileInfo(String filename, double readability, double grade){
        this.filename = filename;
        this.readability = readability;
        this.grade = grade;
    }

    public String getFilename(){
        return filename;
    }

    public double getReadability(){
        return readability;
    }

    public double getGrade(){
        return grade;
    }


    public void setFilename(String filename){
        this.filename = filename;
    }

    public void setReadability(double readability){
        this.readability = readability;
    }
    public void setGrade(double grade){
        this.grade = grade;
    }
}
