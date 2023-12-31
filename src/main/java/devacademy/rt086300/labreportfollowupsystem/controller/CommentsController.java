package devacademy.rt086300.labreportfollowupsystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Comments;
import devacademy.rt086300.labreportfollowupsystem.service.CommentsService;

/**
 * This is a restful controller class that controls the data flow of the
 * Comments model.
 * 
 * @author RT086300
 *
 */
@CrossOrigin(origins = "https://lab-report-follow-up-system.vercel.app")
@RestController
@RequestMapping("/api")
public class CommentsController {
	private static final Logger log = LoggerFactory.getLogger(CommentsController.class);
	private CommentsService commentsService;

	/**
	 * A setter function to initialize the commentsService object. This setter is
	 * autowired with @Autowired annotation to inject the object dependency
	 * implicitly.
	 * 
	 * @param commentsService This is the object that is autowired to the object of
	 *                        the CommentsController class
	 */
	@Autowired
	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}

	/**
	 * This function is to fetch comments of a patient with Patient ID. It uses
	 * listCommentsByPatId() method of the commentsService object.
	 * 
	 * This method is a GET request to the route "/comments/{patId}" where id is a
	 * path variable (i.e) Patient ID.
	 * 
	 * @param patId This is the path variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the found list of comments object with HTTP Status 200 (OK). If
	 *         the request failed, throws a ServerException with a message "Internal
	 *         Server Error".
	 */
	@GetMapping("/comments/{patId}")
	public ResponseEntity<List<Comments>> getCommentsByPatId(@PathVariable("patId") long patId) {
		try {
			List<Comments> comments = commentsService.listCommentsByPatId(patId);
			log.info("GET /comments/" + Long.toString(patId) + " 200 OK - Fetched comments by Patient ID successfully");
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /comments/" + Long.toString(patId)
					+ " 500 INTERNAL_SERVER_ERROR - Error in fetching comments by Patient ID");
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This function is to delete a comment of a patient with Comments ID. It uses
	 * deleteComment() method of the commentsService object.
	 * 
	 * This method is a DELETE request to the route "/comments/{commentId}" where
	 * commentId is a path variable (i.e) Comment ID and has parameter of the
	 * Patient ID patId.
	 * 
	 * @param commentId This is the path variable from the route.
	 * @param patId     This is the request param variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the list of comments object with HTTP Status 200 (OK). If the
	 *         request failed, throws a ResourceNotFoundException with a message
	 *         "Resource (Comment/patient) Not Found".
	 */
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<List<Comments>> deleteCommentById(@PathVariable("commentId") long commentId,
			@RequestParam("patId") long patId) {
		try {
			List<Comments> comments = commentsService.deleteComment(commentId, patId);
			log.info("DELETE /comments/" + Long.toString(commentId)
					+ " 200 OK - Deleted comment and fetched all new comments of the patient successfully");
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} catch (Exception e) {
			log.error("DELETE /comments/" + Long.toString(patId)
					+ " 404 NOT_FOUND - Error in fetching comments by Patient ID");
			throw new ResourceNotFoundException("Resource (Comment/patient) Not Found");
		}
	}
}
