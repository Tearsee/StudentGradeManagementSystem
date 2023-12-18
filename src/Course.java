interface info {//信息接口

    public abstract void show();//展示信息的方法
}

class Course extends Grade implements info {

    private String courseId;//课程号
    private String courseName;//课程名
    private int creditHour;//学分
    private int classHour;//学时

    public Course() {
    }

    public Course(String courseId, String courseName, int creditHour, int classHour) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditHour = creditHour;
        this.classHour = classHour;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public int getClassHour() {
        return classHour;
    }

    //创建实例
    public static final Course ADVANCEDMATHEMATICS = new Course("1", "高数", 8, 2) {
        @Override
        public void show() {
            System.out.println(this);
        }
    };
    public static final Course LINEARALGEBRA = new Course("2", "线性代数", 6, 2) {
        @Override
        public void show() {
            System.out.println(this);
        }
    };
    public static final Course English = new Course("3", "英语", 6, 2) {
        @Override
        public void show() {
            System.out.println(this);
        }
    };



    @Override
    public String toString() {
        return "课程号:" + courseId +
                ",课程名:" + courseName +
                ",学分:" + creditHour +
                ",学时:" + classHour;
    }

    @Override
    public void show() {
        System.out.println(this);
    }


}
