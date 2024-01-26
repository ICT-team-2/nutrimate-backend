package com.nutrimate.nutrimatebackend.controller.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.board.FeedDto;
import com.nutrimate.nutrimatebackend.model.board.FileUtils;
import com.nutrimate.nutrimatebackend.service.board.FeedService;

@RestController
@RequestMapping("/api/board/Feed")
public class FeedControlloer {

  @Autowired
  private FeedService feedService;

  @Value("C://Temp/upload")
  private String saveDirectory;

  /** 피드 **/
  // 피드 글목록 가져오기 (완료)
  // 입력 데이터 : nowPage(디폴트1), receivePage(디폴트10)
  // 출력 데이터 : boardId, thumbnail, nowPage, receivePage, totalPages, totalRecordCount
  @GetMapping("/FeedList")
  public ResponseEntity<List<Map<String, Object>>> findFeedList(
      @RequestParam(defaultValue = "1") int nowPage,
      @RequestParam(defaultValue = "10") int receivePage) {
    int totalRecordCount = feedService.findFeedtotalRecordCount();
    // 페이징 계산
    int totalPages = (int) Math.ceil((double) totalRecordCount / receivePage);
    int startRow = (nowPage - 1) * receivePage + 1;
    int endRow = nowPage * receivePage;
    // 피드 목록 가져오기
    List<FeedDto> FeedList = feedService.findFeedList(startRow, endRow);
    // 각 피드에 페이징 정보 추가
    List<Map<String, Object>> simplifiedFeedList = new ArrayList<>();
    for (FeedDto feed : FeedList) {
      Map<String, Object> simplifiedFeed = new HashMap<>();
      simplifiedFeed.put("boardId", feed.getBoardId());
      simplifiedFeed.put("thumbnail", feed.getBoardThumbnail());
      simplifiedFeed.put("nowPage", nowPage);
      simplifiedFeed.put("receivePage", receivePage);
      simplifiedFeed.put("totalPages", totalPages);
      simplifiedFeed.put("totalRecordCount", totalRecordCount);
      simplifiedFeedList.add(simplifiedFeed);
    }
    return new ResponseEntity<>(simplifiedFeedList, HttpStatus.OK);
  }

  // 피드 상세보기 정보 가져오기 (완료)
  // 댓글 수는 제외하고 조회(상세보기 화면이니까:해당 글번호의 모든 댓글 뿌리니까)
  // 해시 태그는 제외하고 조회(상세보기 화면이니까:해당 글번호의 모든 해시태그 뿌리니까)
  // 좋아요, 북마크 누른 여부는 제외하고 조회(따로 확인)
  // 입력 데이터 : boardId
  // 출력 데이터 : boardId, userNick, userProfile, createdDate, boardThumbnail, boardViewCount,
  // LIKE_COUNT
  @GetMapping("/findFeedDetail")
  public ResponseEntity<List<Map<String, Object>>> findFeedDetail(@RequestBody FeedDto feedDto) {
    List<FeedDto> FeedList = feedService.findFeedDetail(feedDto);
    List<Map<String, Object>> detailfiedFeedList = new ArrayList<>();
    for (FeedDto feed : FeedList) {
      Map<String, Object> detailfiedFeed = new HashMap<>();
      detailfiedFeed.put("boardId", feed.getBoardId());
      detailfiedFeed.put("userNick", feed.getBoardThumbnail());
      detailfiedFeed.put("userProfile", feed.getUserProfile());
      detailfiedFeed.put("createdDate", feed.getCreatedDate());
      detailfiedFeed.put("Thumbnail", feed.getBoardThumbnail());
      detailfiedFeed.put("ViewCount", feed.getBoardViewCount()); // 조회수
      detailfiedFeed.put("LIKE_COUNT", feed.getLikeCount()); // 좋아요 수
      detailfiedFeedList.add(detailfiedFeed);
    }
    return new ResponseEntity<>(detailfiedFeedList, HttpStatus.OK);
  }

