//package com.nutrimate.nutrimatebackend.controller.board.sport;
//
//import com.nutrimate.nutrimatebackend.model.board.sport.SportCommentsDto;
//import com.nutrimate.nutrimatebackend.service.board.sport.SportCommentsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/comments/sport")
//public class SportCommentsController {
//
//	@Autowired
//	private SportCommentsService sportCommentsService;
//
//    /*
//    @GetMapping
//    public List<CommentsDto> getAllComments() {
//        return commentsService.getAllComments();
//    }
//    */
//
//	@GetMapping("/{boardId}")
//	public List<SportCommentsDto> getCommentsByBoardId(@PathVariable int boardId) {
//		return sportCommentsService.getCommentsByBoardId(boardId);
//	}
//
//	@PostMapping
//	public void createComment(@RequestBody SportCommentsDto sportCommentsDto) {
//		sportCommentsService.createComment(sportCommentsDto);
//	}
//
//	@PutMapping("/{cmtId}")
//	public void updateComment(@PathVariable int cmtId, @RequestBody SportCommentsDto sportCommentsDto) {
//		sportCommentsService.updateComment(cmtId, sportCommentsDto);
//	}
//
//	@DeleteMapping("/{cmtId}")
//	public void deleteComment(@PathVariable int cmtId) {
//		sportCommentsService.deleteComment(cmtId);
//	}
//
//}
