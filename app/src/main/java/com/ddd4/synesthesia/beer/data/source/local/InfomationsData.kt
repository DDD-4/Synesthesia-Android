package com.ddd4.synesthesia.beer.data.source.local

enum class InfomationsData(val title : String,val type : InfomationsType) {
    ACTIVE("내활동",InfomationsType.HEADER),
    STAR_REVIEW("내가 준 별점과 리뷰",InfomationsType.ITEM),
    HELP("고객센터 & 도움말",InfomationsType.HEADER),
    NOTICE("공지사항",InfomationsType.ITEM),
    CONTACT("문의하기",InfomationsType.ITEM),
    SERVICE_INFO("서비스 정보",InfomationsType.HEADER),
    TERMS_OF_USE("이용약관",InfomationsType.ITEM),
    SETTING("설정",InfomationsType.HEADER),
    PUSH("알림설정",InfomationsType.ITEM),
    LOGOUT("로그아웃",InfomationsType.LOGOUT),
    UNLINK("회원탈퇴",InfomationsType.UNLINK)
}