package edu.wctc.stockpurchase.controller;

import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.service.StockPurchaseService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/stockpurchases")
public class StockPurchaseController {

    private StockPurchaseService service;

    @Autowired
    public StockPurchaseController(StockPurchaseService sps) {
        this.service = sps;
    }

    @GetMapping
    public List<StockPurchase> getStocks() {
        return service.getAllStocks();
    }

    @PostMapping
    public StockPurchase createStock (@RequestBody StockPurchase newStock) {
        newStock.setId(0);
        return service.save(newStock);
    }

    @DeleteMapping("/{stockId}")
    public String deleteStock(@PathVariable String stockId) {
        try{
            int id = Integer.parseInt(stockId);
            service.delete(id);
            return "Stock deleted: ID " + stockId;
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Stock ID must be a number", e);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }

    @PutMapping
    public StockPurchase updateStock(@RequestBody StockPurchase stock){
        try{
            return service.update(stock);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }

    @GetMapping("/{stockId}")
    public StockPurchase getStock(@PathVariable String stockId) {
        try{
            int id = Integer.parseInt(stockId);
            return  service.getStock(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Stock ID must be a number", e);
        } catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }
}
