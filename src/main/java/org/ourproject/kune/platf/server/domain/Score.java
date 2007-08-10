package org.ourproject.kune.platf.server.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Score {
    @OneToMany
    private Map<User, Float> scores;

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

    public Map<User, Float> getScores() {
	return scores;
    }

    public void setScore(final User user, final Float scoreValue) {
	scores.put(user, scoreValue);
    }

    public void setScores(final Map<User, Float> scores) {
	this.scores = scores;
    }
}
