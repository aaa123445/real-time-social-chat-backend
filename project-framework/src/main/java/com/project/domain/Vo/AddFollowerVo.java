package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFollowerVo {
    private Long beAccountId;
    private Long AccountId;
}
