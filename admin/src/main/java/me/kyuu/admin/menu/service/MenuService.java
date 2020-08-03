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

package me.kyuu.admin.menu.service;

import lombok.RequiredArgsConstructor;
import me.kyuu.admin.menu.dao.MenuMapper;
import me.kyuu.admin.menu.dao.MenuRepository;
import me.kyuu.admin.menu.domain.dto.MenuDto;
import me.kyuu.admin.menu.domain.entity.Menu;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private ModelMapper modelMapper;

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Transactional(readOnly = true)
    public Page<MenuDto.SearchResponse> search(MenuDto.SearchCondition condition, Pageable pageable) {
        return menuRepository.search(condition, pageable);
    }

    @Transactional(readOnly = true)
    public MenuDto.DetailResponse detail(Long id) {
        return menuRepository.detail(id);
    }

    @Transactional
    public Menu create(Menu menu) {
        return menuRepository.save(menu.create());
    }

    @Transactional
    public MenuDto.DetailResponse update(Long id, Menu updateRequest) {
        MenuDto.DetailResponse detail = menuRepository.detail(id);
        Menu menu = new Menu(detail);
        menu.update(updateRequest);
        return new MenuDto.DetailResponse(menu);
    }

    @Transactional
    public void delete(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow();
        menu.deleteMenu();

    }


    public CollectionModel<EntityModel<MenuDto.LeftMenuResponse>> findLeftMenu() {
        Collection<MenuDto.LeftMenuResponse> collect = menuMapper.findLeftMenu().stream().map(
                menu -> modelMapper.map(menu, MenuDto.LeftMenuResponse.class))
                .collect(Collectors.toList());
        for (MenuDto.LeftMenuResponse response : collect) {
            response.add(Link.of(response.getUrl()).withSelfRel());
        }

        return CollectionModel.wrap(collect);
        //return null;
    }
}
