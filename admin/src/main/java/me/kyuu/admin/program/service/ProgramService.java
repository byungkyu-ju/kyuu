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

package me.kyuu.admin.program.service;

import lombok.RequiredArgsConstructor;
import me.kyuu.admin.core.exception.NoDataException;
import me.kyuu.admin.program.dao.ProgramRepository;
import me.kyuu.admin.program.domain.dto.ProgramDto;
import me.kyuu.admin.program.domain.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;

    @Transactional(readOnly = true)
    public Page<ProgramDto.SearchResponse> search(ProgramDto.SearchCondition condition, Pageable pageable) {
        return programRepository.search(condition, pageable);
    }

    @Transactional(readOnly = true)
    public ProgramDto.DetailResponse detail(Long id) {
        return programRepository.detail(id);
    }

    @Transactional
    public Long create(Program program) {
        programRepository.save(program);
        return program.getId();
    }

    @Transactional
    public ProgramDto.DetailResponse update(Long id, Program updateRequest) {
        ProgramDto.DetailResponse detail = programRepository.detail(id);
        Program program = new Program(detail);
        program.update(updateRequest);
        return new ProgramDto.DetailResponse(program);
    }

    @Transactional
    public void delete(Long id) {
        Program program = programRepository.findById(id).orElseThrow();
        program.deleteProgram();

    }

/*
    @Transactional(readOnly = true)
    public Program findProgramDetail(Long programId) {
        return programRepository.findByIdAndIsValid(programId, true);
    }

    @Transactional
    public Long create(Program program) {
        programRepository.save(program);
        return program.getId();
    }

    @Transactional
    public Program update(Long id, ProgramDto.updateRequest request) {
        Program program = programRepository.findById(id).orElseThrow();
        program.update(new Program(request));
        return program;
    }

    @Transactional
    public void deleteProgram(Long id) {
        Program program = programRepository.findById(id).orElseThrow();
        program.deleteProgram();
    }*/
}
