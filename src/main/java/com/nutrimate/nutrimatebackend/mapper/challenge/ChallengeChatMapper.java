package com.nutrimate.nutrimatebackend.mapper.challenge;

import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChallengeChatMapper {
	
	List<ChallengeChatDto> findAllChatByRoomType(ChallengeChatDto dto);
	
	int insertChat(ChallengeChatDto dto);
	
	int deleteChat(ChallengeChatDto dto);
	
	
}
