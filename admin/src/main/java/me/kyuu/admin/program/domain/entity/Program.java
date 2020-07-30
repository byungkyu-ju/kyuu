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

package me.kyuu.admin.program.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyuu.admin.program.domain.dto.ProgramDto;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;
    private String name;
    private String url;
    private String remark;

    private boolean isValid = true;

    public Program(ProgramDto.CreateProgramRequest request) {
        this.name = request.getName();
        this.url = request.getUrl();
        this.remark = request.getRemark();
    }

    public Program(ProgramDto.UpdateProgramRequest request) {
        this.name = request.getName();
        this.url = request.getUrl();
        this.remark = request.getRemark();
    }

    public void updateProgram(Program program) {
        this.name = program.getName();
        this.url = program.getUrl();
        this.remark = program.getRemark();
    }

    public void deleteProgram() {
        this.isValid = false;
    }
}
