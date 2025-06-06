package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Student {

  private String id;
  private String name;
  private String frigana;
  private String nickname;
  private String email;
  private String region;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;

  private List<StudentCourse> studentsCours;
}
