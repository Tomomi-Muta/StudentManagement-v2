package raisetech.student.management.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

/**
 * 学生詳細情報（フォームデータ受け取り用）
 */
@Getter
@Setter
public class StudentDetail {

  // 学生の基本情報（name, email など）
  private Student student;

  // 選択されたコースのIDを格納（フォームから受け取る用）
 //private List<Integer> studentsCourses;

  // データベースの学生コース情報（主に出力用）
  private List<StudentsCourses> studentsCourses;
}

