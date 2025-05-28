package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudent();
  }

  public List<StudentsCourses> searchStudentCourseList() {
    return repository.searchStudentCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    //TODO：コース情報登録も行う
    for(StudentsCourses studentsCourse:studentDetail.getStudentsCourses()){
     studentsCourse.setStudentId(studentDetail.getStudent().getId());
     studentsCourse.setCourseStartAt(LocalDateTime.now());
     studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
     repository.registerStudentsCourses(studentsCourse);
    }
   }
  }


