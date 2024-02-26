package ru.yandex.practicum.filmorate.validators;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.http.HttpMethod;
        import ru.yandex.practicum.filmorate.exceptions.ValidationException;
        import ru.yandex.practicum.filmorate.model.User;

        import java.time.LocalDate;
        import java.util.Map;

class UserValidator {
    private User user;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void validateUserEmailEmpty() {
        user = User.builder()
                .id(1)
                .email("")
                .login("ya")
                .name("Ivan")
                .birthday(LocalDate.of(1986, 11, 15))
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта не может быть пустой.", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserEmailIncorrect() {
        user = User.builder()
                .id(1)
                .email("123ya.ru")
                .login("ya")
                .name("Ivan")
                .birthday(LocalDate.of(1986, 11, 15))
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта должна содержать символ @.", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserLoginEmpty() {
        user = User.builder()
                .id(1)
                .email("123@ya.ru")
                .name("Ivan")
                .birthday(LocalDate.of(1986, 11, 15))
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может быть пустым.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validateUserLoginSpace() {
        user = User.builder()
                .id(1)
                .email("123@ya.ru")
                .login("y a")
                .name("Ivan")
                .birthday(LocalDate.of(1986, 11, 15))
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может содержать пробелы.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validateUserBirthday() {
        user = User.builder()
                .id(1)
                .email("123@ya.ru")
                .login("ya")
                .name("Ivan")
                .birthday(LocalDate.MAX)
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Дата рождения не может быть в будущем.",
                violations.iterator().next().getMessage());
    }
}
