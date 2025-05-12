package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  //学生の情報を取得する
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  //学生が受講しているコースを取得する
  @Select("SELECT * FROM students_courses")
  List<StudentCourse>  searchStudentCourses();
}
