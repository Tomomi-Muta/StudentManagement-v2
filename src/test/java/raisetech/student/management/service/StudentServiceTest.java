package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yaml.snakeyaml.events.Event.ID;
import raisetech.student.management.controler.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void befor() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索＿リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細検索_指定した受講生Idに紐づく受講生情報とコース情報が取得できていること() {
    // 準備
    String studentId = "1";

    Student student = new Student();
    student.setId(studentId);
    student.setName("テスト太郎");
    student.setFrigana("てすとたろう");
    student.setNickname("たろう");
    student.setEmail("test@example.com");
    student.setRegion("東京");
    student.setAge(25);
    student.setGender("男性");
    student.setRemark("メモ");
    student.setDeleted(false);

    StudentCourse course1 = new StudentCourse();
    course1.setId("C1");
    course1.setCourseName("Java基礎");
    course1.setStudentId(studentId);
    course1.setCourseStartAt(LocalDateTime.of(2024, 4, 1, 0, 0));
    course1.setCourseEndAt(LocalDateTime.of(2025, 3, 31, 0, 0));

    StudentCourse course2 = new StudentCourse();
    course2.setId("C2");
    course2.setCourseName("Spring実践");
    course2.setStudentId(studentId);
    course2.setCourseStartAt(LocalDateTime.of(2024, 6, 1, 0, 0));
    course2.setCourseEndAt(LocalDateTime.of(2025, 5, 31, 0, 0));

    List<StudentCourse> courseList = List.of(course1, course2);

    when(repository.searchStudent(studentId)).thenReturn(student);
    when(repository.searchStudentCourse(studentId)).thenReturn(courseList);

    // 実行
    StudentDetail result = sut.searchStudent(studentId);

    // 検証
    assertNotNull(result);
    assertEquals("1", result.getStudent().getId());
    assertEquals("テスト太郎", result.getStudent().getName());
    assertEquals(2, result.getStudentCourseList().size());

    assertEquals("Java基礎", result.getStudentCourseList().get(0).getCourseName());
    assertEquals("Spring実践", result.getStudentCourseList().get(1).getCourseName());

    // モック呼び出し確認
    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourse(studentId);
  }

  @Test
  void registerStudent_受講生と受講生コースが正しく登録されること() {
    // 準備
    Student student = new Student();
    student.setId("1");
    student.setName("テスト花子");

    StudentCourse course1 = new StudentCourse();
    course1.setId("C1");
    course1.setCourseName("Java入門");

    StudentCourse course2 = new StudentCourse();
    course2.setId("C2");
    course2.setCourseName("Spring実践");

    List<StudentCourse> courseList = List.of(course1, course2);
    StudentDetail studentDetail = new StudentDetail(student, courseList);

    // 実行
    StudentDetail result = sut.registerStudent(studentDetail);

    // 検証
    // registerStudent()が1回呼ばれたか
    verify(repository, times(1)).registerStudent(student);

    // 各コースが2回登録されているか（2件）
    verify(repository, times(1)).registerStudentCourse(course1);
    verify(repository, times(1)).registerStudentCourse(course2);

    // 各 course に studentId、開始・終了日が設定されているか
    assertEquals("1", course1.getStudentId());
    assertNotNull(course1.getCourseStartAt());
    assertNotNull(course1.getCourseEndAt());

    assertEquals("1", course2.getStudentId());
    assertNotNull(course2.getCourseStartAt());
    assertNotNull(course2.getCourseEndAt());

    // 結果が元のオブジェクトであること（インスタンスの同一性ではなく、内容）
    assertEquals(studentDetail.getStudent().getId(), result.getStudent().getId());
    assertEquals(2, result.getStudentCourseList().size());
  }

  @Test
  void updateStudent_受講生と受講生コースが正しく更新されること() {
    // 準備
    Student student = new Student();
    student.setId("1");
    student.setName("テスト一郎");

    StudentCourse course1 = new StudentCourse();
    course1.setId("C1");
    course1.setStudentId("1");
    course1.setCourseName("Java入門");

    StudentCourse course2 = new StudentCourse();
    course2.setId("C2");
    course2.setStudentId("1");
    course2.setCourseName("Spring応用");

    List<StudentCourse> courseList = List.of(course1, course2);
    StudentDetail studentDetail = new StudentDetail(student, courseList);

    // 実行
    sut.updateStudent(studentDetail);

    // 検証：受講生の更新が1回呼ばれている
    verify(repository, times(1)).updateStudent(student);

    // 検証：コースの更新が2回（各1回ずつ）呼ばれている
    verify(repository, times(1)).updateStudentCourse(course1);
    verify(repository, times(1)).updateStudentCourse(course2);
  }

}
