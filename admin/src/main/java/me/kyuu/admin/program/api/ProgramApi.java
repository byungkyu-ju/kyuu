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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kyuu.admin.program.domain.dto.ProgramDto;
import me.kyuu.admin.program.domain.entity.Program;
import me.kyuu.admin.program.service.ProgramService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/programs", produces = MediaTypes.HAL_JSON_VALUE)
public class ProgramApi {

    private final ProgramService programService;

    @GetMapping("")
    public ResponseEntity<CollectionModel<EntityModel<ProgramDto.FindProgramsResponse>>> programs() {
        List<Program> programs = programService.findPrograms();
        List<ProgramDto.FindProgramsResponse> collect = programs.stream().map(program -> new ProgramDto.FindProgramsResponse(program))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<ProgramDto.FindProgramsResponse>> collectionModel = CollectionModel.wrap(collect)
                .add(linkTo(ProgramApi.class)
                        .withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/program/{id}")
    public ResponseEntity<EntityModel<ProgramDto.FindProgramResponse>> findProgramDetail(@PathVariable("id") Long id) {
        Program program = programService.findProgramDetail(id);
        ProgramDto.FindProgramResponse createdResponse = new ProgramDto.FindProgramResponse(program);
        return ResponseEntity.ok().body(createdResponse);
    }


    @PostMapping("/program")
    public ResponseEntity<EntityModel<ProgramDto.CreateProgramResponse>> createProgram(@RequestBody @Valid ProgramDto.CreateProgramRequest request) {
        Long programId = programService.createProgram(new Program(request));
        Program savedProgram = programService.findProgramDetail(programId);
        ProgramDto.CreateProgramResponse createdResponse = new ProgramDto.CreateProgramResponse(savedProgram);
        URI createdUri = linkTo(methodOn(ProgramApi.class).programs()).slash(createdResponse.getId()).withSelfRel().toUri();
        return ResponseEntity.created(createdUri).body(createdResponse);
    }

    @PutMapping("/program/{id}")
    public EntityModel<ProgramDto.UpdateProgramResponse> updateProgram(@PathVariable("id") Long id,
                                                                       @RequestBody @Valid ProgramDto.UpdateProgramRequest request) {
        Program program = programService.updateProgram(id, request);
        return new ProgramDto.UpdateProgramResponse(program);
    }

    @DeleteMapping("/program/{id}")
    public ResponseEntity deleteProgram(@PathVariable("id") Long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }
}
