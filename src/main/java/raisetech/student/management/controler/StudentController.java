package raisetech.student.management.controler;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.templateparser.text.TextParseException;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生詳細一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() throws TestException {
    throw new TestException("現在このAPIは利用できません。URLは「studentList」ではなく「students」を利用してください。");
  }

  /**
   * 受講生詳細の検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  @GetMapping("student/{id}")
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id){
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PostMapping("/registerStudent")
public ResponseEntity<StudentDetail> registerStudent(
    @RequestBody @Valid StudentDetail studentDetail){
  // 新規受講生情報を登録する処理を追加
  StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
  return ResponseEntity.ok(responseStudentDetail);
}

  /**
   * 受講生詳細の更新を行います。
   * キャンセルフラグの更新もここで行います（論理削除）
   *
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PutMapping ("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    // 新規受講生情報を登録する処理を追加
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  @ExceptionHandler(TestException.class)
  public  ResponseEntity<String> handleTestException(TestException ex){
   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

 }




