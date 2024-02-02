package com.nutrimate.nutrimatebackend.service.challenge;

import java.util.List;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.challenge.ChallengeChatMapper;
import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;

@Service
public class ChallengeChatService {
	
	private final ChallengeChatMapper challengeChatMapper;
	
	public ChallengeChatService(ChallengeChatMapper challengeChatMapper) {
		this.challengeChatMapper = challengeChatMapper;
	}
	public List<ChallengeChatDto> getAllPrevMessage(ChallengeChatDto dto) {
		return challengeChatMapper.findAllChatByRoomType(dto);
	}
	
	public int enterChatInUser(ChallengeChatDto dto) {
		
		
		return challengeChatMapper.insertChat(dto);
	}
	
	public int insertChat(ChallengeChatDto dto) {
		
		
		return challengeChatMapper.insertChat(dto);
	}
	
	public int deleteChat(ChallengeChatDto dto) {
		return challengeChatMapper.deleteChat(dto);
	}

	
}
