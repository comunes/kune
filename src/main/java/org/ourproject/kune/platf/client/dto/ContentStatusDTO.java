package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum ContentStatusDTO implements IsSerializable {
    editingInProgress, submittedForEvaluation, publishedOnline, rejected, inTheDustbin
}