package edu.wctc.stockpurchase.service;

import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.repo.StockPurchaseRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockPurchaseService {
    private StockPurchaseRepository repo;

    @Autowired
    public StockPurchaseService(StockPurchaseRepository spr) {
        this.repo = spr;
    }

    public void delete(int id) throws ResourceNotFoundException {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("id");
        }
    }

    public StockPurchase update(StockPurchase stock) throws ResourceNotFoundException{
        if (repo.existsById(stock.getId())) {
            return repo.save(stock);
        } else {
            throw new ResourceNotFoundException("id");
        }
    }

    public StockPurchase save(StockPurchase stock) {
        return repo.save(stock);
    }

    public List<StockPurchase> getAllStocks() {
        List<StockPurchase> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        return list;
    }

    public StockPurchase getStock(int id) throws ResourceNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id"));
    }


}
