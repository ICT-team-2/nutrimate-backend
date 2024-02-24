package com.nutrimate.nutrimatebackend.service.profile;

import com.nutrimate.nutrimatebackend.mapper.follow.FollowMapper;
import com.nutrimate.nutrimatebackend.mapper.member.MemberProfileMapper;
import com.nutrimate.nutrimatebackend.model.FileUtils;
import com.nutrimate.nutrimatebackend.model.follow.FollowDto;
import com.nutrimate.nutrimatebackend.model.profile.InfoBoardDto;
import com.nutrimate.nutrimatebackend.model.profile.MemberProfileDto;
import com.nutrimate.nutrimatebackend.model.profile.PagingDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MemberProfileService {
	@Value("${upload-path}")
	private String uploadPath;
	private MemberProfileMapper memberProfileMapper;
	private FollowMapper followMapper;
	
	public MemberProfileService(MemberProfileMapper memberProfileMapper, FollowMapper followMapper) {
		this.memberProfileMapper = memberProfileMapper;
		this.followMapper = followMapper;
	}
	
	//유저의 정보 가져오기
	public MemberProfileDto getMemberProfile(MemberProfileDto dto) {
		MemberProfileDto result = memberProfileMapper.findMemberProfileByUserId(dto);
		FollowDto followDto = new FollowDto();
		followDto.setUserId(result.getUserId());
		result.setFollowingCount(followMapper.findFollowingCount(followDto));//팔로잉 수
		result.setFollowerCount(followMapper.findFollowerCount(followDto));//팔로워 수
		result.setPostCount(memberProfileMapper.findWriteBoardCountByUserId(dto));//작성한 게시글 수
		return result;
	}
	
	public int findWriteInfoBoardCountByUserId(int userId) {
		return memberProfileMapper.findWriteInfoBoardCountByUserId(userId);
	}
	
	public int findBookmarkInfoBoardCountByUserId(int userId) {
		return memberProfileMapper.findBookmarkInfoBoardCountByUserId(userId);
	}
	
	//자신이 작성한 정보공유 게시글 목록 가져오기
	//자신이 북마크한 정보공유 게시글 목록 가져오기
	public List<InfoBoardDto> findInfoBoardList(PagingDto dto) {
		return memberProfileMapper.findInfoBoardListByUserId(dto);
	}
	
	public String changeProfileImage(MemberProfileDto dto, List<MultipartFile> profileImage) throws IOException {
		//파일 업로드
		StringBuffer fileNames = FileUtils.upload(profileImage, uploadPath);
		//DB 업데이트
		dto.setUserProfile(fileNames.toString());
		memberProfileMapper.updateMemberProfileByUserId(dto);
		return fileNames.toString();
	}
	
	//피드는 feedService에서 가져옴
}
