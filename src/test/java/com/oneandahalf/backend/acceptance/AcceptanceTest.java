package com.oneandahalf.backend.acceptance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.oneandahalf.backend.common.DataClearExtension;
import com.oneandahalf.backend.common.image.ImageUploadClient;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(DataClearExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql("/sql/adminInitialData.sql")
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @MockBean
    protected ImageUploadClient imageUploadClient;

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
    }
}
