package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BinaryContentService {

    BinaryContent create(BinaryContentCreateRequest request);

    Optional<BinaryContent> find(UUID id);

    List<BinaryContent> findAllByIdIn(List<UUID> ids);

    void delete(UUID id);

}

/*
BinaryContentDto는 따로 안만듬.
비밀번호같은 민간 정보없고 그냥 파일 던져주는거라서.
 */
