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

package me.kyuu.admin.menu.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kyuu.admin.menu.domain.dto.MenuDto;
import me.kyuu.admin.menu.domain.entity.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author byung-kyu.ju
 * @discription
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuApiTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName(value = "메뉴목록 조회")
    void findMenus() throws Exception {
        mockMvc.perform(get("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("page").exists());
    }

    @Test
    @DisplayName(value = "메뉴목록 조회_컨텐츠타입에러")
    void findMenus_contentTypeError() throws Exception {
        mockMvc.perform(get("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("page").exists());

        //
        mockMvc.perform(get("/api/menus")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("page").exists());
    }

    @Test
    @DisplayName(value = "메뉴 추가")
    void createMenu() throws Exception {
        //given
        MenuDto.CreateRequest createRequest = MenuDto.CreateRequest.builder()
                .name("menu name")
                .url("url")
                .remark("remark")
                .build();

        //when
        ResultActions action = mockMvc.perform(post("/api/menus/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andDo(print());

        //then
        action
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("url").exists())
                .andExpect(jsonPath("_links.self").exists());
    }

    @Test
    @DisplayName(value = "메뉴 수정")
    void updateMenu() throws Exception {

        //given
        Menu menu = Menu.builder()
                .name("menu")
                .url("url")
                .remark("remark")
                .isValid(true)
                .build();

        entityManager.persist(menu);

        MenuDto.UpdateRequest updateRequest = MenuDto.UpdateRequest.builder()
                .name("new menu")
                .url("url")
                .remark("remark")
                .build();

        //when
        menu.update(new Menu(updateRequest));

        ResultActions actions = mockMvc.perform(put("/api/menus/menu/{id}", menu.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateRequest)))
                .andDo(print());
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(updateRequest.getName()));

    }

    @Test
    @DisplayName(value = "메뉴 삭제")
    void deleteMenu() throws Exception {

        //given
        Menu menu = Menu.builder()
                .name("menu")
                .url("url")
                .remark("remark")
                .isValid(true)
                .build();

        entityManager.persist(menu);
        //when
        ResultActions actions = mockMvc.perform(delete("/api/menus/menu/{id}", menu.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        //then
        actions
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName(value = "좌측메뉴")
    void findLeftMenu() throws Exception {
        mockMvc.perform(get("/api/menus/left"))
                .andDo(print());
    }
}