package com.dbas.transfer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MoneyTransfer implements EntryPoint {

	// register gui
	private TextBox firstNameBox;
	private TextBox lastNameBox;
	private TextBox ppnBox;
	private Button registerButton;

	// sending gui
	private TextBox sendAmountBox;
	private TextBox sendSenderBox;
	private TextBox sendReceiverBox;
	private ListBox sendSendKioskList;
	private ListBox sendRecKioskList;
	private Button sendButton;

	// receiving gui
	private TextBox recReceiverBox;
	private ListBox recRecKioskList;
	private Button recButton;

	// Create a handler for the registerButton
	class RegisterHandler implements ClickHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			String firstName = firstNameBox.getValue();
			String lastName = lastNameBox.getValue();
			String ppn = ppnBox.getValue();
			if (firstName.equals("") || lastName.equals("") || ppn.equals("")) {
				Window.alert("Illegal input. Please try again.");
				firstNameBox.setFocus(true);
				firstNameBox.selectAll();
				return;
			}

			transferService.registerCustomer(firstName, lastName, ppn,
					new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {
							System.out
									.println("Failure trying to contact server: "
											+ caught.getMessage());
							Window.alert("An error occurred. Please try again.");
						}

						public void onSuccess(Boolean success) {
							if (success) {
								Window.alert("Registration successful.");
								firstNameBox.setText("First Name");
								lastNameBox.setText("Last Name");
								ppnBox.setText("Passport Number");
							} else {
								Window.alert("An error occurred. Please try again.");
							}
						}
					});
			firstNameBox.setFocus(true);
			firstNameBox.selectAll();
		}
	}

	// Create a handler for the sendButton
	class SendHandler implements ClickHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			double sendAmount = 0.0;
			String senderPPN = null, receiverPPN = null;
			String sendKioskAddress = null, recKioskAddress = null;
			try {
				sendAmount = Double.parseDouble(sendAmountBox.getValue());
				senderPPN = sendSenderBox.getValue();
				receiverPPN = sendReceiverBox.getValue();
				sendKioskAddress = getSelectedKioskAdress(sendSendKioskList);
				recKioskAddress = getSelectedKioskAdress(sendRecKioskList);
			} catch (Exception e) {
				Window.alert("Illegal input. Please try again.");
				sendAmountBox.setFocus(true);
				sendAmountBox.selectAll();
				return;
			}

			transferService.sendMoney(sendAmount, senderPPN, receiverPPN,
					sendKioskAddress, recKioskAddress,
					new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {
							System.out
									.println("Failure trying to contact server: "
											+ caught.getMessage());
							Window.alert("An error occurred. Please try again.");
						}

						public void onSuccess(Boolean success) {
							if (success) {
								Window.alert("Money was successfully sent.");
								sendAmountBox.setText("Amount");
								sendSenderBox.setText("Sender Passport Number");
								sendReceiverBox
										.setText("Receiver Passport Number");
								initializeKioskList(sendSendKioskList);
								initializeKioskList(sendRecKioskList);
							} else {
								Window.alert("An error occurred. Please try again.");

							}
						}
					});

			sendAmountBox.setFocus(true);
			sendAmountBox.selectAll();
		}
	}

	// Create a handler for the recButton
	class RecHandler implements ClickHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			String receiverPPN = null;
			String recKioskAddress = null;
			try {
				receiverPPN = recReceiverBox.getValue();
				recKioskAddress = getSelectedKioskAdress(recRecKioskList);
			} catch (Exception e) {
				Window.alert("Illegal input. Please try again.");
				recReceiverBox.setFocus(true);
				recReceiverBox.selectAll();
				return;
			}

			transferService.receiveMoney(receiverPPN, recKioskAddress,
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							System.out
									.println("Failure trying to contact server: "
											+ caught.getMessage());
							Window.alert("An error occurred. Please try again.");
						}

						public void onSuccess(String received) {
							if (received != null) {
								Window.alert(received
										+ " was successfully received.");
								recReceiverBox
										.setText("Receiver Passport Number");
								initializeKioskList(recRecKioskList);
							} else {
								Window.alert("An error occurred. Please try again.");
							}
						}
					});

			recReceiverBox.setFocus(true);
			recReceiverBox.selectAll();
		}
	}

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final ServerServiceAsync transferService = GWT
			.create(ServerService.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		// register gui
		firstNameBox = new TextBox();
		lastNameBox = new TextBox();
		ppnBox = new TextBox();
		registerButton = new Button("Register");

		// sending gui
		sendAmountBox = new TextBox();
		sendSenderBox = new TextBox();
		sendReceiverBox = new TextBox();
		sendSendKioskList = new ListBox();
		sendRecKioskList = new ListBox();
		sendButton = new Button("Send money");

		// receiving gui
		recReceiverBox = new TextBox();
		recRecKioskList = new ListBox();
		recButton = new Button("Recieve money");

		// adjust gui
		firstNameBox.setText("First Name");
		lastNameBox.setText("Last Name");
		ppnBox.setText("Passport Number");

		sendAmountBox.setText("Amount");
		sendSenderBox.setText("Sender Passport Number");
		sendReceiverBox.setText("Receiver Passport Number");
		initializeKioskList(sendSendKioskList);
		initializeKioskList(sendRecKioskList);

		recReceiverBox.setText("Receiver Passport Number");
		initializeKioskList(recRecKioskList);

		// We can add style names to widgets
		// sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("firstName").add(firstNameBox);
		RootPanel.get("lastName").add(lastNameBox);
		RootPanel.get("ppn").add(ppnBox);
		RootPanel.get("registerButton").add(registerButton);

		RootPanel.get("sendAmount").add(sendAmountBox);
		RootPanel.get("sendSender").add(sendSenderBox);
		RootPanel.get("sendReceiver").add(sendReceiverBox);
		RootPanel.get("sendSendKiosk").add(sendSendKioskList);
		RootPanel.get("sendRecKiosk").add(sendRecKioskList);
		RootPanel.get("sendButton").add(sendButton);

		RootPanel.get("recReceiver").add(recReceiverBox);
		RootPanel.get("recRecKiosk").add(recRecKioskList);
		RootPanel.get("recButton").add(recButton);

		// Add a handler to send the name to the server
		registerButton.addClickHandler(new RegisterHandler());
		sendButton.addClickHandler(new SendHandler());
		recButton.addClickHandler(new RecHandler());
	}

	private String getSelectedKioskAdress(ListBox lb) {
		int sel = lb.getSelectedIndex();
		if (sel == 0)
			throw new IllegalArgumentException("Index 0 was selected.");
		String ret = lb.getItemText(sel);
		return ret.substring(0, ret.length() - " (USD)".length());
	}

	private void initializeKioskList(final ListBox lb) {
		lb.setVisibleItemCount(1);
		lb.clear();
		lb.addItem("Select a kiosk");
		transferService.getKioskAddresses(new AsyncCallback<String[]>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failed to retrieve kiosk adresses: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(String[] adresses) {
				for (String adress : adresses)
					lb.addItem(adress);
			}
		});
		lb.setSelectedIndex(0);
	}
}