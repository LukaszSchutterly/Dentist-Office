package dentist.office.service.entity.token;

import dentist.office.model.entity.token.VisitConfirmationToken;
import dentist.office.repositories.VisitConfirmationTokenRepo;
import dentist.office.service.entity.GenericServiceImpl;
import dentist.office.service.entity.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class VisitConfirmationTokenServiceImpl extends GenericServiceImpl<VisitConfirmationToken, Long>
        implements VisitConfirmationTokenService {

    private VisitConfirmationTokenRepo visitConfirmationTokenRepo;
    private VisitService visitService;

    @Autowired
    public VisitConfirmationTokenServiceImpl(JpaRepository<VisitConfirmationToken, Long> jpaRepository, VisitService visitService) {
        super(jpaRepository);
        this.visitConfirmationTokenRepo = (VisitConfirmationTokenRepo) jpaRepository;
        this.visitService = visitService;
    }

    @Override
    public VisitConfirmationToken getByToken(String token) {
        return visitConfirmationTokenRepo.findByToken(token);
    }

    @Override
    public void saveOrUpdate(VisitConfirmationToken visitConfirmationToken) {
        visitService.saveOrUpdate(visitConfirmationToken.getVisit());
        visitConfirmationTokenRepo.save(visitConfirmationToken);
    }

    @Override
    public VisitConfirmationToken getByVisitDateAndVisitTime(LocalDate date, LocalTime time) {
        return visitConfirmationTokenRepo.findByVisitVisitDateAndVisitVisitTime(date, time);
    }

    @Override
    public List<VisitConfirmationToken> getExpiredTokens() {
        return visitConfirmationTokenRepo.findByExpiryDateTimeLessThan(LocalDateTime.now());
    }

    @Override
    public List<String> getNotConfirmedVisitTimesByDate(LocalDate localDate) {
        List<VisitConfirmationToken> visitConfirmationTokens = visitConfirmationTokenRepo.findByVisitVisitDate(localDate);
        List<String> notConfirmedVisitTimes = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        visitConfirmationTokens.forEach(vct -> {
            LocalTime visitTime = vct.getVisit().getVisitTime();

            notConfirmedVisitTimes.add(visitTime.format(formatter));
        });

        return notConfirmedVisitTimes;
    }
}
