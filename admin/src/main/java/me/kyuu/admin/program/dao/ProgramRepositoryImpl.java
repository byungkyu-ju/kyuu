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

package me.kyuu.admin.program.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.kyuu.admin.program.domain.dto.ProgramDto;
import me.kyuu.admin.program.domain.dto.ProgramDto.SearchResponse;
import me.kyuu.admin.program.domain.dto.QProgramDto_SearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static me.kyuu.admin.program.domain.entity.QProgram.program;
import static org.springframework.util.StringUtils.isEmpty;

public class ProgramRepositoryImpl implements ProgramRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ProgramRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public Page<SearchResponse> search(ProgramDto.SearchProgramCondition condition, Pageable pageable) {
        QueryResults<SearchResponse> results = queryFactory
                .select(new QProgramDto_SearchResponse(
                        program))
                .from(program)
                .where(program.isValid.eq(true),
                        idEq(condition.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SearchResponse> contents = results.getResults();
        return new PageImpl<>(contents, pageable, results.getTotal());
    }

    private BooleanExpression idEq(Long id) {
        return isEmpty(id) ? null : program.id.eq(id);
    }
}
