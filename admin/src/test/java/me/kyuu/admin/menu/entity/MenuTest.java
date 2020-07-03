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

package me.kyuu.admin.menu.entity;

import me.kyuu.admin.menu.core.DefaultTest;
import me.kyuu.admin.menu.dao.MenuRepository;
import me.kyuu.admin.menu.dto.MenuDto.CreateMenuRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author byung-kyu.ju
 * @discription
 */
class MenuTest extends DefaultTest {

    @Autowired
    MenuRepository menuRepository;

    @DisplayName("메뉴생성 테스트")
    @Test
    void create_menu_test() {
        //given
        CreateMenuRequest createMenuRequest = CreateMenuRequest.builder()
                .upMenuId(0L)
                .name("menuName")
                .sortOrder(1)
                .build();
        Menu menu = new Menu(createMenuRequest);
        //when
        Menu savedMenu = menuRepository.save(menu);
        Optional<Menu> findMenu = menuRepository.findById(savedMenu.getId());
        //then
        assertThat(menu.getId()).isEqualTo(findMenu.get().getId());
    }



}