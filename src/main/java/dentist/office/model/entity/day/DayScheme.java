package dentist.office.model.entity.day;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class DayScheme {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    @ElementCollection
    @CollectionTable(name = "availableVisitTimes")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @OrderBy
    @NotNull
    private Set<LocalTime> availableVisitTimes;

    DayScheme() {

    }

    DayScheme(LocalDate date, Set<LocalTime> availableVisitTimes) {
        this.date = date;
        this.availableVisitTimes = availableVisitTimes;
    }

    public void addAvailableVisitTime(LocalTime newAvailableVisitTime) {

        if (!availableVisitTimes.contains(newAvailableVisitTime)) {
            availableVisitTimes.add(newAvailableVisitTime);
        }
    }

    public void removeAvailableVisitTime(LocalTime localTime) {

        if (availableVisitTimes.contains(localTime)) {
            availableVisitTimes.remove(localTime);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<LocalTime> getAvailableVisitTimes() {
        return availableVisitTimes;
    }

    public void setAvailableVisitTimes(Set<LocalTime> availableVisitTimes) {
        this.availableVisitTimes = availableVisitTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayScheme dayScheme = (DayScheme) o;

        return date.equals(dayScheme.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public String toString() {
        return "DayScheme{" +
                "date=" + date +
                ", availableVisitTimes=" + availableVisitTimes +
                '}';
    }
}
