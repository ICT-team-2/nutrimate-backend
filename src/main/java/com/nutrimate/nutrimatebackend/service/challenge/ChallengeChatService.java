package com.nutrimate.nutrimatebackend.service.challenge;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.nutrimate.nutrimatebackend.mapper.challenge.ChallengeChatMapper;
import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;
import com.nutrimate.nutrimatebackend.model.challenge.ChallengeCommentDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ChallengeChatService {
	
	private final ChallengeChatMapper challengeChatMapper;
	
	public ChallengeChatService(ChallengeChatMapper challengeChatMapper) {
		this.challengeChatMapper = challengeChatMapper;
	}

	
	public int deleteChat(ChallengeChatDto dto) {
		return challengeChatMapper.deleteChat(dto);
	}
	
    //채팅에 참여해 본적이 있는지 확인
    public int countChatMember(ChallengeChatDto dto) {
      return challengeChatMapper.findChatMemberbyUseridAndUserRoom(dto);
    }
    
    //채팅 참여 정보 가지고 오기
    public ChallengeChatDto selectChatMember(ChallengeChatDto dto) {
      return challengeChatMapper.findChatMemberInfoByUserIdAndUserRoom(dto);
    }
    
    //챌린지 한 적 있는지 확인
    public int selectChallenge(ChallengeChatDto dto) {
      return challengeChatMapper.findChallengeMemberByUserId(dto);
    }
    
    //채팅방참여 생성
    public int createAccountChat(ChallengeChatDto dto) {
      return challengeChatMapper.insertAccountChatroom(dto);
    }
    
	
    //챌린지 중복 확인
    public int selectchallengeAccount(ChallengeChatDto dto) {
        return challengeChatMapper.findChallengeAccount(dto);
    }
	
    //챌린지 계정 생성
    @Transactional
    public int createAccountChallenge(ChallengeChatDto dto) {
      try {
          challengeChatMapper.insertAccountChallengeMemeber(dto);
          return challengeChatMapper.insertAccountChatroom(dto);
      } catch (Exception e) {
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
          return -1;
      }
    }
    
    //채팅 저장
    public int chatRoomSave(ChallengeChatDto message) {
      return challengeChatMapper.insertChatMessage(message);
    }
    
    //채팅 닉네임 아이디 구하기(임시)
    public long chatIdSave(ChallengeChatDto message) {
      return challengeChatMapper.findUser(message);
    }
    
    //이전의 모든 메세지를 가져오기
    public List<ChallengeChatDto> getAllPrevMessage(ChallengeChatDto dto) {
      return challengeChatMapper.findAllChatByRoomType(dto);
    }

    //챌린지 등수
    public List<ChallengeChatDto> getChallengeSuccessList(ChallengeChatDto dto) {
      return challengeChatMapper.findChallengeSuccessList(dto);
    }

    //당일 챌린지 성공 횟수
    public int countChallengeSuccess(ChallengeChatDto dto) {
      return challengeChatMapper.findChallengeSuccessCount(dto);
    }

    //챌린지 성공 저장
    public int saveChallengeSuccess(ChallengeChatDto dto) {     
      return challengeChatMapper.insertChallengeSuccessCount(dto);
    }
    
    
    //댓글 저장
    @Transactional
	public int savechallengeComment(ChallengeCommentDto dto) {
        try {
            challengeChatMapper.insertComment(dto);
            return challengeChatMapper.insertChallengeComment(dto);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
	}

    
  //챌린지 댓글 가지고 오기
	public List<ChallengeCommentDto> getChallengeCommentList(ChallengeCommentDto dto) {
		return challengeChatMapper.findCommentList(dto);
	}

	//챌린지 댓글 수정
	public int editChallengeComment(ChallengeCommentDto dto) {
		return challengeChatMapper.updateComment(dto);
	}

	//챌린지 댓글 삭제
	public int deleteChallengeComment(ChallengeCommentDto dto) {
		return challengeChatMapper.deleteComment(dto);
	}

	//챌린지 유저 nick찾기 
	public Map findUserNickByUserId(ChallengeCommentDto dto) {
		return challengeChatMapper.selectUserNick(dto);
	}






	
}
