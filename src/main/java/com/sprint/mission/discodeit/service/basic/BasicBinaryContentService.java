package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.BinaryContentNotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContent create(BinaryContentCreateRequest request) {
        BinaryContent binaryContent = BinaryContent.builder()
            .fileName(request.fileName())
            .contentType(request.contentType())
            .bytes(request.bytes())
            .size(request.bytes().length) // bytes 길이로 size 자동 계산
            .build();

        binaryContentRepository.save(binaryContent);
        return binaryContent;
    }
    /*
⭐️ BasicBinaryContentService에서 create는 검증 로직을 안만드는 이유?
1. 남을 참조하는 필드가 있고, 그 대상이 실존하는지 보장해야 할 때
(ex : ReadStatus의 userId/channelId)

2. 비즈니스 규칙상 중복이 금지될 때 (UserStatus의 "한 유저당 하나만")

ㄴ BasicBinaryContentService는 이 조건에 안걸림.

     */

    @Override
    public Optional<BinaryContent> find(UUID id) {
        return binaryContentRepository.findById(id);
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        return binaryContentRepository.findAllByIdIn(ids);
    }

    @Override
    public void delete(UUID id) {
        binaryContentRepository.findById(id)
            .orElseThrow(() -> new BinaryContentNotFoundException(id));
        binaryContentRepository.delete(id);
    }
}
