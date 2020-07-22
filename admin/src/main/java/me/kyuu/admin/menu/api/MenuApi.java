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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kyuu.admin.core.mvc.DefaultApiController;
import me.kyuu.admin.menu.domain.dto.ProgramDto;
import me.kyuu.admin.menu.domain.entity.Program;
import me.kyuu.admin.menu.service.MenuService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.kyuu.admin.menu.domain.dto.ProgramDto.CreateProgramRequest;
import static me.kyuu.admin.menu.domain.dto.ProgramDto.CreateProgramResponse;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/menus", produces = MediaTypes.HAL_JSON_VALUE)
public class MenuApi extends DefaultApiController {

    private final MenuService menuService;

    @GetMapping("/programs")
    public CollectionModel<EntityModel<ProgramDto.FindProgramsResponse>> programs() {
        List<Program> programs = menuService.findPrograms();
        List<ProgramDto.FindProgramsResponse> collect = programs.stream().map(program -> new ProgramDto.FindProgramsResponse(program))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<ProgramDto.FindProgramsResponse>> collectionModel = CollectionModel.wrap(collect);
        collectionModel.add(linkTo(MenuApi.class)
        .withSelfRel());
        return collectionModel;
    }


    @GetMapping("/programs/{id}")
    public ResponseEntity programDetail(@PathVariable Long id) {
        Optional<Program> findProgram = menuService.findProgramById(id);
        return ResponseEntity.ok().body(findProgram);
    }

}



