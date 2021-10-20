package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.List;

import devacademy.rt086300.labreportfollowupsystem.model.Comments;

/**
 * An interface CommentsService, to abstract the methods. The implementations
 * are in CommentsServiceImpl class.
 * 
 * @author RT086300
 *
 */
public interface CommentsService {
	Comments createComment(Comments commentDetails);

	List<Comments> listCommentsByPatId(Long patId);

	List<Comments> deleteComment(Long commentId, Long patId);
}
