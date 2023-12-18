import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class StudentGradeManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static volatile ArrayList<Student> studentList_ = new ArrayList<>();
    static volatile List<Student> studentList = Collections.synchronizedList(studentList_);//实现ArrayList线程安全
    static Course course = new Course();

    public static void main(String[] args) {
        while (true) {
            System.out.println("-------------学生成绩管理系统--------------");
            System.out.println("1. 查看专业码");
            System.out.println("A. 学生信息菜单");
            System.out.println("B. 课程信息菜单");
            System.out.println("C. 成绩信息菜单");
            System.out.println("0. 退出系统");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    showCareer();
                    break;
                case "A":
                    studentInfoMenu();
                    break;
                case "B":
                    studentCourseInfoMenu();
                    break;
                case "C":
                    studentGradeInfoMenu();
                    break;
                case "0":
                    System.out.println("牢底,下次再见");
                    System.exit(0);
                    break;
                default:
                    throw new InputIllegleException("输入有误请重新输入");//异常


            }
        }
    }

    //展示专业码
    public synchronized static void showCareer() {
        BufferedReader br = null;
        try {
            File file = new File("career_.txt");
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {

                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //添加学生
    public synchronized static void addStudent() {
        System.out.println("输入姓名要求:①不能以数字开头 ②只能包含字母和数字和下划线 ③姓名总长度长度在7-11之间");
        System.out.println("输入学生的姓名:");
        String addName = sc.next();
        String regex_name = "^[^0-9][a-zA-Z0-9_]{6,10}$";
        if (!addName.matches(regex_name)) {
            throw new InputIllegleException("输入姓名格式有误");
        }

        for (int i = 0; i < studentList.size(); i++) {
            if (addName.equals(studentList.get(i).getName())) {
                throw new InputIllegleException("该学生已存在，无法重复添加");
            }
        }

        System.out.println("输入学生的学号:");
        String addid = sc.next();//[23-31]
        String regex = "[12]\\d{3}0{3}[0-6][1-9][01](2[3-9]|3[01])";


        char addgender;//性别
        String addclase;//班级
        if (!addid.matches(regex)) {
            throw new InputIllegleException("输入的学号格式有误");
        } else {
            if (addid.charAt(9) == '1') {
                addgender = '男';
            } else {
                addgender = '女';
            }

            addclase = addid.charAt(8) + "";

        }


        System.out.print("输入学生的邮箱(例如:1437369274@qq.com):");
        String addemail = sc.next();
        if (!addemail.matches("\\w{2,}@\\w{2,20}(\\.\\w{2,10}){1,2}")) {
            throw new InputIllegleException("输入的邮箱格式有误");
        }

        //信息初次建立时间
        LocalDateTime startDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sdt = dateTimeFormatter.format(startDateTime);

        //信息最后修改时间
        LocalDateTime endDateTime = LocalDateTime.now();
        String edt = dateTimeFormatter.format(endDateTime);

        Student addstu = new Student(addName, addid, addgender, addclase, addemail, sdt, edt);//获取学生
        studentList.add(addstu);
//        System.out.println("添加成功");

        //将学生信息写入文件中
        BufferedWriter bw = null;
        try {
            File file = new File("studentinfo.txt");

            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);


            bw.write(addstu.toString());
            bw.newLine();
            bw.flush();
            System.out.println(addstu);

            System.out.println("添加成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //根据学生信息添加相应的课程
        addCourse(addstu, true);
    }

    //添加课程
    public synchronized static void addCourse(Student stu, boolean isAppend) {
        //给学生添加公共课程
        stu.getCourses().add(course.ADVANCEDMATHEMATICS);
        stu.getCourses().add(course.LINEARALGEBRA);
        stu.getCourses().add(course.English);


        //给学生添加课程对应专业的特有课程
        switch (stu.getId().substring(4, 8)) {
            case "0000":
                Course jiSuanJi = new Course("4", "计算机科学与技术课", 6, 2);
                stu.getCourses().add(jiSuanJi);

                break;
            case "0001":
                Course infoSystem = new Course("5", "信息系统与信息管理课", 6, 2);
                stu.getCourses().add(infoSystem);
                break;

            case "0002":
                Course wuLianWang = new Course("6", "物联网工程课", 6, 2);
                stu.getCourses().add(wuLianWang);
                break;
            case "0003":
                Course ruanJian = new Course("7", "软件工程课", 6, 2);
                stu.getCourses().add(ruanJian);
                break;
            case "0004":
                Course InfoJiSuan = new Course("8", "信息与计算科学课", 6, 2);
                stu.getCourses().add(InfoJiSuan);
                break;
            case "0005":
                Course dataScience = new Course("9", "数据科学与大数据技术课", 6, 2);
                stu.getCourses().add(dataScience);
                break;
            case "0006":
                Course dianZi = new Course("10", "电子信息工程课", 6, 2);
                stu.getCourses().add(dianZi);
                break;
            default:
                System.out.println("匹配失败");
                break;

        }
        //将课程写入文件中
        CourseIO(stu, isAppend);
    }

    //课程的读写操作
    public synchronized static void CourseIO(Student addstu, boolean isAppend) {//

        BufferedWriter bw = null;
        try {
            File file = new File("courseinfo.txt");

            FileWriter fw = new FileWriter(file, isAppend);
            bw = new BufferedWriter(fw);


            if (isAppend == true) {
                bw.write(addstu.getName() + "的课程:");
                bw.newLine();
                bw.newLine();
                for (Course course1 : addstu.getCourses()) {
                    bw.write(course1.toString());
                    bw.newLine();
                    bw.flush();
                }
                bw.newLine();
//                System.out.println("IO");
            } else {
                for (Student stu : studentList) {
                    bw.write(stu.getName() + "的课程:");
                    bw.newLine();
                    bw.newLine();

                    for (Course course1 : stu.getCourses()) {
                        bw.write(course1.toString());
                        bw.newLine();
                        bw.flush();
                    }
                    bw.newLine();
                }
//                System.out.println("IO");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (bw != null) {

                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    //删除学生
    public synchronized static void deleteStudent() {
        //判断是否有学生
        if (studentList.size() == 0) {
            throw new InputIllegleException("目前暂无学生");
        }

        System.out.println("输入姓名要求:①不能以数字开头 ②只能包含字母和数字和下划线 ③姓名总长度长度在7-11之间");
        System.out.print("输入要删除的学生的姓名:");
        String deleteName = sc.next();
        String regex_name = "^[^0-9][a-zA-Z0-9_]{6,10}$";
        if (!deleteName.matches(regex_name)) {
            throw new InputIllegleException("输入姓名格式有误");
        }

        boolean flag = false;//记录是否找到了对应的学生的索引
        for (int i = 0; i < studentList.size(); i++) {

            if (deleteName.equals(studentList.get(i).getName())) {
                studentList.remove(i);
                flag = true;
                break;
            }
        }

        if (flag) {
            System.out.println("删除成功");
        } else {
            throw new InputIllegleException("删除失败，不存在该学生");
        }

        //在文件中更改对应的学生信息
        BufferedWriter bw = null;
        try {
            File file = new File("studentinfo.txt");

            FileWriter fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);

            for (Student student : studentList) {
                bw.write(student.toString());
                bw.newLine();
                bw.flush();
                System.out.println(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //在文件中更改对应的学生课程信息
        if (studentList.size() == 0) {
            try {
                File file = new File("courseinfo.txt");
                FileWriter fw = new FileWriter(file, false);
                bw = new BufferedWriter(fw);
                bw.write("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (bw != null) {

                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {

            CourseIO(studentList.get(0), false);
        }

        //在文件中更改对应的成绩信息
        BufferedWriter bw2 = null;
        try {
            File file = new File("gradeinfo.txt");
            FileWriter fw = new FileWriter(file);
            bw2 = new BufferedWriter(fw);

            for (int i = 0; i < studentList.size(); i++) {

                bw2.write(studentList.get(i).getName() + "的成绩清单如下:");
                bw2.newLine();
                bw2.newLine();
                bw2.flush();
                for (int j = 0; j < studentList.get(i).getCourses().size(); j++) {
                    bw2.write(studentList.get(i).getCourses().get(j) + "成绩:" + studentList.get(i).getCourses().get(j).getMark()
                            + ", 成绩分段:" + studentList.get(i).getCourses().get(j).getLevel());
                    bw2.newLine();
                    bw2.flush();
                }
                bw2.newLine();
                bw2.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw2 != null) {
                    bw2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //修改学生信息
    public synchronized static void updateStudent() {
        //判断目前是否有学生
        if (studentList.size() == 0) {
            throw new InputIllegleException("目前暂无学生");
        }

        boolean flag = false;//用于记录是否找到了指定的学生
        System.out.println("输入姓名要求:①不能以数字开头 ②只能包含字母和数字和下划线 ③姓名总长度长度在7-11之间");
        System.out.println("请输入要修改学生的姓名");
        String updateName = sc.next();
        String regex_name = "^[^0-9][a-zA-Z0-9_]{6,10}$";
        if (!updateName.matches(regex_name)) {
            throw new InputIllegleException("输入姓名格式有误");
        }

        int count = -1;//记录对应的学生索引
        for (int i = 0; i < studentList.size(); i++) {

            if (updateName.equals(studentList.get(i).getName())) {


                System.out.println("输入学生修改后的学号:");
                String updateid = sc.next();
                String regex = "[12]\\d{3}0{3}[0-6][1-9][01](2[3-9]|3[01])";
                char updategender;//性别
                String updateclase;//班级
                if (!updateid.matches(regex)) {
                    throw new InputIllegleException("输入的学号格式有误");
                } else {
                    if (updateid.charAt(9) == '1') {
                        updategender = '男';
                    } else {
                        updategender = '女';
                    }

                    updateclase = updateid.charAt(8) + "";

                }

                System.out.print("输入学生的邮箱(例如:1437369274@qq.com):");
                String updateemail = sc.next();
                if (!updateemail.matches("\\w{2,}@\\w{2,20}(\\.\\w{2,10}){1,2}")) {
                    throw new InputIllegleException("输入的邮箱格式有误");
                }

                LocalDateTime updateendDateTime = LocalDateTime.now();
                DateTimeFormatter updateenddateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String updateedt = updateenddateTimeFormatter.format(updateendDateTime);

                String sld = studentList.get(i).getStartlocaldatetime();
                Student updateaddstu = new Student(updateName, updateid, updategender, updateclase, updateemail, sld, updateedt);//获取学生
                studentList.set(i, updateaddstu);
//                System.out.println("修改成功");
                count = i;
                flag = true;
                break;
            }
        }

        if (flag) {
            System.out.println("修改成功");
        } else {
            throw new InputIllegleException("修改失败，不存在该学生");
        }

        //在文件中更改学生信息
        BufferedWriter bw = null;
        try {
            File file = new File("studentinfo.txt");

            FileWriter fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);

            for (Student student : studentList) {
                bw.write(student.toString());
                bw.newLine();
                bw.flush();
                System.out.println(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        addCourse(studentList.get(count), false);
//        CourseIO((Student) (new Object()),false);

        //在文件中写入修改学生信息后成绩的相应变动
        BufferedWriter bw2 = null;
        try {
            File file = new File("gradeinfo.txt");
            FileWriter fw = new FileWriter(file);
            bw2 = new BufferedWriter(fw);

            for (int i = 0; i < studentList.size() && i != count; i++) {

                bw2.write(studentList.get(i).getName() + "的成绩清单如下:");
                bw2.newLine();
                bw2.newLine();
                bw2.flush();
                for (int j = 0; j < studentList.get(i).getCourses().size(); j++) {
                    bw2.write(studentList.get(i).getCourses().get(j) + "成绩:" + studentList.get(i).getCourses().get(j).getMark()
                            + ", 成绩分段:" + studentList.get(i).getCourses().get(j).getLevel());
                    bw2.newLine();
                    bw2.flush();
                }
                bw2.newLine();
                bw2.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw2 != null) {
                    bw2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //展示所有学生的信息/课程信息/成绩信息
    public static void showAll(String path) {
        if (studentList.size() == 0) {
            System.out.println("目前暂无学生");
        } else {
            //写法一:集合遍历写法
////            System.out.println("展示如下");
//            Iterator<Student> iterator = studentList.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }
            //写法二:IO流读取并且打印

            BufferedReader br = null;
            try {
                File file = new File(path);

                FileReader fr = new FileReader(file);
                br = new BufferedReader(fr);


                String len = null;
                while ((len = br.readLine()) != null) {
                    System.out.println(len);

                }

                System.out.println("\n---------打印完毕---------");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //展示指定学生的信息/课程信息/成绩信息
    public static void showOne(int choice) {
        //判断目前是否有学生
        if (studentList.size() == 0) {
            throw new InputIllegleException("目前暂无学生");
        }


        System.out.println("输入姓名要求:①不能以数字开头 ②只能包含字母和数字和下划线 ③姓名总长度长度在7-11之间");
        System.out.print("输入要查找的学生的姓名:");
        String searchName = sc.next();
        String regex_name = "^[^0-9][a-zA-Z0-9_]{6,10}$";

        //判断姓名格式
        if (!searchName.matches(regex_name)) {
            throw new InputIllegleException("输入姓名格式有误");
        }


        boolean flag = false;//记录是否找到指定查找的学生
        int count = -1;//记录对应学生的索引
        for (int i = 0; i < studentList.size(); i++) {

            if (searchName.equals(studentList.get(i).getName())) {
                flag = true;
                count = i;
                break;
            }
        }




        //判断是否存在指定查找的学生
        if (!flag) {
            throw new InputIllegleException("查找失败，不存在该学生");
        }

        //介于多个选项引用同一个方法，故利用键盘输入的不同来对应不同之处
        if (choice == 5) {//查找单个学生信息
            System.out.println(studentList.get(count));
        } else if (choice == 4) {//查找单个学生成绩
            if (studentList.get(count).getCourses().get(0).getLevel() == null) {
                System.out.println("该学生尚未添加成绩");
                return;
            }

            for (int j = 0; j < studentList.get(count).getCourses().size(); j++) {
                System.out.println(studentList.get(count).getCourses().get(j) + "成绩:" + studentList.get(count).getCourses().get(j).getMark()
                        + ", 成绩分段:" + studentList.get(count).getCourses().get(j).getLevel());
            }
        } else {//查找单个学生课程的信息
            Iterator<Course> iterator = studentList.get(count).getCourses().iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        }

    }

    //添加所有学生的成绩
    public static void addStudentGrade() {
        //判断目前是否有学生
        if (studentList.size() == 0) {
            System.out.println("目前暂无学生");
        } else {
            for (int i = 0; i < studentList.size(); i++) {

                System.out.println(studentList.get(i).getName() + "的成绩清单如下:");

                for (int j = 0; j < studentList.get(i).getCourses().size(); j++) {

                    System.out.print("请输入" + studentList.get(i).getCourses().get(j) + "成绩:");
                    int mark1 = sc.nextInt();
                    String s = mark1 + "";

                    if (s.matches("^(100|\\d{1,2})$")) {//0-100
                        studentList.get(i).getCourses().get(j).setMark(mark1);


                        int mark = studentList.get(i).getCourses().get(j).getMark();
                        //成绩位于 : 0-100
                        if (mark >= 0 && mark < 60) {
                            studentList.get(i).getCourses().get(j).setLevel(studentList.get(i).getCourses().get(j).getRank().get(3));
                        } else if (mark >= 60 && mark < 80) {
                            studentList.get(i).getCourses().get(j).setLevel(studentList.get(i).getCourses().get(j).getRank().get(2));

                        } else if (mark >= 80 && mark < 90) {

                            studentList.get(i).getCourses().get(j).setLevel(studentList.get(i).getCourses().get(j).getRank().get(1));
                        } else {
                            studentList.get(i).getCourses().get(j).setLevel(studentList.get(i).getCourses().get(j).getRank().get(0));
                        }

                    } else {

                        throw new InputIllegleException("输入成绩有误，成绩应该位于0-100");

                    }
                }
                System.out.println();
            }

            //在文件中写入添加的学生成绩
            BufferedWriter bw = null;
            try {
                File file = new File("gradeinfo.txt");
                FileWriter fw = new FileWriter(file);
                bw = new BufferedWriter(fw);

                for (int i = 0; i < studentList.size(); i++) {

                    bw.write(studentList.get(i).getName() + "的成绩清单如下:");
                    bw.newLine();
                    bw.newLine();
                    bw.flush();
                    for (int j = 0; j < studentList.get(i).getCourses().size(); j++) {
                        bw.write(studentList.get(i).getCourses().get(j) + "成绩:" + studentList.get(i).getCourses().get(j).getMark()
                                + ", 成绩分段:" + studentList.get(i).getCourses().get(j).getLevel());
                        bw.newLine();
                        bw.flush();
                    }
                    bw.newLine();
//                    bw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("添加成功");
        }

    }

    //添加指定学生的成绩
    public static void addOneStudentGrade() {
        //判断目前是否有学生
        if (studentList.size() == 0) {
            throw new InputIllegleException("目前暂无学生");
        }

        //目前有学生的情况下
        System.out.println("输入姓名要求:①不能以数字开头 ②只能包含字母和数字和下划线 ③姓名总长度长度在7-11之间");
        System.out.println("输入学生的姓名:");
        String name = sc.next();
        String regex_name = "^[^0-9][a-zA-Z0-9_]{6,10}$";
        if (!name.matches(regex_name)) {
            throw new InputIllegleException("输入姓名格式有误");
        }

        //判断是否存在指定学生
        boolean isExist = false;
        int index = -1;//记录指定学生的索引
        for (int i = 0; i < studentList.size(); i++) {

            if (name.equals(studentList.get(i).getName())) {

                isExist = true;
                index = i;
            }

        }

        if (!isExist) {
            System.out.println("暂无该学生");
            return;
        }

        //找到了指定的学生的情况下
        //判断该学生是否已经添加过成绩
        for (int j = 0; j < studentList.get(index).getCourses().size(); j++) {

            if (studentList.get(index).getCourses().get(j).getMark() > 0) {
                throw new InputIllegleException("该学生成绩已存在");
            }
        }

        //该学生成绩从未初始化过的情况下
        System.out.println(studentList.get(index).getName() + "的成绩清单如下:");
        for (int j = 0; j < studentList.get(index).getCourses().size(); j++) {

            System.out.print("请输入" + studentList.get(index).getCourses().get(j) + "成绩:");
            int mark1 = sc.nextInt();
            String s = mark1 + "";

            boolean isCorrespond_ = s.matches("^(100|\\d{1,2})$");
            if (!isCorrespond_) {
                throw new InputIllegleException("输入成绩有误，成绩应该位于0-100");
            }

            //成绩区间:0-100
            studentList.get(index).getCourses().get(j).setMark(mark1);
            int mark = studentList.get(index).getCourses().get(j).getMark();

            if (mark >= 0 && mark < 60) {
                studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(3));
                return;
            }

            if (mark >= 60 && mark < 80) {
                studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(2));
                return;
            }

            if (mark >= 80 && mark < 90) {
                studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(1));
                return;
            }

            studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(0));

        }
        System.out.println();

        //在文件中写入学生成绩
        BufferedWriter bw = null;
        try {
            File file = new File("gradeinfo.txt");
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(name + "的成绩清单如下:");
            bw.newLine();
            bw.newLine();
            bw.flush();
            for (int j = 0; j < studentList.get(index).getCourses().size(); j++) {
                bw.write(studentList.get(index).getCourses().get(j) + "成绩:" + studentList.get(index).getCourses().get(j).getMark()
                        + ", 成绩分段:" + studentList.get(index).getCourses().get(j).getLevel());
                bw.newLine();
                bw.flush();
            }
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("添加成功");
    }

    //修改学生成绩信息
    public static void updateStudentGrade() {
        //判断目前是否有学生
        if (studentList.size() == 0) {
            throw new InputIllegleException("目前暂无学生");
        }

        //目前有学生的情况下
        System.out.println("输入姓名要求:①不能以数字开头 ②只能包含字母和数字和下划线 ③姓名总长度长度在7-11之间");
        System.out.println("输入学生的姓名:");
        String name = sc.next();
        String regex_name = "^[^0-9][a-zA-Z0-9_]{6,10}$";
        if (!name.matches(regex_name)) {
            throw new InputIllegleException("输入姓名格式有误");
        }

        //判断是否存在指定学生
        boolean isExist = false;
        int index = -1;//记录指定学生的索引
        for (int i = 0; i < studentList.size(); i++) {

            if (name.equals(studentList.get(i).getName())) {

                isExist = true;
                index = i;
            }

        }

        if (!isExist) {
            System.out.println("暂无该学生");
            return;
        }

        //找到了指定的学生的情况下

        //该学生成绩从未初始化过的情况下
        System.out.println(studentList.get(index).getName() + "的成绩清单如下:");
        for (int j = 0; j < studentList.get(index).getCourses().size(); j++) {

            System.out.print("请输入" + studentList.get(index).getCourses().get(j) + "成绩:");
            int mark1 = sc.nextInt();
            String s = mark1 + "";

            boolean isCorrespond_ = s.matches("^(100|\\d{1,2})$");
            if (!isCorrespond_) {
                throw new InputIllegleException("输入成绩有误，成绩应该位于0-100");
            }

            //成绩区间:0-100
            studentList.get(index).getCourses().get(j).setMark(mark1);
            int mark = studentList.get(index).getCourses().get(j).getMark();

            if (mark >= 0 && mark < 60) {
                studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(3));
                return;
            }

            if (mark >= 60 && mark < 80) {
                studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(2));
                return;
            }

            if (mark >= 80 && mark < 90) {
                studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(1));
                return;
            }

            studentList.get(index).getCourses().get(j).setLevel(studentList.get(index).getCourses().get(j).getRank().get(0));

        }
        System.out.println();

        //在文件中写入学生成绩
        BufferedWriter bw = null;
        try {
            File file = new File("gradeinfo.txt");
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(name + "的成绩清单如下:");
            bw.newLine();
            bw.newLine();
            bw.flush();
            for (int j = 0; j < studentList.get(index).getCourses().size(); j++) {
                bw.write(studentList.get(index).getCourses().get(j) + "成绩:" + studentList.get(index).getCourses().get(j).getMark()
                        + ", 成绩分段:" + studentList.get(index).getCourses().get(j).getLevel());
                bw.newLine();
                bw.flush();
            }
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("修改成功");
    }

    //学生信息管理菜单
    public static void studentInfoMenu() {
        while (true) {
            System.out.println("-----------学生信息管理菜单------------");
            System.out.println("1. 增加学生信息");
            System.out.println("2. 删除学生信息");
            System.out.println("3. 修改学生信息");
            System.out.println("4. 查看所有学生的信息(模糊查询)");
            System.out.println("5. 查找单个学生的信息(条件查询)");
            System.out.println("6. 返回上一个菜单");
            System.out.println("7. 退出系统");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    deleteStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    showAll("studentinfo.txt");
                    break;
                case 5:
                    showOne(choice);
                    break;
                case 6:
                    return;
                case 7:
                    System.out.println("牢底,下次再见");
                    System.exit(0);
                    break;
                default:
                    throw new InputIllegleException("输入有误请重新输入");//异常
            }
        }
    }

    //学生课程信息管理菜单
    public static void studentCourseInfoMenu() {
        while (true) {
            System.out.println("-------------学生课程信息管理菜单--------------");
            System.out.println("1. 查找所有学生的课程信息(模糊查询)");
            System.out.println("2. 查找单个学生的课程信息(条件查询)");
            System.out.println("3. 返回上一个菜单");
            System.out.println("4. 退出系统");

            int choice = sc.nextInt();
            switch (choice) {

                case 1:
                    showAll("courseinfo.txt");
                    break;
                case 2:
                    showOne(choice);
                    break;
                case 3:
                    return;
                case 4:
                    System.out.println("牢底,下次再见");
                    System.exit(0);
                    break;
                default:
                    throw new InputIllegleException("输入有误请重新输入");//异常

            }
        }
    }

    //学生成绩管理菜单
    public static void studentGradeInfoMenu() {
        while (true) {
            System.out.println("-------------学生成绩管理菜单---------------");
            System.out.println("1. 添加所有学生的成绩");
            System.out.println("2. 添加单个学生的成绩");
            System.out.println("3. 修改单个学生的成绩");
            System.out.println("4. 查看单个学生的成绩(条件查询)");
            System.out.println("5. 查看所有学生的成绩(模糊查询)");
            System.out.println("6. 返回上一个菜单");
            System.out.println("7. 退出系统");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addStudentGrade();
                    break;
                case 2:
                    addOneStudentGrade();
                    break;
                case 3:
                    updateStudentGrade();
                    break;
                case 4:
                    showOne(choice);
                    break;
                case 5:
                    showAll("gradeinfo.txt");
                    break;
                case 6:
                    return;
                case 7:
                    System.out.println("牢底,下次再见");
                    System.exit(0);
                    break;
                default:
                    throw new InputIllegleException("输入有误请重新输入");//异常

            }
        }
    }


}
