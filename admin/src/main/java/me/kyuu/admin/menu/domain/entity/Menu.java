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

package me.kyuu.admin.menu.domain.entity;

import lombok.*;
import me.kyuu.admin.menu.domain.dto.MenuDto;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "parent_menu_id")
    private Long parentId;

    private String name;
    private String url;
    private String remark;
    private int sortOrder;
    @Builder.Default
    private boolean isValid = true;


    public Menu(MenuDto.CreateRequest request) {
        this.name = request.getName();
        this.url = request.getUrl();
        this.remark = request.getRemark();
    }

    public Menu(MenuDto.UpdateRequest request) {
        this.name = request.getName();
        this.url = request.getUrl();
        this.remark = request.getRemark();
    }

    public Menu(MenuDto.DetailResponse request) {
        this.id = request.getId();
        this.name = request.getName();
        this.url = request.getUrl();
        this.remark = request.getRemark();
    }


    public void update(Menu menu) {
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.remark = menu.getRemark();
    }


    public void deleteMenu() {
        this.isValid = false;
    }

    public Menu create() {
        this.isValid = true;
        return this;
    }
}
