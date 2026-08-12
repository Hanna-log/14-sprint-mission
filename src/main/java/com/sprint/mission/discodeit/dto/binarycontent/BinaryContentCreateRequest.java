package com.sprint.mission.discodeit.dto.binarycontent;

// 프로필 이미지를 등록할 때 필요한 값들 (선택 사항이라 null일 수 있음)
// BinaryContentCreateRequest는 재료
// <-> BinaryContent는 id랑 createdAt 등 추가 필드가 있는 완성된 결과물
public record BinaryContentCreateRequest (
    /* private final */ String fileName,
    /* private final */ String contentType,
    /* private final */ byte[] bytes
) {
}

/* byte는 primitive이라 null이 안되는데 byte[]는 배열 참조형이라
null이 가능함~
 */
