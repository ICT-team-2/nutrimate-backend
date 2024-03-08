package com.nutrimate.nutrimatebackend.mapper.member;

import com.nutrimate.nutrimatebackend.model.board.InfoBoardDto;
import com.nutrimate.nutrimatebackend.model.board.PagingDto;
import com.nutrimate.nutrimatebackend.model.profile.MemberProfileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberProfileMapper {
	
	MemberProfileDto findMemberProfileByUserId(MemberProfileDto dto);
	
	int findWriteBoardCountByUserId(MemberProfileDto dto);
	
	int findWriteInfoBoardCountByUserId(int userId);
	
	int findBookmarkInfoBoardCountByUserId(int userId);
	
	List<InfoBoardDto> findInfoBoardListByUserId(PagingDto dto);
	
	
	int updateMemberProfileByUserId(MemberProfileDto dto);
}
