package com.example.thawaq.Service;


import com.example.thawaq.Api.ApiException;
import com.example.thawaq.Model.Branch;
import com.example.thawaq.Model.Category;
import com.example.thawaq.Model.Menu;
import com.example.thawaq.Repository.BranchRepository;
import com.example.thawaq.Repository.CategoryRepository;
import com.example.thawaq.Repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {
private final MenuRepository menuRepository;
private final CategoryRepository categoryRepository;
private final BranchRepository branchRepository;

public List<Menu> getAllMenus() {
    if (menuRepository.findAll().isEmpty()) {
        throw new ApiException("Database is empty");
    }
        return menuRepository.findAll();
    }
   public void addMenu(Menu menu,Integer CategoryId,Integer branchId) {
    Category category = categoryRepository.findCategoryById(CategoryId);
    if (category==null){
        throw new ApiException("can not add menu");
    }
    Branch branch = branchRepository.findBranchById(branchId);
    if (branch==null){
        throw new ApiException("can not add menu");
    }
    menu.setBranch(branch);
    menu.setCategory(category);
    menuRepository.save(menu);


   }
    public void updateMenu(Menu menu,Integer MenuId,Integer CategoryId) {
        Menu menu1 = menuRepository.findMenuById(MenuId);
        Category category = categoryRepository.findCategoryById(CategoryId);
        if (menu1 == null ||category==null){
            throw new ApiException("can not add menu");
        }
        menu1.setName(menu1.getName());
        menu1.setDescription(menu1.getDescription());
        menu1.setPrice(menu1.getPrice());
        menu1.setMenuImage(menu1.getMenuImage());

        menu.setCategory(category);
        menuRepository.save(menu);
        menuRepository.save(menu1);


    }
    public void deleteMenu(Integer MenuId) {
    Menu menu = menuRepository.findMenuById(MenuId);
    if (menu==null){
        throw new ApiException("can not delete menu");
    }
    menuRepository.delete(menu);
    }

    //Find dish by price range min and max (Jana) v2
    public List<Menu> findDishesByPriceRange(double minPrice, double maxPrice) {
        List <Menu> m = menuRepository.findDishesByPriceBetween(minPrice, maxPrice);
        if (m.isEmpty()){
            return null;}
        return m;}

}
