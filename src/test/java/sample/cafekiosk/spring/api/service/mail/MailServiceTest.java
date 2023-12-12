package sample.cafekiosk.spring.api.service.mail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

//    @Spy
    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @Test
    @DisplayName("순수 Mock을 이용한 메일 전송 테스트")
    void sendMail() {
        // * given
//        MailSendClient mailSendClient = mock(MailSendClient.class);
//        MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);
//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        // ! @Mock를 쓰는 경우 -> Mock 객체의 모든 메서드들이 지정된 return 값이나 Default 규칙에 의해 정해진 값을 반환한다. 실제 객체의 함수는 동작하지 않음.
//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);

        // * BDDMockito 사용시 when 대신 given을 사용할 수 있다.
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        // ! @Spy를 쓰는 경우 -> 실제 객체를 기반으로 stubbing을 하기 때문에, Spy 객체는 정확히 명시한 메서드만 지정된 값을 return 하고, 그렇지 않은 메서드들은 정상 동작함.
//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(), anyString(), anyString(), anyString());

//        when(mailSendHistoryRepository.save(any(MailSendHistory.class))).thenReturn();

        // * when
        boolean result = mailService.sendMail("", "", "", "");

        // * then
        verify(mailSendHistoryRepository,times(1)).save(any(MailSendHistory.class));

        assertThat(result).isTrue();
    }
}