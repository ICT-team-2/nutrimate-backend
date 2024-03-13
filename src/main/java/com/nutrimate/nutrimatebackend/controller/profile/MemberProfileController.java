package com.nutrimate.nutrimatebackend.controller.profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.model.board.InfoBoardDto;
import com.nutrimate.nutrimatebackend.model.board.PagingDto;
import com.nutrimate.nutrimatebackend.model.profile.MemberProfileDto;
import com.nutrimate.nutrimatebackend.service.board.feed.FeedService;
import com.nutrimate.nutrimatebackend.service.profile.MemberProfileService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/profile")
@Log4j2
public class MemberProfileController {

    MemberProfileService memberProfileService;
    FeedService feedService;
    ObjectMapper objectMapper = new ObjectMapper();

    public MemberProfileController(MemberProfileService memberProfileService,
            FeedService feedService, ObjectMapper objectMapper) {
        this.memberProfileService = memberProfileService;
        this.feedService = feedService;
        this.objectMapper = objectMapper;
    }

    // 유저의 정보 가져오기
    @GetMapping()
    public MemberProfileDto getMemberProfile(MemberProfileDto dto) {
        return memberProfileService.getMemberProfile(dto);
    }

    // 자신이 작성한 정보공유 게시글 목록 가져오기
    @GetMapping("/board/info")
    public Map<String, Object> findMyInfoBoardList(PagingDto dto) {
        dto.setMypage(true);
        List<InfoBoardDto> infoBoardList = memberProfileService.findInfoBoardList(dto);

        int count = memberProfileService.findWriteInfoBoardCountByUserId(dto.getUserId());
        dto.setTotalPage((int) Math.ceil((double) count / dto.getReceivePage()));

        Map<String, Object> result = objectMapper.convertValue(dto, HashMap.class);
        result.put("infoBoardList", infoBoardList);
        return result;
    }

    // 자신이 북마크한 정보공유 게시글 목록 가져오기
    @GetMapping("/board/info/bookmark")
    public Map<String, Object> findBookmarkInfoBoardList(PagingDto dto) {

        dto.setBookmark(true);
        List<InfoBoardDto> infoBoardList = memberProfileService.findInfoBoardList(dto);

        int count = memberProfileService.findBookmarkInfoBoardCountByUserId(dto.getUserId());
        log.info("findBookmarkInfoBoardList, count: " + count);
        dto.setTotalPage((int) Math.ceil((double) count / dto.getReceivePage()));

        Map<String, Object> result = objectMapper.convertValue(dto, HashMap.class);
        result.put("infoBoardList", infoBoardList);
        return result;
    }

    // 프로필 이미지 변경
    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> changeProfileImage(MemberProfileDto dto,
            @RequestParam("profileImage") List<MultipartFile> profileImage) throws IOException {

        if (profileImage == null || profileImage.isEmpty()) {
            return Map.of("result", "fail", "message", "파일이 없습니다.");
        }
        // 프로필 이미지 변경
        String fileName = memberProfileService.changeProfileImage(dto, profileImage);

        return Map.of("result", "success", "message", "프로필 이미지가 변경되었습니다.", "userProfile", fileName);
    }
}
