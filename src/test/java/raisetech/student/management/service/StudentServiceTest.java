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
import static org.mockito.ArgumentMatchers.any;



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
  void 受講生詳細検索_指定IDでリポジトリが呼び出されていること() {
    // 準備
    String studentId = "1";

    Student mockStudent = new Student();
    List<StudentCourse> mockCourseList = new ArrayList<>();

    when(repository.searchStudent(studentId)).thenReturn(mockStudent);
    when(repository.searchStudentCourse(studentId)).thenReturn(mockCourseList);

    // 実行
    sut.searchStudent(studentId);

    // 検証
    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourse(studentId);
    verify(converter, times(1)).convertStudentDetails(List.of(mockStudent), mockCourseList);
  }

  @Test
  void registerStudent_リポジトリの登録メソッドが呼ばれているかだけを確認() {
    // 準備
    Student student = new Student();
    List<StudentCourse> courseList = List.of(new StudentCourse(), new StudentCourse());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(courseList);

    // 実行
    sut.registerStudent(studentDetail);

    // 検証：メソッドが呼ばれているかだけ確認
    verify(repository).registerStudent(any(Student.class));
    verify(repository, times(courseList.size())).registerStudentCourse(any(StudentCourse.class));
  }



  @Test
  void updateStudent_リポジトリの更新メソッド呼び出し確認() {
    // 準備
    Student student = new Student();
    StudentCourse course1 = new StudentCourse();
    StudentCourse course2 = new StudentCourse();

    List<StudentCourse> courseList = List.of(course1, course2);
    StudentDetail studentDetail = new StudentDetail(student, courseList);

    // 実行
    sut.updateStudent(studentDetail);

    // 検証：リポジトリのメソッドが呼ばれたかだけ確認
    verify(repository, times(1)).updateStudent(any(Student.class));
    verify(repository, times(1)).updateStudentCourse(any(StudentCourse.class));
    verify(repository, times(1)).updateStudentCourse(any(StudentCourse.class));
  }

}
