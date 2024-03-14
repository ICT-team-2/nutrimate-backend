package com.nutrimate.nutrimatebackend.controller.board.diet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.nutrimate.nutrimatebackend.model.FileUtils;
import com.nutrimate.nutrimatebackend.model.board.diet.DietDto;
import com.nutrimate.nutrimatebackend.service.board.InfoBoardService;
import com.nutrimate.nutrimatebackend.service.board.diet.DietService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/board/info/diet")

public class DietController {

    @Value("${upload-path}")
    String phisicalPath;

    private DietService dietService;
    private InfoBoardService infoBoardService;

    @Autowired
    public DietController(DietService dietService, InfoBoardService infoBoardService) {
        this.dietService = dietService;
        this.infoBoardService = infoBoardService;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Map<String, String>> handle(Exception ex) {
        // ...
        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 게시글 전체 리스트
    @GetMapping("/list")
    public List<DietDto> dietBoardList(@ModelAttribute DietDto dto) {
        List<DietDto> dietList = dietService.selectListDietBoard(dto);
        return dietList;
    }


    // 게시글 상세 보기,이전글 다음글
    @GetMapping("/view")
    public DietDto dietBoardOne(DietDto dto) throws Exception {
        if (dto.getUpdateViewCount() != null && dto.getUpdateViewCount().equalsIgnoreCase("true")) {
            dietService.saveViewCount(dto);
        }
        DietDto dietOne = dietService.selectDietBoardOne(dto);// [0]이전글 [1]다음글 [2]상세보기
        if (dietOne == null) {
            throw new Exception("게시물이 존재하지 않습니다.");
        }
        log.info(dto);
        Map<String, Integer> prevAndNext =
                infoBoardService.findPrevAndNextByBoardId(dto.getBoardId(), "FOOD");
        log.info("prevAndNext: " + prevAndNext);
        if (prevAndNext != null) {
            dietOne.setPrevBoardId(prevAndNext.get("PREV_BOARD_ID") == null ? null
                    : Integer.parseInt(String.valueOf(prevAndNext.get("PREV_BOARD_ID"))));
            dietOne.setNextBoardId(prevAndNext.get("NEXT_BOARD_ID") == null ? null
                    : Integer.parseInt(String.valueOf(prevAndNext.get("NEXT_BOARD_ID"))));
        }
        return dietOne;
    }

    // 해당하는 보드의 해시태그네임 얻어오기
    @GetMapping("/hashtag")
    public ResponseEntity<List<Map<String, Object>>> hashTagFind(@ModelAttribute DietDto dto) {
        List<DietDto> hashtagList_ = dietService.selectHashTag(dto);
        List<Map<String, Object>> hashtagList = new ArrayList<>();
        for (DietDto hashtag : hashtagList_) {
            Map<String, Object> hashtag_ = new HashMap<>();
            hashtag_.put("tagName", hashtag.getTagName());
            hashtag_.put("tagId", hashtag.getTagId());
            hashtagList.add(hashtag_);
        }
        return new ResponseEntity<>(hashtagList, HttpStatus.OK);
    }


    // 게시글 입력
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // @RequestBody
    public Map writeBlock(DietDto dto, HttpServletRequest req,
            @RequestPart("files") List<MultipartFile> files) {
        StringBuffer fileNames = new StringBuffer();
        Map map = new HashMap();
        if (dto.getFoodId() == null) {
            map.put("writeOK", "음식 사진을 올려주세요.");
            return map;
        }

        try {
            fileNames = FileUtils.upload(files, phisicalPath);
            dto.setFbImg(fileNames.toString());
        } catch (Exception e) {// 파일용량 초과시
            map.put("writeOK", "파일용량 초과시/게시물 입력을 실패했습니다!!");
            return map;
        }
        if (dto.getTagNameList() != null) {
            int affected = dietService.saveBoardANDHashBoardANDHashTag(dto);
            if (affected == 1) {
                map.put("writeOK", "게시물 입력을 성공했습니다.");
                map.put("boardId", dto.getBoardId());
            } else {
                FileUtils.deletes(fileNames, phisicalPath, ",");
                map.put("writeOK", "게시물 입력을 실패했습니다!");
                return map;
            }

        } else {
            int affected = dietService.saveBoard(dto);
            if (affected == 1) {
                map.put("writeOK", "게시물 입력을 성공했습니다.");
                map.put("boardId", dto.getBoardId());
            } else {
                FileUtils.deletes(fileNames, phisicalPath, ",");
                map.put("writeOK", "게시물 입력을 실패했습니다!");
                return map;
            }
        }
        return map;
    }

    // 게시글 수정
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map editBoard(DietDto dto, HttpServletRequest req, List<MultipartFile> files) {
        Map map = new HashMap();
        StringBuffer fileNames = new StringBuffer();
        if (files != null) {
            try {
                DietDto titledto = dietService.selectDietBoardOne(dto);
                log.info(titledto);
                StringBuffer titledto_ = new StringBuffer(titledto.getFbImg());
                fileNames = FileUtils.upload(files, phisicalPath);
                FileUtils.deletes(titledto_, phisicalPath, ",");
                dto.setFbImg(fileNames.toString());
            } catch (Exception e) {// 파일용량 초과시
                map.put("EDITOK", "게시물 수정을 실패했습니다!");
                return map;
            }
        }
        if (dto.getTagNameList() != null) {
            int affected = dietService.editBoardANDHashBoardANDHashTag(dto);
            if (affected == 1) {
                map.put("EDITOK", "게시물 수정을 성공했습니다.");
            } else {
                FileUtils.deletes(fileNames, phisicalPath, ",");
                map.put("EDITOK", "게시물 수정을 실패했습니다!");
                return map;
            }
        } else {
            int affected = dietService.editBoard(dto);
            if (affected == 1) {
                map.put("EDITOK", "게시물 수정을 성공했습니다.");
            } else {
                FileUtils.deletes(fileNames, phisicalPath, ",");
                map.put("EDITOK", "게시물 수정에 실패했습니다!!");
            }
        }
        return map;
    }


    // 게시글 삭제
    @DeleteMapping("")
    public Map deleteBoard(@ModelAttribute DietDto dto) {
        Map map = new HashMap();
        int affected = dietService.deleteBoard(dto);
        if (affected == 1) {
            map.put("DELETEOK", "게시물 삭제에 성공했습니다.");
        } else {
            map.put("DELETEOK", "게시물 삭제를 실패했습니다!");
        }
        return map;

    }
    // 좋아요 입력 삭제
    // @PostMapping("/infoboard/likeboard/{boardId}")
    // public Map writeLike(@ModelAttribute DietDto dto) {
    // Map map = new HashMap();
    // int count = dietService.countLike(dto);//
    // if (count == 0) {
    // int affected = dietService.saveLikeBoard(dto);
    // if (affected == 1) {
    // map.put("LIKEOK", "좋아요 입력에 성공했습니다.");
    // } else {
    // map.put("LIKEOK", "종아요 입력에 실패했습니다!");
    //
    // }
    //
    // } else {
    // int affected = dietService.deleteLikeBoard(dto);
    // if (affected == 1) {
    // map.put("LIKEOK", "좋아요 입력 쥐소에 성공했습니다.");
    // } else {
    // map.put("LIKEOK", "종아요 입력 취소에 실패했습니다!");
    //
    // }
    // }
    // return map;
    // }
    //
    // //북마크 입력 삭제
    // @PostMapping("/infoboard/bookmark/{boardId}")
    // public Map writeBookmark(@ModelAttribute DietDto dto) {
    // Map map = new HashMap();
    // int count = dietService.countBookMark(dto);//
    // if (count == 0) {
    // int affected = dietService.saveBookMarkBoard(dto);
    // if (affected == 1) {
    // map.put("BOOKMARKOK", "북마크 입력에 성공했습니다.");
    // } else {
    // map.put("BOOKMARKOK", "북마크 입력에 실패했습니다!");
    //
    // }
    //
    // } else {
    // int affected = dietService.deleteBookMarkBoard(dto);
    // if (affected == 1) {
    // map.put("BOOKMARKOK", "북마크 취소에 성공했습니다.");
    // } else {
    // map.put("BOOKMARKOK", "북마크 취소에 실패했습니다!");
    //
    // }
    // }
    // return map;
    // }


}
