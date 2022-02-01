package de.server.repository.accounting;

import de.server.model.accounting.EndOfTheDay;
import de.server.model.userAndSelfServiceStand.SelfServiceStand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EndOfTheDayRepository extends JpaRepository<EndOfTheDay, Integer> {

    EndOfTheDay findByGraduationDateAndGoodsNr_GoodsNameNr_GoodsNameId(LocalDate graduationDate, Integer goodsNameId);

    EndOfTheDay findByGoodsNr_GoodsIdAndGraduationDate(Integer goodsId, LocalDate graduationDate);

    boolean existsByGoodsNr_GoodsIdAndGraduationDate(Integer goodsId, LocalDate graduationDate);

    //list with all entry
    List<EndOfTheDay> findByOrderByGraduationDateAsc();

    List<EndOfTheDay> findBySelfServiceStandNr_SelfServiceStandNameOrderByGraduationDateAsc(String selfServiceStandName);

    List<EndOfTheDay> findBySelfServiceStandNrOrderByGraduationDateAsc(SelfServiceStand selfServiceStandNr);


}