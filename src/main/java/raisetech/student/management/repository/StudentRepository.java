package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  //学生の情報を取得する
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  //学生が受講しているコースを取得する
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses>  searchStudentCourses();

  @Insert(
      "INSERT INTO students(name,frigana,nickname,email,region,age,gender,remark,isDeleted) "
          + "VALUES(#{name}, #{frigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, false)")

  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id, course_name, course_start_at, course_end_at) "
      +"VALUES(#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);

}
