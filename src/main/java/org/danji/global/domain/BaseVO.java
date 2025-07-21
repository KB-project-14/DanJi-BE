package org.danji.global.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BaseVO {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
