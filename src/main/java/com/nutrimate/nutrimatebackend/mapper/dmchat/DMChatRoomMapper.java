package com.nutrimate.nutrimatebackend.mapper.dmchat;

import com.nutrimate.nutrimatebackend.model.dmchat.DMChatRoomDto;
import com.nutrimate.nutrimatebackend.model.dmchat.DMChatUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DMChatRoomMapper {
	//crud
	
	int insertChatRoom(DMChatRoomDto dto);
	
	List<DMChatRoomDto> findChatRoomsByUserId(int userId);
	
	int updateChatRoomName(DMChatRoomDto dto);
	
	int updateChatroomProfile(DMChatRoomDto dto);
	
	int deleteChatRoom(int chatroomId);
	
	int insertMemberChatRoom(DMChatRoomDto dto);
	
	List<DMChatUserInfo> findUserInfoByUserId(Map map);
	
	List<DMChatUserInfo> findUserListBySearchWord(String searchWord);
	
	int deleteMemberChatRoom(DMChatRoomDto dto);
}
