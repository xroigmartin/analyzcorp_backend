package xroigmartin.analyzcorp.finance.account.domain.model;

import lombok.Builder;

@Builder
public record Account(
    Long id,
    String name
){}
