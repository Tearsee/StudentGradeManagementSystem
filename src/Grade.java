import java.util.ArrayList;

public class Grade{

    private String courseId;//课程号
    private String id;//学号
    private int mark;//成绩
    private static ArrayList<String> rank = new ArrayList<>();//成绩分段集合
    private String level;//成绩分段

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    static {
        rank.add("优秀");
        rank.add("良好");
        rank.add("及格");
        rank.add("不及格");
    }
    public Grade() {
    }

    public Grade(String courseId, String id, int mark, ArrayList<String> rank) {
        this.courseId = courseId;
        this.id = id;
        this.mark = mark;
        this.rank = rank;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public ArrayList<String> getRank() {
        return rank;
    }

    public void setRank(ArrayList<String> rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "课程号:" + courseId + '\'' +
                ", 学号:" + id + '\'' +
                ", 成绩:" + mark +
                ", 成绩分段:" + rank;
    }
}
