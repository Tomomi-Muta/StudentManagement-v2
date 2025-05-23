package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@Mapper
public interface StudentRepository {

  //学生の情報を取得する
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  //学生が受講しているコースを取得する
  @Select("SELECT * FROM students_courses")
  List<StudentCourse>  searchStudentCourses();

  @Insert(
      "INSERT INTO students(name,frigana,nickname,email,region,age,gender,remark,isDeleted) "
          + "VALUES(#{name}, #{frigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, false)")

  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);
}
