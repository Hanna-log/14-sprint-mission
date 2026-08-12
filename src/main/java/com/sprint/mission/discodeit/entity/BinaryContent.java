package com.sprint.mission.discodeit.entity;

/*
BaseEntity만 상속 -> (updateAt 없음) 'BinaryContent'

BinaryContent는 이미지, 파일 같은 바이너리(binary) 데이터를 담아두는 별도의 도메인 모델!
파일 저장소" 역할을 하는 엔티티임. "파일 하나에 대한 모든 정보 + 실제 내용물"**을 담는 그릇.
"바이너리(binary)"라는 이름이 붙은 이유는, 이미지든 pdf든 zip이든 텍스트가 아닌 파일은 결국 0과 1로 이루어진 순수 데이터 덩어리(byte[])로 저장되기 때문

 User에서 유저 프로필 이미지 (User.profileId)
 Message에서 메시지에 첨부하는 파일들 (Message.attachmentIds)

 "파일 저장(ex 프로필 이미지 저장)/파일 조회/파일 삭제" 로직을 BinaryContentService 하나로 통일해서 처리함.
 회원 있는지 유무 같은 가벼운 조회를 할땐 User같은 객체만 보고 실제 파일이 필요할 때만
 BinaryContent를 따로 조회해서 가져옴. 이걸 User에 다 넣었더라면 회원 있는지만 조회하는건데
 다른 쓸모없는 것까지 다 딸려와서 무거웠을 것 그래서 분리함. User, Message는 파일을 직접
 들고있지않고 id로만 가르킴.

 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BinaryContent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    String fileName; // 원본 파일명 (예: "profile.jpg")
    long size; // 파일 크기 (바이트)
    String contentType; // 타입 (예: "image/jpeg", "application/pdf")
    byte[] bytes; // 실제 파일 내용물 (진짜 데이터 그 자체)

    private BinaryContent(String fileName, long size, String contentType, byte[] bytes) {
        super();
        this.fileName = fileName;
        this.size = size;
        this.contentType = contentType;
        this.bytes = bytes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String fileName;
        private long size;
        private String contentType;
        private byte[] bytes;

        public Builder fileName(String fileName){
            this.fileName = fileName;
            return this;
        }
        public Builder size(long size){
            this.size = size;
            return this;
        }
        public Builder contentType(String contentType){
            this.contentType = contentType;
            return this;
        }
        public Builder bytes(byte[] bytes){
            this.bytes = bytes;
            return this;
        }

            public BinaryContent build(){
            return new BinaryContent(this.fileName,this.size,this.contentType,this.bytes);
            }


    }


}
