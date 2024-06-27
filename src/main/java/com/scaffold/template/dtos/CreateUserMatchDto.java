package com.scaffold.template.dtos;

import com.scaffold.template.models.MatchDifficulty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserMatchDto {
    private MatchDifficulty matchDifficulty;
}