  // 피드의 조회수를 +1하기 (완료)
  // 입력 데이터 : boardId
  // 출력 데이터 : message, boardViewCount
  @PutMapping("/updateincreaseViewCount")
  public ResponseEntity<Map<String, Object>> updateincreaseViewCount(@RequestBody FeedDto feedDto) {
    feedService.updateincreaseViewCount(feedDto);
    int boardViewCount = feedDto.getBoardViewCount();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "boardViewCount Update successfully");
    jsonResponse.put("boardViewCount", boardViewCount);
    return ResponseEntity.ok(jsonResponse);
  }

  // 피드 작성 (완료)
  // 입력 데이터 : userId, boardTitle, boardContent, files, hashtag
  // 이미지 저장 경로 : C://Temp/upload
  // 해시태그 입력 예시 : "hashtag" : "태그1, 태그2"
  // multipart/form-data로 보낸다
  // 출력 데이터 : message, boardId
  @PostMapping(value = "/insertFeed", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Map insertFeed(FeedDto dto, @RequestParam String userId, HttpServletRequest req) {
    String phisicalPath = "C://Temp/upload";
    StringBuffer fileNames = new StringBuffer();
    System.out.println(phisicalPath);
    Map map = new HashMap();
    if (dto.getFiles() == null) {
      map.put("WriteOK", "사진을 올려주세요.");
      return map;
    }
    try {
      System.out.println("dto.getFiles()" + dto.getFiles());
      fileNames = FileUtils.upload(dto.getFiles(), phisicalPath);
      dto.setBoardThumbnail(fileNames.toString());
    } catch (Exception e) {// 파일용량 초과시
      map.put("message", "게시물 입력을 실패했습니다!");
      return map;
    }
    try {
      feedService.insertFeed(dto);
      int boardId = dto.getBoardId();
      map.put("message", "게시물 입력을 성공했습니다.");
      map.put("boardId", boardId);
    } catch (Exception e) {
      FileUtils.deletes(fileNames, phisicalPath, ",");
      map.put("message", "게시물 입력을 실패했습니다!");
    }
    return map;
  }

  // 피드 수정
  // 입력 데이터 : boardId, userId, boardTitle, boardContent, boardThumbnail, hashtag
  // 출력 데이터 : message, boardId
  @PutMapping(value = "/updateFeed", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Map updateFeed(FeedDto dto, @RequestParam String userId, HttpServletRequest req) {
    // feedService.updateFeed(feedDto);
    // int boardId = feedDto.getBoardId();
    // Map<String, Object> jsonResponse = new HashMap<>();
    // jsonResponse.put("message", "Feed Update successfully");
    // jsonResponse.put("boardId", boardId);
    // return ResponseEntity.ok(jsonResponse);
    Map<String, Object> map = new HashMap<>();

    try {
      if (dto.getFiles() != null) {
        String phisicalPath = "C://Temp/upload";
        StringBuffer fileNames = new StringBuffer();

        fileNames = FileUtils.upload(dto.getFiles(), phisicalPath);
        dto.setBoardThumbnail(fileNames.toString());
      }

      feedService.updateFeed(dto);
      int boardId = dto.getBoardId();

      map.put("message", "Feed Update successfully");
      map.put("boardId", boardId);
    } catch (Exception e) {
      map.put("message", "Feed Update failed");
    }
    return map;
  }

  // 피드 삭제 (완료)
  // 입력 데이터 : boardId
  // 출력 데이터 : message, boardId
  @DeleteMapping("/deleteFeed")
  public ResponseEntity<Map<String, Object>> deleteFeed(@RequestBody FeedDto feedDto) {
    feedService.deleteFeed(feedDto);
    int boardId = feedDto.getBoardId();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Feed Delete successfully");
    jsonResponse.put("boardId", boardId);
    return ResponseEntity.ok(jsonResponse);
  }


  /** 좋아요 **/
  // 해당 글의 좋아요 수를 가져오기 (완료)
  // 입력 데이터 : boardId
  // 출력 데이터 : message, boardId, LIKE_COUNT
  @GetMapping("/findLikeCount")
  public ResponseEntity<Map<String, Object>> findLikeCount(@RequestBody FeedDto feedDto) {
    int likeCount = feedService.findLikeCount(feedDto);
    int boardId = feedDto.getBoardId();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Feed Update successfully");
    jsonResponse.put("boardId", boardId);
    jsonResponse.put("likeCount", likeCount);
    return ResponseEntity.ok(jsonResponse);
  }

  // 유저가 좋아요 누른지 확인 (완료)
  // 입력 데이터 : userId, boardId
  // 출력 데이터 : message, boardId, userId
  @GetMapping("/checkUserLike")
  public ResponseEntity<Map<String, Object>> checkUserLike(@RequestBody FeedDto feedDto) {
    int checkUserLike = feedService.checkUserLike(feedDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    if (checkUserLike == 0) {
      jsonResponse.put("message", "좋아요를 안눌렀어요");
    } else {
      jsonResponse.put("message", "좋아요를 이미 눌렀어요");
    }
    jsonResponse.put("boardId", feedDto.getBoardId());
    jsonResponse.put("userId", feedDto.getUserId());
    return ResponseEntity.ok(jsonResponse);
  }

  // 좋아요 추가/해제 (완료)
  // 입력 데이터 : userId, boardId
  // 출력 데이터 : message, boardId, userId
  @PostMapping("/pushLike")
  public ResponseEntity<Map<String, Object>> insertLike(@RequestBody FeedDto feedDto) {
    int checkUserLike = feedService.checkUserLike(feedDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    if (checkUserLike == 0) {
      feedService.insertLike(feedDto);
      jsonResponse.put("message", "Like Insert successfully");
    } else {
      feedService.deleteLike(feedDto);
      jsonResponse.put("message", "Like Delete successfully");
    }
    jsonResponse.put("boardId", feedDto.getBoardId());
    jsonResponse.put("userId", feedDto.getUserId());
    return ResponseEntity.ok(jsonResponse);
  }


  /** 북마크 **/
  // 유저가 북마크 누른지 확인 (완료)
  // 입력 데이터 : userId, boardId
  // 출력 데이터 : message, boardId, userId
  @GetMapping("/checkUserBookmark")
  public ResponseEntity<Map<String, Object>> checkUserBookmark(@RequestBody FeedDto feedDto) {
    int checkUserBookmark = feedService.checkUserBookmark(feedDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    if (checkUserBookmark == 0) {
      jsonResponse.put("message", "북마크를 안눌렀어요");
    } else {
      jsonResponse.put("message", "북마크를 이미 눌렀어요");
    }
    jsonResponse.put("boardId", feedDto.getBoardId());
    jsonResponse.put("userId", feedDto.getUserId());
    return ResponseEntity.ok(jsonResponse);
  }

  // 북마크 추가/해제 (완료)
  // 입력 데이터 : userId, boardId
  // 출력 데이터 : message, boardId, userId
  @PostMapping("/pushBookmark")
  public ResponseEntity<Map<String, Object>> insertBookmark(@RequestBody FeedDto feedDto) {
    int checkUserBookmark = feedService.checkUserBookmark(feedDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    if (checkUserBookmark == 0) {
      feedService.insrtBookmark(feedDto);
      jsonResponse.put("message", "Bookmark Insert successfully");
    } else {
      feedService.deleteBookmark(feedDto);
      jsonResponse.put("message", "Bookmark Delete successfully");
    }
    jsonResponse.put("boardId", feedDto.getBoardId());
    jsonResponse.put("userId", feedDto.getUserId());
    return ResponseEntity.ok(jsonResponse);
  }

  // 북마크된 글 목록 가져오기 (완료)
  // 입력 데이터 : userId
  // 출력 데이터 : boardId, boardThumbnail, commentCount
  @GetMapping("/findBookmarkFeedByUserId")
  public ResponseEntity<List<Map<String, Object>>> findBookmarkFeedByUserId(
      @RequestBody FeedDto feedDto) {
    List<FeedDto> findBookmarks = feedService.findBookmarkFeedByUserId(feedDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    List<Map<String, Object>> simplifiedFeedList = new ArrayList<>();
    for (FeedDto findBookmark : findBookmarks) {
      Map<String, Object> simplifiedFeed = new HashMap<>();
      simplifiedFeed.put("boardId", findBookmark.getBoardId());
      simplifiedFeed.put("thumbnail", findBookmark.getBoardThumbnail());
      simplifiedFeed.put("commentCount", findBookmark.getCommentCount());
      simplifiedFeedList.add(simplifiedFeed);
    }
    if (findBookmarks.isEmpty()) {
      Map<String, Object> simplifiedFeed = new HashMap<>();
      simplifiedFeed.put("message", "북마크된 글 목록이 없어요");
      simplifiedFeedList.add(simplifiedFeed);
    }
    return new ResponseEntity<>(simplifiedFeedList, HttpStatus.OK);
  }


  /** 해시태그 **/
  // 해당 글의 해시태그 가져오기 (완료)
  // 입력 데이터 : boardId
  // 출력 데이터 : tagName or message(해시태그가 비어있거나 없는 게시글번호)
  @GetMapping("/findHashtagsByBoardId")
  public ResponseEntity<List<Map<String, Object>>> findHashtagsByBoardId(
      @RequestBody FeedDto feedDto) {
    List<FeedDto> tagNames = feedService.findHashtagsByBoardId(feedDto);
    List<Map<String, Object>> tagNameList = new ArrayList<>();
    for (FeedDto tagName : tagNames) {
      Map<String, Object> tagNameFeed = new HashMap<>();
      tagNameFeed.put("tagName", tagName.getTagName());
      tagNameList.add(tagNameFeed);
    }
    if (tagNameList.isEmpty()) {
      Map<String, Object> tagNameFeed = new HashMap<>();
      tagNameFeed.put("message", "해시태그가 없어요");
      tagNameList.add(tagNameFeed);
      return new ResponseEntity<>(tagNameList, HttpStatus.OK);
    }
    return new ResponseEntity<>(tagNameList, HttpStatus.OK);
  }

  // 해시태그로 글 검색 (완료)
  // 입력 데이터 : tagName
  // 출력 데이터 : BoardList or message(해당 해시태그로 검색된 글이 없음)
  @GetMapping("/findBoardsByTagName")
  public ResponseEntity<List<Map<String, Object>>> findBoardsByTagName(
      @RequestBody FeedDto feedDto) {
    List<FeedDto> findBoardsByTagNames = feedService.findBoardsByTagName(feedDto);
    int boardId = feedDto.getBoardId();
    List<Map<String, Object>> BoardsByTagNameList = new ArrayList<>();
    for (FeedDto findBoardsByTagName : findBoardsByTagNames) {
      Map<String, Object> BoardList = new HashMap<>();
      BoardList.put("BoardList", findBoardsByTagName.getBoardId());
      BoardsByTagNameList.add(BoardList);
    }
    if (BoardsByTagNameList.isEmpty()) {
      Map<String, Object> tagNameFeed = new HashMap<>();
      tagNameFeed.put("message", "해당 해시태그로 검색된 글이 없어요");
      BoardsByTagNameList.add(tagNameFeed);
      return new ResponseEntity<>(BoardsByTagNameList, HttpStatus.OK);
    }
    return new ResponseEntity<>(BoardsByTagNameList, HttpStatus.OK);
  }

}
