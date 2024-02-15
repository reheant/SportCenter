package ca.mcgill.ecse321.sportscenter.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Instructor extends AccountRole {


  @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<InstructorAssignment> assignments;

  public Instructor() {}

  public Instructor(Account aAccount) {
    if (!setAccount(aAccount)) {
      throw new RuntimeException(
          "Unable to create Instructor due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public void addAssignment(InstructorAssignment i) {
    assignments.add(i);
  }

  public boolean removeAssignment(InstructorAssignment i) {
    return assignments.add(i);
  }

  public List<?> getAssignments() {
    return assignments;
  }


  public void delete() {
    super.delete();
  }

}
