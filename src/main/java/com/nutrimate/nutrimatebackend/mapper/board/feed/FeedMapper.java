package com.nutrimate.nutrimatebackend.mapper.board.feed;

import com.nutrimate.nutrimatebackend.model.board.feed.FeedDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedMapper {
	
	// 피드 글목록 가져오기
	List<FeedDto> findFeedList(
			@Param("startRow") int startRow,
			@Param("endRow") int endRow,
			@Param("userId") int userId,
			@Param("searchWord") String searchWord,
			@Param("profileUserId") int profileUserId,
			@Param("profile") Boolean profile,
			@Param("bookmark") Boolean bookmark);
	
	int findFeedtotalRecordCount(
			@Param("searchWord") String searchWord,
			@Param("profileUserId") int profileUserId,
			@Param("profile") Boolean profile,
			@Param("bookmark") Boolean bookmark);
	
	// 피드의 상세보기 정보를 가져오기
	FeedDto findFeedDetail(FeedDto feedDto);
	
	// 피드의 조회수를 +1하기
	void updateincreaseViewCount(FeedDto feedDto);
	
	// 피드 작성
	void insertFeed(FeedDto feedDto);
	
	int checkTagId(@Param("tag") String tag);
	
	void insertTag(@Param("tag") String tag);
	
	void insertHashtag(FeedDto feedDto);
	
	// 피드 수정
	void updateFeed(FeedDto feedDto);
	
	void updateHashtag(FeedDto feedDto);
	
	// 피드 삭제
	int deleteFeed(FeedDto feedDto);
	
	
	/** 좋아요 **/
	// 유저가 좋아요 누른지 확인
	int checkUserLike(FeedDto feedDto);
	
	// 해당 글의 좋아요 수를 가져오기
	int findLikeCount(FeedDto feedDto);
	
	// 좋아요 추가
	int insertLike(FeedDto feedDto);
	
	// 좋아요 해제
	int deleteLike(FeedDto feedDto);
	
	
	/** 북마크 **/
	// 유저가 북마크 누른지 확인
	int checkUserBookmark(FeedDto feedDto);
	
	// 북마크 추가
	
	void insertBookmark(FeedDto feedDto);
	
	
	// 북마크 해제
	void deleteBookmark(FeedDto feedDto);
	
	// 북마크된 글 목록 가져오기
	List<FeedDto> findBookmarkFeedByUserId(FeedDto feedDto);
	
	
	/** 해시태그 **/
	// 해당 글의 해시태그 가져오기
	List<String> findHashtagsByBoardId(FeedDto feedDto);
	
	// 해시태그로 글 검색
	List<FeedDto> findBoardsByTagName(FeedDto feedDto);
	
}
