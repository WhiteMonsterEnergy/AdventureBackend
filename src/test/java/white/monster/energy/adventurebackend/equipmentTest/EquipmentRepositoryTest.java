// Tests for EquipmentRepository using pre-populated H2 data from data-h2.sql
package white.monster.energy.adventurebackend.equipmentTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import white.monster.energy.adventurebackend.equipment.Equipment;
import white.monster.energy.adventurebackend.equipment.EquipmentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
//
//@Sql(
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
//        scripts = {"classpath:data-h2.sql"}
//)
@DataJpaTest
public class EquipmentRepositoryTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    void testFindByTitle() {
        // Arrange: Data is pre-populated by data-h2.sql

        // Act: Find equipment by title
        Optional<Equipment> found = equipmentRepository.findByTitle("Helmet");

        // Assert: Equipment is found and title matches
        assertTrue(found.isPresent());
        assertEquals("Helmet", found.get().getTitle());
    }

    @Test
    void testFindByBrokenGreaterThan() {
        // Arrange: Data is pre-populated by data-h2.sql

        // Act: Find equipment with broken > 1
        List<Equipment> result = equipmentRepository.findByBrokenGreaterThan(1);

        // Assert: Only "Axe" is returned
        assertEquals(1, result.size());
        assertEquals("Axe", result.get(0).getTitle());
    }

    @Test
    void testFindByAmountLessThanEqual() {
        // Arrange: Data is pre-populated by data-h2.sql

        // Act: Find equipment with amount <= 3
        List<Equipment> result = equipmentRepository.findByAmountLessThanEqual(3);

        // Assert: "Axe" and "Shield" and "Rope" are returned
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Axe")));
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Shield")));
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Rope")));
    }
}
