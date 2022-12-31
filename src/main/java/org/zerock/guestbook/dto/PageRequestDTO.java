package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;    // 검색을 위한 변수 추가
    private String keyword; // 검색을 위한 변수 추가

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
        // JPA의 경우 페이지 번호가 0부터 시작하여 일단 page -1 형태로 작성 TODO 추후 음수가 들어올 여지가 있어 수정
    }

}
