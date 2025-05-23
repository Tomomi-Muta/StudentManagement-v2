package raisetech.student.management.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controler.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students,studentCourses));
    return "studentList";
  }

  @GetMapping("/StudentCourseList")
  public List<StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
  @GetMapping("/newStudent")
  public  String newStudent(Model model){
    model.addAttribute("studentDetail",new StudentDetail());
    return "registerStudent";
  }

@PostMapping("/registerStudent")
public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
  if(result.hasErrors()){
    return "registerStudent";
  }

  // 新規受講生情報を登録する処理を追加
  service.registerStudent(studentDetail);

  return "redirect:/studentList";
}

 }




