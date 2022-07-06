package com.gracefl.propfirm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefl.propfirm.domain.enumeration.TRADEDIRECTION;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mt4Trade.
 */
@Entity
@Table(name = "mt_4_trade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mt4Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ticket", precision = 21, scale = 2, nullable = false)
    private BigDecimal ticket;

    @Column(name = "open_time")
    private Instant openTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_type")
    private TRADEDIRECTION directionType;

    @Column(name = "position_size")
    private Double positionSize;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "open_price")
    private Double openPrice;

    @Column(name = "stop_loss_price")
    private Double stopLossPrice;

    @Column(name = "take_profit_price")
    private Double takeProfitPrice;

    @Column(name = "close_time")
    private Instant closeTime;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "taxes")
    private Double taxes;

    @Column(name = "swap")
    private Double swap;

    @Column(name = "profit")
    private Double profit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mt4Trades", "accountDataTimeSeries" }, allowSetters = true)
    private Mt4Account mt4Account;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mt4Trade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTicket() {
        return this.ticket;
    }

    public Mt4Trade ticket(BigDecimal ticket) {
        this.setTicket(ticket);
        return this;
    }

    public void setTicket(BigDecimal ticket) {
        this.ticket = ticket;
    }

    public Instant getOpenTime() {
        return this.openTime;
    }

    public Mt4Trade openTime(Instant openTime) {
        this.setOpenTime(openTime);
        return this;
    }

    public void setOpenTime(Instant openTime) {
        this.openTime = openTime;
    }

    public TRADEDIRECTION getDirectionType() {
        return this.directionType;
    }

    public Mt4Trade directionType(TRADEDIRECTION directionType) {
        this.setDirectionType(directionType);
        return this;
    }

    public void setDirectionType(TRADEDIRECTION directionType) {
        this.directionType = directionType;
    }

    public Double getPositionSize() {
        return this.positionSize;
    }

    public Mt4Trade positionSize(Double positionSize) {
        this.setPositionSize(positionSize);
        return this;
    }

    public void setPositionSize(Double positionSize) {
        this.positionSize = positionSize;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Mt4Trade symbol(String symbol) {
        this.setSymbol(symbol);
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getOpenPrice() {
        return this.openPrice;
    }

    public Mt4Trade openPrice(Double openPrice) {
        this.setOpenPrice(openPrice);
        return this;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getStopLossPrice() {
        return this.stopLossPrice;
    }

    public Mt4Trade stopLossPrice(Double stopLossPrice) {
        this.setStopLossPrice(stopLossPrice);
        return this;
    }

    public void setStopLossPrice(Double stopLossPrice) {
        this.stopLossPrice = stopLossPrice;
    }

    public Double getTakeProfitPrice() {
        return this.takeProfitPrice;
    }

    public Mt4Trade takeProfitPrice(Double takeProfitPrice) {
        this.setTakeProfitPrice(takeProfitPrice);
        return this;
    }

    public void setTakeProfitPrice(Double takeProfitPrice) {
        this.takeProfitPrice = takeProfitPrice;
    }

    public Instant getCloseTime() {
        return this.closeTime;
    }

    public Mt4Trade closeTime(Instant closeTime) {
        this.setCloseTime(closeTime);
        return this;
    }

    public void setCloseTime(Instant closeTime) {
        this.closeTime = closeTime;
    }

    public Double getClosePrice() {
        return this.closePrice;
    }

    public Mt4Trade closePrice(Double closePrice) {
        this.setClosePrice(closePrice);
        return this;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getCommission() {
        return this.commission;
    }

    public Mt4Trade commission(Double commission) {
        this.setCommission(commission);
        return this;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getTaxes() {
        return this.taxes;
    }

    public Mt4Trade taxes(Double taxes) {
        this.setTaxes(taxes);
        return this;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getSwap() {
        return this.swap;
    }

    public Mt4Trade swap(Double swap) {
        this.setSwap(swap);
        return this;
    }

    public void setSwap(Double swap) {
        this.swap = swap;
    }

    public Double getProfit() {
        return this.profit;
    }

    public Mt4Trade profit(Double profit) {
        this.setProfit(profit);
        return this;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Mt4Account getMt4Account() {
        return this.mt4Account;
    }

    public void setMt4Account(Mt4Account mt4Account) {
        this.mt4Account = mt4Account;
    }

    public Mt4Trade mt4Account(Mt4Account mt4Account) {
        this.setMt4Account(mt4Account);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mt4Trade)) {
            return false;
        }
        return id != null && id.equals(((Mt4Trade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mt4Trade{" +
            "id=" + getId() +
            ", ticket=" + getTicket() +
            ", openTime='" + getOpenTime() + "'" +
            ", directionType='" + getDirectionType() + "'" +
            ", positionSize=" + getPositionSize() +
            ", symbol='" + getSymbol() + "'" +
            ", openPrice=" + getOpenPrice() +
            ", stopLossPrice=" + getStopLossPrice() +
            ", takeProfitPrice=" + getTakeProfitPrice() +
            ", closeTime='" + getCloseTime() + "'" +
            ", closePrice=" + getClosePrice() +
            ", commission=" + getCommission() +
            ", taxes=" + getTaxes() +
            ", swap=" + getSwap() +
            ", profit=" + getProfit() +
            "}";
    }
}
