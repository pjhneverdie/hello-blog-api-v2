package corp.pjh.hello_blog_v2.category.exception;

import corp.pjh.hello_blog_v2.common.dto.ExceptionInfo;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
public enum CategoryExceptionInfo implements ExceptionInfo {
    THUMBNAIL_UPLOAD_FAILED("THUMBNAIL_UPLOAD_FAILED", "썸네일 업로드 중 오류가 발생했습니다.", HttpStatusCode.valueOf(500)),
    THUMBNAIL_DELETE_FAILED("THUMBNAIL_DELETE_FAILED", "썸네일 삭제 중 오류가 발생했습니다.", HttpStatusCode.valueOf(500)),
    SAME_AS_PARENT_TITLE("SAME_AS_PARENT_TITLE", "이미 부모 카테고리에서 사용 중인 카테고리명입니다.", HttpStatusCode.valueOf(400)),
    SAME_AS_COMPANY_TITLE("SAME_AS_COMPANY_TITLE", "동일 계층에서 카테고리명은 중복될 수 없습니다.", HttpStatusCode.valueOf(400)),
    NO_SUCH_PARENT("NO_SUCH_PARENT", "부모 카테고리가 존재하지 않습니다.", HttpStatusCode.valueOf(400)),
    BROKEN_HIERARCHY("BROKEN_HIERARCHY", "계층 구조를 망가뜨리는 이동입니다.", HttpStatusCode.valueOf(400)),
    CACHING_FAILED("CACHING_FAILED", "카테고리 캐싱에 실패했습니다.", HttpStatusCode.valueOf(500)),
    UNREADABLE_CACHE("UNREADABLE_CACHE", "카테고리 캐시를 읽을 수 없습니다.", HttpStatusCode.valueOf(500)),
    PARENT_NEVER_DIE("PARENT_NEVER_DIE", "아직 하위 카테고리가 존재합니다.", HttpStatusCode.valueOf(400));

    @Override
    public String errorId() {
        return this.codeName;
    }

    @Override
    public String errorMessage() {
        return this.errorMessage;
    }

    @Override
    public HttpStatusCode httpStatusCode() {
        return this.httpStatusCode;
    }

    private final String codeName;
    private final String errorMessage;
    private final HttpStatusCode httpStatusCode;
}
