package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name ="discodeit.repository.type", havingValue = "file")
public class FileReadStatusRepository extends FileRepository<ReadStatus> implements
    ReadStatusRepository {

    public FileReadStatusRepository(
        @Value("${discodeit.repository.file-directory:.discodeit}") String fileDirectory) {
        super(fileDirectory, "readStatus");
    }

    @Override // 특정 유저의 읽음 상태 전체 찾기 (유저가 속한 채널마다 하나씩 있음)
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return findAll().stream()
            .filter(readStatus -> readStatus.getUserId().equals(userId))
            .toList();
    }

    @Override // 특정 채널의 읽음 상태 전체 찾기 (채널에 속한 유저마다 하나씩 있음)
    public List<ReadStatus> findAllByChannelId(UUID channelId) {
        return findAll().stream()
            .filter(readStatus -> readStatus.getChannelId().equals(channelId))
            .toList();
    }
}
