package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "ShoppingCarts")
public class ShoppingCart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne // Thay từ @OneToOne thành @ManyToOne
    @JoinColumn(name = "variantId", referencedColumnName = "variantId")
    private Variant variant;

    public ShoppingCart() {}

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Variant getVariant() { return variant; }
    public void setVariant(Variant variant) { this.variant = variant; }
}