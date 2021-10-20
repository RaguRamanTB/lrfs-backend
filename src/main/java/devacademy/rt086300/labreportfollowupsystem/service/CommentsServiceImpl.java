package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Comments;
import devacademy.rt086300.labreportfollowupsystem.repository.CommentsRepository;

/**
 * This is a service implementation class of the Comments service, that uses
 * CommentsRepository to execute database queries in the Comments Entity.
 * 
 * @author RT086300
 *
 */
@Service
public class CommentsServiceImpl implements CommentsService {

	/**
	 * CommentsRepository Interface that extends JPARepository with Comments entity.
	 * This interface is autowired with @Autowired annotation to inject the object
	 * dependency implicitly.
	 */
	@Autowired
	private CommentsRepository commentsRepository;

	/**
	 * This method is used to return the List of Comments of a specific patient
	 * using findByPatId() method of the CommentsRepository Interface.
	 * 
	 * @param patId Patient ID whose comments are to be fetched.
	 * @return List<Comments> If the JPA repository method is successful, else
	 *         throws ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<Comments> listCommentsByPatId(Long patId) {
		try {
			return (List<Comments>) commentsRepository.findByPatId(patId);
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to create a comment for a specific patient using save()
	 * method of the CommentsRepository Interface.
	 * 
	 * @param commentDetails Comments object that has the details of the comments
	 *                       that are to be inserted into the Comments Table.
	 * @return Comments If saved successfully, it returns the created Comments
	 *         object. If validation fails, it throws ConstraintViolationException,
	 *         since validation is done in Request Params.
	 */
	@Override
	public Comments createComment(Comments commentDetails) {
		return commentsRepository.save(new Comments(commentDetails.getPatId(), commentDetails.getSTATUS(),
				commentDetails.getCOMMENT(), commentDetails.getCOMMENTED_AT()));
	}

	/**
	 * This method is used to delete a comment of a specific patient using
	 * deleteById() method of the CommentsRepository Interface.
	 * 
	 * @param commentId ID of the comment that has to be deleted
	 * @param patId     Patient ID whose comments are to be returned
	 * @return List<Comments> If saved successfully, it returns the created Comments
	 *         object. If validation fails, it throws ResourceNotFoundException,
	 *         with the message "Resource (Comment/patient) Not Found".
	 */
	@Override
	public List<Comments> deleteComment(Long commentId, Long patId) {
		try {
			commentsRepository.deleteById(commentId);
			return (List<Comments>) commentsRepository.findByPatId(patId);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Resource (Comment/patient) Not Found");
		}
	}

}
