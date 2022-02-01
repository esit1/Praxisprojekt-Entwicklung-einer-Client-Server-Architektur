package de.server.controller.accounting;

import de.server.model.accounting.ReceiptGoods;
import de.server.request.accounting.ReceiptGoodsRequest;
import de.server.response.accounting.ReceiptResponse;
import de.server.service.accounting.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class manages incoming and outgoing goods.
 */
@RestController
@RequestMapping("/receipt")
public class ReceiptGoodsController {

    /**
     * Logger of the GoodsNameController class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptGoodsController.class);

    /**
     * Service provides the receipt logic.
     */
    private final ReceiptService receiptService;

    public ReceiptGoodsController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * Saves a new goods receipt.
     *
     * @param receiptGoodsRequest LocalDate receiptDate;
     *                            int goodsPieces;
     *                            String goodsName;
     *                            String userName;
     *                            String selfServiceStandName;
     * @return new receipt and httpStatus
     */
    @PostMapping("/")
    private ResponseEntity<ReceiptGoods> receipt(@RequestBody ReceiptGoodsRequest receiptGoodsRequest) {

        //Method checks whether the number of goods available is greater than the outflow.
        if (receiptService.checkSumGoodsAvailable(receiptGoodsRequest.getReceiptDate(), receiptGoodsRequest.getGoodsPieces(), receiptGoodsRequest.getGoodsID())) {

            ReceiptGoods receipt = receiptService.newReceipt(receiptGoodsRequest);
            LOGGER.info("New goods receipt has been saved.");

            return new ResponseEntity<>(receipt, HttpStatus.OK);
        } else {

            LOGGER.info("New goods receipt has not saved.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Deletes an entry receipt from the table.
     *
     * @param receiptGoodsNr receiptGoodsNr
     * @return httpsStatus
     */
    @DeleteMapping("/{receiptGoodsNr}")
    private ResponseEntity<ReceiptGoods> receiptDelete(@PathVariable int receiptGoodsNr) {

        //delete receipt
        boolean receiptDelete = receiptService.deleteReceipt(receiptGoodsNr);

        if (receiptDelete) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Returns a receipt entry.
     *
     * @param receiptGoodsNr receipt Id
     * @return one receipt good
     */
    @GetMapping("/{receiptGoodsNr}")
    private ResponseEntity<ReceiptGoods> getOneReceipt(@PathVariable int receiptGoodsNr) {
        return new ResponseEntity<>(receiptService.getOneReceiptEntry(receiptGoodsNr), HttpStatus.OK);
    }

    /**
     * Returns all incoming goods, a SelfServiceStand, sorted by date.
     *
     * @param selfServiceStandName selfServiceStandName
     * @return list receipt goods
     */
    @GetMapping("/all/{selfServiceStandName}")
    private ResponseEntity<List<ReceiptResponse>> getAllReceipt(@PathVariable String selfServiceStandName) {

        return new ResponseEntity<>(receiptService.getAllReceiptEntry(selfServiceStandName), HttpStatus.OK);
    }

    /**
     * Returns all goods receipts for a product, a self-service stand, sorted by date.
     *
     * @param selfServiceStandName selfServiceStandName
     * @return list receipt goods
     */
    @GetMapping("/same/{selfServiceStandName}/{goodsName}")
    private ResponseEntity<Object> getAllGoodsNameReceipt(@PathVariable String selfServiceStandName, @PathVariable String goodsName) {

        return new ResponseEntity<>(receiptService.getAllGoodsNameReceiptEntry(selfServiceStandName, goodsName), HttpStatus.OK);
    }

    /**
     * Changes an receipt goods entry.
     *
     * @param receiptGoodsRequest receiptGoodsInput
     * @return HttpStatus
     */
    @PutMapping("/")
    private ResponseEntity<ReceiptGoods> receiptUpdate(@RequestBody ReceiptGoodsRequest receiptGoodsRequest) {

        boolean receiptChange = receiptService.changeReceipt(receiptGoodsRequest);

        if (receiptChange) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
