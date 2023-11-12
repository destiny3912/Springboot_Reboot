package com.rookie.springbootreboot.config.api.code.status;

import com.rookie.springbootreboot.config.api.code.BaseErrorCode;
import com.rookie.springbootreboot.config.api.code.reason.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "Server Error, 서버파트에 문의 바랍니다."),
    // 리퀘스트 오류
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    // 시큐리티 관련
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "권한 오류, 인증 여부 확인해주세요"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "현 권한에 제한된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청한 리소스를 찾을 수 없습니다.");

    /**
     * <코드 작성 요령>
     * 예외가 터진 API 와 그 원인을 상세히 기술한다
     * 코드도 숫자만이 아닌 API를 잘 나타내는 접두사와 적절한 4자리의 번호로 지정한다
     * 예) 회원가입에서 리퀘스트 에러가 발생했을때
     *      메세지: 회원가입 API에서 리퀘스트 파라미터를 확인해주세요
     *      코드: SIGNUP4001 -> 400번대 코드의 첫번째 애러코드라는 의미 만약 2번째 코드가 필요하면 4002가 되겠다.
     * */

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReason getReason() {
        return ErrorReason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReason getReasonHttpStatus() {
        return ErrorReason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
