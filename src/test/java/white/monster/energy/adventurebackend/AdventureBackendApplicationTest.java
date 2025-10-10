package white.monster.energy.adventurebackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Ensures the application-test.properties is used
class AdventureBackendApplicationTest
{

    @Test
    void contextLoads()
    {
    }

}
