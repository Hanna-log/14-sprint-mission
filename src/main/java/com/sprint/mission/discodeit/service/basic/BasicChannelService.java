package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
스프링이 @Controller, @Repository, @Service, @Component 달린 클래스를 찾아서
객체를 만들고 빈 컨테이너에 등록해둔다.
그 객체가 필요한 다른 곳(생성자 등)에 자동으로 넣어주는 걸 "주입"이라고 한다.
*/

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;  // private 채널 참여자 정보만들때 필요
    private final MessageRepository messageRepository;
    // 채널에 연관된 메세지 정보 삭제, ChannelDto를 만들 때 해당 채널의 가장 최근 메시지의 시간 정보 구하려고

    @Override // create 메서드 : 공개채널 만들기
    public ChannelDto createPublic(PublicChannelCreateRequest request) {
        Channel channel = Channel.builder()
            .type(ChannelType.PUBLIC)
            .channelName(request.name())
            .description(request.description())
            .build();
        channelRepository.save(channel);
        return toDto(channel);
    }

    @Override // create 메서드 : 비밀채널 만들기(name, description 없이 생성)
    public ChannelDto createPrivate(PrivateChannelCreateRequest request) {
        Channel channel = Channel.builder()
            .type(ChannelType.PRIVATE)
            .channelName(null)
            .description(null)
            .build();
        channelRepository.save(channel);

        /*
        PRIVATE 채널을 만들 때, 참여하는 사람 수만큼 ReadStatus를 하나씩 만들어주기
        Public 채널엔 ReadStatus를 안만드는 이유 -> 누구나 접근 가능해서 참여자의 개념이 없음.
        반면 PRIVATE 채널은 특정 사용자만 이용해서 readStatus를 이용해서
        이 채널의 참여자를 구분하는 목적으로 재활용하기 있기때문에 별도로 부여필요함.
        for문: 누구누구를 이 private 채널에 넣을지에 대한 정보 코드 반복해서 일어남
         */
        for (UUID userId : request.participantIds()) {
            // request dto에서 participantIds 아이디 뽑아서 참여자 아이디 만들어 저장
            ReadStatus readStatus = ReadStatus.builder()
                .userId(userId) // userId = 참여자 아이디 정보
                .channelId(channel.getId()) // 아까만든채널id
                .lastReadAt(Instant.now()) // 지금 시각
                .build();
            readStatusRepository.save(readStatus);
            // 저장소에 for문 반복돌은 것만큼 private 채널 참여자 정보가 쌓임.
        }
        return toDto(channel); // 참여자 등록 끝나서 완성된 channel정보를 Dto로 변환함.
    }

    @Override
    public Optional<ChannelDto> find(UUID id) {
        return channelRepository.findById(id)
            .map(this::toDto);
        /* Optional의 map 메소드라서 .toList(), .collect() 같은 최종연산자 안써도됨.
        스트림은 마지막 단계에 최종연산자가 필요한데 Optional은 map을 거치고나면
         Optional<ChannelDto> 반환타입 그 자체로 이미 완성된 결과물이 나옴.

         this::toDto → 지금 이 클래스 자신(BasicChannelService)이 가진 toDto 메소드를 가리킬 때 씀
         .map(this::toDto)는 .map(channel -> this.toDto(channel))

         ⭐️ Optional과 다르게 Stream은 최종 연산자를 써야하는 이유 ⭐️
         스트림은 담을 수 있는 데이터 갯수 - 여러개 / 반면 Optional은 그냥 없음과 있음(0과 1)

         아직 데이터 흐름 가공중 "상태" 종결 명령 필요(.toList(), .collect() 등)  / Optional은 그 자체로 이미 최종 결과
::스트림 최종 연산자 차이::
- .toList() : 지금까지 가공한 결과를, 수정 불가능한(immutable) List로 만들어 주는것
- .collect() : 수정 가능한 리스트 반환. (ArrayList라서 .add() 가능)
         */

    }

    @Override // 특정 유저가 참여한 채널 찾는 메서드
    public List<ChannelDto> findAllByUserId(UUID userId) {
        // PUBLIC 채널: 전체 조회
        List<Channel> publicChannels = channelRepository
            .findAllByType(ChannelType.PUBLIC);

        // 이 유저가 참여한 것만 (ReadStatus로 소속 채널 id를 먼저 구함)
        // ---> 나온 ReadStatus 객체에서 채널 아이디만 뽑아서 리스트 만들기.
        // 단점 : 이런식으로 하면 이 사용자의 채널들이 PUBLIC인지 PRIVATE인지 구분이 안됨. 아래 PRIVATE 채널 추출 과정 필요
        List<UUID> myPrivateChannelIds = readStatusRepository.findAllByUserId(userId).stream()
            .map(ReadStatus::getChannelId)
            .toList();

        // PrivateChannelIds는 뽑아서 만들었으니까 PRIVATE 채널 뽑아서 만들기
        List<Channel> myPrivateChannels = myPrivateChannelIds.stream()
            .map(channelRepository::findById)
            // 1단계 : 위에서 뽑아온 해당유저 참가 ChannelId로 채널 객체 뽑아오기
            // 이때 타입 Optional<Channel> + 빈값일 경우  Optional.empty들 공존하는 상태
            .filter(Optional::isPresent)
            // Optional::isPresent는 채널이 있니없니 확인(삭제된 것도 있을 수 있으니)
            // 살아있는 채널만 확인 후 empty 나머지 버림. filter(optional -> optional.isPresent()) 줄인것.
            // filter 거치면 Optional<Channel>만 남음
            .map(Optional::get)
            // 위에서filter로 값있는 것만 안전하게 걸려서
            // get메서드로 Stream<Channel>에서 -> channel객체 뽑기 (상자 벗기고 진짜 객체만 꺼냄)
            .filter(channel -> channel.getType() == ChannelType.PRIVATE)
            // 유저가 참여한 생존된 채널 중에서 private 채널만 남기기
            .toList();
        // Optional.empty()을 방지하는 이유, 아래 delete 메소드에서 readStatusRepository 내에 저장된
        // 채널 아이디, 유저 아이디 정보 전부 지우라고 만들어두긴했는데 혹시 몰라서 위에서 readStatusRepository에서
        // 채널 아이디 추출할 때 삭제된 채널 아이디도 딸려올까봐 한번더 거르려고 안전하게 방어로직 넣은거라고함.

        return Stream.concat(publicChannels.stream(),
                myPrivateChannels.stream()).map(this::toDto)
            .toList();
        /*
        findAllByUserId 메서드의 목적은 이 유저가 참가한 공개채널 + 비밀채널이 뭐가있는지 리스트 뽑는거라
        위에서 공개채널 리스트 + 비밀채널 리스트 두개 다 있으니까 둘다 모아주고 및 객체를 toDto로 변환하는 과정이 필요함.

        Stream.concat(publicChannels.stream(), myPrivateChannels.stream())
        이건 두 스트림을 concat 이어붙이는거임 두 스트림을 순서대로 이어서 앞스트림 흐르면 두번째 스트림 흘러서
        하나의 스트림으로 만들어줌.

        map(this::toDto) 합쳐진 Channel 각각을 ChannelDto로 바꿔치기함
         */
    }

    @Override // 채널 정보 수정 메소드
    public ChannelDto update(UUID id, ChannelUpdateRequest request) {
        // 수정하려는 채널 정보 넣어서 없으면 채널 없다고 에러던져
        Channel channel = channelRepository.findById(id)
            .orElseThrow(() -> new ChannelNotFoundException(id));

        // 채널이 PRIVATE일 경우 수정 안되고 에러가 터짐.
        channel.update(request.name(), request.description());
        /*
        Channel.update() 내부에서 PRIVATE이면 예외를 던지기로 메서드 만들어둠

        public class Channel extends UpdatableEntity {
          public String update(String channelName, String description) {
        if (this.type == ChannelType.PRIVATE) {       // <- 여기!
            throw new PrivateChannelUpdateException();
        }

        ⭐️ 왜 PRIVATE면 에러 발생되는 코드를 ChannelService가 아니라 Channel에 만들었을까? ⭐️
        둘다 어디 넣든 결과는 똑같지만 Channel에 넣는게 더 안전한 이유가 update()를 다른 어디서 호출하더라도
        이 규칙을 무조건 지키게 강제됨. ChannelService에만 검증을 넣으면 실수로 검증 로직이 없는
        다른 곳에서 channel.update(...)를 직접 부르면 규칙이 깨질 수 있음.

        */

        channelRepository.update(channel);
        return toDto(channel);
    }

    @Override // 채널 정보 삭제 메소드
    public void delete(UUID id) {
        // 삭제할 채널이 있는지 먼저 조회 후 없으면 채널 없다고 에러 던지기
        channelRepository.findById(id)
            .orElseThrow(() -> new ChannelNotFoundException(id));

        // 채널 아이디 넣어서 연관된 메세지 전부 삭제하기
        messageRepository.findAllByChannelId(id)
            .forEach(message -> messageRepository.delete(message.getId()));
        /*
        여기서 forEach는 아래 for문과 의미가 같음 / 최종 종결 연산자임. /
        한개마다 반복분 실행 필요할 때 쓰는 게 forEach

        List<Message> messages = messageRepository.findAllByChannelId(id);
        for (Message message : messages) {
        messageRepository.delete(message.getId());
        }
        */

        // 채널 아이디 넣어서 연관된 ReadStatus(읽은 상태) 전부 삭제하기
        readStatusRepository.findAllByChannelId(id)
            .forEach(readStatus -> readStatusRepository.delete(readStatus.getId()));

        channelRepository.delete(id);
    }

    /*
    ======= 공통적으로 들어가는 메서드 toDto 별도 추출하기 / 가독성, 유지보수성 강화 =======

    ⭐️ 공통 추출 메소드 만들때 static이 붙는 기준은? ⭐️
   "이 메소드가 자기 자신(클래스)의 필드를 쓰는가, 안 쓰는가"
   : 안 쓴다 → static 가능 (권장)
   : 쓴다 → static 불가능

   ChannelDto는 channel만으로 부족하고 다른 저장소를 사용해서 static 불가함

   ‼️ 앞으로 toDto류 메소드를 만들 때마다,
   **"이 메소드 안에서 this.무언가(필드)를 쓰고 있나?"**만 확인하면 됨! ‼️

    - 파라미터로 받은 값만 조립해서 DTO 만들면 → static
    - 저장소(~Repository)를 추가로 호출해서 뭔가 더 가져와야 하면 → static 불가

    */
    private ChannelDto toDto(Channel channel) {

        /*
        가장 최근 메시지 시간 구하기 :: 채널 목록에서 최근에 무슨 일 있었는지 한눈에 보여주려는 목적으로 만듬.
        이 채팅방 최근에 활발했나?
        📢 공지사항           마지막 메시지: 3분 전
        💬 자유게시판          마지막 메시지: 1시간 전
        🔒 팀 프로젝트방       마지막 메시지: 방금 전
        */

        Instant lastMessageAt = messageRepository
            .findAllByChannelId(channel.getId())
            // 이 채널(channel)에 속한 메시지를 전부 가져옴!
            // 결과 타입: List<Message> (메시지 여러 개가 든 리스트)
            .stream()
            .map(Message::getCreatedAt)
            /*
            Message 객체를, Message의 createdAt 값으로 바꿔치기
            message -> message.getCreatedAt()을 줄여 쓴것
            List<Message>가 "메시지 5개가 든 상자"라면,
            .map(Message::getCreatedAt)은 그 5개를 하나씩 열어서
            "작성 시각만 뽑아내고 나머지는 버리는" 작업
             */
            .max(Comparator.naturalOrder())
            /*
            이 시각들 중에서 가장 늦은(가장 최근) 시각 하나를 찾는 것임!
            .max(...): 스트림에서 최댓값을 구하는 메소드
            Comparator.naturalOrder(): "어떤 기준으로 크고 작음을 비교할지" 정하는 것
            Instant끼리는 "더 나중 시각일수록 더 큰 값"이라는 자연스러운 순서가 이미
             정의되어있으니 그 기준대로 그대로 비교하라는 뜻
             그러니까 "그 자연스러운 기준 그대로 비교해라"는 뜻이에요.
             (...) 자체가 결과를 Optional로 감싸서 리턴 (최대값이 있을 수도 없을 수도 있어서_
*/
            .orElse(null);
            /*
         Optional<Instant> 상자에서 값을 꺼내기
        값이 있으면 → 그 Instant 값을 그대로 꺼냄
        값이 없으면(메시지가 아예 없는 채널이면)
         → null을 대신 씀 ("최근 메시지 없음"을 null로 표현)


왜 max가 Optional을 리턴하도록 설계됐는지?
max는 스트림 안에서 "제일 큰 값 하나"를 찾는 메소드인데, 스트림이 비어있을 수 있음.
빈 스트림-> 큰값 없음 -> 비교할 값 자체가 없으니 답 존재X

Optional<T> max(Comparator<? super T> comparator)
맥스의 실제 시그니처 이렇게 생김

⭐ 참고로 비슷한 스트림 메소드들도 다 Optional을 씀 ⭐
findFirst() → Optional<T> --- 찾은 것중에서 가장 첫번째꺼 찾아줘 // 필터링 결과 없을 수있으니까 Optional<T>
findAny() → Optional<T> --- 찾은 것중에서 아무거나 던져줘 // 필터링 결과 없을 수있으니까 Optional<T>
max(...), min(...) → Optional<T>
reduce(...) (인자 하나짜리 버전) → Optional<T>

공통점: 전부 "스트림이 비어있으면 답을 줄 수 없는 상황"이
있는 메소드들이라 Optional로 감쌈

반대로 count()(개수 세기)는 빈 스트림이어도 0이라는 확실한 답이 있으니
Optional 없이 그냥 long(큰 수대비해서 이거쓴다고함)을 리턴함.

 */

        // PRIVATE 채널이면 참여자 id 목록 구하기, PUBLIC이면 빈 리스트
        List<UUID> participantIds = channel.getType() == ChannelType.PRIVATE
            ? readStatusRepository.findAllByChannelId(channel.getId()).stream()
            .map(ReadStatus::getUserId)
            .toList() : List.of(); // 거짓이면 빈 리스트 반환
        /* 삼항 연산자 >> PRIVATE 채널이면 A, 아니면 B
        List<UUID> participantIds = (조건) ? (참일 때 값) : (거짓일 때 값);

        channel.getType() == ChannelType.PRIVATE
        이건 enum끼리 비교하는건데 안전하게 값비교로 동작함 (enum은 값 1개 싱글턴)
         */

        // 🔒 팀 프로젝트방       마지막 메시지: 방금 전
        return new ChannelDto(
            channel.getId(), // 채널 고유 아이디
            channel.getType(), // PUBLIC인지 PRIVATE인지
            channel.getChannelName(), // 팀 프로젝트방 이름
            channel.getDescription(), // 채널방 설명
            participantIds, // 비밀방일 경우 참여자 확인하려고 유저 아이디 데이터값
            lastMessageAt // 방금 전 여부확인
        );

        /*
        Dto의 목적 : 무조건 최소한만"이 아니라, "엔티티를 그대로 노출하지 않고,
        클라이언트/화면이 실제로 필요로 하는 형태로 가공해서 준다"는 게 목적 맞게 만드는게 핵심 원칙임.
         */

    }

}
