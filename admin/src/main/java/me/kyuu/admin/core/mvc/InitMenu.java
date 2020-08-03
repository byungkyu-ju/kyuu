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

package me.kyuu.admin.core.mvc;

import lombok.RequiredArgsConstructor;
import me.kyuu.admin.menu.domain.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMenu {
    private final InitMenuService initMenuService;

    @PostConstruct
    public void init() {
        initMenuService.init();
    }

    @Component
    static class InitMenuService {

        @Autowired
        private EntityManager entityManager;

        @Transactional
        public void init() {
            Menu root = Menu.builder()
                    .name("root")
                    .url("/")
                    .remark("root")
                    .sortOrder(0)
                    .isValid(true)
                    .build();

            entityManager.persist(root);

            Menu menuManager = Menu.builder()
                    .parentId(root.getId())
                    .name("메뉴관리")
                    .url("/menu")
                    .remark("")
                    .sortOrder(1)
                    .isValid(true)
                    .build();

            entityManager.persist(menuManager);
        }
    }
}
