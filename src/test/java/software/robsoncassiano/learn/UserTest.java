package software.robsoncassiano.learn;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlunit.assertj.XmlAssert;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;


//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
@Tag("unit")
//@ExtendWith(MockitoExtension.class)
class UserTest {
    @Mock
    User user;

    @BeforeEach
    void setUp() {
        user = new User("Marco", 37, false, LocalDate.now().minusYears(37));
        System.out.println("Setup was called");
    }

    @AfterEach
    void tearDown() {
        user = null;
        System.out.println("Cleanup was called");
    }

    @Test
    void userShouldBeAtLeast18() {
        assertThat(user.age()).isGreaterThanOrEqualTo(18);
    }

    @Test
    void userShouldBeCalledMarco() {
        assertThat(user.name()).startsWith("Marco");
    }

    @Test
    void userShouldBeUnblocked() {
        assertThat(user.blocked())
                .as("User %s should be unblocked", user.name())
                .isFalse();
    }

    @Test
    void jsonShouldHaveValidMethods() {
        assertThatJson(user).isEqualTo("""
                {
                  "name": "Marco",
                  "age": 37,
                  "blocked": false,
                  "birthDate": [1985, 6, 12]
                }
                """);
    }

    @Test
    void Xml_should_be_valid() {
        XmlAssert.assertThat("""
                <a>\s
                    <b attr="abc">
                        </b>
                </a>
                """).nodesByXPath("//a/b/@attr").exist();
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestCreatedThroughCode() {

        return List.of();
    }

    @ParameterizedTest
//    @ValueSource(ints = {20, 50, 80})
//    @EnumSource()
    @CsvFileSource(resources = "/friends.csv", numLinesToSkip = 1)
    void all_friends_should_be_at_least_18(String name, int age) {
        assertThat(age).isGreaterThanOrEqualTo(18);
    }
}
