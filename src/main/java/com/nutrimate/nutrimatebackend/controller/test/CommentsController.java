package com.nutrimate.nutrimatebackend.controller.test;

import com.nutrimate.nutrimatebackend.model.member.CommentsDto;
import com.nutrimate.nutrimatebackend.service.test.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    /*
    @GetMapping
    public List<CommentsDto> getAllComments() {
        return commentsService.getAllComments();
    }
    */
    
    @GetMapping("/{boardId}")
    public List<CommentsDto> getCommentsByBoardId(@PathVariable int boardId) {
        return commentsService.getCommentsByBoardId(boardId);
    }
    
    @PostMapping
    public void createComment(@RequestBody CommentsDto commentsDto) {
        commentsService.createComment(commentsDto);
    }

    @PutMapping("/{cmtId}")
    public void updateComment(@PathVariable int cmtId, @RequestBody CommentsDto commentsDto) {
        commentsService.updateComment(cmtId, commentsDto);
    }

    @DeleteMapping("/{cmtId}")
    public void deleteComment(@PathVariable int cmtId) {
        commentsService.deleteComment(cmtId);
    }
    
}
