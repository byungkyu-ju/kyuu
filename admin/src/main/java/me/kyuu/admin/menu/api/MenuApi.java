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

import lombok.extern.slf4j.Slf4j;
import me.kyuu.admin.menu.dto.ProgramDto;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static me.kyuu.admin.menu.dto.ProgramDto.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Slf4j
@RequestMapping(value = "/api/menus")
public class MenuApi {

    @PostMapping(value = "program")
    public ResponseEntity program(@RequestBody @Valid CreateProgramRequest request){
        System.out.println("running...");
        CreateProgramResponse response = new CreateProgramResponse(1234L,"name","url");

        URI createdUri = linkTo((MenuApi.class)).slash("1234").toUri();
        return ResponseEntity.created(createdUri).body(response);
    }

}