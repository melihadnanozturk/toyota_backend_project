package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.model.entity.TerminalEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class TerminalResponse {

    private String name;
    private Boolean sofDelete;
    private Boolean pagination;
    private Boolean abilityToCreate;
    private Boolean abilityToUpdate;
    private Boolean abilityToDelete;

    public static TerminalResponse mappedEntityToResponse(TerminalEntity terminalEntity) {
        return TerminalResponse.builder()
                .name(terminalEntity.getName())
                .sofDelete(terminalEntity.getSofDelete())
                .pagination(terminalEntity.getPagination())
                .abilityToCreate(terminalEntity.getAbilityToCreate())
                .abilityToUpdate(terminalEntity.getAbilityToUpdate())
                .abilityToDelete(terminalEntity.getAbilityToDelete())
                .build();
    }
}
