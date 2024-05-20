package org.api.stockmarket.stocks.stock.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.api.stockmarket.stocks.stock.enums.InvestorRating;
import org.api.stockmarket.stocks.stock.enums.MarketCap;
import org.api.stockmarket.stocks.stock.enums.Volatility;

import java.util.Random;

@Entity
@DiscriminatorValue(value = "SMALL_CAP")
public class SmallCapStock extends Stock{

    private static final Random random = new Random();

    public SmallCapStock(String ticker,
                 String companyName,
                 String sector,
                 MarketCap marketCap,
                 Volatility volatileStock,
                 InvestorRating investorRating) {
        super(ticker, companyName, sector, marketCap, volatileStock, investorRating);
    }

    public SmallCapStock() {

    }

    @Override
    public void updatePrice() {
        //Volatile stocks change twice to increase market movements
        double interval = random.nextDouble(-.003, .003);
        double positiveInterval = random.nextDouble(0, .00028);
        double newPrice = Math.round((getPrice() +
                (getPrice() * interval) +
                (getPrice() * (interval * this.getVolatileStock().ordinal())) +
                (this.getInvestorRating().investorRatingMultiplier() * positiveInterval) +
                (this.getMomentum() * positiveInterval)) * 100.00 ) / 100.00;
        setPrice(newPrice);
    }
}
