package com.sprint.mission.discodeit.dto.readstatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ReadStatusCreateRequest(
    @NotNull(message = "유저ID를 비워둘 수 없습니다.")
    UUID userId,
    @NotNull(message = "채널ID를 비워둘 수 없습니다.")
    UUID channelId
) {

}
/*
@NotBlank : 문자열(String)의 빈 문자열/공백을 검사하는 검증

@NotNull :UUID는 문자열이 아니라서(문자열 처럼 빈칸을 가지지않음) NotNull이 더 의도에 적합
값 자체가 존재하나? 확인하는 용도


 */