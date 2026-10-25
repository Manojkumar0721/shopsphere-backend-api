package com.shopsphere.mapper;

import com.shopsphere.dto.CategoryCreateRequest;
import com.shopsphere.dto.CategoryResponse;
import com.shopsphere.dto.CategoryUpdateRequest;
import com.shopsphere.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        categoryMapper = new CategoryMapper();
    }

    @Test
    @DisplayName("Should correctly map CategoryCreateRequest to Category entity")
    void shouldMapCreateRequestToEntity() {
        CategoryCreateRequest request = new CategoryCreateRequest(
                "Electronics",
                "Consumer electronic devices and accessories"
        );

        Category entity = categoryMapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo("Electronics");
        assertThat(entity.getDescription()).isEqualTo("Consumer electronic devices and accessories");
        assertThat(entity.getActive()).isTrue();
    }

    @Test
    @DisplayName("Should correctly update Category entity from CategoryUpdateRequest")
    void shouldUpdateEntityFromRequest() {
        Category entity = new Category("Old Name", "Old Desc");

        CategoryUpdateRequest updateRequest = new CategoryUpdateRequest(
                "New Name",
                "New Desc",
                false
        );

        categoryMapper.updateEntityFromRequest(entity, updateRequest);

        assertThat(entity.getName()).isEqualTo("New Name");
        assertThat(entity.getDescription()).isEqualTo("New Desc");
        assertThat(entity.getActive()).isFalse();
    }

    @Test
    @DisplayName("Should correctly map Category entity to CategoryResponse DTO")
    void shouldMapEntityToResponse() {
        Category entity = new Category("Audio", "Audio equipment");
        entity.setId(10L);

        CategoryResponse response = categoryMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.name()).isEqualTo("Audio");
        assertThat(response.description()).isEqualTo("Audio equipment");
        assertThat(response.active()).isTrue();
    }
}
