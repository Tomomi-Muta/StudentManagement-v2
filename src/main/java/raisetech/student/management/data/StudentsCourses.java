package raisetech.student.management.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

    private String id;          // コースID
    private String courseName; // コース名
    private LocalDateTime courseStartAt;  // 開始日
    private LocalDateTime courseEndAt;    // 終了日
    private String studentId;  // 学生ID（このコースを受講する学生）




}