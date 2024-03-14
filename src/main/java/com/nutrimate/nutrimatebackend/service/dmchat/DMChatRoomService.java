package com.nutrimate.nutrimatebackend.service.dmchat;

import com.nutrimate.nutrimatebackend.mapper.dmchat.DMChatRoomMapper;
import com.nutrimate.nutrimatebackend.model.FileUtils;
import com.nutrimate.nutrimatebackend.model.dmchat.DMChatRoomDto;
import com.nutrimate.nutrimatebackend.model.dmchat.DMChatUserInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Log4j2
public class DMChatRoomService {
	
	DMChatRoomMapper mapper;
	@Value("${upload-path}")
	private String uploadPath;
	
	public DMChatRoomService(DMChatRoomMapper mapper) {
		this.mapper = mapper;
	}
	
	//crud
	public int createChatRoom(DMChatRoomDto dto) {
		try {
			mapper.insertChatRoom(dto);
			mapper.insertMemberChatRoom(dto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("Chatroom creation failed");
		}
		return 1;
	}
	
	@Transactional
	public int createChatRoomPrivate(DMChatRoomDto dto) {
		dto.setChatroomType(DMChatRoomDto.ChatRoomType.PRIVATE);
		dto.setUserIds(List.of(dto.getUserId(), dto.getOpponentId()));
		return createChatRoom(dto);
	}
	
	@Transactional
	public int createChatRoomGroup(DMChatRoomDto dto) {
		dto.setChatroomType(DMChatRoomDto.ChatRoomType.GROUP);
		dto.setChatroomName("그룹 채팅방");
		return createChatRoom(dto);
	}
	
	public int updateChatRoomName(DMChatRoomDto dto) {
		return mapper.updateChatRoomName(dto);
	}
	
	public String updateChatRoomProfile(DMChatRoomDto dto,
	                                    List<MultipartFile> profileImage) throws IOException {
		//파일 업로드
		StringBuffer fileNames = FileUtils.upload(profileImage, uploadPath);
		dto.setChatroomProfile(fileNames.toString());
		mapper.updateChatroomProfile(dto);
		return fileNames.toString();
	}
	
	public int deleteChatRoom(int chatroomId) {
		return mapper.deleteChatRoom(chatroomId);
	}
	
	public List<DMChatRoomDto> findChatRoomsByUserId(int userId) {
		return mapper.findChatRoomsByUserId(userId);
	}
	
	public List<DMChatUserInfo> findUserListBySearchWord(String searchWord) {
		return mapper.findUserListBySearchWord(searchWord);
	}
	public int deleteMemberChatRoom(DMChatRoomDto dto) {
		return mapper.deleteMemberChatRoom(dto);
	}
}
