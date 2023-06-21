package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.model.entity.TerminalEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This class is used to response terminal information.
 * This class contains information field about to terminal
 * The class uses the Lombok annotations @Getter and @Builder to automatically generate getters and a builders,@EqualsAndHashCode to equals process.
 */
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

    /**
     * Maps the TerminalEntity to the TerminalResponse
     *
     * @param terminalEntity TerminalEntity to be mapped
     * @return TerminalResponse holding information mapped from terminalEntity
     */
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
