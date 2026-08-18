package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.DuplicateReadStatusException;
import com.sprint.mission.discodeit.exception.ReadStatusNotFoundException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    // ReadStatus 정보확인하는데 User랑 Channel데이터 필요해서 리포지토리 끌고옴.

    @Override
    public ReadStatusDto create(ReadStatusCreateRequest request) {

        // 1. 새로 만들기전에 User, Channel이 실제로 존재하는지 확인!
        // user 정보와 channel정보가 둘다 있어야 ReadStatus가 성립됨.
        userRepository.findById(request.userId())
            .orElseThrow(() -> new UserNotFoundException(request.userId()));
        channelRepository.findById(request.channelId())
            .orElseThrow(() -> new ChannelNotFoundException(request.channelId()));

        // 2. 같은 User + Channel 조합이 이미 있는지 확인
        List<ReadStatus> existing =
            readStatusRepository.findAllByUserId(request.userId());

        for (ReadStatus readStatus : existing) {
            if (readStatus.getChannelId().equals(request.channelId())) {
                throw new DuplicateReadStatusException();
            }
        }

        // 3. 위에 정보있는지, 중복여부 다 체크하고 새로 만들기
        ReadStatus readStatus = ReadStatus.builder()
            .userId(request.userId())
            .channelId(request.channelId())
            .build();
        readStatusRepository.save(readStatus);
        return toDto(readStatus);
    }

    @Override
    public Optional<ReadStatusDto> find(UUID id) {
        return readStatusRepository.findById(id)
            .map(BasicReadStatusService::toDto);
        /*
        여기를 BasicReadStatusService로 쓰는 이유 :
        BasicReadStatusService로 toDto 메소드는 파라미터로 이 클래스의 값을 안쓰고
        readStatus만 받아서 static 가능해서 static으로 선언했는데 이러니까
        toDto는 자기자신 객체를 쓸수없음(this)
        그 대신 static 메소드가 속한 클래스 이름 자체로 가리켜야 해서
        BasicReadStatusService::toDto가 된 거임.

        아래 toDto를 static 아니라 인스턴스 메소드(비-static)로 바꾸면
        .map(this::toDto)로 사용 가능함!

        인스턴스 메소드 (this가 필요) ->	인스턴스이름::메소드 또는 this::메소드
static 메소드 (클래스 자체에 속함, this 없음)->클래스이름::메소드

         */
    }

    @Override
    public List<ReadStatusDto> findAllByUserId(UUID userId) {
        List<ReadStatus> readStatuses =
            readStatusRepository.findAllByUserId(userId);

        List<ReadStatusDto> result = new ArrayList<>();
        for (ReadStatus readStatus : readStatuses) {
            result.add(toDto(readStatus));
            /*
            ReadStatus값이 뭉탱이로(리스트)로 있는 readStatuses에서
            하나하나 값 꺼내서 Dto로 바꿔줘야하니까
            readStatus(한개값, 알맹이 하나를 담을 임시 변수이름)에
            Dto로 바꿔서 result상자에 담음.
             */
        }
        return result;
    }

    @Override
    public ReadStatusDto update(UUID id, ReadStatusUpdateRequest request) {
        // 수정할 ReadStatus가 존재하는지 아이디넣어서 조회해보고 없으면 에러던지기
        ReadStatus readStatus = readStatusRepository.findById(id)
            .orElseThrow(() -> new ReadStatusNotFoundException(id));

        // 수정한 시간 업데이트
        readStatus.updateLastReadAt(request.lastReadAt());
        // 정보 업데이트
        readStatusRepository.update(readStatus);

        // 다시 객체 만들어서 반환
        return toDto(readStatus);
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.findById(id)
            .orElseThrow(() -> new ReadStatusNotFoundException(id));
        readStatusRepository.delete(id);
    }


    // 공통적으로 들어가는 ReadStatusDto 메서드 추출하기
    private static ReadStatusDto toDto(ReadStatus status) {
        return new ReadStatusDto(
            status.getId(),
            status.getUserId(),
            status.getChannelId(),
            status.getLastReadAt()
        );


    }
}
