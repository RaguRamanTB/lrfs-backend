package devacademy.rt086300.labreportfollowupsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Comments;
import devacademy.rt086300.labreportfollowupsystem.repository.CommentsRepository;
import devacademy.rt086300.labreportfollowupsystem.service.CommentsServiceImpl;

@ExtendWith(MockitoExtension.class)
class CommentsServiceTest {

	@Mock
	CommentsRepository commentsRepository;

	@InjectMocks
	CommentsServiceImpl commentsService;

	@Test
	void getCommentsByPatIdSuccess() throws Exception {
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		Comments comment1 = new Comments(1, 1, "No status found", "No comment found", commentMod);
		Comments comment2 = new Comments(2, 1, "Patient Contacted", "Sent Email to patient", commentMod);
		List<Comments> comments = new ArrayList<Comments>();
		comments.add(comment1);
		comments.add(comment2);
		when(commentsRepository.findByPatId((long) 1)).thenReturn(comments);
		List<Comments> fetchedComments = commentsService.listCommentsByPatId((long) 1);
		assertEquals(fetchedComments, comments);
		verify(commentsRepository, times(1)).findByPatId((long) 1);
	}

	@Test
	void getCommentsByPatIdFailure() throws Exception {
		when(commentsRepository.findByPatId((long) 12345)).thenThrow(new ServerException("Internal Server Error"));
		ServerException se = assertThrows(ServerException.class,
				() -> commentsService.listCommentsByPatId((long) 12345));
		assertEquals("Internal Server Error", se.getMessage());
	}

	@Test
	void deleteCommentByIdSuccess() throws Exception {
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		Comments comment1 = new Comments(1, 1, "No status found", "No comment found", commentMod);
		List<Comments> comments = new ArrayList<Comments>();
		comments.add(comment1);
		commentsRepository.save(comment1);
		commentsRepository.deleteById(comment1.getId());
		assertThat(commentsRepository.count()).isEqualTo(0);
	}
}
