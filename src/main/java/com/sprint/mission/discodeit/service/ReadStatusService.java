package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusService {

    ReadStatusDto create(ReadStatusCreateRequest request);

    Optional<ReadStatusDto> find(UUID id);

    List<ReadStatusDto> findAllByUserId(UUID userId);

    ReadStatusDto update(UUID id, ReadStatusUpdateRequest request);

    void delete(UUID id);

    /*
    ⭐️ 똑같이 id 조회하는건데 find는 Optional 쓰고 findByUserId는 List인 이유 ⭐️

find로 검색하는건 ReadStatus 고유 식별자 아이디 검색하면 없음(0) 또는 있음(1)밖에 안뜸.
둘 중에 하나 가능하고 2개 이상 나올 수 없으므로 있을 수도 없을 수도 있지만, 있다면 반드시 하나인
상황 이므로 Optional이 맞음.

findByUserId의 경우 한 유저가 여러 채널을 참여할 수 있기 때문에, 참여한 채널 개수만큼
ReadStatus를 여러개 가질 수있음.

철수(userId=A)의 ReadStatus들:
- ReadStatus(userId=A, channelId=팀방)
- ReadStatus(userId=A, channelId=자유게시판)
- ReadStatus(userId=A, channelId=공지방)

그래서 userId로 검색하면 0개부터 몇개가 나올수 있는지 알수 없기때문에 List가 맞음.
userId로 검색해도 전혀 참여를 안해서 0이 나올 수있겠으나
이미 List는 없을 수도 있음을 내포해서 굳이 Optional을 쓸 필요가 없음.

List<ReadStatusDto> result = findAllByUserId(userId);
결과가 없으면 result는 null이 아니라 그냥 "빈 리스트"([])**가 나옴.
result.isEmpty()로 확인하면 되고, result.size()는 그냥 0이 됨.

     */

}
