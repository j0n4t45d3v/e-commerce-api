package br.com.jonatas.ecommerce.core.usecase.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.core.domain.order.OrderItemDomain;
import br.com.jonatas.ecommerce.gateway.out.order.OrderItemRepositoryGateway;
import br.com.jonatas.ecommerce.gateway.out.order.OrderRepositoryGateway;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SearchOrderUseCaseTest {

  @Mock private OrderRepositoryGateway orderRepositoryGateway;

  @Mock private OrderItemRepositoryGateway orderItemRepositoryGateway;

  @InjectMocks private SearchOrderUseCase searchOrderUseCase;

  private static AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterAll
  static void tearDown() throws Exception {
    closeable.close();
  }

  @Test
  @DisplayName("should return all order")
  void testcase1() {
    var orders = List.of(mock(OrderDomain.class), mock(OrderDomain.class));
    when(this.orderRepositoryGateway.findAll()).thenReturn(orders);
    var result = this.searchOrderUseCase.all();
    assertThat(result).hasSize(2);
    verify(this.orderRepositoryGateway, times(1)).findAll();
  }

  @Test
  @DisplayName("should return all itens in order selected")
  void testcase2() {
    var order = mock(OrderDomain.class);
    var items = List.of(mock(OrderItemDomain.class), mock(OrderItemDomain.class));
    when(this.orderRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(order));
    when(this.orderItemRepositoryGateway.findAllByOrder(anyLong())).thenReturn(items);
    var result = this.searchOrderUseCase.items(1l);
    assertThat(result).hasSize(2);
    verify(this.orderRepositoryGateway, times(1)).findById(anyLong());
    verify(this.orderItemRepositoryGateway, times(1)).findAllByOrder(anyLong());
  }

  @Test
  @DisplayName("should return exception when order selected not exists in method list all items")
  void testcase3() {
    when(this.orderRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
    var thrown =
        Assertions.assertThrows(NotFoundException.class, () -> this.searchOrderUseCase.items(1l));
    assertThat(thrown).hasMessage("Order Not Found");
    verify(this.orderRepositoryGateway, times(1)).findById(anyLong());
    verify(this.orderItemRepositoryGateway, times(0)).findAllByOrder(anyLong());
  }

  @Test
  @DisplayName("should return one order selected")
  void testcase4() {
    var order = mock(OrderDomain.class);
    when(this.orderRepositoryGateway.findById(anyLong())).thenReturn(Optional.of(order));
    var result = this.searchOrderUseCase.byId(1l);
    assertThat(result).isEqualTo(order);
    verify(this.orderRepositoryGateway, times(1)).findById(anyLong());
  }

  @Test
  @DisplayName("should return exception when order selected not exists")
  void testcase5() {
    when(this.orderRepositoryGateway.findById(anyLong())).thenReturn(Optional.empty());
    var thrown =
        Assertions.assertThrows(NotFoundException.class, () -> this.searchOrderUseCase.byId(1l));
    assertThat(thrown).hasMessage("Order Not Found");
    verify(this.orderRepositoryGateway, times(1)).findById(anyLong());
  }
}
