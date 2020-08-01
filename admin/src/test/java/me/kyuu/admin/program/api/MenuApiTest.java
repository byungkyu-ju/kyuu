/*
 * Copyright 2020 kyuu private project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.kyuu.admin.program.api;

import me.kyuu.admin.program.core.DefaultApiControllerTest;
import me.kyuu.admin.program.domain.dto.ProgramDto;
import me.kyuu.admin.program.domain.entity.Program;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

/**
 * @author byung-kyu.ju
 * @discription
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuApiTest extends DefaultApiControllerTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName(value = "프로그램목록 조회")
    void findPrograms() throws Exception {
        mockMvc.perform(get("/api/programs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("page").exists());
    }

    @Test
    @DisplayName(value = "프로그램 추가")
    void createProgram() throws Exception {
        //given
        ProgramDto.CreateRequest createRequest = ProgramDto.CreateRequest.builder()
                .name("program name")
                .url("url")
                .remark("remark")
                .build();



        //when
        ResultActions action = mockMvc.perform(post("/api/programs/program")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andDo(print());

        //then
        action
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("url").exists())
                .andExpect(jsonPath("_links.self").exists());
    }

    @Test
    @DisplayName(value = "프로그램 수정")
    void updateProgram() throws Exception{

        //given
        Program program = Program.builder()
                .name("program")
                .url("url")
                .remark("remark")
                .isValid(true)
                .build();

        entityManager.persist(program);

        ProgramDto.UpdateRequest updateRequest = ProgramDto.UpdateRequest.builder()
                .name("new program")
                .url("url")
                .remark("remark")
                .build();

        //when
        program.update(new Program(updateRequest));

        ResultActions actions = mockMvc.perform(put("/api/programs/program/{id}", program.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateRequest)))
                .andDo(print());
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(updateRequest.getName()));

    }

    @Test
    @DisplayName(value = "프로그램 삭제")
    void deleteProgram() throws Exception{

        //given
        Program program = Program.builder()
                .name("program")
                .url("url")
                .remark("remark")
                .isValid(true)
                .build();

        entityManager.persist(program);
        //when
        ResultActions actions = mockMvc.perform(delete("/api/programs/program/{id}", program.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        //then
        actions
                .andExpect(status().isNoContent());

    }



}