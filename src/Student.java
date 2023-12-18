import java.util.ArrayList;

public class Student extends Course {
    private String name;//姓名
    private String id;//学号
    private char gender;//性别
    private String clase;//班级
    private String email;//邮箱
    private String startlocaldatetime;//信息建立时间
    private String endlocaldatetime;//最后修改时间

    private ArrayList<Course> courses = new ArrayList<Course>();//作为实例变量

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Student() {
        super();
    }

    //不包含最后修改时间
    public Student(String name, String id, char gender, String clase, String email, String endlocaldatetime) {

        this.name = name;
        this.id = id;
        this.gender = gender;
        this.clase = clase;
        this.email = email;
        this.endlocaldatetime = endlocaldatetime;
    }

    //包含最后修改时间
    public Student(String name, String id, char gender, String clase, String email, String startlocaldatetime, String endlocaldatetime) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.clase = clase;
        this.email = email;
        this.startlocaldatetime = startlocaldatetime;
        this.endlocaldatetime = endlocaldatetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartlocaldatetime() {
        return startlocaldatetime;
    }

    public void setStartlocaldatetime(String startlocaldatetime) {
        this.startlocaldatetime = startlocaldatetime;
    }

    public String getEndlocaldatetime() {
        return endlocaldatetime;
    }

    public void setEndlocaldatetime(String endlocaldatetime) {
        this.endlocaldatetime = endlocaldatetime;
    }

    @Override
    public String toString() {
        return "姓名:" + name +
                ", 学号:" + id +
                ", 性别:" + gender +
                ", 班级:" + clase +
                ", 邮箱:" + email +
                ", 信息建立时间:" + startlocaldatetime +
                ", 最后修改时间:" + endlocaldatetime;
    }

    //    public void show() {//展示课程
////        ADVANCEDMATHEMATICS.show() +",成绩:" + String.valueOf((new Grade()).getMark());
////        LINEARALGEBRA.show();
////        English.show();
////        PROGRAMMINGCOURSE.show();
//    }
//
//    public void showGrade() {
//        this.getMark()
//        if (mark >= 0 || mark < 60) {
//            this.getCourses().get(0).setMark();
//        }
//        System.out.println(name + "的各课程成绩:");
//        System.out.println();
//        System.out.println("课程号:" + super.getCourseId() +
//                ", 成绩:" + super.getMark() +
//                ", 成绩分段:" + super.getRank().get());
//    }
}
