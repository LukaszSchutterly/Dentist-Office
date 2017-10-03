package dentist.office.model.entity.visit;

import com.fasterxml.jackson.annotation.JsonFormat;
import dentist.office.model.DentalService;
import dentist.office.model.entity.patient.Patient;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Visit {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate visitDate;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime visitTime;
    @ManyToOne
    @Valid
    @NotNull
    private Patient patient;
    @Length(max = 400)
    private String description;
    private DentalService dentalService;
    private boolean confirmed;
    private boolean accepted;

    Visit() {

    }

    public Visit(@NotNull LocalDate visitDate, @NotNull LocalTime visitTime, @NotNull Patient patient) {
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public LocalTime getVisitTime() {
        return visitTime;
    }

    public void setPatient(@NotNull Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DentalService getDentalService() {
        return dentalService;
    }

    public void setDentalService(DentalService dentalService) {
        this.dentalService = dentalService;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visit visit = (Visit) o;

        if (id != visit.id) return false;
        if (visitDate != null ? !visitDate.equals(visit.visitDate) : visit.visitDate != null) return false;
        if (visitTime != null ? !visitTime.equals(visit.visitTime) : visit.visitTime != null) return false;
        return patient != null ? patient.equals(visit.patient) : visit.patient == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (visitDate != null ? visitDate.hashCode() : 0);
        result = 31 * result + (visitTime != null ? visitTime.hashCode() : 0);
        result = 31 * result + (patient != null ? patient.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", visitDate=" + visitDate +
                ", visitTime=" + visitTime +
                ", patient=" + patient +
                ", description='" + description + '\'' +
                ", dentalService=" + dentalService +
                ", confirmed=" + confirmed +
                ", accepted=" + accepted +
                '}';
    }
}
