package com.sprint.mission.discodeit.dto.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@JsonInclude(Include.NON_NULL)
public record MessageDto(
    UUID id,
    @JsonProperty("message_contents")
    String contents,
    UUID channelId,
    UUID authorId, // 작성자 아이디(사실상 userId와 동일)
    List<UUID> attachmentIds,
    /*
    이 메시지에 첨부된 파일(이미지, 문서 등)들을 가리키는 id 리스트
    첨부파일을 여러개 붙일 수있으니까 List 사용

    ⭐️ List<BinaryContent> 객체가 아니라 List<UUID> 아이디만 들고있는 이유 ⭐
    : Message를 조회할 때마다 첨부파일의 실제 이미지/파일 데이터가(bytes)
    통째로 딸려오면 무거우니까, "이 메시지엔 이런 첨부파일들이 딸려있다"는
     참조(id)만 갖고 있는 거임.
    */

    Instant createdAt
) {

}
