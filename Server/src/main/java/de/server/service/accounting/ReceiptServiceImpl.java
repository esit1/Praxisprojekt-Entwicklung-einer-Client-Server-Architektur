package de.server.service.accounting;

import de.server.model.accounting.ReceiptGoods;
import de.server.repository.accounting.EndOfTheDayRepository;
import de.server.repository.accounting.ReceiptGoodRepository;
import de.server.repository.goods.GoodsRepository;
import de.server.repository.userAndSelfServiceStand.SelfServiceStandRepository;
import de.server.repository.userAndSelfServiceStand.UserRepository;
import de.server.request.accounting.ReceiptGoodsRequest;
import de.server.response.accounting.ReceiptResponse;
import de.server.service.userAndSelfServiceStand.SelfServiceStandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Service class contains methods that are used to manage the receipt information.
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(de.server.service.goods.GoodsServiceImpl.class);
    /**
     * Service to handle self-service stand information.
     */
    private final SelfServiceStandService selfServiceStandService;

    /**
     * goodsRepository to handle goods information.
     */
    private final GoodsRepository goodsRepository;

    /**
     * selfServiceStandRepository to self-service-stand information.
     */
    private final SelfServiceStandRepository selfServiceStandRepository;

    /**
     * receiptGoodRepository to receipt goods information.
     */
    private final ReceiptGoodRepository receiptGoodRepository;

    /**
     * endOfTheDayRepository to end of the day information.
     */
    private final EndOfTheDayRepository endOfTheDayRepository;

    /**
     * userRepository to user information.
     */
    private final UserRepository userRepository;

    /**
     * Service to handle end of the day service information.
     */
    private final EndOfTheDayService endOfTheDayService;

    /**
     * The constructor of the class GoodsServiceImpl.
     *
     * @param selfServiceStandService    The self-service-stand service.
     * @param goodsRepository            The goods repository.
     * @param selfServiceStandRepository The self-service-stand repository.
     * @param receiptGoodRepository      The receipt good repository.
     * @param endOfTheDayRepository      The receipt endOfTheDay repository.
     * @param userRepository             The user repository.
     * @param endOfTheDayService         The end of the day service.
     */
    public ReceiptServiceImpl(SelfServiceStandService selfServiceStandService, GoodsRepository goodsRepository, SelfServiceStandRepository selfServiceStandRepository, ReceiptGoodRepository receiptGoodRepository, EndOfTheDayRepository endOfTheDayRepository, UserRepository userRepository, EndOfTheDayService endOfTheDayService) {
        this.selfServiceStandService = selfServiceStandService;
        this.goodsRepository = goodsRepository;
        this.selfServiceStandRepository = selfServiceStandRepository;
        this.receiptGoodRepository = receiptGoodRepository;
        this.endOfTheDayRepository = endOfTheDayRepository;
        this.userRepository = userRepository;
        this.endOfTheDayService = endOfTheDayService;
    }

    /**
     * Changes an receipt goods entry.
     *
     * @param receiptGoodsRequest receiptGoodsInput
     * @return true
     */
    @Override
    public boolean changeReceipt(ReceiptGoodsRequest receiptGoodsRequest) {

        ReceiptGoods entry = new ReceiptGoods();

        //self-service-stand number
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(receiptGoodsRequest.getSelfServiceStandName());

        entry.setReceiptGoodId(receiptGoodsRequest.getReceiptID());

        //Checks whether the attributes are not empty. Then these are set.
        if (receiptGoodsRequest.getReceiptDate() != null)
            entry.setDateReceipt(receiptGoodsRequest.getReceiptDate());

        if (receiptGoodsRequest.getGoodsID() != 0)
            entry.setGoodsNr(goodsRepository.findByGoodsId(receiptGoodsRequest.getGoodsID()));

        if (receiptGoodsRequest.getSelfServiceStandName() != null)
            entry.setSelfServiceStandNr(selfServiceStandRepository.findBySelfServiceStandNId(selfServiceStandNr));

        if (receiptGoodsRequest.getGoodsPieces() != 0)
            entry.setGoodsPieces(receiptGoodsRequest.getGoodsPieces());

        if (receiptGoodsRequest.getUserName() != null)
            entry.setUserNr(userRepository.findByUserName(receiptGoodsRequest.getUserName()));

        return true;
    }

    /**
     * Deletes an entry receipt from the table.
     *
     * @param receiptGoodsNr receipt nr
     * @return true
     */
    @Override
    public boolean deleteReceipt(int receiptGoodsNr) {

        ReceiptGoods receiptEntry = new ReceiptGoods();
        receiptEntry.setReceiptGoodId(receiptGoodRepository.findByReceiptGoodId(receiptGoodsNr).getReceiptGoodId());

        receiptGoodRepository.delete(receiptEntry);

        LOGGER.info("Receipt has been delete.");

        return true;
    }

    /**
     * Saves the new goods receipt.
     *
     * @param receiptGoodsRequest LocalDate receiptDate;
     *                            int goodsPieces;
     *                            String goodsName;
     *                            String userName;
     *                            String selfServiceStandName;
     * @return new receipt
     */
    @Override
    public ReceiptGoods newReceipt(ReceiptGoodsRequest receiptGoodsRequest) {


        checkSumGoodsAvailable(receiptGoodsRequest.getReceiptDate(), receiptGoodsRequest.getGoodsPieces(), receiptGoodsRequest.getGoodsID());

        ReceiptGoods newReceipt = new ReceiptGoods();

        //self-service-stand number
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(receiptGoodsRequest.getSelfServiceStandName());

        //Sets the attributes.
        newReceipt.setDateReceipt(receiptGoodsRequest.getReceiptDate());
        newReceipt.setGoodsNr(goodsRepository.findByGoodsId(receiptGoodsRequest.getGoodsID()));
        newReceipt.setSelfServiceStandNr(selfServiceStandRepository.findBySelfServiceStandNId(selfServiceStandNr));
        newReceipt.setGoodsPieces(receiptGoodsRequest.getGoodsPieces());
        newReceipt.setUserNr(userRepository.findByUserName(receiptGoodsRequest.getUserName()));

        //save new Saves the new goods receipt.
        receiptGoodRepository.save(newReceipt);

        return newReceipt;
    }

    /**
     * Returns a receipt entry.
     *
     * @param receiptGoodsNr receipt Id
     * @return one receipt good
     */
    public ReceiptGoods getOneReceiptEntry(int receiptGoodsNr) {
        return receiptGoodRepository.findByReceiptGoodId(receiptGoodsNr);
    }

    /**
     * Returns all incoming goods, a SelfServiceStand, sorted by date.
     *
     * @param selfServiceStandName selfServiceStandName
     * @return list receipt goods
     */
    public List<ReceiptResponse> getAllReceiptEntry(String selfServiceStandName) {
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(selfServiceStandName);

        List<ReceiptResponse> listWithReceipt = new ArrayList<>();

        for (ReceiptGoods entry : receiptGoodRepository.findBySelfServiceStandNr_SelfServiceStandNIdOrderByDateReceiptAsc(selfServiceStandNr)) {
            ReceiptResponse re = new ReceiptResponse(entry.getReceiptGoodId(), entry.getDateReceipt(), entry.getGoodsPieces(), entry.getGoodsNr().getGoodsNameNr().getGoodsName(), entry.getGoodsNr().getGoodsUnitNr().getUnitName(), entry.getUserNr().getUserName());
            listWithReceipt.add(re);
        }

        return listWithReceipt;
    }

    /**
     * Returns all goods receipts for a product, a self-service stand, sorted by date.
     *
     * @param selfServiceStandName selfServiceStandName
     * @return list receipt goods
     */
    public List<ReceiptGoods> getAllGoodsNameReceiptEntry(String selfServiceStandName, String goodsName) {
        int selfServiceStandNr = selfServiceStandService.getSelfServiceStandNr(selfServiceStandName);
        return receiptGoodRepository.findByGoodsNr_GoodsNameNr_GoodsNameAndSelfServiceStandNr_SelfServiceStandNId(goodsName, selfServiceStandNr);
    }

    /**
     * Method checks whether the number of goods available is greater than the outflow.
     *
     * @param receiptDate receiptDate
     * @param goodsPieces goodsPieces
     * @param goodsID     goodsID
     * @return boolean
     */
    public boolean checkSumGoodsAvailable(LocalDate receiptDate, int goodsPieces, int goodsID) {
        boolean checkAvailable = true;

        //Checks whether the number is negative
        if (goodsPieces < 0) {

            LocalDate yesterdayDate = receiptDate.minusDays(1);
            int previousSum = 0;

            //Determined is present, the number of goods from the previous day
            if (endOfTheDayRepository.existsByGoodsNr_GoodsIdAndGraduationDate(goodsID, yesterdayDate)) {
                previousSum = endOfTheDayRepository.findByGoodsNr_GoodsIdAndGraduationDate(goodsID, yesterdayDate).getGraduationCount();
            }

            previousSum = previousSum + endOfTheDayService.countGraduationGoods(receiptDate, goodsID);

            //Total available goods greater than goodsPices
            if (previousSum < (goodsPieces * -1)) {
                checkAvailable = false;
            }
        }
        return checkAvailable;
    }
}
