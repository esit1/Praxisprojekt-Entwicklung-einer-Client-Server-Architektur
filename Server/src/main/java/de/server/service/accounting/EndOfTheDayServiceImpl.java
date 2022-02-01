package de.server.service.accounting;

import de.server.model.accounting.EndOfTheDay;
import de.server.model.accounting.ReceiptGoods;
import de.server.repository.accounting.EndOfTheDayRepository;
import de.server.repository.accounting.ReceiptGoodRepository;
import de.server.repository.goods.GoodsRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandRepository;
import de.server.repository.userAndSelfServiceStand.UserRepository;
import de.server.request.accounting.EndOfTheDayRequest;
import de.server.response.accounting.EndOfTheDayResponse;
import de.server.service.goods.GoodsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class contains methods that are used to manage the end of the day information.
 */
@Service
public class EndOfTheDayServiceImpl implements EndOfTheDayService {

    /**
     * Service provides the goods logic.
     */
    private final GoodsService goodsService;

    /**
     * receiptGoodRepository to receipt information.
     */
    private final ReceiptGoodRepository receiptGoodRepository;

    /**
     * selfServiceStandRepository to self-service-stand information.
     */
    private final SelfServiceStandRepository selfServiceStandRepository;

    /**
     * endOfTheDayRepository to end ot the day information.
     */
    private final EndOfTheDayRepository endOfTheDayRepository;


    /**
     * goodsRepository to goods  information.
     */
    private final GoodsRepository goodsRepository;

    /**
     * userRepository to user information.
     */
    private final UserRepository userRepository;

    public EndOfTheDayServiceImpl(GoodsService goodsService, ReceiptGoodRepository receiptGoodRepository, SelfServiceStandRepository selfServiceStandRepository, EndOfTheDayRepository endOfTheDayRepository, GoodsRepository goodsRepository, UserRepository userRepository) {
        this.goodsService = goodsService;
        this.receiptGoodRepository = receiptGoodRepository;
        this.selfServiceStandRepository = selfServiceStandRepository;
        this.endOfTheDayRepository = endOfTheDayRepository;
        this.goodsRepository = goodsRepository;
        this.userRepository = userRepository;
    }


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
    @Override
    public EndOfTheDay carryOutCompletion(EndOfTheDayRequest endOfTheDayRequest, int selfServiceStandNr) {

        //goods nr
        int goodsNr = endOfTheDayRequest.getGoodsID();

        //determines the theoretical number of goods sold.
        int calculateSoldGoods = calculateSoldGoods(endOfTheDayRequest.getGraduationDate(), goodsNr, endOfTheDayRequest.getGraduationCount());

        EndOfTheDay entry = new EndOfTheDay();

        //set Attributes
        entry.setGraduationDate(endOfTheDayRequest.getGraduationDate());
        entry.setGoodsNr(goodsRepository.findByGoodsId(endOfTheDayRequest.getGoodsID()));
        entry.setUserNr(userRepository.findByUserName(endOfTheDayRequest.getUserName()));
        entry.setSelfServiceStandNr(selfServiceStandRepository.findBySelfServiceStandNId(selfServiceStandNr));
        entry.setGraduationCount(endOfTheDayRequest.getGraduationCount());
        entry.setGraduationSold(calculateSoldGoods);
        entry.setGraduationRevenue((int) calculateRevenue(goodsNr, calculateSoldGoods));

        endOfTheDayRepository.save(entry);

        return entry;
    }

