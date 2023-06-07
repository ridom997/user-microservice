package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.configuration.ControllerAdvisor;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserAgeNotAllowedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @Mock
    private IUserHandler mockPersonHandler;

    private UserRestController userRestControllerUnderTest;

    private MockMvc mockMvc;

    private final String RESPONSE_MESSAGE_KEY_EXPECTED = "message";
    private final String PERSON_CREATED_MESSAGE_EXPECTED =  "Person created successfully";
    private final String RESPONSE_ERROR_MESSAGE_KEY_EXPECTED = "error";

    private final String USER_AGE_NOT_ALLOWED_MESSAGE_EXPECTED = "User age is not valid";

    @BeforeEach
    void setUp() {
        userRestControllerUnderTest = new UserRestController(mockPersonHandler);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestControllerUnderTest).setControllerAdvice(new ControllerAdvisor()).build();
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
    private Map<String,String> jsonToMap(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Map.class);
    }

    private <T> T jsonToObject(String json, Class<T> classToMap) throws JsonProcessingException {
        return new ObjectMapper().readValue(json,classToMap);
    }

    private OwnerRequestDto validOwnerRequestDto(){
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("name");
        ownerRequestDto.setSurname("surname");
        ownerRequestDto.setDniNumber("123");
        ownerRequestDto.setPhone("+12312");
        ownerRequestDto.setBirthday("11-11-1111");
        ownerRequestDto.setMail("adda@as.c");
        ownerRequestDto.setPassword("pass");
        ownerRequestDto.setAddress("add");
        ownerRequestDto.setIdDniType("asda");
        ownerRequestDto.setIdPersonType("asd");
        return ownerRequestDto;
    }

    @Test
    void testSaveOwner_created() throws Exception {
        UserResponseDto userResponseDtoExpected = new UserResponseDto(1L, "name", "surname",
                "adda@.as.c", "+12312", "add", "asda", "123", "asd", "ROLE_OWNER",null);
        when(mockPersonHandler.saveOwner(any(OwnerRequestDto.class))).thenReturn(userResponseDtoExpected);

        MockHttpServletResponse response = mockMvc.perform(post("/user/createOwner")
                        .content(mapToJson(validOwnerRequestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(),response.getStatus()),
                () -> assertEquals(mapToJson(userResponseDtoExpected),response.getContentAsString()),
                () -> verify(mockPersonHandler).saveOwner(any(OwnerRequestDto.class))
        );
    }

    @Test
    void testSaveOwner_failValidationOfPhone() throws Exception {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("name");
        ownerRequestDto.setSurname("surname");
        ownerRequestDto.setDniNumber("123");
        ownerRequestDto.setPhone("+1asdad");
        ownerRequestDto.setBirthday("11-11-1111");
        ownerRequestDto.setMail("adda@as.c");
        ownerRequestDto.setPassword("pass");
        ownerRequestDto.setAddress("add");
        ownerRequestDto.setIdDniType("asda");
        ownerRequestDto.setIdPersonType("asd");

        MockHttpServletResponse response = mockMvc.perform(post("/user/createOwner")
                        .content(mapToJson(ownerRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> {
                    String errorMessage = Objects.requireNonNull(result.getResolvedException()).getMessage();
                    assertTrue(errorMessage.contains("Phone is in bad format"));
                })
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void testSaveOwner_ageIsNotValid() throws Exception {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("name");
        ownerRequestDto.setSurname("surname");
        ownerRequestDto.setDniNumber("123");
        ownerRequestDto.setPhone("+121");
        ownerRequestDto.setBirthday("11-11-2020");
        ownerRequestDto.setMail("adda@as.c");
        ownerRequestDto.setPassword("pass");
        ownerRequestDto.setAddress("add");
        ownerRequestDto.setIdDniType("asda");
        ownerRequestDto.setIdPersonType("asd");
        Map<String, String> expectedResponseBody = Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY_EXPECTED, USER_AGE_NOT_ALLOWED_MESSAGE_EXPECTED);
        doThrow(new UserAgeNotAllowedException()).when(mockPersonHandler).saveOwner(any(OwnerRequestDto.class));


        MockHttpServletResponse response = mockMvc.perform(post("/user/createOwner")
                        .content(mapToJson(validOwnerRequestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus()),
                () -> assertEquals(expectedResponseBody,jsonToMap(response.getContentAsString())),
                () -> verify(mockPersonHandler).saveOwner(any(OwnerRequestDto.class))
        );
    }
}
