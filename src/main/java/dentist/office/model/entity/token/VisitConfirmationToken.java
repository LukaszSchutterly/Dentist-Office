package dentist.office.model.entity.token;

import dentist.office.model.entity.visit.Visit;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
public class VisitConfirmationToken {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @OneToOne(targetEntity = Visit.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Visit visit;
    private LocalDateTime expiryDateTime;

    VisitConfirmationToken() {

    }

    public VisitConfirmationToken(Visit visit) {
        this.visit = visit;
        this.expiryDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now()).plusMinutes(30);
        this.token = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Visit getVisit() {
        return visit;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitConfirmationToken that = (VisitConfirmationToken) o;

        if (!token.equals(that.token)) return false;
        return visit.equals(that.visit);
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + visit.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VisitConfirmationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", visit=" + visit +
                ", expiryDate=" + expiryDateTime +
                '}';
    }
}