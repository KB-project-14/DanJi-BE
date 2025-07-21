package org.danji.global.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDTO implements Serializable {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

