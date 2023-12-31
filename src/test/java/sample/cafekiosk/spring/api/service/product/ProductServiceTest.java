package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    void createProduct() {
        // * given
        Product product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product);

        ProductCreateRequest request = ProductCreateRequest.builder()
                        .type(HANDMADE)
                        .name("카푸치노")
                        .sellingStatus(SELLING)
                        .price(5000)
                        .build();

        // * when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // * then
        assertThat(productResponse)
                .extracting("productNumber","type","sellingStatus","name","price")
                .contains("002",HANDMADE, SELLING, "카푸치노",5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber","type","sellingStatus","name","price")
                .containsExactlyInAnyOrder(
                        tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
                        tuple("002",HANDMADE, SELLING, "카푸치노",5000));
    }

    @Test
    @DisplayName(" 상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    void createProductWhenProductIsEmpty() {
        // * given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .name("카푸치노")
                .sellingStatus(SELLING)
                .price(5000)
                .build();

        // * when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // * then
        assertThat(productResponse)
                .extracting("productNumber","type","sellingStatus","name","price")
                .contains("001",HANDMADE, SELLING, "카푸치노",5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber","type","sellingStatus","name","price")
                .containsExactlyInAnyOrder(
                        tuple("001",HANDMADE, SELLING, "카푸치노",5000));
    }

    private Product createProduct(String number, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(number)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}