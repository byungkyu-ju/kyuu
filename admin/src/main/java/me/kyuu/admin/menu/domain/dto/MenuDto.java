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

package me.kyuu.admin.menu.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import me.kyuu.admin.menu.api.MenuApi;
import me.kyuu.admin.menu.domain.entity.Menu;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author byung-kyu.ju
 * @discription
 */

public class MenuDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchCondition {
        private Long id;
        private String name;
        private String url;
    }

    @Relation(value = "menu", collectionRelation = "menus")
    //@Getter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchResponse extends EntityModel<SearchResponse> {
        private Long id;
        private String name;
        private String url;
        private String remark;
        private int sortOrder;
        private List<Menu> childMenu;

        @QueryProjection
        public SearchResponse(Menu menu) {
            this.id = menu.getId();
            this.name = menu.getName();
            this.url = menu.getUrl();
            this.remark = menu.getRemark();
            this.sortOrder = menu.getSortOrder();
            add(linkTo(methodOn(MenuApi.class).search(null, null, null)).slash(id).withSelfRel());
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DetailResponse extends EntityModel<DetailResponse> {
        private Long id;
        private String name;
        private String url;
        private String remark;
        private int sortOrder;

        @QueryProjection
        public DetailResponse(Menu menu) {
            this.id = menu.getId();
            this.name = menu.getName();
            this.url = menu.getUrl();
            this.remark = menu.getRemark();
            this.sortOrder = menu.getSortOrder();
            add(linkTo(methodOn(MenuApi.class).detail(id)).withSelfRel());
        }
    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CreateRequest {
        @NotNull
        private String name;
        @NotNull
        private String url;
        private String remark;
        private int sortOrder;
    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UpdateRequest {
        @NotNull
        private String name;
        private String url;
        private String remark;
        private int sortOrder;
    }

    @Relation(value = "menus", collectionRelation = "leftMenus")
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class LeftMenuResponse extends EntityModel<LeftMenuResponse> {
        private Long id;
        private Long parentId;
        private String name;
        private String url;
        private int sortOrder;
    }
}
