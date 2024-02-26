package ru.yandex.practicum.filmorate.controllers;

        import org.springframework.http.HttpMethod;
        import ru.yandex.practicum.filmorate.model.Film;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.web.bind.annotation.*;
        import ru.yandex.practicum.filmorate.validators.FilmValidator;

        import javax.validation.Valid;
        import java.util.List;
        import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private static int generatorId = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        FilmValidator.validate(film, repository.getAll(), HttpMethod.POST);
        log.info("object " + film + " passed validation. return object");
        repository.add(film);
        return film;
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        int id = film.getId();
        if (!films.containsKey(id)) {
            log.debug("Фильм не найден.");
            throw new ValidationException("Фильм не найден.");
        }
        films.put(film.getId(), film);
        log.debug("Фильм под названием {} обновлен.", film.getName());
        return film;

    }

    @GetMapping
    public List<Film> getFilms() {
        return repository.getAllInList();
    }
}
