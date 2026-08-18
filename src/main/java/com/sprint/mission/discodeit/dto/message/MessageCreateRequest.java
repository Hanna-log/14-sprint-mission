package com.sprint.mission.discodeit.dto.message;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record MessageCreateRequest(

    @NotBlank(message = "내용을 비워둘 수 없습니다.")
    /* private final */ String contents,
    /* private final */ UUID channelId, // 작성할 채널 아이디
    /* private final */ UUID authorId // 작성자 아이디(사실상 userId와 동일)

) {

}
