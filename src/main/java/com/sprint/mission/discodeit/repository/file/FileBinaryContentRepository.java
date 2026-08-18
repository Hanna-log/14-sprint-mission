package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
public class FileBinaryContentRepository extends FileRepository<BinaryContent>
    implements BinaryContentRepository {

    public FileBinaryContentRepository(
        @Value("${discodeit.repository.file-directory:.discodeit}") String fileDirectory
    ) {
        super(fileDirectory,"binaryContent");
    }

    @Override // id 목록(ids)을 받아서, 그 중에 실제로 저장돼 있는 것들만 찾아서 반환
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        return findAll().stream()
            .filter(binaryContent -> ids.contains(binaryContent.getId()))
            .toList();
    }

    /*
    findAll().stream() 써서 데이터 흐름에 1개 1개 binaryContent 자료들이 흐르는데
    ids는 List타입 아이디 뭉치들 말하는거니까 ids.contains 이걸 써서 이 리스트에 () 괄호안에 이 값이 있냐고
    물어봄. ids 아이디값 뭉치들이랑 binaryContent의 개별 자료 아이디1개값이랑 일치하면 넘어가서 다음 리스트에 담김.

    * id.equals(ids)	/ "id 하나" == "리스트 전체"	/ 항상 false (타입부터 안 맞음)
    * ids.contains(id) / "리스트 안에 이 id가 있니?"	/ 원하는 대로 true/false 판단됨

    - equals는 "이 둘이 완전히 똑같은 값이니?"를 1:1로 볼 때 씀
    - contains는 "이 컬렉션(리스트, 셋 등) 안에 이 값이 들어있니?"를 볼때 씀.

    여러개중 포함하는지 확인할 땐 contains

     */

}
