package com.nutrimate.nutrimatebackend.mapper.challenge;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;
import com.nutrimate.nutrimatebackend.model.challenge.ChallengeCommentDto;

@Mapper
public interface ChallengeChatMapper {

	
	
	int deleteChat(ChallengeChatDto dto);
     //채팅에 참여해 본적이 있는지 확인
     int findChatMemberbyUseridAndUserRoom(ChallengeChatDto dto);
     //채팅 참여 유저 정보 가지고 오기
     ChallengeChatDto findChatMemberInfoByUserIdAndUserRoom(ChallengeChatDto dto);
	
     
     //챌린지 참여 여부 확인
     int findChallengeMemberByUserId(ChallengeChatDto dto);
	
	//챌린지 계정의 중복 여부 확인
      int findChallengeAccount(ChallengeChatDto dto);
    
    
    //챌린지 계정 생성
    int insertAccountChallengeMemeber(ChallengeChatDto dto);
   
    //채팅 참여 생성
    int insertAccountChatroom(ChallengeChatDto dto);
    
    //채팅 닉네임 아이디 구하기(임시)
    int findUser(ChallengeChatDto message);  
    
    //채팅 저장
    int insertChatMessage(ChallengeChatDto message);
    

    //이전의 모든 메세지를 가져오기
    List<ChallengeChatDto> findAllChatByRoomType(ChallengeChatDto dto);
    
    //챌린지 등수
    List<ChallengeChatDto> findChallengeSuccessList(ChallengeChatDto dto);
    
    //당일 챌린지 성공 횟수
    int findChallengeSuccessCount(ChallengeChatDto dto);
    //챌린지 성공 기록하기
    int insertChallengeSuccessCount(ChallengeChatDto dto);
	
    ///댓글 기록
    int insertComment(ChallengeCommentDto dto);
    //챌린지 댓글 기록
	int insertChallengeComment(ChallengeCommentDto dto);
	
	//챌린지 댓글 가지고 오기
	List<ChallengeCommentDto> findCommentList(ChallengeCommentDto dto);
	
	//챌린지 댓글 수정
	int updateComment(ChallengeCommentDto dto);
	

	//챌린지 댓글 삭제
	int deleteComment(ChallengeCommentDto dto);
	
	//챌린지 유저 nick찾기 
	Map selectUserNick(ChallengeCommentDto dto);

   

	
	
}
