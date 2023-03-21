package com.blog.search.common.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MetaDto {

    @JsonProperty("total_count")
    private Long totalCount;

    @JsonProperty("is_end")
    private Boolean isEnd;
}
