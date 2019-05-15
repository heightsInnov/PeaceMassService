package com.ubn.model;

public class Payload {

	int[] seat_positions;
	String payment_status;
	String transaction_code;
	String phone;
	String description;
	String booking_type;
	String customer_id;
	String pmt_schedule_id;
	int seat_quantity;
	int fare;
	String payment_method;
	String payment_gateway;
	String created_at;
	String updated_at;
	String id;
	
	public int[] getSeat_positions() {
		return seat_positions;
	}
	public void setSeat_positions(int[] seat_positions) {
		this.seat_positions = seat_positions;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public String getTransaction_code() {
		return transaction_code;
	}
	public void setTransaction_code(String transaction_code) {
		this.transaction_code = transaction_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBooking_type() {
		return booking_type;
	}
	public void setBooking_type(String booking_type) {
		this.booking_type = booking_type;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getPmt_schedule_id() {
		return pmt_schedule_id;
	}
	public void setPmt_schedule_id(String pmt_schedule_id) {
		this.pmt_schedule_id = pmt_schedule_id;
	}
	public int getSeat_quantity() {
		return seat_quantity;
	}
	public void setSeat_quantity(int seat_quantity) {
		this.seat_quantity = seat_quantity;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getPayment_gateway() {
		return payment_gateway;
	}
	public void setPayment_gateway(String payment_gateway) {
		this.payment_gateway = payment_gateway;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Payload(String transaction_code) {
		super();
		this.transaction_code = transaction_code;
	}
	public Payload() {
		// TODO Auto-generated constructor stub
	}
}
