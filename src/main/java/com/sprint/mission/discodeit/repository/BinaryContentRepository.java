package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.List;
import java.util.UUID;

public interface BinaryContentRepository extends Repository<BinaryContent> {

    // 용량 줄이려고 BinaryContentId 만 따로 저장했는데
    // BinaryContent id 목록 여러개 받아서, 그 중에 저장된 거 다 찾아오기

    List<BinaryContent> findAllByIdIn(List<UUID> ids);

}