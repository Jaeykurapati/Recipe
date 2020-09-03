package com.application.recipe.controllers;

import com.application.recipe.model.Recipe;
import com.application.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    IndexController indexController;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void testMockMvc() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name(("index")));

    }

    @Test
    void getIndex() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        recipesData.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipesData);
        ArgumentCaptor<Set<Recipe>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String index = indexController.getIndex(model);

        //then
        assertEquals("index",index);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute("recipes",recipeService.getRecipes());
        verify(model, times(1)).addAttribute(eq("recipes"), setArgumentCaptor.capture());
        Set<Recipe> set = setArgumentCaptor.getValue();
        assertEquals(2,set.size());
    }
}