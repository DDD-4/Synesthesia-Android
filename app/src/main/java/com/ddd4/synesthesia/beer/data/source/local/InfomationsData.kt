package com.ddd4.synesthesia.beer.data.source.local

enum class InfomationsData(val title : String,val type : InfomationsType) {
    ACTIVE("내활동",InfomationsType.HEADER),
    REVIEW("내가 준 별점과 리뷰",InfomationsType.ITEM),
    RECENTLY_VISIT_TIME("최근 접속한 시간",InfomationsType.TEXT),
    HELP("고객센터 & 도움말",InfomationsType.HEADER),
    NOTICE("공지사항",InfomationsType.ITEM),
    CONTACT("문의하기",InfomationsType.ITEM),
    SERVICE_INFO("서비스 정보",InfomationsType.HEADER),
    TERMS_OF_USE("이용약관",InfomationsType.ITEM),
    RELEASE_NOTE("릴리즈 노트",InfomationsType.ITEM),
    OPEN_SOURCE_LIB("오픈소스 라이브러리",InfomationsType.ITEM),
    PLAY_STORE("플레이 스토어 평가",InfomationsType.ITEM),
    APP_VERSION("앱 버전",InfomationsType.TEXT),
    SETTING("설정",InfomationsType.HEADER),
    PUSH("알림설정",InfomationsType.ITEM),
    LOGOUT("로그아웃",InfomationsType.LOGOUT),
    UNLINK("회원탈퇴",InfomationsType.UNLINK)
}