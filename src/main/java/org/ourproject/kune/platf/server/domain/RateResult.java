package org.ourproject.kune.platf.server.domain;

public class RateResult {
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;

    public RateResult() {
        this(null, null, null);
    }

    public RateResult(Double rate, Integer rateByUsers, Double currentUserRate) {
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
