package org.scoula.common.pagination;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// Getter, Setter, toString, equals, hashCode 자동 생성
@AllArgsConstructor(access = AccessLevel.PRIVATE)
// 모든 필드를 파라미터로 갖는 생성자를 private으로 생성
public class PageRequest {
    private int page; // 요청 페이지
    private int amount; // 한 페이지 당 데이터 건수

    public PageRequest() {
        page = 1;
        amount = 10;
    }

    public static PageRequest of(int page, int amount) {
        return new PageRequest(page, amount);
    }

    public int getOffset() { // offset 값 계산
        return (page - 1) * amount;
    }
}
