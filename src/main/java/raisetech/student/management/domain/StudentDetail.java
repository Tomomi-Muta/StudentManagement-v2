package raisetech.student.management.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 学生詳細情報（フォームデータ受け取り用）
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  // 学生の基本情報（name, email など）
  private Student student;

  // データベースの学生コース情報（主に出力用）
  private List<StudentCourse> studentCourseList;
}

