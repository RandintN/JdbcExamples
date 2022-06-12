package software.robsoncassiano.learn;

import java.time.LocalDate;

public record User(String name, Integer age, Boolean blocked, LocalDate birthDate) {
}
