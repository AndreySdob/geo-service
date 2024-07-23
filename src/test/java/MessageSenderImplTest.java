import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MessageSenderImplTest {

    @Test
    void sendTest() {

        GeoService geoService = mock(GeoService.class);
        LocalizationService localizationService = mock(LocalizationService.class);
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");

        Location location = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        when(geoService.byIp("172.0.32.11"))
                .thenReturn(location);
        when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");


        String result = messageSender.send(headers);


        verify(geoService).byIp("172.0.32.11");
        verify(localizationService, times(2)).locale(Country.RUSSIA);
        assertEquals("Добро пожаловать", result);
    }
}


