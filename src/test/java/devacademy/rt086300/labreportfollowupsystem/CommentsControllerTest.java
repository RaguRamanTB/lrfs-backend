package devacademy.rt086300.labreportfollowupsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.fasterxml.jackson.databind.ObjectMapper;

import devacademy.rt086300.labreportfollowupsystem.controller.CommentsController;
import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Comments;
import devacademy.rt086300.labreportfollowupsystem.service.CommentsServiceImpl;

@WebMvcTest(CommentsController.class)
class CommentsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CommentsServiceImpl commentsService;

	@Test
	void getCommentsByPatIdSuccess() throws Exception {
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		Comments comment1 = new Comments(1, 1, "No status found", "No comment found", commentMod);
		Comments comment2 = new Comments(2, 1, "Patient Contacted", "Sent Email to patient", commentMod);
		List<Comments> comments = new ArrayList<Comments>();
		comments.add(comment1);
		comments.add(comment2);
		when(commentsService.listCommentsByPatId((long) 1)).thenReturn(comments);
		this.mockMvc.perform(get("/api/comments/1").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(comments)));
	}

	@Test
	void getCommentsByPatIdFailure() throws Exception {
		when(commentsService.listCommentsByPatId((long) 11234)).thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc.perform(get("/api/comments/11234").contentType("application/json"))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void deleteCommentByIdSuccess() throws Exception {
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		Comments comment1 = new Comments(1, 1, "No status found", "No comment found", commentMod);
		List<Comments> comments = new ArrayList<Comments>();
		comments.add(comment1);
		when(commentsService.deleteComment((long) 2, (long) 1)).thenReturn(comments);
		this.mockMvc.perform(delete("/api/comments/2").contentType("application/json").param("patId", "1"))
				.andExpect(status().isOk()).andExpect(content().string(this.objectMapper.writeValueAsString(comments)));
	}

	@Test
	void deleteCommentByIdFailure() throws Exception {
		when(commentsService.deleteComment((long) 2, (long) 1))
				.thenThrow(new ResourceNotFoundException("Resource (Comment/patient) Not Found"));
		this.mockMvc.perform(delete("/api/comments/2").contentType("application/json").param("patId", "1"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("Resource (Comment/patient) Not Found",
						result.getResolvedException().getMessage()));
	}
	
	@Test
	void deleteCommentByIdParamsFailure() throws Exception {
		this.mockMvc.perform(delete("/api/comments/2").contentType("application/json"))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
		.andExpect(result -> assertEquals("Required long parameter 'patId' is not present",
				result.getResolvedException().getMessage()));
	}
}
