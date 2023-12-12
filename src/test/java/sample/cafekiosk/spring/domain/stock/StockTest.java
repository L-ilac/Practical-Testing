package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    private static final Stock stock = Stock.create("001", 1);

    @Test
    @DisplayName("재고의 수량이 요청된 수량보다 적은지 확인한다.")
    void isQuantityLessThanEx() {
        // * given
        int quantity = 2;

        // * when
        boolean result = stock.isQuantityLessThan(quantity);

        // * then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("재고를 요청된 개수만큼 차감할 수 있다.")
    void deductQuantityEx() {
        // * given
        int quantity = 1;

        // * when
        stock.deductQuantity(quantity);

        // * then
        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    @DisplayName("재고의 수량이 요청된 수량보다 적은지 확인한다.")
    void isQuantityLessThan() {
        // * given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // * when
        boolean result = stock.isQuantityLessThan(quantity);

        // * then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("재고를 요청된 개수만큼 차감할 수 있다.")
    void deductQuantity() {
        // * given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // * when
        stock.deductQuantity(quantity);

        // * then
        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    @DisplayName("재고가 요청된 개수보다 적을 경우 재고를 차감할 수 없고 예외가 발생한다.")
    void deductQuantityWrong() {
        // * given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // * when, then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("차감할 재고 수량이 없습니다.");
    }

    @DisplayName("DynamicTest 예시")
    @TestFactory
    Collection<DynamicTest> dynamicTest(){
        return List.of(
                DynamicTest.dynamicTest("예시 테스트1", () ->{

                }),
                DynamicTest.dynamicTest("예시 테스트2", () -> {

                })
        );
    }

    @DisplayName("재고 차감 시나리오")
    @TestFactory
    Collection<DynamicTest> stockDeductionDynamicTest(){
        // * given
        Stock stock = Stock.create("001", 1);

        return List.of(
                DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", () ->{
                    // * given
                    int quantity = 1;

                    // * when
                    stock.deductQuantity(quantity);

                    // * then
                    assertThat(stock.getQuantity()).isZero();
                }),
                DynamicTest.dynamicTest("재고가 요청된 개수보다 적을 경우 재고를 차감할 수 없고 예외가 발생한다.", () -> {
                    // * given
                    int quantity = 1;

                    // * when, then
                    assertThatThrownBy(() -> stock.deductQuantity(quantity))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("차감할 재고 수량이 없습니다.");
                })
        );
    }
}