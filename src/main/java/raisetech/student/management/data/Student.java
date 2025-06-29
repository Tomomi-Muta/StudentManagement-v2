package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Student {

  @NotBlank
  @Pattern(regexp = "^\\d+$")
  private String id;

  @NotBlank
  private String name;

  @NotBlank
  private String frigana;

  @NotBlank
  private String nickname;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String region;

  private int age;

  @NotBlank
  private String gender;

  private String remark;

  private boolean isDeleted;

}
