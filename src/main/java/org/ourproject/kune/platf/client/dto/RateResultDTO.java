package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RateResultDTO implements IsSerializable {
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;

    public RateResultDTO() {
        this(null, null, null);
    }

    public RateResultDTO(Double rate, Integer rateByUsers, Double currentUserRate) {
        this.rate = rate;
        this.rateByUsers = rateByUsers;
        this.currentUserRate = currentUserRate;
    }

    public Double getCurrentUserRate() {
        return currentUserRate;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getRateByUsers() {
        return rateByUsers;
    }

    public void setCurrentUserRate(Double currentUserRate) {
        this.currentUserRate = currentUserRate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setRateByUsers(Integer rateByUsers) {
        this.rateByUsers = rateByUsers;
    }
}
