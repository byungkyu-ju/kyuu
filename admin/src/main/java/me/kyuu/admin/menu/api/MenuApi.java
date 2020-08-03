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
import me.kyuu.admin.menu.domain.dto.MenuDto;
import me.kyuu.admin.menu.domain.entity.Menu;
import me.kyuu.admin.menu.service.MenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/menus", produces = MediaTypes.HAL_JSON_VALUE)
public class MenuApi {

    private final MenuService menuService;

    @GetMapping("")
    public ResponseEntity<PagedModel<EntityModel<MenuDto.SearchResponse>>> search(@RequestBody(required = false) MenuDto.SearchCondition condition, @Nullable Pageable pageable, PagedResourcesAssembler<MenuDto.SearchResponse> assembler) {
        if (condition == null) condition = new MenuDto.SearchCondition();
        Page<MenuDto.SearchResponse> menus = menuService.search(condition, pageable);
        PagedModel<EntityModel<MenuDto.SearchResponse>> pagedModel = assembler.toModel(menus);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<EntityModel<MenuDto.DetailResponse>> detail(@PathVariable("id") Long id) {
        EntityModel<MenuDto.DetailResponse> detail = menuService.detail(id);
        return ResponseEntity.ok(detail);
    }

    @PostMapping("/menu")
    public ResponseEntity<EntityModel<MenuDto.DetailResponse>> create(@RequestBody @Valid MenuDto.CreateRequest request) {
        Menu savedMenu = menuService.create(new Menu(request));
        EntityModel<MenuDto.DetailResponse> detail = new MenuDto.DetailResponse(savedMenu);
        URI createdUri = linkTo(methodOn(MenuApi.class).detail(savedMenu.getId())).toUri();
        return ResponseEntity.created(createdUri).body(detail);
    }

    @PutMapping("/menu/{id}")
    public ResponseEntity<EntityModel<MenuDto.DetailResponse>> update(@PathVariable("id") Long id,
                                                                      @RequestBody @Valid MenuDto.UpdateRequest request) {
        MenuDto.DetailResponse response = menuService.update(id, new Menu(request));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/menu/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/left")
    public ResponseEntity<CollectionModel<EntityModel<MenuDto.LeftMenuResponse>>> findLeftMenu() {
        CollectionModel<EntityModel<MenuDto.LeftMenuResponse>> leftBar = menuService.findLeftMenu();
        leftBar.add(linkTo(methodOn((MenuApi.class)).findLeftMenu()).withSelfRel());
        return ResponseEntity.ok(leftBar);
    }
}
