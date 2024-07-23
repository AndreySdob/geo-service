import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;


public class GeoServiceImplTest {

    @ParameterizedTest
    @CsvSource({
            "127.0.0.1, , , , 0",
            "172.0.32.11, Moscow, RUSSIA, Lenina, 15",
            "96.44.183.149, New York, USA, 10th Avenue, 32",
            "172.123.12.19, Moscow, RUSSIA, , 0",
            "96.44.12.12, New York, USA, , 0",
            "10.0.0.1, , , , 0"
    })
    void byIpTest(String ip, String city, String country, String street, int building) {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location location = geoService.byIp(ip);
        if (location != null) {
            assertEquals(city, location.getCity(), "Unexpected city for IP: " + ip);
            assertEquals(country == null ? null : Country.valueOf(country), location.getCountry(), "Unexpected country for IP: " + ip);
            assertEquals(street, location.getStreet(), "Unexpected street for IP: " + ip);
            assertEquals(building, location.getBuiling(), "Unexpected building for IP: " + ip);
        } else {
            assertNull(location, "Location should be null for IP: " + ip);
        }
    }

    @Test
    void byCoordinatesTest() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        assertThrows(RuntimeException.class, () -> geoService.byCoordinates(0.0, 0.0));
    }

    @Test
    void localeTest() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        assertEquals("Добро пожаловать", localizationService.locale(Country.RUSSIA));
        assertEquals("Welcome", localizationService.locale(Country.USA));
        assertEquals("Welcome", localizationService.locale(Country.GERMANY));
        assertEquals("Welcome", localizationService.locale(Country.BRAZIL));
    }

}





