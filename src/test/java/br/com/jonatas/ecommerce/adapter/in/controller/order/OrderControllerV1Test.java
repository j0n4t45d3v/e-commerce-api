package br.com.jonatas.ecommerce.adapter.in.controller.order;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.jonatas.ecommerce.core.domain.order.OrderDomain;
import br.com.jonatas.ecommerce.core.domain.order.enums.Status;
import br.com.jonatas.ecommerce.gateway.in.order.AddItemInOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.CreateOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.SearchOrderGateway;
import br.com.jonatas.ecommerce.gateway.in.order.dto.CreateOrderDto;
import br.com.jonatas.ecommerce.gateway.in.order.dto.OrderItemDTO;
import br.com.jonatas.ecommerce.infra.common.exception.NotFoundException;

@ExtendWith({SpringExtension.class})
@WebMvcTest(OrderControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerV1Test  {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private CreateOrderGateway createOrderGateway;
  @MockBean private SearchOrderGateway searchOrderGateway;
  @MockBean private AddItemInOrderGateway addItemInOrderGateway;

  @Test
  @DisplayName("should return created status code when order successfully create")
  void shouldReturnCreatedStatusCodeWhenOrderSuccessfullyCreate() throws Exception {
    var bodyRequest = new CreateOrderDto(100.0, Status.PENDING, null, List.of());
    doNothing().when(this.createOrderGateway).execute(any(CreateOrderDto.class));
    var bodyJson = this.objectMapper.writeValueAsString(bodyRequest);
    var request = post("/v1/orders").contentType(MediaType.APPLICATION_JSON).content(bodyJson);
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data", is("Order Created")));
  }

	@Test
  @DisplayName("should return 'Add item in order' when exists order")
  void shouldReturnAddItemInOrderWhenExistsOrder() throws Exception {
		when(this.addItemInOrderGateway.execute(any(OrderItemDTO.class)))
			.thenReturn(1L);
		var bodyRequest = new OrderItemDTO(1l, 1l, 10);
    var bodyJson = this.objectMapper.writeValueAsString(bodyRequest);
    var request = put("/v1/orders/items")
		.contentType(MediaType.APPLICATION_JSON)
		.content(bodyJson);
;
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data", is("Add item in order")));
  }

	@Test
  @DisplayName("should return 'Order Not Found' in add new item in order when not exists order")
  void shouldReturnOrderNotFoundInAddItemInOrderWhenNotExistsOrder() throws Exception {
		when(this.addItemInOrderGateway.execute(any(OrderItemDTO.class)))
			.thenThrow(new NotFoundException("Order Not Found"));
		var bodyRequest = new OrderItemDTO(1l, 1l, 10);
    var bodyJson = this.objectMapper.writeValueAsString(bodyRequest);
    var request = put("/v1/orders/items")
		.contentType(MediaType.APPLICATION_JSON)
		.content(bodyJson);
;
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error", is("Order Not Found")));
  }
  @Test
  @DisplayName("should return orders registers")
  void shouldReturnOrdersRegisters() throws Exception {
    when(this.searchOrderGateway.all()).thenReturn(List.of());
    var request = get("/v1/orders");
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data", is(List.of())));
  }

  @Test
  @DisplayName("should return order by id when exists order")
  void shouldReturnOrderByIdWhenExistsOrder() throws Exception {
		var mockOrder = mock(OrderDomain.class);
    when(this.searchOrderGateway.byId(anyLong())).thenReturn(mockOrder);
    var request = get("/v1/orders/1");
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id", is(mockOrder.getId().intValue())))
        .andExpect(jsonPath("$.data.total", is(mockOrder.getTotal())))
        .andExpect(jsonPath("$.data.status", is(mockOrder.getStatus())))
        .andExpect(jsonPath("$.data.items", is(mockOrder.getItems())));
  }

	@Test
  @DisplayName("should return not found order by id when order not exists")
  void shouldReturnNotFoundOrderByIdWhenOrderNotExists() throws Exception {
		when(this.searchOrderGateway.byId(anyLong()))
			.thenThrow(new NotFoundException("Order Not Found"));
    var request = get("/v1/orders/1");
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error", is("Order Not Found")));
  }

	@Test
  @DisplayName("should return list order items by id when exists order")
  void shouldReturnListOrderItemsByIdWhenExistsOrder() throws Exception {
		when(this.searchOrderGateway.items(anyLong()))
			.thenReturn(List.of());
    var request = get("/v1/orders/1/items");
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data", is(List.of())));
  }

	@Test
  @DisplayName("should return not found order items by id when not exists order")
  void shouldReturnNotFoundOrderItemsByIdWhenNotExistsOrder() throws Exception {
		when(this.searchOrderGateway.items(anyLong()))
			.thenThrow(new NotFoundException("Order Not Found"));
    var request = get("/v1/orders/1/items");
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error", is("Order Not Found")));
  }
}
