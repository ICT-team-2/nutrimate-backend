package com.nutrimate.nutrimatebackend.model.challenge;

import java.util.Date;
import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Builder
@Setter
@Getter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Alias("ChallengeCommentDto")
public class ChallengeCommentDto {

    private int cmtId;
    // private int userId;
    private String cmtContent;
    // private String challengeNick;
    private String userNick;
    private Date createdDate;
    private int label;
    private int nowPage;
    private String userProfile;

    private String deleted;
    private String cmtDepth;
    private String cmtRef;
    // private String cmtRefWriter; //부모 댓글의 작성자
}
