package stackoverflow.domain.answer.Controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import stackoverflow.domain.answer.controller.AnswerController;
import stackoverflow.domain.answer.dto.AnswerPatchDto;
import stackoverflow.domain.answer.dto.AnswerPostDto;
import stackoverflow.domain.answer.dto.AnswerResponseDto;
import stackoverflow.domain.answer.entity.Answer;
import stackoverflow.domain.answer.mapper.AnswerMapper;
import stackoverflow.domain.answer.service.AnswerService;
import stackoverflow.domain.member.entity.Member;
import stackoverflow.domain.question.entity.Question;


import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stackoverflow.util.ApiDocumentUtils.getRequestPreProcessor;
import static stackoverflow.util.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    public void postAnswerTest() throws Exception {
        // given
        AnswerPostDto answerPostDto = new AnswerPostDto("answer1", 1L, 1L);
        String content = gson.toJson(answerPostDto);

        AnswerResponseDto answerResponseDto =
                new AnswerResponseDto(1L,
                        "answer1",
                        1L,
                        "nickname1",
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now());

        given(mapper.answerPostDtoToAnswer(Mockito.any(AnswerPostDto.class))).willReturn(new Answer());
        Answer mockResultAnswer = new Answer();
        mockResultAnswer.setAnswerId(1L);
        given(answerService.createAnswer(Mockito.any(Answer.class))).willReturn(new Answer());
        given(mapper.answerToAnswerResponse(Mockito.any(Answer.class))).willReturn(answerResponseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/answers")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.content").value(answerPostDto.getContent()))
                .andExpect(jsonPath("$.data.questionId").value(answerPostDto.getQuestionId()))
                .andExpect(jsonPath("$.data.memberId").value(answerPostDto.getMemberId()))
                .andDo(document("post-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("?????? ?????????")
                                )
                        )
                ));
    }

    @Test
    public void patchAnswerTest() throws Exception {
        // given
        AnswerPatchDto answerPatchDto = new AnswerPatchDto(1L, "answer1");
        String content = gson.toJson(answerPatchDto);

        AnswerResponseDto answerResponseDto =
                new AnswerResponseDto(1L,
                        "answer1",
                        1L,
                        "nickname1",
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now());

        given(mapper.answerPatchDtoToAnswer(Mockito.any(AnswerPatchDto.class))).willReturn(new Answer());
        given(answerService.updateAnswer(Mockito.any(Answer.class))).willReturn(new Answer());
        given(mapper.answerToAnswerResponse(Mockito.any(Answer.class))).willReturn(answerResponseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/answers/{answer-id}", answerPatchDto.getAnswerId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.answerId").value(answerPatchDto.getAnswerId()))
                .andExpect(jsonPath("$.data.content").value(answerPatchDto.getContent()))
                .andDo(document("patch-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("answer-id").description("?????? ?????????")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("?????? ?????????")
                                )
                        )
                ));
    }

    @Test
    public void getAnswersTest() throws Exception {
        // given
        int page = 1;
        int size = 10;

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", String.valueOf(page));
        queryParams.add("size", String.valueOf(size));

        Answer answer1 = new Answer();
        answer1.setAnswerId(1L);
        answer1.setContent("answer1");
        answer1.setMember(new Member());
        answer1.setQuestion(new Question());

        Answer answer2 = new Answer();
        answer2.setAnswerId(2L);
        answer2.setContent("answer2");
        answer2.setMember(new Member());
        answer2.setQuestion(new Question());

        Page<Answer> pages = new PageImpl<>(List.of(answer1, answer2),
                PageRequest.of(0, 10, Sort.by("answerId").descending()), 2);

        AnswerResponseDto answerResponseDto1 =
                new AnswerResponseDto(1L,
                        "answer1",
                        1L,
                        "nickname1",
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now());

        AnswerResponseDto answerResponseDto2 =
                new AnswerResponseDto(2L,
                        "answer2",
                        2L,
                        "nickname2",
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now());

        List<AnswerResponseDto> responses = List.of(answerResponseDto1, answerResponseDto2);

        given(answerService.findAnswers(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).willReturn(pages);
        given(mapper.answersToAnswerResponses(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/answers/{question-id}", answerResponseDto1.getQuestionId())
                                .params(queryParams)
                                .accept(MediaType.APPLICATION_JSON)
                );


        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("get-answers",
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("????????? ??????"),
                                        parameterWithName("size").description("????????? ?????????")
                                )
                        ),
                        pathParameters(
                                parameterWithName("question-id").description("?????? ?????????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????").optional(),
                                        fieldWithPath("data[*].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[*].content").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[*].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data[*].nickname").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                        fieldWithPath("data[*].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data[*].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data[*].questionId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ??????"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("??? ???????????? ????????? ????????? ??????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("??? ????????? ??????"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("??? ????????? ??????")
                                )
                        )
                ));
    }

    @Test
    public void deleteAnswerTest() throws Exception {
        // given
        long answerId = 1L;

        // when
        ResultActions actions =
                mockMvc.perform(
                        delete("/answers/{answer-id}", answerId)
                );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-answer",
                        pathParameters(parameterWithName("answer-id").description("?????? ?????????"))
                ));
    }
}
