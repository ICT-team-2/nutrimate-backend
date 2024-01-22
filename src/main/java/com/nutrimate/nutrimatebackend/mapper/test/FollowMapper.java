package com.nutrimate.nutrimatebackend.mapper.test;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.nutrimate.nutrimatebackend.model.member.FollowDto;

@Mapper
public interface FollowMapper {

  // userNick으로 userId를 가져오기
  // 입력 데이터 : userNick
  // @Select("SELECT user_id FROM member WHERE user_nick = #{userNick}")
  // int getUserIdByNick(@Param("userNick") String userNick);

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원 가져오기)
  @Select("SELECT * FROM (SELECT * FROM member ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM <= 5")
  List<FollowDto> getRecommendedFollowers();

  // 내가 상대를 팔로우 하는 쿼리문
  @Insert("INSERT INTO follow (follow_id, follower_id, created_date, followee_id) VALUES (SEQ_FOLLOW.NEXTVAL, #{followerId}, SYSDATE, #{followeeId})")
  int followUser(FollowDto followDto);

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기
  @Select("SELECT COUNT(*) FROM follow WHERE follower_id = #{userId}")
  int getFollowingCount(FollowDto userId);

  // 내 팔로워(나를 등록한 사람) 수를 가져오기
  @Select("SELECT COUNT(*) FROM follow WHERE followee_id = #{userId}")
  int getFollowerCount(FollowDto userId);

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기
  @Select("SELECT user_profile, user_nick, user_intro, user_id, MAX(follow_id) AS follow_id "
      + "FROM follow F RIGHT JOIN member M ON M.user_id = F.followee_id "
      + "WHERE M.user_id IN (SELECT DISTINCT followee_id FROM follow WHERE follower_id = #{userId}) "
      + "GROUP BY user_profile, user_nick, user_intro, user_id")
  List<FollowDto> getFollowingList(FollowDto userId);

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기
  @Select("SELECT user_profile, user_nick, user_intro, user_id "
      + "FROM follow F RIGHT JOIN member M ON M.user_id = F.follower_id "
      + "WHERE M.user_id IN (SELECT DISTINCT follower_id FROM follow WHERE followee_id = #{userId})")
  List<FollowDto> getFollowerList(FollowDto userId);

  // 팔로우 유무 확인 (0일시 안누름)
  @Select("SELECT COUNT(*) FROM follow WHERE follower_id = #{userId} AND followee_id = #{followeeId}")
  int checkFollowStatus(FollowDto followDto);

  // 내가 등록한 사람 삭제. 팔로우 취소
  @Delete("DELETE FROM follow WHERE follow_id = #{followId}")
  int unfollowUser(FollowDto followId);

}
