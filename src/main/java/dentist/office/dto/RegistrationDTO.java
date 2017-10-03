package dentist.office.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dentist.office.model.DentalService;
import dentist.office.model.entity.patient.Patient;
import dentist.office.model.entity.visit.Visit;
import dentist.office.validation.AvailableVisitDateTime;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class RegistrationDTO {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @AvailableVisitDateTime
    private LocalDateTime visitDateTime;
    private DentalService dentalService;
    @NotBlank
    @Length(min = 3, max = 25)
    private String firstName;
    @Length(min = 3, max = 30)
    @NotBlank
    private String lastName;
    @Length(min = 7, max = 40)
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "[-+()0-9]{8,15}")
    private String phoneNumber;
    @Length(max = 400)
    private String description;

    public Visit makeVisit() {
        Patient patient = new Patient(this.firstName, this.lastName);
        patient.setEmail(this.email);
        patient.setPhoneNumber(this.phoneNumber);

        Visit visit = new Visit(this.visitDateTime.toLocalDate(), this.visitDateTime.toLocalTime(), patient);
        visit.setDescription(this.description);
        visit.setDentalService(this.dentalService);

        return visit;
    }

    public LocalDateTime getVisitDateTime() {
        return visitDateTime;
    }

    public DentalService getDentalService() {
        return dentalService;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "visitDateTime=" + visitDateTime +
                ", dentalService=" + dentalService +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
