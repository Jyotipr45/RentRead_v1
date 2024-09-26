package com.crio.rentRead.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.dto.User;
import com.crio.rentRead.models.BookEntity;
import com.crio.rentRead.models.RoleEntity;
import com.crio.rentRead.models.UserEntity;
import com.crio.rentRead.repositories.RoleRepository;

@Component
public class Mapper implements ApplicationContextAware {

    private static ModelMapper modelMapper;

    private static RoleRepository roleRepository;

    private static TypeMap<UserEntity, User> userEntityToUserTypeMap;

    private static TypeMap<User, UserEntity> userToUserEntityTypeMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        modelMapper = applicationContext.getBean(ModelMapper.class);
        roleRepository = applicationContext.getBean(RoleRepository.class);
        userEntityToUserTypeMap = modelMapper.createTypeMap(UserEntity.class, User.class);
        userToUserEntityTypeMap = modelMapper.createTypeMap(User.class, UserEntity.class);

    }

    public static User mapToUser(UserEntity userEntity) {
		userEntityToUserTypeMap.addMappings(
			mapper -> mapper.map(src -> src.getRole().getName(), User::setRole)
		);

		User user = modelMapper.map(userEntity, User.class);
		return user;
    } 

    public static Book mapToBook(BookEntity bookEntity) {
        Book book = modelMapper.map(bookEntity, Book.class);
        return book;
    }

    public static List<Book> mapToBookList(List<BookEntity> bookEntities) {
        List<Book> books = new ArrayList<>();

        for(BookEntity bookEntity : bookEntities) {
            Book book = modelMapper.map(bookEntity, Book.class);
            books.add(book);
        }

        return books;
    }

    public static BookEntity mapToBookEntity(Book book) {
        BookEntity bookEntity = modelMapper.map(book, BookEntity.class);
        return bookEntity;
    }

    public static UserEntity mapToUserEntity(User user) {
		userToUserEntityTypeMap.addMappings(mapper -> mapper.using(fetchRoleEntityConverter).map(User::getRole, UserEntity::setRole));
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		return userEntity;
	}

	private static Converter<String, RoleEntity> fetchRoleEntityConverter = context -> {
        String roleName = context.getSource();
        return roleRepository.findByName(roleName).get();
    };
}
