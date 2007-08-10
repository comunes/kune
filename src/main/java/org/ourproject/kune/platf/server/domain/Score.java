package org.ourproject.kune.platf.server.domain;

import java.util.Collection;
import java.util.HashMap;

import javax.persistence.Embeddable;

@Embeddable
public class Score {
    private HashMap<User, Float> scores;

    public Score() {
	scores = new HashMap<User, Float>();
    }

    public float getAverage() {
	double sum = 0.0;
	Collection<Float> values = scores.values();
	for (Float score : values) {
	    sum += score;
	}
	return (float) sum / scores.size();
    }

    public HashMap<User, Float> getScores() {
	return scores;
    }

    public void setScores(final HashMap<User, Float> scores) {
	this.scores = scores;
    }
}
