package xroigmartin.analyzcorp.finance.account.domain.exception;

import lombok.Getter;
import xroigmartin.analyzcorp.shared.logging.domain.enums.EventCategory;
import xroigmartin.analyzcorp.shared.logging.domain.enums.Outcome;
import xroigmartin.analyzcorp.shared.logging.domain.model.BusinessEvent;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final BusinessEvent event;

    protected BusinessException(String messge, BusinessEvent event) {
        super(messge);

        this.event = event != null
            ? event
                .withCategory(EventCategory.BUSINESS)
                .withOutcome(Outcome.FAIL)
            : null;
    }
}
