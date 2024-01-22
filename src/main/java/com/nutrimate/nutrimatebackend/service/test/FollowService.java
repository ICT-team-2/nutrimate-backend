package com.nutrimate.nutrimatebackend.service.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.test.TestMapper;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;

@Service
public class FollowService {

  @Autowired
  private TestMapper testMapper;

  public String getTimeByAnnotation() {
    return testMapper.getTimeByAnnotation();
  }

  public List<MemberDto> getAllUser() {
    return testMapper.getAllUser();
  }

}
