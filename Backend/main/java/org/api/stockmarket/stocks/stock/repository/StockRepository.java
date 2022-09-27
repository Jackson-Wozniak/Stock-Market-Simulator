package org.api.stockmarket.stocks.stock.repository;

import org.api.stockmarket.stocks.stock.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
