package de.server.service.accounting;

import de.server.model.accounting.EndOfTheDay;
import de.server.request.accounting.EndOfTheDayRequest;
import de.server.response.accounting.EndOfTheDayResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface EndOfTheDayService {

    /**
     * This method helps to create the daily closing.
     *
     * @param endOfTheDayRequest LocalDate graduationDate; (Date of the end of the day.)
     *                           String codesName; (The name of the goods.)
     *                           String userName; (The name of the user.)
     *                           String selfServiceStandName; (The self-service-stan name.)
     *                           int graduationCount; (The counted number of goods still available.)
     * @param selfServiceStandNr The self-service-stand nr.
     * @return The created entry.
     */
    EndOfTheDay carryOutCompletion(EndOfTheDayRequest endOfTheDayRequest, int selfServiceStandNr);

    /**
     * This method determines the theoretical number of goods sold. Without loss of theft, damaged goods or the like.
     *
     * @param graduationDate  End of day date
     * @param goodsNr         The name of the goods.
     * @param graduationCount The amount counted at the end of the day.
     * @return The sold goods.
     */
    int calculateSoldGoods(LocalDate graduationDate, int goodsNr, int graduationCount);

    /**
     * This method determines the total price of the goods sold.
     *
     * @param goodsNr         The goods nr.
     * @param numberSoldGoods The number of goods sold.
     * @return Income from goods sold.
     */
    double calculateRevenue(int goodsNr, int numberSoldGoods);

    /**
     * This method determines the entire receipt of a product.
     *
     * @param graduationDate The graduation date.
     * @param goodsNr        The goods nr.
     * @return Number of goods
     */
    int countGraduationGoods(LocalDate graduationDate, int goodsNr);

    /**
     * This method changes an "end Of The Day" entry.
     *
     * @param endOfTheDayRequest new
     *                           LocalDate graduationDate; (Date of the end of the day.)
     *                           String codesName; (The name of the goods.)
     *                           String userName; (The name of the user.)
     *                           String selfServiceStandName; (The self-service-stan name.)
     *                           int graduationCount; (The counted number of goods still available.)
     * @param selfServiceStandNr The self-service-stand nr.
     * @param oldDate            The old Date.
     * @param oldGoodsName       The goods bane:
     * @return The new entry.
     */
    EndOfTheDay changeEntryEndOfDay(EndOfTheDayRequest endOfTheDayRequest, int selfServiceStandNr, LocalDate oldDate, String oldGoodsName);

    /**
     * Return al list with all end of the day entries
     *
     * @param selfServiceStandName The selfServiceStand name
     * @return list wit entry
     */
    List<EndOfTheDayResponse> listWithAllEntry(String selfServiceStandName);
}
