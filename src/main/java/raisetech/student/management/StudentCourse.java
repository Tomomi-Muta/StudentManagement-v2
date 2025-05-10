package raisetech.student.management;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

    private String id;          // コースID
    private String courseName; // コース名
    private String startDate;  // 開始日
    private String endDate;    // 終了日
    private String studentId;  // 学生ID（このコースを受講する学生）




}