    /**
     * This method determines the theoretical number of goods sold. Without loss of theft, damaged goods or the like.
     *
     * @param graduationDate  End of day date
     * @param goodsNr         The name of the goods.
     * @param graduationCount The amount counted at the end of the day.
     * @return The sold goods.
     */
    @Override
    public int calculateSoldGoods(LocalDate graduationDate, int goodsNr, int graduationCount) {

        //Yesterday's date.
        LocalDate dayYesterday = graduationDate.minusDays(1);

        //Initial inventory of the goods.
        int inventoryPreviousDay = 0;

        //Number of receipts of the goods.
        int countReceiptsGoods = countGraduationGoods(dayYesterday, goodsNr);


        //It is checked whether there is already an inventory from the previous day.
        if (endOfTheDayRepository.existsByGoodsNr_GoodsIdAndGraduationDate(goodsNr, graduationDate)) {
            //Inventory from the previous day
            inventoryPreviousDay = endOfTheDayRepository.findByGoodsNr_GoodsIdAndGraduationDate(goodsNr, graduationDate).getGraduationCount();
        }

        return inventoryPreviousDay + countReceiptsGoods - graduationCount;
    }

    /**
     * This method determines the total price of the goods sold.
     *
     * @param goodsNr         The goods nr.
     * @param numberSoldGoods The number of goods sold.
     * @return Income from goods sold.
     */
    @Override
    public double calculateRevenue(int goodsNr, int numberSoldGoods) {
        return goodsRepository.findByGoodsId(goodsNr).getGoodsPrice() * numberSoldGoods;
    }

    /**
     * This method determines the entire receipt of a product.
     *
     * @param graduationDate The graduation date.
     * @param goodsNr        The goods nr.
     * @return Number of goods
     */
    @Override
    public int countGraduationGoods(LocalDate graduationDate, int goodsNr) {

        int numberAccess = 0;
        List<ReceiptGoods> listAccess = receiptGoodRepository.findByDateReceiptAndGoodsNr_GoodsId(graduationDate, goodsNr);

        for (ReceiptGoods entry : listAccess) {
            numberAccess = numberAccess + entry.getGoodsPieces();
        }
        return numberAccess;
    }

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
    @Override
    public EndOfTheDay changeEntryEndOfDay(EndOfTheDayRequest endOfTheDayRequest, int selfServiceStandNr, LocalDate oldDate, String oldGoodsName) {

        //old entry
        EndOfTheDay changeEntry = endOfTheDayRepository.findByGraduationDateAndGoodsNr_GoodsNameNr_GoodsNameId(oldDate, goodsService.getGoodsNr(selfServiceStandNr, oldGoodsName));

        int goodsNr = endOfTheDayRequest.getGoodsID();

        int calculateSoldGoods = calculateSoldGoods(endOfTheDayRequest.getGraduationDate(), goodsNr, endOfTheDayRequest.getGraduationCount());

        if (endOfTheDayRequest.getUserName() != null)
            changeEntry.setUserNr(userRepository.findByUserName(endOfTheDayRequest.getUserName()));

        if (endOfTheDayRequest.getGraduationCount() != 0) {
            changeEntry.setGraduationCount(endOfTheDayRequest.getGraduationCount());
            changeEntry.setGraduationSold(calculateSoldGoods);
            changeEntry.setGraduationRevenue((int) calculateRevenue(goodsNr, calculateSoldGoods));
        }

        endOfTheDayRepository.save(changeEntry);

        return changeEntry;
    }

    /**
     * Return al list with all end of the day entries
     *
     * @param selfServiceStandName The selfServiceStand name
     * @return list wit entry
     */
    public List<EndOfTheDayResponse> listWithAllEntry(String selfServiceStandName) {

        List<EndOfTheDayResponse> listWithEndOfDay = new ArrayList<>();

        for (EndOfTheDay entry : endOfTheDayRepository.findBySelfServiceStandNrOrderByGraduationDateAsc(selfServiceStandRepository.findBySelfServiceStandName(selfServiceStandName))) {
            EndOfTheDayResponse endOfDay = new EndOfTheDayResponse(entry.getGraduationDate(), entry.getGoodsNr().getGoodsNameNr().getGoodsName(), entry.getGoodsNr().getGoodsUnitNr().getUnitName(), entry.getGraduationCount(), entry.getGraduationSold(), entry.getGraduationRevenue(), entry.getGoodsNr().getGoodsPrice());
            listWithEndOfDay.add(endOfDay);
        }

        return listWithEndOfDay;
    }

}
