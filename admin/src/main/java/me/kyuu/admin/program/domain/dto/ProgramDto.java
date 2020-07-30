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

package me.kyuu.admin.program.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import me.kyuu.admin.program.api.ProgramApi;
import me.kyuu.admin.program.domain.entity.Program;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author byung-kyu.ju
 * @discription
 */

public class ProgramDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchProgramCondition {
        private Long id;
        private String name;
        private String url;
    }

    @Relation(value = "program", collectionRelation = "programs")
    //@Getter
    @Data
    public static class SearchResponse extends EntityModel<SearchResponse> {
        private Long id;
        private String name;
        private String url;
        private String remark;

        @QueryProjection
        public SearchResponse(Program program) {
            this.id = program.getId();
            this.name = program.getName();
            this.url = program.getUrl();
            this.remark = program.getRemark();
            add(linkTo(methodOn(ProgramApi.class).search(null,null,null)).slash(id).withSelfRel());
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DetailResponse extends EntityModel<DetailResponse> {
        private Long id;
        private String name;
        private String url;
        private String remark;

        public DetailResponse(Program program) {
            this.id = program.getId();
            this.name = program.getName();
            this.url = program.getUrl();
            this.remark = program.getRemark();
            add(linkTo(methodOn(ProgramApi.class).detail(id)).withSelfRel());
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CreateProgramRequest {
        @NotNull
        private String name;
        @NotNull
        private String url;
        private String remark;

    }

    @Getter
    @NoArgsConstructor
    public static class CreateProgramResponse extends EntityModel<CreateProgramResponse> {
        private Long id;
        private String name;
        private String url;
        private String remark;

        public CreateProgramResponse(Program program) {
            this.id = program.getId();
            this.name = program.getName();
            this.url = program.getUrl();
            this.remark = program.getRemark();
            //add(linkTo(methodOn(ProgramApi.class).createProgram(null)).slash(id).withSelfRel());
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UpdateProgramRequest {
        @NotNull
        private String name;
        private String url;
        private String remark;
    }

    @Getter
    public static class UpdateProgramResponse extends EntityModel<UpdateProgramResponse> {
        private Long id;
        private String name;
        private String url;
        private String remark;

        public UpdateProgramResponse(Program program) {
            this.id = program.getId();
            this.name = program.getName();
            this.url = program.getUrl();
            this.remark = program.getRemark();
            //add(linkTo(methodOn(ProgramApi.class).findProgramDetail(id)).withSelfRel());
        }
    }

    public class DeleteProgramRequest {
    }

}
