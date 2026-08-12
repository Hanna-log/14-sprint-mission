package com.sprint.mission.discodeit.dto.user;

// 유저 업데이트 요청건 DTO 생성(닉네임, 비번)
public record UserUpdateRequest(
    /* private final */   String nickName,  //<- 데이터 선언부
    /* private final */   String password
) {
    // 추가적인 메서드 넣고싶을 때 여기에 쓰면 됨.
}

/*
⭐️ record가 자동으로 만들어주는 것들 ⭐️
private final String nickName; // 접근제어자 고정
private final String password; // 접근제어자 고정

// 전체 생성자 만들어주기
public UserUpdateRequest(String nickName, String password) {
    this.nickName = nickName;
    this.password = password;
}

public String nickName() { <------읽기 함수(사실상 getter)
    return nickName;
}

public String password() { <------읽기 함수(사실상 getter)
    return password;
}

+++ equals(), hashCode(), toString() 포함

record 클래스는 private final라서 값 변경 불가함.
대신 요청 record Dto가 들어오면 거기 있는 값 참고해서 기존에 저장된
Entity 값 수정함.

기존 User(Entity)에
nickName = "철수", password = "1234" 값이 있으면

                요청 DTO(record)
                     │
              "영희", null로 변경해줘!
                     │
                     ▼
              ┌─────────────┐
              │   Service   │
              └─────────────┘
                 │       │
          "영희" │       │ null
                 ▼       ▼
             변경함    안 건드림
                 │
                 ▼
           기존 User(Entity)

           nickName = "영희"
           password = "1234"

 */