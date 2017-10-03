package dentist.office.service.entity.token;

import dentist.office.model.entity.token.VisitConfirmationToken;
import dentist.office.service.entity.GenericService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VisitConfirmationTokenService extends GenericService<VisitConfirmationToken, Long> {

    List<VisitConfirmationToken> getExpiredTokens();

    List<String> getNotConfirmedVisitTimesByDate(LocalDate localDate);

    VisitConfirmationToken getByToken(String token);

    VisitConfirmationToken getByVisitDateAndVisitTime(LocalDate date, LocalTime time);

}
