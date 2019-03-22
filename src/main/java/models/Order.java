package models;

public class Order {
    private Integer id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private OrderStatus status;
    private boolean complete = false;

    public enum OrderStatus {
        PLACED("placed"),
        APPROVED("approved"),
        DELIVERED("delivered");

        private String translation;

        OrderStatus(String translation) {
            this.translation = translation;
        }

        public String getTranslation() {
            return translation;
        }
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }
    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }
    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate='" + shipDate + '\'' +
                ", status=" + status.getTranslation() +
                ", complete=" + complete +
                '}';
    }
}