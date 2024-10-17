package com.sheiladiz.dtos.friendship;

import com.sheiladiz.models.FriendshipStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestUpdateFriendship(
        @NotNull(message = "Es obligatorio seleccionar un perfil.")
        Long friendProfileId,
        FriendshipStatus status
) {
}
